import java.util.concurrent.Semaphore;

public class Book {
    Semaphore readLocker;
    Semaphore writeLocker;
    boolean timeForWriters;

    public Book() {
        this.readLocker = new Semaphore(10);
        this.writeLocker = new Semaphore(1);
        timeForWriters = false;
    }

    public boolean read(String name) {
        if (writeLocker.availablePermits() == 1 && !timeForWriters) {
            try {
                readLocker.acquire();
                System.out.println(name + " czyta. Dane są czytane przez  " + (10 - readLocker.availablePermits()) + " czytelnikow");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                readLocker.release();
                System.out.println(name + " przestal czytac.  Dane są czytane przez  " + (10 - readLocker.availablePermits()) + " czytelnikow");
                return true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timeForWriters = true;
        }
        return false;
    }

    public synchronized boolean write(String name) {
        if (readLocker.availablePermits() == 10 && writeLocker.availablePermits() == 1) {
            try {
                writeLocker.acquire();
                System.out.println("Zapis zablokowany\n" + name + " pisze");
                System.out.println(10-readLocker.availablePermits() + " czytelnikow czyta");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Zapis zwolniony");
                writeLocker.release();
                return true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timeForWriters = false;
        }
        return false;
    }
}
