/**
 * @author Yuriy on 25.10.2017.
 */
class Building {
    private int nFloors;
    private Elevator elevator;

    Building(int nFloors) {
        this.nFloors = nFloors;
        this.elevator = new Elevator(nFloors);
    }

    Elevator getElevator() {
        return elevator;
    }


}
