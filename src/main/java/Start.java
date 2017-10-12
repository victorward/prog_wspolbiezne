import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Start {
    public static void main(String[] args)
    {
        final int numberOfElements = 10;
        final int numberOfProcesses = 3;
        final int numberOfPermits = 1;

        Semaphore semaphore = new Semaphore(numberOfPermits, true);
        MyThread p[] = new MyThread[numberOfProcesses];

        int A[] = new int[numberOfElements];
        int B[] = new int[numberOfElements];
        Random random = new Random();
        for(int i=0; i<A.length; i++){
            A[i] = random.nextInt(10) + 1;
            B[i] = random.nextInt(10) + 1;
            System.out.println(A[i] + " " + B[i]);
        }

        Arrays.sort(A);
        Arrays.sort(B);
        System.out.println("hahahahahahha");
        for(int i=0; i<A.length; i++){
            System.out.println(A[i] + " " + B[i]);
        }
    }
}

class MyThread extends Thread
{
    private int threadId;
    private Semaphore semaphore;

    public MyThread(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public void run()
    {
        for (int i = 0; i < 3; i++) {
            try {
                semaphore.acquire();
                System.out.println(threadId+ " " + 1);
                semaphore.release();
            } catch (InterruptedException e) {
                System.out.println("Exception " + e.toString());
            }
        }
    }
}














