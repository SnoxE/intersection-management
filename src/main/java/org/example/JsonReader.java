package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonReader {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(JsonReader.class);

    public static List<Command> readCommandsFromJson(String fileName) {
        try {
            JsonNode rootNode = objectMapper.readTree(new File(fileName));
            JsonNode commandsNode = rootNode.get("commands");

            return objectMapper.readValue(commandsNode.toString(), new TypeReference<>() {
            });
        } catch (IOException e) {
            logger.error("Unexpected error reading JSON file: {}", fileName, e);
            return List.of();
        }
    }
}
