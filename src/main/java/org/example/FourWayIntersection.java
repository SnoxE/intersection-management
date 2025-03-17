package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FourWayIntersection implements Intersection {
    private final String id;
    private final Set<Road> roads;

    public FourWayIntersection(String id, Set<Road> roads) {
        this.id = id;
        this.roads = roads;
    }

    public void intersectionInit() {
        for (Road road : roads) {
            for (Lane lane : road.getStartLanes()) {
                if (TrafficLightSequenceManager.sequences.getFirst().contains(lane.getId())) {
                    lane.setTrafficLightColor(LightColor.GREEN);
                }
            }
        }
    }

    public String getId() {
        return id;
    }

    public Set<Road> getRoads() {
        return roads;
    }

    @Override
    public void addVehicle(Car car) {
        for (Road road : roads) {
            if (road.getId().toLowerCase().equals(car.getStartRoad())) {
                for (Lane lane : road.getStartLanes()) {
                    if (lane.getPossibleExitDirections().contains(car.getEndRoad())) {
                        lane.addCar(car);
                    }
                }
            }
        }
    }

    @Override
    public List<String> step() {
//        List<String> vehiclesThatLeft = new ArrayList<>();
//        for (Road road : roads) {
//            for (Lane lane : road.getStartLanes()) {
//                if (lane.getTrafficLightColor() == LightColor.GREEN) {
//                    lane.releaseFirstCar();
//                    vehiclesThatLeft.add(road.getId());
//                }
//            }
//        }
//
//        return vehiclesThatLeft;
        return null;
    }
}
