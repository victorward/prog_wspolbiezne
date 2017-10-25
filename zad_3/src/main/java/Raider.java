/**
 * @author Yuriy on 25.10.2017.
 */
public class Raider implements Runnable {
    private Building building;
    private int raiderId;
    private int startFloor;
    private int stopFloor;
    private Thread thread;

    public Raider(Building building, int raiderId, int startFloor, int stopFloor) {
        this.building = building;
        this.raiderId = raiderId;
        this.startFloor = startFloor;
        this.stopFloor = stopFloor;
        this.thread = new Thread(this);
    }

    @Override
    public void run() {
        while (true) {
            if (startFloor < stopFloor) {
                System.out.println("Raider " + raiderId + " want to go up from floor " + startFloor);
                this.building.callUp(startFloor, raiderId);
            } else {
                System.out.println("Raider " + raiderId + " want to go down from floor " + startFloor);
                this.building.callDown(startFloor, raiderId);
            }
            if (this.building.getElevator().enterElevator(raiderId)) {
                System.out.println("Raider " + raiderId + " has entered elevator");
                break;
            }
        }

        System.out.println("Raider " + raiderId + " on elevator wants to go to floor " + stopFloor);
        this.building.getElevator().requestFloor(stopFloor, raiderId);

        System.out.println("Raider " + raiderId + " exit elevator on floor " + stopFloor);
        this.building.getElevator().exitElevator(raiderId);

    }

    public Thread getThread() {
        return thread;
    }
}
