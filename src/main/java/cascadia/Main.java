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

import cascadia.scoring.Scoring;

public class Main {
    public static void main(String[] args) {
        Display.welcome();  // prints cascadia welcome message

        /*
         * sleep so there's a bit of time after the welcome to cascadia message
         * is printed, just to make the output nicer.
         */
        Display.sleep(300);

        // game starts here
        Game g = new Game();
        g.startGame();

        Scoring.startScoring();
        Display.endScreen();
    }
}
