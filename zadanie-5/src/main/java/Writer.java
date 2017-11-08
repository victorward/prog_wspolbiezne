public class Writer implements Runnable {
    Book book;
    int id;

    public Writer(int id, Book book) {
        this.book = book;
        this.id = id;
    }

    @Override
    public void run() {
        boolean cont = true;
        while (cont) {
            Sleeping.sleep();
            System.out.println("Writer " + id + " want to write");
            book.acquireWriteLock(id);
            Sleeping.sleep();
            book.releaseWriteLock(id);
        }
    }
}
