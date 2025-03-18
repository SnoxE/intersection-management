package org.example.dto;

import java.util.List;

public record SimulationResult(List<StepStatus> stepStatuses) {
  public record StepStatus(List<String> leftVehicles) {}
}
