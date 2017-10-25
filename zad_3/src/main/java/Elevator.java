import java.util.TreeSet;

/**
 * @author Yuriy on 25.10.2017.
 */
public class Elevator implements Runnable {
    private int currentFloor;
    private boolean isGooingUp;
    private boolean isGoingDown;
    private Building building;
    private TreeSet<Integer> upRequests;
    private TreeSet<Integer> downRequests;

    private ElevatorEvent[] floorsEvents;

    private Thread thread;

    public Elevator(Building building) {
        this.building = building;
        this.currentFloor = -1;
        this.upRequests = new TreeSet<>();
        this.downRequests = new TreeSet<>();
        this.floorsEvents = new ElevatorEvent[building.getnFloors()];
        for (int i = 0; i < building.getnFloors(); i++) {
            this.floorsEvents[i] = new ElevatorEvent();
        }
        this.thread = new Thread(this);
    }

    @Override
    public void run() {

    }

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
}
