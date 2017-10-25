/**
 * @author Yuriy on 25.10.2017.
 */
class Building {
    private int nFloors;
    private Elevator elevator;

    Building(int nFloors) {
        this.nFloors = nFloors;
        this.elevator = new Elevator(this);
    }

    Elevator getElevator() {
        return elevator;
    }

    private void callElevator(int fromFloor, boolean isGoingUp, int raiderId) {
        if (isGoingUp) {
            System.out.println("Elevator called by rider " + raiderId + " on floor " + fromFloor + " to go up");
        } else {
            System.out.println("Elevator  called by rider " + raiderId + " on floor " + fromFloor + " to go down");
        }
        this.elevator.requestFloor(fromFloor, isGoingUp, raiderId);
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
        }
    }

    void callUp(int fromFloor, int raiderId) {
        callElevator(fromFloor, true, raiderId);
    }

    void callDown(int fromFloor, int raiderId) {
        callElevator(fromFloor, false, raiderId);
    }

    public int getnFloors() {
        return nFloors;
    }
}
