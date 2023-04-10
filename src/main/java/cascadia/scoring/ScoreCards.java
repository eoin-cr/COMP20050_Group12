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

package cascadia.scoring;

import cascadia.Display;
import java.util.Random;

public class ScoreCards {
	/*
	stores the 5 scorecard options selected,
	each randomly generated from a choice of 4 possible options of cards
	indexing:
	index 0 stores Bear scorecard option as a string		(B1,B2,B3,B4)
	index 1 stores Elk scorecard option as a string		(E1,E2,E3,E4)
	index 2 stores Salmon scorecard option as a string		(S1,S2,S3,S4)
	index 3 stores Hawk scorecard option as a string		(H1,H2,H3,H4)
	index 4 stores Fox scorecard option as a string		(F1,F2,F3,F4)
	*/
	private final static String[] scorecards = new String[5];

	public static String[] getScorecards() {
		return scorecards;
	}

	public static void generateScorecards() {
		Random rand = new Random();
		int NUM_SCORECARDS = 5;
		int[] randNums = new int[NUM_SCORECARDS];
		
		for (int i = 0; i < 5; i++) {
			randNums[i] = 1 + rand.nextInt(3); //generate a number between 1-4
		}
		bearCards(randNums[0]);
		elkCards(randNums[1]);
		salmonCards(randNums[2]);
		hawkCards(randNums[3]);
		foxCards(randNums[4]);
		
		printScoreCardRules();
	}
	
	private static void bearCards(int randNum) {
		switch (randNum) {
			case 1 -> scorecards[0] = "B1";
			case 2 -> scorecards[0] = "B2";
			case 3 -> scorecards[0] = "B3";
			default -> throw new IllegalArgumentException("Invalid randNum choice (" + randNum + ") for Bear "
					+ "scorecard");
		}	
	}
	
	private static void elkCards(int randNum) {
		switch (randNum) {
			case 1 -> scorecards[1] = "E1";
			case 2 -> scorecards[1] = "E2";
			case 3 -> scorecards[1] = "E3";
			default -> throw new IllegalArgumentException("Invalid randNum choice (" + randNum + ") for "
					+ "Elk scorecard");
		}	
	}
	
	private static void salmonCards(int randNum) {
		switch (randNum) {
			case 1 -> scorecards[2] = "S1";
			case 2 -> scorecards[2] = "S2";
			case 3 -> scorecards[2] = "S3";
			default -> throw new IllegalArgumentException("Invalid randNum choice (" + randNum + ") for "
					+ "Salmon scorecard");
		}	
	}
	
	private static void hawkCards(int randNum) {
		switch (randNum) {
			case 1 -> scorecards[3] = "H1";
			case 2 -> scorecards[3] = "H2";
			case 3 -> scorecards[3] = "H3";
			default -> throw new IllegalArgumentException("Invalid randNum choice (" + randNum + ") for "
					+ "Hawk scorecard");
		}	
	}
	
	private static void foxCards(int randNum) {
		switch (randNum) {
			case 1 -> scorecards[4] = "F1";
			case 2 -> scorecards[4] = "F2";
			case 3 -> scorecards[4] = "F3";
			default -> throw new IllegalArgumentException("Invalid randNum choice (" + randNum + ") for "
					+ "Fox scorecard");
		}	
	}
	
