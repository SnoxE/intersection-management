package org.example;

import static org.example.core.CommandProcessor.processCommands;
import static org.example.core.FourWayIntersection.createFourWayIntersection;

import java.util.ArrayList;
import java.util.List;
import org.example.core.FourWayIntersection;
import org.example.dto.Command;
import org.example.dto.SimulationResult.StepStatus;
import org.example.utils.JsonReader;
import org.example.utils.SimulationResultJsonGenerator;

/** The entry point to the intersection traffic simulation. */
public class Main {

  /**
   * The main method that starts the simulation. It expects two command-line arguments - the input
   * file path containing simulation commands and the output file path where the simulation results
   * will be saved.
   *
   * @param args Command-line arguments: {@code <inputFilePath> <outputFilePath>}.
   */
  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("Usage: java Main <inputFilePath> <outputFilePath>");
      return;
    }

    String inputFilePath = args[0];
    String outputFilePath = args[1];

    FourWayIntersection fourWayIntersection = createFourWayIntersection("Intersection1");
    List<Command> commandList = JsonReader.readCommandsFromJson(inputFilePath);

    List<StepStatus> stepStatuses = new ArrayList<>();
    processCommands(commandList, fourWayIntersection, stepStatuses);

    SimulationResultJsonGenerator.generateJsonOutputFile(stepStatuses, outputFilePath);
  }
}
