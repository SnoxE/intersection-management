# Intersection Manager

This project simulates a `four-way intersection` with dynamically controlled traffic lights,
ensuring efficient traffic flow, as well as collision and deadlock prevention.

---

## Intersection Roads and Lanes definition

The intersection has 4 roads, with 2 entry lanes in North and South roads and a single entry lane in East and West
roads. The lanes of the former 2 roads have 1 dedicated lane for turning left and 1 for going straight and turning 
right.

## Traffic Light Sequence Algorithm

The traffic light sequence algorithm is responsible for switching lights at the predefined rate, as well as 
doing it dynamically based on traffic conditions.

### Predefined Sequences

1. The simulation follows predefined traffic light sequences, allowing different sets of lanes to move at each
   step.
2. Each sequence remains active for a maximum number of steps (default = 3).
3. The system prioritizes sequences where lanes have the most waiting vehicles.
4. The system implements collision prevention (for left turns). 
   - If a vehicle located on `east1` or `west1` lane and wants to turn left, but the opposite one is driving 
     straight or taking a right turn, the left-turning vehicle yields.
   - If both vehicles located on aforementioned lanes want to turn left, the one located on `west1` lane takes 
     priority.

### Step Method

The `step` method executes a simulation step, where vehicles pass through the intersection.

1. Identify which lanes have a green light.
2. Move cars from the front of the queue in green-light lanes.
3. Check for potential left-turn conflicts in `east1` and `west1`.
4. Increment step counter.
5. If `stepCounterLimit` is reached or all cars have moved, switch lights (the system prioritizes the lanes with 
   most traffic present).


   