public class Person implements Runnable {

    private Lift lift;
    private int destinationFloor;
    private int startFloor;
    private String name;
    private boolean entered;

    public Person(Lift lift, int destinationFloor, int startFloor, String name){
        this.lift = lift;
        this.destinationFloor = destinationFloor;
        this.startFloor = startFloor;
        this.name = name;
        entered = false;
        new Thread(this).start();
    }
    @Override
    public void run() {
        lift.orderElevator(startFloor, this);
    }
    public Lift getLift() {
        return lift;
    }
    public void setLift(Lift lift) {
        this.lift = lift;
    }
    public int getDestinationFloor() {
        return destinationFloor;
    }
    public void setDestinationFloor(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }
    public int getStartFloor() {
        return startFloor;
    }
    public void setStartFloor(int startFloor) {
        this.startFloor = startFloor;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isEntered() {
        return entered;
    }
    public void setEntered(boolean entered) {
        this.entered = entered;
    }


}
