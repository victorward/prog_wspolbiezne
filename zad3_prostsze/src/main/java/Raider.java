

public class Raider implements Runnable {

    private Elevator elevator;
    private int destinationFloor;
    private int startFloor;
    private String name;
    private boolean entered;

    public Raider(Elevator elevator,int destinationFloor,int startFloor, String name){
        this.elevator = elevator;
        this.destinationFloor = destinationFloor;
        this.startFloor = startFloor;
        this.name = name;
        entered = false;
        new Thread(this).start();
    }
    @Override
    public void run() {
        while(elevator.getCurrentFloor()!=destinationFloor){
            if(!entered){
                if(elevator.getIsWaiting()){
                    elevator.orderElevator(startFloor);
                }
                if(elevator.getCurrentFloor()==startFloor && !entered){
                    elevator.enterElevator(this);
                    entered = true;
                    System.out.println("Raider "+name + " entered the elevator");
                }
            }else if(entered){
                elevator.callToFloor(destinationFloor);
            }
        }

        System.out.println("Student "+name + " arrived to his destination floor");

    }
    public Elevator getElevator() {
        return elevator;
    }
    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
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
