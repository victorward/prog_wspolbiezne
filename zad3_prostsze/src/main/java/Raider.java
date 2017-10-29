

public class Raider implements Runnable {

    private Elevator elevator;
    private int destinationFloor;
    private int startFloor;
    private String name;
    private boolean entered;

    Raider(Elevator elevator, int destinationFloor, int startFloor, String name) {
        this.elevator = elevator;
        this.destinationFloor = destinationFloor;
        this.startFloor = startFloor;
        this.name = name;
        entered = false;
        System.out.println("Raider " + name + " now is on " + startFloor + " and want to go to " + destinationFloor + " floor");
        new Thread(this).start();
    }

    @Override
    public void run() {
        this.elevator.addRaiderToQueue(this);
        while (!entered) {
            if (elevator.getIsWaiting()) {
                try {
                    elevator.orderElevator(this);
                } catch (InterruptedException e) {

                }
            }

            if (elevator.getCurrentFloor() == startFloor) {
                elevator.enterElevator(this);
                setEntered(true);
            }
        }
        while (elevator.getCurrentFloor() != destinationFloor) {
            elevator.callToFloor(this);
        }

        System.out.println("Raider " + name + " arrived to his destination floor");
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

    private void setEntered(boolean entered) {
        this.entered = entered;
    }


}
