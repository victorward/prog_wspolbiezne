import java.util.ArrayList;

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

        ArrayList<Raider> raiders = new ArrayList<>();


        raiders.add(new Raider(3, 0, "Cyndia"));
        raiders.add(new Raider(4, 0, "Cyndia's friend"));
        raiders.add(new Raider(8, 6, "Bella"));
        raiders.add(new Raider(5, 4, "Anna"));
        Elevator elevator = new Elevator(raiders);

        for (Raider r : raiders) {
            r.setElevator(elevator);
            r.getThread().start();
        }
    }
}