	public static void printScoreCardRules() {
		Display.outln("Below are the scorecards drawn for possible Wildlife Token placements "
				+ "on your board:\n");
		for (String s : scorecards) {
			switch (s) {
				case "B1" -> {
					Display.outln("""
							BEAR SCORECARD 1:\s
							Score as below, based on the total number of\s
							pairs of exactly 2 bears placed side by side.
							""");
					Display.outln("PAIRS   ||   POINTS");
					Display.outln("-------------------");
					Display.outln("  1     ||     4   ");
					Display.outln("  2     ||     11  ");
					Display.outln("  3     ||     19  ");
					Display.outln("  4     ||     27  ");
					Display.outln("");
				}
				case "B2" -> {
					Display.outln("""
							BEAR SCORECARD 2:\s
							Score 10 points for each group of exactly three bears.
							""");
					Display.outln("TRIPLE  ||   POINTS");
					Display.outln("-------------------");
					Display.outln("  1     ||     10  ");
					Display.outln("");
				}
				case "B3" -> {
					Display.outln("""
							BEAR SCORECARD 3:\s
							Score as below for each group of bears 1-3 in size,\s
							with a bonus of 3 points for having one of each of the 3 group sizes.
							""");
					Display.outln("GROUP   ||   POINTS");
					Display.outln("-------------------");
					Display.outln("  1     ||     2   ");
					Display.outln("  2     ||     5   ");
					Display.outln("  3     ||     8   ");
					Display.outln("  ALL   ||    +3   ");
					Display.outln("");
				}
				case "E1" -> {
					Display.outln("""
							ELK SCORECARD 1:\s
							Score as below for lines of elk in any orientation,\s
							connected from flat side to flat side of the hexagons.
							""");
					Display.outln("LINE    ||   POINTS");
					Display.outln("-------------------");
					Display.outln("  1     ||     2   ");
					Display.outln("  2     ||     5   ");
					Display.outln("  3     ||     9   ");
					Display.outln("  4     ||     13  ");
					Display.outln("");
				}
				case "E2" -> {
					Display.outln("""
							ELK SCORECARD 2:\s
							Score as below for each contiguous group of elk, based on size.\s
							These groups may be of any shape or size, not necessarily in a line or group exclusively.
							""");
					Display.outln("GROUP   ||   POINTS");
					Display.outln("-------------------");
					Display.outln("  1     ||     2   ");
					Display.outln("  2     ||     4   ");
					Display.outln("  3     ||     7   ");
					Display.outln("  4     ||     10  ");
					Display.outln("  5     ||     14  ");
					Display.outln("  6     ||     18  ");
					Display.outln("  7     ||     23  ");
					Display.outln("  8+    ||     28  ");
					Display.outln("");
				}
				case "E3" -> {
					Display.outln("""
							ELK SCORECARD 3:\s
							Score for one lone elk, two elk side by side, three elk in a triangle formation,\s
							or four elk in a diamond formation.
							""");
					Display.outln("GROUP   ||   POINTS");
					Display.outln("-------------------");
					Display.outln("  1     ||     2   ");
					Display.outln("  2     ||     5   ");
					Display.outln("  3     ||     9   ");
					Display.outln("  4     ||     13  ");
					Display.outln("");
				}
				case "S1" -> {
					Display.outln("""
							SALMON SCORECARD 1:\s
							Score as below for each run of salmon (where a run is a group of adjacent salmon
							where each salmon is connected to no more than 2 other salmon), based on size,\s
							up to a max of 7.
							""");
					Display.outln("RUN     ||   POINTS");
					Display.outln("-------------------");
					Display.outln("  1     ||     2   ");
					Display.outln("  2     ||     4   ");
					Display.outln("  3     ||     7   ");
					Display.outln("  4     ||     11  ");
					Display.outln("  5     ||     15  ");
					Display.outln("  6     ||     20  ");
					Display.outln("  7+    ||     26  ");
					Display.outln("");
				}
				case "S2" -> {
					Display.outln("""
							SALMON SCORECARD 2:\s
							Score as below for each run of salmon (where a run is a group of adjacent salmon
							where each salmon is connected to no more than 2 other salmon), based on size,\s
							up to a max of 4.
							""");
					Display.outln("RUN     ||   POINTS");
					Display.outln("-------------------");
					Display.outln("  1     ||     2   ");
					Display.outln("  2     ||     4   ");
					Display.outln("  3     ||     8   ");
					Display.outln("  4+    ||     12  ");
					Display.outln("");
				}
				case "S3" -> {
					Display.outln("""
							SALMON SCORECARD 3:\s
							Score as below for each run of salmon (where a run is a group of adjacent salmon
							where each salmon is connected to no more than 2 other salmon), based on size,\s
							up to a max of 5.
							""");
					Display.outln("RUN     ||   POINTS");
					Display.outln("-------------------");
					Display.outln("  1     ||     2   ");
					Display.outln("  2     ||     5   ");
					Display.outln("  3     ||     9   ");
					Display.outln("  4     ||     11  ");
					Display.outln("  5+    ||     17  ");
					Display.outln("");
				}
				case "H1" -> {
					Display.outln("""
							HAWK SCORECARD 1:\s
							Score as below for each hawk that is not adjacent to any other hawk.
							""");
					Display.outln("SINGLES ||   POINTS");
					Display.outln("-------------------");
					Display.outln("  1     ||      2  ");
					Display.outln("  2     ||      5  ");
					Display.outln("  3     ||      8  ");
					Display.outln("  4     ||     11  ");
					Display.outln("  5     ||     14  ");
					Display.outln("  6     ||     18  ");
					Display.outln("  7     ||     22  ");
					Display.outln("  8+    ||     26  ");
					Display.outln("");
				}
				case "H2" -> {
					Display.outln("""
							HAWK SCORECARD 2:\s
							Score as below for each hawk based on its line of sight\s
							to other hawks, i.e. not adjacent to each other and not interrupted by\s
							other wildlife or a gap in the tiles.
							""");
					Display.outln("IN SIGHT||   POINTS");
					Display.outln("-------------------");
					Display.outln("  1     ||      2  ");
					Display.outln("  2     ||      5  ");
					Display.outln("  3     ||      9  ");
					Display.outln("  4     ||     12  ");
					Display.outln("  5     ||     16  ");
					Display.outln("  6     ||     20  ");
					Display.outln("  7     ||     24  ");
					Display.outln("  8     ||     28  ");
					Display.outln("");
				}
				case "H3" -> {
					Display.outln("""
							HAWK SCORECARD 3:\s
							Score 3 points for each line of sight between two hawks\s
							(i.e. not adjacent to each other and not interrupted by\s
							other wildlife or a gap in the tiles).
							(Note: multiple lines of sight may involve the same hawk).
							""");
					Display.outln("IN SIGHT||   POINTS");
					Display.outln("-------------------");
					Display.outln("2 HAWKS ||     +3  ");
					Display.outln("");
				}
				case "F1" -> {
					Display.outln("""
							FOX SCORECARD 1:\s
							Score for each fox as below, based on the number of unique animal types\s
							(including other foxes) directly adjacent to it.""");
					Display.outln("ANIMALS ||   POINTS");
					Display.outln("-------------------");
					Display.outln("  1     ||      1  ");
					Display.outln("  2     ||      2  ");
					Display.outln("  3     ||      3  ");
					Display.outln("  4     ||      4  ");
					Display.outln("  5     ||      5  ");
					Display.outln("");
				}
				case "F2" -> {
					Display.outln("""
							FOX SCORECARD 2:\s
							Score for each fox as below, based on the number of unique animal pairs\s
							(not including other fox pairs) directly adjacent to it.\s
							Pairs of other animals do not need to be adjacent to each other.
							The same wildlife cannot count for more than one pair.
							""");
					Display.outln("PAIRS   ||   POINTS");
					Display.outln("-------------------");
					Display.outln("  1     ||      3  ");
					Display.outln("  2     ||      5  ");
					Display.outln("  3     ||      7  ");
					Display.outln("");
				}
				case "F3" -> {
					Display.outln("""
							FOX SCORECARD 3:\s
							Score for each fox as below, based on the number of similar animals\s
							(not including other foxes) directly adjacent to it.\s
							Only score the most abundant adjacent animal type.\s
							""");
					Display.outln("SIMILAR ||   POINTS");
					Display.outln("-------------------");
					Display.outln("  1     ||      1  ");
					Display.outln("  2     ||      2  ");
					Display.outln("  3     ||      3  ");
					Display.outln("  4     ||      4  ");
					Display.outln("  5     ||      5  ");
					Display.outln("  6     ||      6  ");
					Display.outln("");
				}
				default -> throw new IllegalArgumentException("Invalid choice of scorecard "
						+ "to be printed (" + s + ")");
			}
		}
	}
}
