package org.example;

public class Car {
    private final String id;
    private final String startRoad;
    private final String endRoad;
    private final Direction direction;

    public Car(String id, String startRoad, String endRoad, Direction direction) {
        this.id = id;
        this.startRoad = startRoad;
        this.endRoad = endRoad;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return id + " " + startRoad + " " + endRoad + " " + direction;
    }

    public String getId() { return id; }
    public String getStartRoad() { return startRoad; }
    public String getEndRoad() { return endRoad; }
    public Direction getDirection() { return direction; }
}
