import java.util.ArrayList;

class Elevator {
    private ArrayList<Raider> raiders;
    private volatile int currentFloor;
    private volatile int destinationFloor;
    private volatile boolean isWaiting;
    private ArrayList<Raider> raidersQueue;

    Elevator(ArrayList<Raider> raidersQueue) {
        isWaiting = true;
        currentFloor = 0;
        destinationFloor = 0;
        raiders = new ArrayList<>();
        this.raidersQueue = raidersQueue;
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
        raider.setEntered(true);
        raiders.add(raider);
        raidersQueue.remove(raider);
        System.out.println("Raider " + raider.getName() + " entered the elevator on the " + currentFloor + " floor ");
        for (Raider r : raidersQueue) {
            if (r.getStartFloor() == currentFloor) {
                if (raiders.contains(r)) {
                    notifyAll();
                    return;
                } else {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
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
                Raider tmpRaider = raiders.get(i);
                System.out.println("Raider " + tmpRaider.getName() + " arrived to his destination floor");
                raiders.remove(i);
                i--;
                tmpRaider.setCurrentFloor(currentFloor);
            }
        }
    }
    public boolean getIsWaiting(){
        return isWaiting;
    }
}
