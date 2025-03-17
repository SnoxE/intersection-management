package org.example;

import java.util.Set;

public class Road {
    private final String id;
    private final Set<Lane> startLanes;
    private final Set<String> endLanes;

    public Road(String id, Set<Lane> startLanes, Set<String> endLanes) {
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

    public Set<String> getEndLanes() {
        return endLanes;
    }
}
