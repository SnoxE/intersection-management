import java.util.ArrayDeque;
import java.util.Set;

public class Lane {
  private final String id;
  private final Set<String> possibleExitDirections;
  private ArrayDeque<Car> queue;
  private LightColor trafficLightColor;

  public Lane(
      String id,
      Set<String> possibleExitDirections,
      ArrayDeque<Car> queue,
      LightColor trafficLightColor) {
    this.id = id;
    this.possibleExitDirections = possibleExitDirections;
    this.queue = queue;
    this.trafficLightColor = trafficLightColor;
  }

  public String getId() {
    return id;
  }

  public Set<String> getPossibleExitDirections() {
    return possibleExitDirections;
  }

  public ArrayDeque<Car> getQueue() {
    return queue;
  }

  public LightColor getTrafficLightColor() {
    return trafficLightColor;
  }

  public void setTrafficLightColor(LightColor trafficLightColor) {
    this.trafficLightColor = trafficLightColor;
  }

  public void addCar(Car car) {
    queue.add(car);
  }

  public void releaseFirstCar() {
    if (!queue.isEmpty()) {
      queue.poll();
    }
  }
}
