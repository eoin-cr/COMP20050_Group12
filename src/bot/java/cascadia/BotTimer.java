/*
	COMP20050 Group 12
	Eoin Creavin – Student ID: 21390601
	eoin.creavin@ucdconnect.ie
	GitHub ID: eoin-cr

	Mynah Bhattacharyya – Student ID: 21201085
	malhar.bhattacharyya@ucdconnect.ie
	GitHub ID: mynah-bird

	Ben McDowell – Student ID: 21495144
	ben.mcdowell@ucdconnect.ie
	GitHub ID: Benmc1
 */

package cascadia;

public class BotTimer {
    private final static double TIME_ALLOWED = 4.5;
    private static long startTime;

    public static void startTimer() {
        startTime = System.nanoTime();
    }

    /**
     * True if there is time left.
     * Otherwise, its False
     */
    public static boolean isTimeLeft() {
        double milliSeconds = (System.nanoTime() - startTime) * .000001;
        return !(milliSeconds > TIME_ALLOWED);
    }
}

