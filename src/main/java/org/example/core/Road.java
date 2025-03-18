package org.example.core;

import java.util.Set;

public record Road(String id, Set<Lane> startLanes, Set<String> endLanes) {}
