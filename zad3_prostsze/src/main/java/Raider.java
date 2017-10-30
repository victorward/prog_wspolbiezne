

public class Raider implements Runnable {

    private Elevator elevator;
    private int destinationFloor;
    private int startFloor;
    private int currentFloor;
    private String name;
    private boolean entered;
    private Thread thread;

    Raider(int destinationFloor, int startFloor, String name) {
        this.destinationFloor = destinationFloor;
        this.currentFloor = startFloor;
        this.startFloor = startFloor;
        this.name = name;
        entered = false;
        this.thread = new Thread(this);
        System.out.println("Raider " + name + " now is on " + startFloor + " and want to go to " + destinationFloor + " floor");
    }

    void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }

    @Override
    public void run() {
        while (!entered) {
            if (elevator.getIsWaiting()){
                elevator.orderElevator(this);
            }

            if (elevator.getCurrentFloor() == startFloor) {
                elevator.enterElevator(this);
            }
            if (this.destinationFloor == this.currentFloor){
                System.out.println(name + " thread ended");
                stopTheThread();
            }
        }

        elevator.callToFloor(this);
        //System.out.println(name + " thread ended");
    }

    int getDestinationFloor() {
        return destinationFloor;
    }

    int getStartFloor() {
        return startFloor;
    }

    String getName() {
        return name;
    }

    void setEntered(boolean entered) {
        this.entered = entered;
    }

    void stopTheThread() {
        thread.interrupt();
    }


    public Thread getThread() {
        return thread;
    }

    public void setCurrentFloor(int currentFloor){
        this.currentFloor = currentFloor;
    }
}
