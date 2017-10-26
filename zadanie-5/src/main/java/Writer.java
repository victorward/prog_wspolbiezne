/**
 * @author Yuriy on 26.10.2017.
 */
public class Writer implements Runnable{
    Book book;
    long delay;

    public Writer(Book book, long delay) {
        this.book = book;
        this.delay = delay;
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
            flag = book.write();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
