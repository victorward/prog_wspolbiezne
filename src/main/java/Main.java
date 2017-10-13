import sun.plugin.javascript.navig.Array;

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

        fillAndSort(setX, setY, sizeX, sizeY);
        System.out.println("Array X");
        showArray(setX);
        System.out.println("\nArray Y");
        showArray(setY);

        Semaphore semaphore = new Semaphore(numberOfPermits, true);
        MyThread p[] = new MyThread[numberOfProcesses];

        //algorytm na znajdywanie różnicy symetrycznej
//        SortedSet<Integer> setC = new TreeSet<>(setX);
//        setC.removeAll(setY);
//        setY.removeAll(setX);
//        setC.addAll(setY);

        ArrayList<Integer> tmpX = new ArrayList<>(setX);
        ArrayList<Integer> tmpY = new ArrayList<>(setY);

        int chunkSizeX = tmpX.size() % 2 == 0 ? tmpX.size() / 2 : (tmpX.size() / 2) + 1;
        int chunkSizeY = tmpY.size() % 2 == 0 ? tmpY.size() / 2 : (tmpY.size() / 2) + 1;

        ArrayList<Integer> tmpX_part_1 = new ArrayList<>(part(tmpX.subList(0, chunkSizeX), tmpY.subList(0, chunkSizeY)));
        ArrayList<Integer> tmpX_part_2 = new ArrayList<>(part(tmpX.subList(chunkSizeX, tmpX.size()), tmpY.subList(chunkSizeY, tmpY.size())));

        SortedSet<Integer> out = new TreeSet<>(tmpX_part_1);
        out.addAll(tmpX_part_2);

        System.out.println("\nOutput");
        showArray(out);
    }

    private List<Integer> part(List<Integer> setX, List<Integer> setY) {
        List<Integer> tmpY = new ArrayList<>(setY);
        List<Integer> out = new ArrayList<>(setX);
        out.removeAll(tmpY);
        tmpY.removeAll(setX);
        out.addAll(tmpY);
        return out;
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

    private void showArray(List<Integer> set) {
        for (Integer x : set) System.out.print(x + " ");
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