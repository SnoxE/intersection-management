package org.example;

import java.util.Map;

public class TrafficDirectionCalculator {
    private static final Map<String, Integer> ROAD_ANGLES = Map.of(
            "north", 0,
            "east", 90,
            "south", 180,
            "west", 270
    );

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

    public static void main(String[] args) {
        System.out.println(getAbsoluteDirection("north", "south")); // Straight
        System.out.println(getAbsoluteDirection("north", "east"));  // Left
        System.out.println(getAbsoluteDirection("north", "west"));  // Right
        System.out.println(getAbsoluteDirection("west", "south"));  // Right
    }
}
