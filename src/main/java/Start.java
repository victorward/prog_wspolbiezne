import java.util.Arrays;
import java.util.Random;
import java.util.Stack;
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
        int C[] = new int[numberOfElements*2];
        Random random = new Random();
        for(int i=0; i<A.length; i++){
            A[i] = random.nextInt(10) + 1;
            B[i] = random.nextInt(10) + 1;
            //System.out.println(A[i] + " " + B[i]);
        }

        Arrays.sort(A);
        Arrays.sort(B);
        System.out.println("Posortowane tablice:");
        for(int i=0; i<A.length; i++){
            System.out.println(A[i] + " " + B[i]);
        }

        //algorytm na znajdywanie różnicy symetrycznej
        int itA=0, itB=0, itC=0;
        while (itA < numberOfElements && itB < numberOfElements) {
            if (A[itA] < B[itB]) {
                C[itC] = A[itA];
                itA++;
                itC++;
            } else if (B[itB] < A[itA]) {
                C[itC] = B[itB];
                itB++;
                itC++;
            } else {
                itA++;
                itB++;
            }
        }
        for (int i=itA; i<A.length; i++){
            C[itC] = A[itA];
            itC++;
        }
        for (int i=itB; i<B.length; i++){
            C[itC] = B[itB];
            itC++;
        }
        System.out.println("Tablica c");
        for(int i=0; i<C.length; i++){
            System.out.print(C[i] + " ");
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