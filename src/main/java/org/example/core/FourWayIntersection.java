package org.example.core;

import static org.example.utils.GeographicDirections.EAST;
import static org.example.utils.GeographicDirections.NORTH;
import static org.example.utils.GeographicDirections.SOUTH;
import static org.example.utils.GeographicDirections.WEST;
import static org.example.utils.GeographicDirections.getGeographicDirectionWithNumber;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.example.utils.Direction;
import org.example.utils.LightColor;

/**
 * Represents a four-way intersection, with two entry lanes in North and South roads and a single
 * entry lane in East and West roads.
 *
 * <p>This class extends {@link Intersection} and implements specific behaviour of {@code step}
 * method, as well as predefined intersection object initialization.
 */
public class FourWayIntersection extends Intersection {

  public FourWayIntersection(String id, Set<Road> roads) {
    super(id, roads);
  }

  /** Initializes the intersection by registering lanes and setting initial traffic lights. */
  public void intersectionInit() {
    List<String> initialSequence = TrafficLightSequenceManager.getNextSequence();
    for (Road road : roads) {
      for (Lane lane : road.startLanes()) {
        TrafficLightSequenceManager.registerLane(lane);
        if (initialSequence.contains(lane.getId())) {
          lane.setTrafficLightColor(LightColor.GREEN);
        }
      }
    }
  }

  /**
   * Provides a specific implementation of vehicles movement through the intersection
   *
   * <p>Vehicles in lanes with green lights proceed. Left-turn conflicts between {@code east1} and
   * {@code west1} lanes are resolved independently. Traffic lights are updated if the step counter
   * reaches its limit.
   *
   * @return a list of vehicle IDs, which have left the intersection in a given step
   */
  @Override
  public List<String> step() {
    List<String> vehiclesThatLeft = new ArrayList<>();

    processEastWestConflicts(vehiclesThatLeft);

    for (Road road : roads) {
      for (Lane lane : road.startLanes()) {
        if (lane.getTrafficLightColor() == LightColor.GREEN) {
          if (!lane.getId().equals(getGeographicDirectionWithNumber(EAST, 1))
              && !lane.getId().equals(getGeographicDirectionWithNumber(WEST, 1))) {
            processCarRelease(lane, vehiclesThatLeft);
          }
        }
      }
    }

    TrafficLightSequenceManager.stepCounter++;
    if (TrafficLightSequenceManager.stepCounter >= TrafficLightSequenceManager.STEP_COUNTER_LIMIT) {
      TrafficLightSequenceManager.updateTrafficLights();
    }

    vehiclesThatLeft.sort(String::compareTo);

    return vehiclesThatLeft;
  }

  /**
   * Method used to resolve left-turn conflicts between {@code east1} and {@code west1} lanes. Every
   * case is checked to prevent collisions as well as deadlocks from occurring.
   *
   * @param vehiclesThatLeft a list of vehicle IDs, which have left the intersection in a given step
   */
  private void processEastWestConflicts(List<String> vehiclesThatLeft) {
    Lane east1Lane =
        TrafficLightSequenceManager.getLaneMap().get(getGeographicDirectionWithNumber(EAST, 1));
    Lane west1Lane =
        TrafficLightSequenceManager.getLaneMap().get(getGeographicDirectionWithNumber(WEST, 1));

    if (east1Lane != null && west1Lane != null) {
      Car eastCar = east1Lane.getQueue().peek();
      Car westCar = west1Lane.getQueue().peek();

      if (eastCar != null && westCar != null) {
        boolean eastTurningLeft = eastCar.direction().equals(Direction.LEFT);
        boolean westTurningLeft = westCar.direction().equals(Direction.LEFT);

        if (eastTurningLeft && !westTurningLeft) {
          processCarRelease(west1Lane, vehiclesThatLeft);
        } else if (westTurningLeft && !eastTurningLeft) {
          processCarRelease(east1Lane, vehiclesThatLeft);
        } else if (eastTurningLeft) {
          processCarRelease(west1Lane, vehiclesThatLeft);
        }
      } else if (eastCar == null) {
        processCarRelease(west1Lane, vehiclesThatLeft);
      } else {
        processCarRelease(east1Lane, vehiclesThatLeft);
      }
    }
  }

  /**
   * Creates and initializes a {@link FourWayIntersection} object.
   *
   * @param id intersection's unique identifier
   * @return initialized instance of {@link FourWayIntersection} object
   */
  public static FourWayIntersection createFourWayIntersection(String id) {
    Road southRoad = createSouthRoad();
    Road westRoad = createWestRoad();
    Road northRoad = createNorthRoad();
    Road eastRoad = createEastRoad();

    FourWayIntersection fourWayIntersection =
        new FourWayIntersection(id, Set.of(southRoad, westRoad, northRoad, eastRoad));
    fourWayIntersection.intersectionInit();

    return fourWayIntersection;
  }

  /**
   * Creates and returns the South road with its lanes.
   *
   * @return A new instance of {@link Road} representing the South road.
   */
  private static Road createSouthRoad() {
    return new Road(
        SOUTH,
        Set.of(
            new Lane(
                getGeographicDirectionWithNumber(SOUTH, 1),
                Set.of(WEST),
                new ArrayDeque<>(),
                LightColor.RED),
            new Lane(
                getGeographicDirectionWithNumber(SOUTH, 2),
                Set.of(NORTH, EAST),
                new ArrayDeque<>(),
                LightColor.RED)),
        Set.of(WEST, NORTH, EAST));
  }

  /**
   * Creates and returns the West road with its lanes.
   *
   * @return A new instance of {@link Road} representing the West road.
   */
  private static Road createWestRoad() {
    return new Road(
        WEST,
        Set.of(
            new Lane(
                getGeographicDirectionWithNumber(WEST, 1),
                Set.of(NORTH, EAST, SOUTH),
                new ArrayDeque<>(),
                LightColor.RED)),
        Set.of(NORTH, EAST, SOUTH));
  }

  /**
   * Creates and returns the North road with its lanes.
   *
   * @return A new instance of {@link Road} representing the North road.
   */
  private static Road createNorthRoad() {
    return new Road(
        NORTH,
        Set.of(
            new Lane(
                getGeographicDirectionWithNumber(NORTH, 1),
                Set.of(EAST),
                new ArrayDeque<>(),
                LightColor.RED),
            new Lane(
                getGeographicDirectionWithNumber(NORTH, 2),
                Set.of(SOUTH, WEST),
                new ArrayDeque<>(),
                LightColor.RED)),
        Set.of(EAST, SOUTH, WEST));
  }

  /**
   * Creates and returns the East road with its lanes.
   *
   * @return A new instance of {@link Road} representing the East road.
   */
  private static Road createEastRoad() {
    return new Road(
        EAST,
        Set.of(
            new Lane(
                getGeographicDirectionWithNumber(EAST, 1),
                Set.of(SOUTH, WEST, NORTH),
                new ArrayDeque<>(),
                LightColor.RED)),
        Set.of(SOUTH, WEST, NORTH));
  }
}
