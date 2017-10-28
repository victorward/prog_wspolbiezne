import java.util.ArrayList;
import java.util.Random;
public class Task {
    public static void main(String[] args) {
        System.out.println("Launching elevator...");
        Task task = new Task();
        try {
            task.startSimulation(10, 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startSimulation(int nFloors, int nRaiders) throws InterruptedException {
        Building building = new Building(nFloors);
        ArrayList<Raider> raiders = new ArrayList<>();

        System.out.println("***.**.*.**.***.**.*.**.***.**.*.**.***");
        System.out.println("Number of floors in building: " + nFloors);
        System.out.println("Number of raiders: " + nRaiders);
        System.out.println("***.**.*.**.***.**.*.**.***.**.*.**.***");
        Random startFloor = new Random();
        Random endFloor = new Random();
        for (int id = 1; id <= nRaiders; id++) {
            int start;
            int end;
            do {
                start = startFloor.nextInt(nFloors+1);
                end = endFloor.nextInt(nFloors+1);
            } while (start == end);
            Raider raider = new Raider(building, id, start, end);
            raiders.add(raider);
        }

        // Start raider threads
        for (Raider r : raiders) {
            r.getThread().start();
        }

        // Start elevator
        building.getElevator().getThread().start();

        // Wait for raiders threads to terminate
        for (Raider r : raiders) {
            r.getThread().join();
        }

        // Stop elevator
        building.getElevator().getThread().interrupt();

    }

}
