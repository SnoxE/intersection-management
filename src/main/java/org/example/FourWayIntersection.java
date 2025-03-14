import java.util.Set;

public class FourWayIntersection implements Intersection {
    private final String id;
    private final Set<Road> roads;

    @Override
    public void addVehicle(Car car) {

    }

    @Override
    public void step() {

    }

    public FourWayIntersection(String id, Set<Road> roads) {
        this.id = id;
        this.roads = roads;
    }

    public String getId() {
        return id;
    }

    public Set<Road> getRoads() {
        return roads;
    }
}
