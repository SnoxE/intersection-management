import java.util.Set;

public class Road {
    private final String id;
    private final Set<Lane> startLanes;
    private final Set<Lane> endLanes;

    public Road(String id, Set<Lane> startLanes, Set<Lane> endLanes) {
        this.id = id;
        this.startLanes = startLanes;
        this.endLanes = endLanes;
    }

    public String getId() {
        return id;
    }

    public Set<Lane> getStartLanes() {
        return startLanes;
    }

    public Set<Lane> getEndLanes() {
        return endLanes;
    }
}
