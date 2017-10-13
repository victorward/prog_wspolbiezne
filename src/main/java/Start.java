import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Semaphore;

//rozwiazanie problemu gdy elementy w zbiorach A i B się powtarzaja

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
        boolean nextIteration = false;
        while (itA < numberOfElements && itB < numberOfElements) {
            //zmienna pomocnicza ktora ustawiam na true jest w posortowanym zbiorze A lub B wystepuja te same liczby jedna za druga
            // (wtedy zwiekszam licznik aby nie analizowac tej samej liczby drugi raz
            nextIteration = false;
            if (itA > 0 && A[itA] == A[itA-1]){
                itA++;
                nextIteration = true;
            }
            if (itB > 0 && B[itB] == B[itB-1]){
                itB++;
                nextIteration = true;
            }

            //wlasciwe rozwiazanie problemu (gdy mam pewnosc ze liczby w jednym zbiorze sie nie powtarzaja
            if (!nextIteration) {
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
        }
        //przepisywanie ewentualnych pozostalych liczb ze zbioru A i B do zbioru C
        for (int i=itA; i<A.length; i++){
            if (i > 0  && A[i] > A[i-1]) {
                C[itC] = A[i];
                itC++;
            }
        }
        for (int i=itB; i<B.length; i++){
            if (i > 0 && B[i] > B[i - 1]) {
                C[itC] = B[i];
                itC++;
            }
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