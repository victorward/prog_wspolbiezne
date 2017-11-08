public class Reader implements Runnable {
    Book book;
    int id;

    public Reader(int id, Book book) {
        this.book = book;
        this.id = id;
    }

    @Override
    public void run() {
        boolean cont = true;
        while (cont) {
            Sleeping.sleep();
            System.out.println("Reader " + id + " want to read");
            book.acquireReadLock(id);
            Sleeping.sleep();
            book.releaseReadLock(id);
        }
    }
}
