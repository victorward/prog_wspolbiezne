public class Reader implements Runnable {
    Book book;
    long delay;
    String name;

    public Reader(Book book, long delay, String name) {
        this.book = book;
        this.delay = delay;
        this.name = name;
        new Thread(this).start();
    }


    @Override
    public void run() {
        boolean flag = false;
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!flag) {
            flag = book.read(this.name);
        }
    }
}
