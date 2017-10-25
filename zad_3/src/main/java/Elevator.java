import java.nio.file.Path;
import java.util.TreeSet;

/**
 * @author Yuriy on 25.10.2017.
 */
public class Elevator implements Runnable {
    private int currentFloor;
    private int nRaiders;
    private boolean isGooingUp;
    private boolean isGoingDown;
    private boolean isDoorOpen;
    private TreeSet<Integer> upRequests;
    private TreeSet<Integer> downRequests;

    private ElevatorEvent[] floorsEvents;

    private Thread thread;

    public Elevator(int nFloors) {
        this.currentFloor = -1;
        this.nRaiders = 0;
        this.isDoorOpen = false;
        this.upRequests = new TreeSet<>();
        this.downRequests = new TreeSet<>();
        this.floorsEvents = new ElevatorEvent[nFloors];
        for (int i = 0; i < nFloors; i++) {
            this.floorsEvents[i] = new ElevatorEvent();
        }
        this.thread = new Thread(this);
    }

    @Override
    public void run() {

    }

    void openDoor() {
        this.isDoorOpen = true;
        System.out.println("Elevator open door on floor " + this.currentFloor);
        int nWaiters = this.floorsEvents[this.currentFloor].getWaiters();
        System.out.println("Floor " + this.currentFloor + " has " + nWaiters + " raiders waiting on elevator");
//        this.floorsEvents[this.currentFloor].
    }

    void closeDoor() {
        this.isDoorOpen = false;
        System.out.println("Elevator closed door on " + this.currentFloor);
    }

    void simulateMovement(int floor) {
        int floorToMove = Math.abs(floor - currentFloor);
        int speed = 1000;
        try {
            thread.join(floorToMove * speed); // nie wiem czy to tak, czy przez Thread.sleep czy inaczej
            this.currentFloor = floor;
            System.out.println("Elevator " + (this.isGooingUp ? " has moved up to floor " : " has moved down to floor ") + this.currentFloor);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void visitFloor(int floor) {
        if (this.isGooingUp) {
            this.upRequests.remove(floor);
        } else {
            this.downRequests.remove(floor);
        }
        simulateMovement(floor);
    }

    void enterElevator(int raiderId) {
        this.nRaiders++;
        System.out.println("Raider " + raiderId + " enter elevator on floor " + this.currentFloor);
//        this.floorsEvents[this.currentFloor].c
    }

    void exitElevator(int raiderId) {
        this.nRaiders--;
        System.out.println("Raider " + raiderId + " has exited elevator on floor " + this.currentFloor);
//        this.floorsEvents[this.currentFloor].e
    }

    void passIfWrongDirection() {
//        this.floorsEvents[this.currentFloor].c
    }

    void requestFloor(int requestedFloor, int raiderId) {
        boolean goingUp = requestedFloor > this.currentFloor;
        requestFloor(requestedFloor, goingUp, raiderId);
    }

    void requestFloor(int requestedFloor, boolean goingUp, int raiderId) {
        if (goingUp) {
            this.upRequests.add(requestedFloor);
            System.out.println("Elevator receives request from raider " + raiderId + " to go up to floor " + requestedFloor);
        } else {
            this.downRequests.add(requestedFloor);
            System.out.println("Elevator receives request from raider " + raiderId + " to go down to floor " + requestedFloor);
        }
//        this.floorsEvents[this.currentFloor].ar
    }

    int getNextRequestedFloor() {
        if (this.isGooingUp) {
            Integer next = this.upRequests.higher(this.currentFloor);
            if (next != null) {
                System.out.println("Elevator is going to processes request from raider to go up to floor " + next);
                return next;
            } else {
                this.isGooingUp = false;
                next = this.downRequests.lower(this.currentFloor);
                if (next != null) {
                    System.out.println("Elevator is going to process request from raider to go down to floor  " + next);
                    return next;
                } else {
                    System.out.println("Elevator is going to go to the default floor");
                    return -1;
                }
            }
        } else {
            Integer next = this.downRequests.lower(this.currentFloor);
            if (next != null) {
                System.out.println("Elevator is going to process request from raider to go down to floor " + next);
                return next;
            } else {
                this.isGooingUp = true;
                next = this.upRequests.higher(this.currentFloor);
                if (next != null) {
                    System.out.println("Elevator is going to process request from raider to go pu to floor " + next);
                    return next;
                } else {
                    System.out.println("Elevator is going to go to the default floor");
                    return -1;
                }
            }
        }
    }

    // getters
    public int getCurrentFloor() {
        return currentFloor;
    }

    public boolean isGooingUp() {
        return isGooingUp;
    }

    public boolean isGoingDown() {
        return isGoingDown;
    }

    public Thread getThread() {
        return thread;
    }

    public boolean isDoorOpen() {
        return isDoorOpen;
    }
}
