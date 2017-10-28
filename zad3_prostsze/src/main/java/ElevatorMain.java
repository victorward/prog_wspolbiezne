import java.util.Random;

public class ElevatorMain {
    public static void main(String[] args) {
        ElevatorMain elevatorMain = new ElevatorMain();
        elevatorMain.startElevator(10, 3);
    }

    private void startElevator(int nFloors, int nRaiders) {
        System.out.println("The elevator is starting...");
        System.out.println("***.**.*.**.***.**.*.**.***.**.*.**.***");
        System.out.println("Number of floors in building: " + nFloors);
        System.out.println("Number of raiders: " + nRaiders);
        System.out.println("***.**.*.**.***.**.*.**.***.**.*.**.***");
        Elevator elevator = new Elevator();
        new Raider(elevator, 5, 6, "A");
        new Raider(elevator, 0, 1, "B");
        new Raider(elevator, 5, 0, "C");

    }
}
