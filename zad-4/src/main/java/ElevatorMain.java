import java.util.Random;

public class ElevatorMain {

    public static void main(String[] args) {
        ElevatorMain elevatorMain = new ElevatorMain();
        elevatorMain.startElevator(10, 2);
    }

    private void startElevator(int nFloors, int nRaiders) {
        System.out.println("The elevator is starting...");
        System.out.println("***.**.*.**.***.**.*.**.***.**.*.**.***");
        System.out.println("Number of floors in building: " + nFloors);
        System.out.println("Number of raiders: " + nRaiders);
        System.out.println("***.**.*.**.***.**.*.**.***.**.*.**.***");
        Elevator elevator = new Elevator();
        Random startFloor = new Random();
        Random endFloor = new Random();
        for (int id = 1; id <= nRaiders; id++) {
            int start;
            int end;
            do {
                start = startFloor.nextInt(nFloors);
                end = endFloor.nextInt(nFloors);
            } while (start == end);
            Raider raider = new Raider(start, end, "[Raider " + id + "]");
            elevator.addRaiderToQueue(raider);
        }
//        elevator.addRaiderToQueue(new Raider(7,2, "[masha]"));
//        elevator.addRaiderToQueue(new Raider(10,2, "[pasha]"));
        elevator.run();
    }
}
