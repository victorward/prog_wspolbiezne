import java.util.concurrent.Semaphore;

/**
 * @author Yuriy on 26.10.2017.
 */
public class Book {
    Semaphore readLocker;
    Semaphore writeLocker;

    public Book() {
        this.readLocker = new Semaphore(10);
        this.writeLocker = new Semaphore(1);
    }

    public boolean read() {
        if (writeLocker.availablePermits() == 1) {
            try {
                readLocker.acquire();
                System.out.println("Odczyt zablokowany\n Dane są czytane przez  " + (10 - readLocker.availablePermits()) + " czytelnikow");
                System.out.println("Odczyt zwolniony\n Dane są czytane przez  " + (10 - readLocker.availablePermits() - 1) + " czytelnikow");
                readLocker.release();
//                return true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public synchronized boolean write() {
        if (readLocker.availablePermits() == 10) {
            try {
                writeLocker.acquire();
                System.out.println("Zapis zablokowany\n Trwa zapis");
                Thread.sleep(200);
                System.out.println("Zapis zwolniony");
                writeLocker.release();
                return true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
