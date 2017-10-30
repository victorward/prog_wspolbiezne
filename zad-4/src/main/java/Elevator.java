import java.util.ArrayList;

public class Elevator {
    private ArrayList<Raider> queue;
    private ArrayList<Raider> inElevator;
    private int currentFloor;
    private int destinationFloor;
    private boolean isWaiting;

    Elevator() {
        isWaiting = true;
        currentFloor = 0;
        destinationFloor = 0;
        queue = new ArrayList<>();
        inElevator = new ArrayList<>();
    }

    void run() {
        while ((queue.size() != 0 || queue != null) && (inElevator.size() != 0 || inElevator != null)) {
            accept();
            if (queue.size() == 0 && inElevator.size() == 0) {
                System.out.println("No more requests");
                break;
            }
        }
        System.exit(0);
    }

    private void accept() {
        if (inElevator.size() == 0 && queue.size() != 0) {
            if (isWaiting) {
                queue.get(0).acquire();
                callForElevator(queue.get(0).getStartingFloor());
                System.out.println("The " + queue.get(0).getName() + " enter the elevator");
                moveToFloor(queue.get(0).getTargetFloor());
                enterElevator();
                System.out.println("The " + queue.get(0).getName() + " exit the elevator");
                queue.get(0).setLeft();
                queue.remove(0);
            }
        } else {
            inElevator.get(0).acquire();
            moveToFloor(inElevator.get(0).getTargetFloor());
            System.out.println("The " + inElevator.get(0).getName() + " exit the elevator");
            inElevator.get(0).setLeft();
            inElevator.remove(0);
            enterElevator();
        }
    }

    private void callForElevator(int floor) {
        System.out.println("The elevator is going to requested floor");
        destinationFloor = floor;
        while (currentFloor != destinationFloor) {
            moveElevator();
        }
    }

    private void moveToFloor(int floor) {
        destinationFloor = floor;
        while (currentFloor != destinationFloor) {
            moveElevator();

        }
    }

    private void enterElevator() {
        for (int i = 0; i < queue.size(); i++) {
            if (currentFloor == queue.get(i).getStartingFloor()) {
                System.out.println("The " + queue.get(i).getName() + " enter the elevator");
                inElevator.add(queue.remove(i));
                i--;
            }
        }
    }

    private void moveElevator() {
        if (destinationFloor > currentFloor) {
            currentFloor++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
            System.out.println("The elevator moved to " + currentFloor + " floor");
        } else if (destinationFloor < currentFloor) {
            currentFloor--;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
            System.out.println("The elevator is now on " + currentFloor + " floor");
        } else if (destinationFloor == currentFloor) {
            System.out.println("The elevator is now on requested floor");
        }
    }

    void addRaiderToQueue(Raider student) {
        queue.add(student);
    }
}
