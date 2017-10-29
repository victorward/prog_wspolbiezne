import java.util.ArrayList;

class Elevator {
    private ArrayList<Raider> raiders;
    private ArrayList<Raider> raidersWaiting;
    private volatile int currentFloor;
    private volatile int destinationFloor;
    private volatile boolean isWaiting;

    Elevator() {
        isWaiting = true;
        currentFloor = 0;
        destinationFloor = 0;
        raiders = new ArrayList<>();
        raidersWaiting = new ArrayList<>();

        waitForOrder();
    }

    private void moveElevator() {
        if (currentFloor < destinationFloor) {
            moveUp();
        } else if (currentFloor > destinationFloor) {
            moveDown();
        }
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

    synchronized void orderElevator(Raider raider) throws InterruptedException  {
        if (isWaiting) {
            isWaiting = false;
            int destination = raider.getStartFloor();
            destinationFloor = destination;
            System.out.println("The elevator is called and go to the " + destination + " floor");
            moveElevator(destination);
            wait();
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
            if (raiders.size() > 0) {
                checkRaiders();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized void enterElevator(Raider raider) {
        notify();
        System.out.println("Raider " + raider.getName() + " entered the elevator");
        for (int i=0; i<raidersWaiting.size(); i++){
            if(raidersWaiting.get(i).getStartFloor() == this.getCurrentFloor()){
                raiders.add(raider);
            }
        }
    }

    int getCurrentFloor() {
        return currentFloor;
    }

    private void checkRaiders() {
        for (int i = 0; i < raiders.size(); i++) {
            if (raiders.get(i).getDestinationFloor() == currentFloor) {
                raiders.remove(i);
                i--;
                if (raiders.size() > 0 ){
                    destinationFloor = raiders.get(0).getDestinationFloor();
                }
            }
        }
        if (raiders.size() == 0){
            waitForOrder();
        }
    }

    public void addRaiderToQueue(Raider raider){
        raidersWaiting.add(raider);
    }

    public boolean getIsWaiting(){
        return isWaiting;
    }
}
