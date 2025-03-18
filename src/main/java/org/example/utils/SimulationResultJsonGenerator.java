package org.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.example.dto.SimulationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Utility class for generating JSON output file from simulation results. */
public class SimulationResultJsonGenerator {
  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static final Logger logger = LoggerFactory.getLogger(JsonReader.class);

  /**
   * Method generating a JSON file. The output file contains a list of statuses, showing which
   * vehicles left the intersection at every step.
   *
   * @param stepStatuses list of statuses generated after every step
   * @param filePath output file path
   */
  public static void generateJsonOutputFile(
      List<SimulationResult.StepStatus> stepStatuses, String filePath) {
    SimulationResult simulationResult = new SimulationResult(stepStatuses);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    try {
      File outputFile = new File(filePath);
      objectMapper.writeValue(outputFile, simulationResult);
    } catch (IOException e) {
      logger.error("Unexpected error serializing JSON file", e);
    }
  }
}
