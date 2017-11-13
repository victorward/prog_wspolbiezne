public class Sleeping {
    private static final int duration = 5;
    private static final int sleep = 100;

    public static void sleep() {
        int sleepTime = (int) (duration * Math.random());
        try {
            int stop = sleepTime * sleep;
//            System.out.println("Sleep by " + stop);
            Thread.sleep(stop);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
