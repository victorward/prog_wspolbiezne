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
        System.out.println("Array X");
        showArray(setX);
        System.out.println("\nArray Y");
        showArray(setY);

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
        showArray(setY);
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

    private void showArray(Set<Integer> set) {
        for (Integer x : set) System.out.print(x + " ");
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