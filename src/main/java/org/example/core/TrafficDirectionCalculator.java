package org.example.core;

import static org.example.utils.GeographicDirections.EAST;
import static org.example.utils.GeographicDirections.NORTH;
import static org.example.utils.GeographicDirections.SOUTH;
import static org.example.utils.GeographicDirections.WEST;

import java.util.Map;
import org.example.utils.Direction;

/**
 * Utility class for calculating the absolute direction of a vehicle based on its starting and
 * ending road positions.
 */
public class TrafficDirectionCalculator {

  /** A mapping of road names to their respective angles in degrees. */
  private static final Map<String, Integer> ROAD_ANGLES =
      Map.of(
          NORTH, 0,
          EAST, 90,
          SOUTH, 180,
          WEST, 270);

  /**
   * Calculates the absolute direction of car's movement, depending on its starting and end
   * positions.
   *
   * @param startRoad road on which the car will start its movement
   * @param endRoad road on which the car will end its movement
   * @return A {@link Direction} enum indicating the vehicle's absolute movement direction
   * @throws IllegalArgumentException if either {@code startRoad} or {@code endRoad} is invalid
   * @throws IllegalStateException if an unexpected angle difference is encountered
   */
  public static Direction getAbsoluteDirection(String startRoad, String endRoad) {
    if (!ROAD_ANGLES.containsKey(startRoad) || !ROAD_ANGLES.containsKey(endRoad)) {
      throw new IllegalArgumentException("Invalid road name: " + startRoad + " or " + endRoad);
    }

    int startAngle = ROAD_ANGLES.get(startRoad);
    int endAngle = ROAD_ANGLES.get(endRoad);

    int difference = (endAngle - startAngle + 360) % 360;

    return switch (difference) {
      case 0 -> Direction.U_TURN;
      case 90 -> Direction.LEFT;
      case 180 -> Direction.STRAIGHT;
      case 270 -> Direction.RIGHT;
      default -> throw new IllegalStateException("Unexpected value: " + difference);
    };
  }
}
