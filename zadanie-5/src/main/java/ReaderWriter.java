public class ReaderWriter {
    public static void main(String[] args) {
        ReaderWriter readerWriter = new ReaderWriter();
        readerWriter.start(5, 3);
    }

    private void start(int nWriters, int nReaders) {
        Book book = new Book();

        Thread[] readers = new Thread[nReaders];
        Thread[] writers = new Thread[nWriters];

        for (int i = 0; i < nReaders; i++) {
            readers[i] = new Thread(new Reader(i, book));
            readers[i].start();
        }

        for (int i = 0; i < nWriters; i++) {
            writers[i] = new Thread(new Writer(i, book));
            writers[i].start();
        }
    }
}
