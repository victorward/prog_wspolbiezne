import java.util.ArrayList;

public class Lift {

    private volatile int currentFloor;
    private volatile boolean busy;
    private volatile int destinationFloor;
    public static final int minFloor = 0;
    public static final int maxFloor = 10;
    private ArrayList<Person> passengers;

    public Lift(){
        passengers = new ArrayList<>();
        currentFloor = 0;
        destinationFloor = 0;
        waitForOrder();
    }

    private void informFloor(){
        System.out.println("Winda jest na : " + currentFloor + " pietrze");
    }

    private void moveUp(){
        currentFloor +=1;
        informFloor();
    }
    private void moveDown(){
        currentFloor -= 1;
        informFloor();
    }
    public synchronized void orderElevator(int destination, Person person) {
        System.out.println("Winda zamowiona na pietro " + destination);
        busy = true;
        moveToFloor(destination);
        enterElevator(person);
        moveToFloor(person.getDestinationFloor());
        if(passengers.size()==0){
            waitForOrder();
        }
    }

    private void moveToFloor(int destination) {
        this.destinationFloor = destination;
        while (this.currentFloor != this.destinationFloor) {
            moveElevator();
            checkPassangers();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void waitForOrder(){
        System.out.println("Winda pusta na " + this.currentFloor + " pietrze, oczekuje na wezwanie");
        busy = false;
    }

    public synchronized void enterElevator(Person person){
        passengers.add(person);
        System.out.println("Student "+ person.getName() + " wszedl do windy");
    }
    public boolean getBusy(){
        return busy;
    }
    public int getCurrentFloor(){
        return currentFloor;
    }
    private void moveElevator(){
        if(currentFloor< destinationFloor){
            moveUp();
        }else if(currentFloor> destinationFloor){
            moveDown();
        }
    }
    private void checkPassangers(){
        for(int i=0;i<passengers.size();i++){
            if(passengers.get(i).getDestinationFloor()==currentFloor){
                System.out.println("Student "+ passengers.get(i).getName() + " dotarl na swoje pietro");
                passengers.remove(i);
                i--;
            }
        }
    }
}
