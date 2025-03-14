import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Command(String type, String vehicleId, String startRoad, String endRoad) {}
