import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuriy on 25.10.2017.
 */
class Building {
    private int nFloors;
    private List<Elevator> elevators;
    private Elevator[] goingUpStops;
    private Elevator[] goingDownStops;

    Building(int nFloors) {
        this.nFloors = nFloors;
        this.elevators = new ArrayList<>();
        elevators.add(new Elevator(this));

        this.goingUpStops = new Elevator[this.nFloors];
        this.goingDownStops = new Elevator[this.nFloors];
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    public synchronized void removeStop(int arrivedFloor, boolean isGoingUp) {
        if (isGoingUp) {
            this.goingUpStops[arrivedFloor] = null;
        } else {
            this.goingDownStops[arrivedFloor] = null;
        }
    }

    public synchronized void addStop(int requestedFloor, boolean isGoingUp, Elevator elevator) {
        if (isGoingUp) {
            this.goingUpStops[requestedFloor] = elevator;
        } else {
            this.goingDownStops[requestedFloor] = elevator;
        }
    }


    public synchronized Elevator find(int floor, boolean isGoingUp) {
        Elevator elevator;
        boolean hasElevatorGoingUpToFloor = this.goingUpStops[floor] != null;
        boolean hasElevatorGoingDownToFloor = this.goingDownStops[floor] != null;
        if (isGoingUp && hasElevatorGoingUpToFloor) {
            elevator = this.goingUpStops[floor];
            return elevator;
        } else if (!isGoingUp && hasElevatorGoingDownToFloor) {
            elevator = this.goingDownStops[floor];
            return elevator;
        } else {
            elevator = elevators.get(0);
            this.addStop(floor, isGoingUp, elevator);
            return elevator;
        }
    }

    private Elevator callElevator(int fromFloor, boolean isGoingUp, int raiderId) {
        Elevator elevator = find(fromFloor, isGoingUp);

        if (isGoingUp) {
            System.out.println("Elevator called by rider " + raiderId + " on floor " + fromFloor + " to go up");
        } else {
            System.out.println("Elevator  called by rider " + raiderId + " on floor " + fromFloor + " to go down");
        }
        elevator.requestFloor(fromFloor, isGoingUp, raiderId);
        while (elevator.isGoingUp() != isGoingUp) {
            elevator.passIfWrongDirection();
            synchronized (this) {
                while (elevator.isDoorOpen()) {
                    try {
                        System.out.println("Elevator is not going in the requested direction");
                        wait();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            elevator.requestFloor(fromFloor, isGoingUp, raiderId);
        }
        return elevator;
    }

    Elevator callUp(int fromFloor, int raiderId) {
        return callElevator(fromFloor, true, raiderId);
    }

    Elevator callDown(int fromFloor, int raiderId) {
        return callElevator(fromFloor, false, raiderId);
    }

    public int getnFloors() {
        return nFloors;
    }
}
