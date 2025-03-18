package org.example.utils;

public class GeographicDirections {
  public static final String NORTH = "north";
  public static final String SOUTH = "south";
  public static final String EAST = "east";
  public static final String WEST = "west";

  public static String getGeographicDirectionWithNumber(String direction, int number) {
    return direction + number;
  }
}
