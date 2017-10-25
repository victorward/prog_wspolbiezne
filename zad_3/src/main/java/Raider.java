/**
 * @author Yuriy on 25.10.2017.
 */
public class Raider implements Runnable {
    private int raiderId;
    private int startFloor;
    private int stopFloor;
    private Thread thread;

    public Raider(int raiderId, int startFloor, int stopFloor) {
        this.raiderId = raiderId;
        this.startFloor = startFloor;
        this.stopFloor = stopFloor;
        this.thread = new Thread(this);
    }

    @Override
    public void run() {

    }

    public Thread getThread() {
        return thread;
    }
}
