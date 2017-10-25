/**
 * @author Yuriy on 25.10.2017.
 */
public class ElevatorEvent {
    private int waiters;
    private boolean eventInProgress;

    public ElevatorEvent() {
        this.waiters = 0;
        this.eventInProgress = false;
    }

    public int getWaiters() {
        return waiters;
    }
}
