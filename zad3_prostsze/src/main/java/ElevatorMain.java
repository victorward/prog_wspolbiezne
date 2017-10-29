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
        new Raider(elevator, 0, 6, "Anna");
        new Raider(elevator, 0, 6, "Bella");
        new Raider(elevator, 6, 1, "Cyndia");

    }
}
