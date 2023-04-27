package cascadia;

public class BotTimer {
    private final static double TIME_ALLOWED = 4.5;
    private static long startTime;

    public static void startTimer(){
        startTime = System.nanoTime();

    }

    /**
     * True if there is time left.
     * Otherwise, its False
     * @return Boolean
     */
    public static boolean checkTimeLeft(){
        double milliSeconds = (System.nanoTime() - startTime)*.000001;
        return !(milliSeconds > TIME_ALLOWED);
    }
}
