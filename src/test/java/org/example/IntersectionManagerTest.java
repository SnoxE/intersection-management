package org.example;

import static org.example.core.CommandProcessor.processCommands;
import static org.example.dto.SimulationResult.StepStatus;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.example.core.FourWayIntersection;
import org.example.dto.Command;
import org.example.utils.JsonReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntersectionManagerTest {

  private FourWayIntersection fourWayIntersection;
  private List<StepStatus> stepStatuses;

  @BeforeEach
  void setup() {
    fourWayIntersection = FourWayIntersection.createFourWayIntersection("TestIntersection");
    stepStatuses = new ArrayList<>();
  }

  @Test
  void testTrafficSimulation() {
    String testInputPath = Path.of("src/test/resources/test_input.json").toString();
    List<Command> commandList = JsonReader.readCommandsFromJson(testInputPath);

    processCommands(commandList, fourWayIntersection, stepStatuses);

    List<List<String>> expectedLeftVehicles =
        List.of(
            List.of("vehicle1", "vehicle2"),
            List.of(),
            List.of("vehicle3"),
            List.of("vehicle4"),
            List.of("vehicle6"),
            List.of("vehicle5"));

    assertEquals(expectedLeftVehicles.size(), stepStatuses.size(), "Mismatch in step count");

    for (int i = 0; i < expectedLeftVehicles.size(); i++) {
      List<String> expected = expectedLeftVehicles.get(i);
      List<String> actual = stepStatuses.get(i).leftVehicles();

      assertEquals(expected, actual, "Mismatch at step " + (i + 1));
    }
  }
}
