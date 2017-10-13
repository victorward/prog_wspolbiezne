import java.util.*;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.symmetricDifference(5, 12, 3, 1);
    }

    private void symmetricDifference(int sizeX, int sizeY, int numberOfProcesses, int numberOfPermits) {
        SortedSet<Integer> setX = new TreeSet<>();
        SortedSet<Integer> setY = new TreeSet<>();
        SortedSet<Integer> setC = new TreeSet<>();

        fillAndSort(setX, setY, sizeX, sizeY);
        showArrays(setX, setY);

        Semaphore semaphore = new Semaphore(numberOfPermits, true);
        MyThread p[] = new MyThread[numberOfProcesses];

        //algorytm na znajdywanie różnicy symetrycznej
        for (Integer integer : setX) {
            if (setY.contains(integer)) {
                setY.remove(integer);
            } else {
                setY.add(integer);
            }
        }

        System.out.println("\nOutput");
        for (Integer x : setY) System.out.print(x + " ");
    }

    private void fillAndSort(Set<Integer> setX, Set<Integer> setY, int sizeX, int sizeY) {
        Random random = new Random();
        while (setX.size() != sizeX) {
            setX.add(random.nextInt(sizeX * 2) + 1);
        }
        while (setY.size() != sizeY) {
            setY.add(random.nextInt(sizeY * 2) + 1);
        }
    }

    private void showArrays(Set<Integer> setX, Set<Integer> setY) {
        System.out.println("Array X");
        for (Integer x : setX) System.out.print(x + " ");
        System.out.println("\nArray Y");
        for (Integer y : setY) System.out.print(y + " ");

    }
}

class MyThread extends Thread {
    private int threadId;
    private Semaphore semaphore;

    public MyThread(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                semaphore.acquire();
                System.out.println(threadId + " " + 1);
                semaphore.release();
            } catch (InterruptedException e) {
                System.out.println("Exception " + e.toString());
            }
        }
    }
}