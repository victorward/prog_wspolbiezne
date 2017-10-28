import java.util.ArrayList;

public class Elevator {
    private ArrayList<Raider> queue;
    private ArrayList<Raider> raiders;
    private volatile int currentFloor;
    private volatile int destinationFloor;
    private volatile boolean isWaiting;

    Elevator() {
        isWaiting = true;
        currentFloor = 0;
        destinationFloor = 0;
        queue = new ArrayList<>();
        raiders = new ArrayList<>();

        waitForOrder();
    }

    private void moveUp(){
        currentFloor +=1;
        System.out.println("Aktualne pietro: "+ currentFloor);
    }
    private void moveDown(){
        currentFloor -=1;
        System.out.println("Aktualne pietro: "+ currentFloor);
    }

    public synchronized void waitForOrder(){
        System.out.println("Elevator is empty, waiting for order");
        isWaiting = true;
    }

    public synchronized void orderElevator(int destination){
        if(isWaiting){
            System.out.println("Winda zamowiona na pietro "+ destination);
            isWaiting = false;
            destinationFloor = destination;
            while(currentFloor != destinationFloor){
                checkRaiders();
                moveElevator();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public synchronized void callToFloor(int destination){
        destinationFloor = destination;
        while(currentFloor != destinationFloor){
            checkRaiders();
            moveElevator();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(raiders.size()==0){

        }
    }

    public synchronized void enterElevator(Raider raider){
        raiders.add(raider);
    }

    public boolean getIsWaiting(){
        return isWaiting;
    }
    public int getCurrentFloor(){
        return currentFloor;
    }

    private void moveElevator(){
        if(currentFloor<destinationFloor){
            moveUp();
        }else if(currentFloor>destinationFloor){
            moveDown();
        }
    }
    private void checkRaiders(){
        for(int i = 0; i< raiders.size(); i++){
            if(raiders.get(i).getDestinationFloor()==currentFloor){
                raiders.remove(i);
                i--;
            }
        }
    }

//    public void addRaiderToQueue(Raider student) {
//        queue.add(student);
//    }
}
