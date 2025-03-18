package org.example.core;

import java.util.List;
import org.example.dto.Command;
import org.example.dto.SimulationResult;

/** Utility class that processes the commands and applies them to the intersection. */
public class CommandProcessor {

  /**
   * Is responsible for processing of the commands depending on their nature.
   *
   * @param commandList a list of commands passed in the input file
   * @param fourWayIntersection an instance of {@link FourWayIntersection}
   * @param stepStatuses a list of step statuses later used to create the output file
   */
  public static void processCommands(
      List<Command> commandList,
      FourWayIntersection fourWayIntersection,
      List<SimulationResult.StepStatus> stepStatuses) {
    for (Command command : commandList) {
      if (command.type().equals(Command.ADD_VEHICLE)) {
        fourWayIntersection.addVehicle(
            new Car(
                command.vehicleId(),
                command.startRoad(),
                command.endRoad(),
                TrafficDirectionCalculator.getAbsoluteDirection(
                    command.startRoad(), command.endRoad())));
      } else if (command.type().equals(Command.STEP)) {
        stepStatuses.add(new SimulationResult.StepStatus(fourWayIntersection.step()));
      }
    }
  }
}
