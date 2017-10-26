/**
 * @author Yuriy on 26.10.2017.
 */
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
        System.out.println(name + " want to go to " + targetFloor + " floor");
        return targetFloor;
    }


    int getStartingFloor() {
        System.out.println(name + " is now on " + startingFloor + " floor");
        return startingFloor;
    }

    String getName() {
        return name;
    }

    void setLeft() {
        left = true;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        while (!left) {
          
        }
    }
}
