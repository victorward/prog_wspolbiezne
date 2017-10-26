import java.util.Random;

/**
 * @author Yuriy on 26.10.2017.
 */
public class ReaderWriter {
    public static void main(String[] args) {
        ReaderWriter readerWriter = new ReaderWriter();
        readerWriter.start(2, 5);
    }

    private void start(int nWriters, int nReaders) {
        Random random = new Random();
        Book book = new Book();
        int delay;
        for (int i = 1; i <= nWriters; i++) {
            delay = random.nextInt(10) * 100;
            new Writer(book, delay);
            System.out.println("Writer " + i + " has delay " + delay);
        }

        for (int i = 1; i <= nReaders; i++) {
            delay = random.nextInt(10) * 100;
            new Reader(book, delay);
            System.out.println("Reader " + i + " has delay " + delay);
        }
    }
}
