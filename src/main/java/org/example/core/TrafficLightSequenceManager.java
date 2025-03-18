package org.example.core;

import static org.example.utils.GeographicDirections.EAST;
import static org.example.utils.GeographicDirections.NORTH;
import static org.example.utils.GeographicDirections.SOUTH;
import static org.example.utils.GeographicDirections.WEST;
import static org.example.utils.GeographicDirections.getGeographicDirectionWithNumber;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import org.example.utils.LightColor;

/**
 * Class responsible for management of the entire traffic lights simulation. It initializes and
 * manages the traffic lights sequences. handles updates of the traffic lights as well as calculates
 * the priority of the lanes to make the traffic flow more efficient.
 */
public final class TrafficLightSequenceManager {

  /** The predefined traffic light sequences for the intersection. */
  public static final LinkedList<List<String>> SEQUENCES = loadTrafficSequences();

  /** Maximum number of steps that can be executed during single traffic lights sequence. */
  public static final int STEP_COUNTER_LIMIT = 3;

  /**
   * Counter that tracks the number of simulation steps that were executed during current sequence.
   */
  public static int stepCounter = 0;

  /** Iterator that manages the cycling through the predefined sequences. */
  private static ListIterator<List<String>> sequenceIterator = SEQUENCES.listIterator();

  /** Currently active traffic lights sequence. */
  private static List<String> currentSequence = null;

  /** A map storing lanes by their unique IDs for easier access. */
  private static Map<String, Lane> laneMap = new HashMap<>();

  /**
   * Loads the predefined traffic light sequences for different lanes. The sequences define which
   * lanes have green lights at any given step.
   *
   * @return a linked list of traffic light sequences
   */
  private static LinkedList<List<String>> loadTrafficSequences() {
    LinkedList<List<String>> sequences = new LinkedList<>();
    sequences.add(
        List.of(
            getGeographicDirectionWithNumber(SOUTH, 2),
            getGeographicDirectionWithNumber(NORTH, 2)));
    sequences.add(
        List.of(
            getGeographicDirectionWithNumber(SOUTH, 1),
            getGeographicDirectionWithNumber(NORTH, 1)));
    sequences.add(
        List.of(
            getGeographicDirectionWithNumber(EAST, 1), getGeographicDirectionWithNumber(WEST, 1)));
    return sequences;
  }

  /**
   * Retrieves the next traffic light sequence. If the sequence iterator reaches the end, it is
   * cycled back to the beginning.
   *
   * @return the list of lanes that will have a green light during the next sequence
   */
  public static List<String> getNextSequence() {
    if (!sequenceIterator.hasNext()) {
      sequenceIterator = SEQUENCES.listIterator();
    }
    currentSequence = sequenceIterator.next();
    return currentSequence;
  }

  public static Map<String, Lane> getLaneMap() {
    return laneMap;
  }

  /**
   * Registers the given lane in the system.
   *
   * @param lane the lane to be registered
   */
  public static void registerLane(Lane lane) {
    laneMap.put(lane.getId(), lane);
  }

  /**
   * Updates the traffic light sequence based on step limits as well as vehicle presence. By
   * default, the sequence changes when the step counter exceeds its limit. Additionally, the
   * sequence can be sped up, when there are a lot of cars waiting in the queue on others lanes.
   */
  public static void updateTrafficLights() {
    int attempts = 0;

    while (attempts < SEQUENCES.size()) {
      if (stepCounter >= STEP_COUNTER_LIMIT) {
        changeLightsColor(currentSequence, LightColor.RED);
        currentSequence = getNextSequence();
        changeLightsColor(currentSequence, LightColor.GREEN);
        stepCounter = 0;
      }

      boolean hasWaitingCars =
          currentSequence.stream()
              .map(laneMap::get)
              .filter(Objects::nonNull)
              .anyMatch(lane -> !lane.getQueue().isEmpty());

      if (hasWaitingCars) {
        break;
      }

      changeLightsColor(currentSequence, LightColor.RED);
      prioritizeTrafficSequence();
      changeLightsColor(currentSequence, LightColor.GREEN);
      attempts++;
    }
  }

  /**
   * Prioritizes the sequence with the most waiting cars. To improve traffic efficiency, dynamically
   * selects the sequence with the highest number of vehicles waiting.
   */
  private static void prioritizeTrafficSequence() {
    List<String> sequenceWithMostTrafficOnLanes = null;
    int maxCarsWaiting = Integer.MIN_VALUE;

    for (List<String> sequence : SEQUENCES) {
      int totalWaitingCars =
          sequence.stream()
              .map(laneMap::get)
              .filter(Objects::nonNull)
              .mapToInt(lane -> lane.getQueue().size())
              .sum();

      if (totalWaitingCars > maxCarsWaiting) {
        maxCarsWaiting = totalWaitingCars;
        sequenceWithMostTrafficOnLanes = sequence;
      }
    }

    if (sequenceWithMostTrafficOnLanes == null || maxCarsWaiting == 0) {
      sequenceWithMostTrafficOnLanes = getNextSequence();
    }

    if (sequenceWithMostTrafficOnLanes != null) {
      currentSequence = sequenceWithMostTrafficOnLanes;
      sequenceIterator = SEQUENCES.listIterator(SEQUENCES.indexOf(sequenceWithMostTrafficOnLanes));
    }
  }

  /**
   * Changes the light color on every lane from provided sequence.
   *
   * @param sequence the lanes whose lights should be changed
   * @param color the new traffic light color ({@code GREEN} or {@code RED})
   */
  private static void changeLightsColor(List<String> sequence, LightColor color) {
    if (sequence == null) return;
    for (String laneId : sequence) {
      Lane lane = laneMap.get(laneId);
      if (lane != null) {
        lane.setTrafficLightColor(color);
      }
    }
  }
}
