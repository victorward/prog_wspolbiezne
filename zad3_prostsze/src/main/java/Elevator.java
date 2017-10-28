import java.util.ArrayList;

class Elevator {
    private ArrayList<Raider> raiders;
    private volatile int currentFloor;
    private volatile int destinationFloor;
    private volatile boolean isWaiting;

    Elevator() {
        isWaiting = true;
        currentFloor = 0;
        destinationFloor = 0;
        raiders = new ArrayList<>();

        waitForOrder();
    }

    private void moveUp() {
        currentFloor += 1;
        System.out.println("Current floor: " + currentFloor);
    }

    private void moveDown() {
        currentFloor -= 1;
        System.out.println("Current floor: " + currentFloor);
    }

    private synchronized void waitForOrder() {
        System.out.println("Elevator is empty, waiting for order");
        isWaiting = true;
    }

    synchronized void orderElevator(Raider raider) {
        if (isWaiting) {
            int destination = raider.getStartFloor();
            destinationFloor = destination;
            isWaiting = false;
            System.out.println("The elevator is called and go to the " + destination + " floor");
            moveElevator(destination);
        }
    }

    synchronized void callToFloor(Raider raider) {
        int floorToGo = raider.getDestinationFloor();
        destinationFloor = floorToGo;
        moveElevator(floorToGo);
    }

    private void moveElevator(int destinationFloor) {
        while (currentFloor != destinationFloor) {
            moveElevator();
            checkRaiders();
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized void enterElevator(Raider raider) {
        System.out.println("Raider " + raider.getName() + " entered the elevator");
        raiders.add(raider);
    }

    int getCurrentFloor() {
        return currentFloor;
    }

    private void moveElevator() {
        if (currentFloor < destinationFloor) {
            moveUp();
        } else if (currentFloor > destinationFloor) {
            moveDown();
        }
    }

    private void checkRaiders() {
        for (int i = 0; i < raiders.size(); i++) {
            if (raiders.get(i).getDestinationFloor() == currentFloor) {
                isWaiting = true;
                raiders.remove(i);
                i--;
            }
        }
    }
}
