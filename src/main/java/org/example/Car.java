public class Car {
    private final String id;
    private final String startRoad;
    private final String endRoad;

    public Car(String id, String startRoad, String endRoad) {
        this.id = id;
        this.startRoad = startRoad;
        this.endRoad = endRoad;
    }

    public String getId() { return id; }
    public String getStartRoad() { return startRoad; }
    public String getEndRoad() { return endRoad; }
}
