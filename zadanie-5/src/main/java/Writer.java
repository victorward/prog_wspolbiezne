public class Writer implements Runnable {
    Book book;
    int id;

    public Writer(int id, Book book) {
        this.book = book;
        this.id = id;
    }

    @Override
    public void run() {
        while (true) {
            Sleeping.sleep();
            System.out.println("Writer " + id + " want to write");
            book.acquireWriteLock(id);
            Sleeping.sleep();
            book.acquireWriteLock(id);
        }
    }
}
