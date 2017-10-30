public class Raider implements Runnable {
    private int targetFloor;
    private int startingFloor;
    private String name;
    private boolean left;

    Raider(int targetFloor, int startingFloor, String name) {
        super();
        this.targetFloor = targetFloor;
        this.startingFloor = startingFloor;
        this.name = name;
    }

    void acquire() {
        System.out.println(name + " call elevator");
        left = false;
        new Thread(this).start();
    }

    int getTargetFloor() {
        return targetFloor;
    }


    int getStartingFloor() {
        return startingFloor;
    }

    String getName() {
        return name;
    }

    void setLeft() {
        System.out.println(name + " thread ended");
        left = true;
    }

    @Override
    public void run() {
        if (startingFloor < targetFloor) {
            System.out.println(name + " want to go up from floor " + startingFloor + " to " + targetFloor + " floor");
        } else {
            System.out.println(name + " want to go down from floor " + startingFloor + " to " + targetFloor + " floor");
        }
        while (!left) {

        }
    }
}
