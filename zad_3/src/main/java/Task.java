import java.util.ArrayList;
import java.util.Random;

/**
 * @author Yuriy on 25.10.2017.
 */
public class Task {
    public static void main(String[] args) {
        System.out.println("Launching elevator...");
        Task task = new Task();
        try {
            task.startSimulation(10, 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startSimulation(int nFloors, int nRaiders) throws InterruptedException {
        Building building = new Building(nFloors);
        Elevator elevator = building.getElevator();
        ArrayList<Raider> raiders = new ArrayList<>();

        System.out.println("***.**.*.**.***.**.*.**.***.**.*.**.***");
        System.out.println("Number of floors in building: " + nFloors);
        System.out.println("Number of raiders: " + nRaiders);
        System.out.println("***.**.*.**.***.**.*.**.***.**.*.**.***");
        Random startFloor = new Random();
        Random endFloor = new Random();
        for (int i = 0; i < nRaiders; i++) {
            Raider raider = new Raider(i, startFloor.nextInt(nFloors), endFloor.nextInt(nFloors));
            raiders.add(raider);
        }

        // Start raider threads
        for (Raider r : raiders) {
            r.getThread().start();
        }

        // Start elevator
        elevator.getThread().start();

        // Wait for raiders threads to terminate
        for (Raider r : raiders) {
            r.getThread().join();
        }

        // Stop elevator
        elevator.getThread().interrupt();


    }

}
