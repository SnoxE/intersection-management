package org.example.core;

import java.util.List;
import java.util.Set;
import org.example.utils.LightColor;

/**
 * Provides functionality for managing roads, handling vehicles, and executing traffic simulation
 * steps.
 */
public abstract class Intersection {

  protected final String id;
  protected final Set<Road> roads;

  public Intersection(String id, Set<Road> roads) {
    this.id = id;
    this.roads = roads;
  }

  public String getId() {
    return id;
  }

  public Set<Road> getRoads() {
    return roads;
  }

  /**
   * Performs a step of the simulation, where vehicles attempt to exit the intersection. Specific
   * behaviour of this method is defined by a subclass.
   *
   * @return a list of vehicle IDs, which have left the intersection in that step
   */
  public abstract List<String> step();

  /**
   * Adds a vehicle to the appropriate lane in the intersection. If the vehicle is added to a lane
   * with active red light, the traffic lights are updated.
   *
   * @param car vehicle to be added to the intersection
   */
  public void addVehicle(Car car) {
    for (Road road : roads) {
      if (road.id().toLowerCase().equals(car.startRoad())) {
        for (Lane lane : road.startLanes()) {
          if (lane.getPossibleExitDirections().contains(car.endRoad())) {
            lane.addCar(car);

            if (lane.getTrafficLightColor() == LightColor.RED) {
              TrafficLightSequenceManager.updateTrafficLights();
            }
          }
        }
      }
    }
  }

  /**
   * Releases the first car in a given lane and adds it to the list of vehicles that have left the
   * intersection.
   *
   * @param lane the lane from which the vehicle is being released
   * @param vehiclesThatLeft the list to which the released vehicle's ID will be added
   */
  protected void processCarRelease(Lane lane, List<String> vehiclesThatLeft) {
    Car car = lane.releaseFirstCar();
    if (car != null) {
      vehiclesThatLeft.add(car.id());
    }
  }
}
