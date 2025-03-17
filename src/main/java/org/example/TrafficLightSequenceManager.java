package org.example;

import java.util.LinkedList;
import java.util.List;

public final class TrafficLightSequenceManager {
    public static final LinkedList<List<String>> sequences = loadTrafficSequences();
    public static final int stepCounterLimit = 3;

    public static int stepCounter = 0;

    private static LinkedList<List<String>> loadTrafficSequences() {
        LinkedList<List<String>> sequences = new LinkedList<>();
        sequences.add(List.of("south2", "north2"));
        sequences.add(List.of("south1", "north1"));
        sequences.add(List.of("east2", "west2"));
        return sequences;
    }
}
