import java.util.Random;

public class ReaderWriter {
    public static void main(String[] args) {
        ReaderWriter readerWriter = new ReaderWriter();
        readerWriter.start(5, 8);
    }

    private void start(int nWriters, int nReaders) {
        Random random = new Random();
        Book book = new Book();
        int delay;
        for (int i = 1; i <= nWriters; i++) {
            delay = random.nextInt(10) * 100;
            String name = "Writer"+i;
            new Writer(book, delay, name);
            System.out.println(name + " has delay " + delay);
        }

        for (int i = 1; i <= nReaders; i++) {
            delay = random.nextInt(10) * 100;
            String name = "Reader"+i;
            new Reader(book, delay, name);
            System.out.println(name + " has delay " + delay);
        }
    }
}
