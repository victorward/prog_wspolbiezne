/**
 * @author Yuriy on 26.10.2017.
 */
public class Reader implements Runnable {
    Book book;
    long delay;

    public Reader(Book book, long delay) {
        this.book = book;
        this.delay = delay;
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
            flag = book.read();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
