import java.util.*;
import java.util.concurrent.Semaphore;

public class Start {
    public static void main(String[] args) {
        Start main = new Start();
        main.symmetricDifference(5, 12, 1);
    }

    private void symmetricDifference(int sizeX, int sizeY, int numberOfPermits) {
        SortedSet<Integer> setX = new TreeSet<>();
        SortedSet<Integer> setY = new TreeSet<>();
        SymmetricDifferenceSet symmetricDifferenceSet = new SymmetricDifferenceSet();

        fillData(setX, setY, sizeX, sizeY);
        System.out.println("Array X");
        showArray(setX);
        System.out.println("\nArray Y");
        showArray(setY);
        System.out.println();

        Semaphore semaphore = new Semaphore(numberOfPermits, true);

        //algorytm na znajdywanie różnicy symetrycznej
        MyThread one = new MyThread(semaphore, symmetricDifferenceSet, setX, setY, "One");
        MyThread two = new MyThread(semaphore, symmetricDifferenceSet, setY, setX, "Two");
        Output output = new Output(semaphore, symmetricDifferenceSet);
        one.start();
        two.start();
        try {
            one.join();
            two.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        output.start();

    }

    private void fillData(Set<Integer> setX, Set<Integer> setY, int sizeX, int sizeY) {
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

class SymmetricDifferenceSet {
    SortedSet<Integer> set;

    SymmetricDifferenceSet() {
        set = new TreeSet<>();
    }
}

class MyThread extends Thread {
    private Semaphore semaphore;
    private SymmetricDifferenceSet symmetricDifferenceSet;
    private Set<Integer> setIterated;
    private Set<Integer> setCompared;
    private String name;

    MyThread(Semaphore semaphore, SymmetricDifferenceSet symmetricDifferenceSet, Set<Integer> setIterated, Set<Integer> setCompared, String name) {
        this.semaphore = semaphore;
        this.symmetricDifferenceSet = symmetricDifferenceSet;
        this.setIterated = setIterated;
        this.setCompared = setCompared;
        this.name = name;
    }

    public void run() {
        symmetricDifferenceSet.set = findElements(setIterated, setCompared, symmetricDifferenceSet.set);
    }

    private SortedSet<Integer> findElements(Set<Integer> setIterated, Set<Integer> setCompared, SortedSet<Integer> setC) {
        boolean theSameElement;
        int helper;
        Iterator<Integer> it1 = setIterated.iterator();
        Iterator<Integer> it2;
        while (it1.hasNext()) {
            System.out.println("Thread name " + name);
            helper = it1.next();
            theSameElement = false;
            it2 = setCompared.iterator();
            while (it2.hasNext()) {
                if (it2.next() == helper) {
                    theSameElement = true;
                }
            }
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!theSameElement) {
                setC.add(helper);
            }
//            System.out.println("Semafor " + name + " is release");
            semaphore.release();
        }
        return setC;
    }

}

class Output extends Thread {
    private Semaphore semaphore;
    private SymmetricDifferenceSet symmetricDifferenceSet;

    Output(Semaphore semaphore, SymmetricDifferenceSet symmetricDifferenceSet) {
        this.semaphore = semaphore;
        this.symmetricDifferenceSet = symmetricDifferenceSet;
    }

    public void run() {
        System.out.println("Output is calling");
        showArray(symmetricDifferenceSet.set);
    }

    private void showArray(Set<Integer> set) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Output:");
        for (Integer x : set) System.out.print(x + " ");
        semaphore.release();
    }

}