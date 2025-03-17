package org.example;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        Road southRoad = new Road("south",
                Set.of(new Lane("south1", Set.of("west"), new ArrayDeque<>(), LightColor.RED),
                        new Lane("south2", Set.of("north", "east"), new ArrayDeque<>(), LightColor.RED)),
                Set.of("west", "north", "east"));

        Road westRoad = new Road("west",
                Set.of(new Lane("west1", Set.of("north", "east", "south"), new ArrayDeque<>(), LightColor.RED)),
                Set.of("north", "east", "south"));

        Road northRoad = new Road("north",
                Set.of(new Lane("north1", Set.of("east"), new ArrayDeque<>(), LightColor.RED),
                        new Lane("north2", Set.of("south", "west"), new ArrayDeque<>(), LightColor.RED)),
                Set.of("east", "south", "west"));

        Road eastRoad = new Road("east",
                Set.of(new Lane("east1", Set.of("south", "west", "north"), new ArrayDeque<>(), LightColor.RED)),
                Set.of("south", "west", "north"));

        FourWayIntersection fourWayIntersection = new FourWayIntersection("Intersection1",
                Set.of(southRoad, westRoad, northRoad, eastRoad));
        fourWayIntersection.intersectionInit();

//        for (Road road : fourWayIntersection.getRoads()) {
//            for (Lane lane : road.getStartLanes()) {
//                if(lane.getTrafficLightColor() == LightColor.GREEN) {
//                    System.out.println(lane.getId() + " " + lane.getQueue() + " " + lane.getQueueLength());
//                }
//            }
//        }


        List<Command> commandList = JsonReader.readCommandsFromJson("src/main/resources/input.json");

        for (Command command : commandList) {
            if (command.type().equals(Command.ADD_VEHICLE)) {
                fourWayIntersection.addVehicle(
                        new Car(command.vehicleId(),
                                command.startRoad(),
                                command.endRoad(),
                                TrafficDirectionCalculator.getAbsoluteDirection(command.startRoad(), command.endRoad())));
            } else if (command.type().equals(Command.STEP)) {
                fourWayIntersection.step();
            }
        }

        for (Road road : fourWayIntersection.getRoads()) {
            for (Lane lane : road.getStartLanes()) {
                System.out.println(lane.getId() + " " + lane.getQueue() + " " + lane.getQueue().size());
            }
        }
    }
}