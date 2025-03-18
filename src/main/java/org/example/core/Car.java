package org.example.core;

import org.example.utils.Direction;

public record Car(String id, String startRoad, String endRoad, Direction direction) {}
