import java.util.TreeSet;

import static java.lang.Thread.interrupted;

/**
 * @author Yuriy on 25.10.2017.
 */
public class Elevator implements Runnable {
    private int currentFloor;
    private int nRaiders;
    private boolean isGooingUp;
    private boolean isDoorOpen;
    private Building building;
    private TreeSet<Integer> upRequests;
    private TreeSet<Integer> downRequests;

    private ElevatorEvent[] floorsEvents;

    private Thread thread;

    public Elevator(Building building) {
        this.currentFloor = -1;
        this.nRaiders = 0;
        this.isGooingUp = true;
        this.isDoorOpen = false;
        this.upRequests = new TreeSet<>();
        this.downRequests = new TreeSet<>();
        this.building = building;
        int nFloors = building.getnFloors();
        this.floorsEvents = new ElevatorEvent[nFloors];
        for (int i = 0; i < nFloors; i++) {
            this.floorsEvents[i] = new ElevatorEvent();
        }
        this.thread = new Thread(this);
    }

    @Override
    public void run() {
        while (true) {
            if (interrupted()) return;

            boolean noMoreRequests = this.upRequests.isEmpty() && this.downRequests.isEmpty();
            int nextFloor = getNextRequestedFloor();

            if (noMoreRequests) {
                synchronized (this) {
                    this.currentFloor = -1;
                    try {
                        System.out.println("Elevator is waiting for riders requests");
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else if (nextFloor != -1) {
                System.out.println("Elevator will move up to floor " + nextFloor);
                visitFloor(nextFloor);
                openDoor();
                closeDoor();
            }
        }
    }

    void openDoor() {
        this.isDoorOpen = true;
        System.out.println("Elevator open door on floor " + this.currentFloor);
        int nWaiters = this.floorsEvents[this.currentFloor].getWaiters();
        System.out.println("Floor " + this.currentFloor + " has " + nWaiters + " raiders waiting on elevator");
        this.floorsEvents[this.currentFloor].raise(this.currentFloor);
    }

    void closeDoor() {
        this.isDoorOpen = false;
        synchronized (this.building) {
            this.building.notifyAll();
        }
        System.out.println("Elevator closed door on " + this.currentFloor);
    }

    private void simulateMovement(int floor) {
//        int floorToMove = Math.abs(floor - currentFloor);
//        int speed = 1000;
//        try {
//            thread.join(floorToMove * speed); // nie wiem czy to tak, czy przez Thread.sleep czy inaczej
//            this.currentFloor = floor;
//            System.out.println("Elevator " + (this.isGooingUp ? " has moved up to floor " : " has moved down to floor ") + this.currentFloor);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        this.building.removeStop(floor, this.isGooingUp);
        this.currentFloor = floor;
        System.out.println("Elevator has moved to " + this.currentFloor);
    }

    synchronized void visitFloor(int floor) {
        if (this.isGooingUp) {
            this.upRequests.remove(floor);
        } else {
            this.downRequests.remove(floor);
        }
        simulateMovement(floor);
    }

    synchronized boolean enterElevator(int raiderId) {
        this.nRaiders++;
        System.out.println("Raider " + raiderId + " enter elevator on floor " + this.currentFloor);
        this.floorsEvents[this.currentFloor].complete(this.currentFloor);
        return true;
    }

    synchronized void exitElevator(int raiderId) {
        this.nRaiders--;
        System.out.println("Raider " + raiderId + " has exited elevator on floor " + this.currentFloor);
        this.floorsEvents[this.currentFloor].complete(this.currentFloor);
    }

    void passIfWrongDirection() {
        this.floorsEvents[this.currentFloor].complete(this.currentFloor);
    }

    void requestFloor(int requestedFloor, int raiderId) {
        boolean goingUp = requestedFloor > this.currentFloor;
        requestFloor(requestedFloor, goingUp, raiderId);
    }

    void requestFloor(int requestedFloor, boolean goingUp, int raiderId) {
        synchronized (this) {
            if (goingUp) {
                this.upRequests.add(requestedFloor);
                System.out.println("Elevator receives request from raider " + raiderId + " to go up to floor " + requestedFloor);
            } else {
                this.downRequests.add(requestedFloor);
                System.out.println("Elevator receives request from raider " + raiderId + " to go down from floor " + requestedFloor);
            }
            this.building.addStop(requestedFloor, goingUp, this);
            notifyAll();
        }
        this.floorsEvents[requestedFloor].arrive(requestedFloor, raiderId);
    }

    synchronized int getNextRequestedFloor() {
        if (this.isGooingUp) {
            Integer next = this.upRequests.higher(this.currentFloor);
            if (next != null) {
                System.out.println("Elevator is going to processes request from raider to go up to floor " + next);
                return next;
            } else {
                this.isGooingUp = false;
                this.currentFloor = this.floorsEvents.length;
                next = this.downRequests.lower(this.currentFloor);
                if (next != null) {
                    System.out.println("Elevator is going to process request from raider to go down from floor  " + next);
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
                this.currentFloor = -1;
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
    public boolean isGoingUp() {
        return isGooingUp;
    }

    public Thread getThread() {
        return thread;
    }

    public boolean isDoorOpen() {
        return isDoorOpen;
    }
}
