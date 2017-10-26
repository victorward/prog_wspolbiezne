/**
 * @author Yuriy on 25.10.2017.
 */
class ElevatorEvent {
    private int waiters;
    private boolean eventInProgress;

    ElevatorEvent() {
        this.waiters = 0;
        this.eventInProgress = false;
    }

    int getWaiters() {
        return waiters;
    }

    // Rider thread signals its arrival (rider thread is waiting for elevator door to open)
    synchronized void arrive(int floor, int raiderId) {
        this.waiters++;
        if (this.eventInProgress) {
            return;
        }
        while (!this.eventInProgress) {
            try {
                System.out.println(this.waiters + " rider/s waiting for elevator on floor " + floor);
                super.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Event barrier raises an event (elevator door opening)
    synchronized void raise(int floor) {
        if (this.eventInProgress) {
            return;
        }
        System.out.println("Elevator doors are opened on floor " + floor);
        this.eventInProgress = true;
        notifyAll();
        while (this.waiters != 0) {
            try {
                System.out.println("Elevator is waiting for raiders to enter door on floor " + floor);
                super.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.eventInProgress = false;
        System.out.println("Elevator is closing door on floor " + floor);
    }

    // Rider thread signals its exit (rider has exited the elevator)
    synchronized void complete(int floor) {
        this.waiters--;
        if (this.waiters == 0) {
            System.out.println("There are no more raiders waiting for elevator on floor " + floor);
            notifyAll();
        } else {
            System.out.println(this.waiters + " rider/s left waintg to enter elevator on floor " + floor);
        }
    }
}
