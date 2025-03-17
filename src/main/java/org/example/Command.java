package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Command(String type, String vehicleId, String startRoad, String endRoad) {
    public static final String ADD_VEHICLE = "addVehicle";
    public static final String STEP = "step";
}
