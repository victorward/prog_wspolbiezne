import java.util.concurrent.Semaphore;

public class Book {
    Semaphore readLocker;
    Semaphore writeLocker;
    int readersCount;

    public Book() {
        this.readLocker = new Semaphore(1);
        this.writeLocker = new Semaphore(1);
        readersCount = 0;
    }

    public void acquireReadLock(int readerNum) {
        try{
            readLocker.acquire();
        }
        catch (InterruptedException e) {}

        ++readersCount;

        // if I am the first reader tell all others
        // that the book is being read
        if (readersCount == 1){
            try{
                writeLocker.acquire();
            }
            catch (InterruptedException e) {}
        }

        System.out.println("Reader " + readerNum + " is reading. Reader count = " + readersCount);

        readLocker.release();
    }

    public void releaseReadLock(int readerNum) {
        try{
            readLocker.acquire();
        }
        catch (InterruptedException e) {}

        --readersCount;

        // if I am the last reader tell all others
        // that the database is no longer being read
        if (readersCount == 0){
            writeLocker.release();
        }

        System.out.println("Reader " + readerNum + " is done reading. Reader count = " + readersCount);

        //mutual exclusion for readerCount
        readLocker.release();
    }

    public void acquireWriteLock(int writerNum) {
        try{
            writeLocker.acquire();
        }
        catch (InterruptedException e) {}
        System.out.println("Writer " + writerNum + " is writing.");
    }

    public void releaseWriteLock(int writerNum) {
        System.out.println("Writer " + writerNum + " is done writing.");
        writeLocker.release();
    }
}
