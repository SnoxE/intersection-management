package org.example.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.example.dto.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Utility class for reading JSON file . */
public class JsonReader {
  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static final Logger logger = LoggerFactory.getLogger(JsonReader.class);

  /**
   * Reads a JSON file and extracts a list of {@link Command} objects.
   *
   * @param fileName path to the file from which commands are to be read
   * @return list of {@link Command} objects, parsed from the JSON file
   */
  public static List<Command> readCommandsFromJson(String fileName) {
    try {
      JsonNode rootNode = objectMapper.readTree(new File(fileName));
      JsonNode commandsNode = rootNode.get("commands");

      return objectMapper.readValue(commandsNode.toString(), new TypeReference<>() {});
    } catch (IOException e) {
      logger.error("Unexpected error reading JSON file: {}", fileName, e);
      return List.of();
    }
  }
}
