public class Start {
    public static void main(String[] args) {
        Lift lift = new Lift();
        Person a = new Person(lift, 6, 3, "A");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Person b = new Person(lift, 10, 1, "B");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Person f = new Person(lift, 8, 4, "C");
    }
}

