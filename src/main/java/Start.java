import java.util.*;
import java.util.concurrent.Semaphore;

public class Start {
    public static void main(String[] args) {
        Start main = new Start();
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
        setC = findElements(setX, setY, setC);
        setC = findElements(setY, setX, setC);

        System.out.println("\nOutput array");
        showArray(setC);
    }

    private SortedSet<Integer> findElements(Set<Integer> setIterated, Set<Integer> setCompared, SortedSet<Integer> setC) {
        boolean theSameElement;
        int helper;
        Iterator<Integer> it1 = setIterated.iterator();
        Iterator<Integer> it2;
        while (it1.hasNext()) {
            helper = it1.next();
            theSameElement = false;
            it2 = setCompared.iterator();
            while (it2.hasNext()) {
                if (it2.next() == helper) {
                    theSameElement = true;
                }
            }
            if (!theSameElement) {
                setC.add(helper);
            }
        }
        return setC;
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