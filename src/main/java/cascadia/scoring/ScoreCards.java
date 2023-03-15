package cascadia.scoring;

import java.util.Random;
import cascadia.Display;

public class ScoreCards {
	private final static String[] scorecards = new String[5];
//	stores the 5 scorecard options selected,
//	each randomly generated from a choice of 4 possible options of cards
//	indexing:
//	index 0 stores Bear scorecard option as a string		(B1,B2,B3,B4)
//	index 1 stores Elk scorecard option as a string		(E1,E2,E3,E4)
//	index 2 stores Salmon scorecard option as a string		(S1,S2,S3,S4)
//	index 3 stores Hawk scorecard option as a string		(H1,H2,H3,H4)
//	index 4 stores Fox scorecard option as a string		(F1,F2,F3,F4)
	
	public ScoreCards() {}

	public static String[] getScorecards() {
		return scorecards;
	}

	public static void generateScorecards() {
		Random rand = new Random();
		int[] randNums = new int[5];
		
		for (int i = 0; i < 5; i++) {
			randNums[i] = 1+rand.nextInt(3); //generate a number between 1-4
		}
		bearCards(randNums[0]);
		elkCards(randNums[1]);
		salmonCards(randNums[2]);
		hawkCards(randNums[3]);
		foxCards(randNums[4]);
		
		printScoreCardRules();
	}
	
	private static void bearCards(int randNum) {
		switch(randNum) {
		case 1 -> scorecards[0] = "B1";
		case 2 -> scorecards[0] = "B2";
		case 3 -> scorecards[0] = "B3";
		default -> throw new IllegalArgumentException("Invalid randNum choice for Bear scorecard");
		}	
	}
	
	private static void elkCards(int randNum) {
		switch(randNum) {
		case 1 -> scorecards[1] = "E1";
		case 2 -> scorecards[1] = "E2";
		case 3 -> scorecards[1] = "E3";
		default -> throw new IllegalArgumentException("Invalid randNum choice for Elk scorecard");
		}	
	}
	
	private static void salmonCards(int randNum) {
		switch(randNum) {
		case 1 -> scorecards[2] = "S1";
		case 2 -> scorecards[2] = "S2";
		case 3 -> scorecards[2] = "S3";
		default -> throw new IllegalArgumentException("Invalid randNum choice for Salmon scorecard");
		}	
	}
	
	private static void hawkCards(int randNum) {
		switch(randNum) {
		case 1 -> scorecards[3] = "H1";
		case 2 -> scorecards[3] = "H2";
		case 3 -> scorecards[3] = "H3";
		default -> throw new IllegalArgumentException("Invalid randNum choice for Hawk scorecard");
		}	
	}
	
	private static void foxCards(int randNum) {
		switch(randNum) {
		case 1 -> scorecards[4] = "F1";
		case 2 -> scorecards[4] = "F2";
		case 3 -> scorecards[4] = "F3";
		default -> throw new IllegalArgumentException("Invalid randNum choice for Fox scorecard");
		}	
	}
	
	public static void printScoreCardRules() {
		Display.out("Below are the scorecards drawn for possible Wildlife Token placements on your board:\n");
		for (String s : scorecards) {
			switch (s) {
				case "B1" -> {
					Display.out("""
							BEAR SCORECARD 1:\s
							Score as below, based on the total number of\s
							pairs of exactly 2 bears placed side by side.
							""");
					Display.out("PAIRS   ||   POINTS");
					Display.out("-------------------");
					Display.out("  1     ||     4   ");
					Display.out("  2     ||     11  ");
					Display.out("  3     ||     19  ");
					Display.out("  4     ||     27  ");
					Display.out("");
				}
				case "B2" -> {
					Display.out("""
							BEAR SCORECARD 2:\s
							Score 10 points for each group of exactly three bears.
							""");
					Display.out("TRIPLE  ||   POINTS");
					Display.out("-------------------");
					Display.out("  1     ||     10  ");
					Display.out("");
				}
				case "B3" -> {
					Display.out("""
							BEAR SCORECARD 3:\s
							Score as below for each group of bears 1-3 in size,\s
							with a bonus of 3 points for having one of each of the 3 group sizes.
							""");
					Display.out("GROUP   ||   POINTS");
					Display.out("-------------------");
					Display.out("  1     ||     2   ");
					Display.out("  2     ||     5   ");
					Display.out("  3     ||     8   ");
					Display.out("  ALL   ||    +3   ");
					Display.out("");
				}
				case "E1" -> {
					Display.out("""
							ELK SCORECARD 1:\s
							Score as below for lines of elk in any orientation,\s
							connected from flat side to flat side of the hexagons.
							""");
					Display.out("LINE    ||   POINTS");
					Display.out("-------------------");
					Display.out("  1     ||     2   ");
					Display.out("  2     ||     5   ");
					Display.out("  3     ||     9   ");
					Display.out("  4     ||     13  ");
					Display.out("");
				}
				case "E2" -> {
					Display.out("""
							ELK SCORECARD 2:\s
							Score as below for each contiguous group of elk, based on size.\s
							These groups may be of any shape or size, not necessarily in a line or group exclusively.
							""");
					Display.out("GROUP   ||   POINTS");
					Display.out("-------------------");
					Display.out("  1     ||     2   ");
					Display.out("  2     ||     4   ");
					Display.out("  3     ||     7   ");
					Display.out("  4     ||     10  ");
					Display.out("  5     ||     14  ");
					Display.out("  6     ||     18  ");
					Display.out("  7     ||     23  ");
					Display.out("  8+    ||     28  ");
					Display.out("");
				}
				case "E3" -> {
					Display.out("""
							ELK SCORECARD 3:\s
							Score for one lone elk, two elk side by side, three elk in a triangle formation,\s
							or four elk in a diamond formation.
							""");
					Display.out("GROUP   ||   POINTS");
					Display.out("-------------------");
					Display.out("  1     ||     2   ");
					Display.out("  2     ||     5   ");
					Display.out("  3     ||     9   ");
					Display.out("  4     ||     13  ");
					Display.out("");
				}
				case "S1" -> {
					Display.out("""
							SALMON SCORECARD 1:\s
							Score as below for each run of salmon (where a run is a group of adjacent salmon
							where each salmon is connected to no more than 2 other salmon), based on size,\s
							up to a max of 7.
							""");
					Display.out("RUN     ||   POINTS");
					Display.out("-------------------");
					Display.out("  1     ||     2   ");
					Display.out("  2     ||     4   ");
					Display.out("  3     ||     7   ");
					Display.out("  4     ||     11  ");
					Display.out("  5     ||     15  ");
					Display.out("  6     ||     20  ");
					Display.out("  7+    ||     26  ");
					Display.out("");
				}
				case "S2" -> {
					Display.out("""
							SALMON SCORECARD 2:\s
							Score as below for each run of salmon (where a run is a group of adjacent salmon
							where each salmon is connected to no more than 2 other salmon), based on size,\s
							up to a max of 4.
							""");
					Display.out("RUN     ||   POINTS");
					Display.out("-------------------");
					Display.out("  1     ||     2   ");
					Display.out("  2     ||     4   ");
					Display.out("  3     ||     8   ");
					Display.out("  4+    ||     12  ");
					Display.out("");
				}
				case "S3" -> {
					Display.out("""
							SALMON SCORECARD 3:\s
							Score as below for each run of salmon (where a run is a group of adjacent salmon
							where each salmon is connected to no more than 2 other salmon), based on size,\s
							up to a max of 5.
							""");
					Display.out("RUN     ||   POINTS");
					Display.out("-------------------");
					Display.out("  1     ||     2   ");
					Display.out("  2     ||     5   ");
					Display.out("  3     ||     9   ");
					Display.out("  4     ||     11  ");
					Display.out("  5+    ||     17  ");
					Display.out("");
				}
				case "H1" -> {
					Display.out("""
							HAWK SCORECARD 1:\s
							Score as below for each hawk that is not adjacent to any other hawk.
							""");
					Display.out("SINGLES ||   POINTS");
					Display.out("-------------------");
					Display.out("  1     ||      2  ");
					Display.out("  2     ||      5  ");
					Display.out("  3     ||      8  ");
					Display.out("  4     ||     11  ");
					Display.out("  5     ||     14  ");
					Display.out("  6     ||     18  ");
					Display.out("  7     ||     22  ");
					Display.out("  8+    ||     26  ");
					Display.out("");
				}
				case "H2" -> {
					Display.out("""
							HAWK SCORECARD 2:\s
							Score as below for each hawk based on its line of sight\s
							to other hawks, i.e. not adjacent to each other and not interrupted by\s
							other wildlife or a gap in the tiles.
							""");
					Display.out("IN SIGHT||   POINTS");
					Display.out("-------------------");
					Display.out("  1     ||      2  ");
					Display.out("  2     ||      5  ");
					Display.out("  3     ||      9  ");
					Display.out("  4     ||     12  ");
					Display.out("  5     ||     16  ");
					Display.out("  6     ||     20  ");
					Display.out("  7     ||     24  ");
					Display.out("  8     ||     28  ");
					Display.out("");
				}
				case "H3" -> {
					Display.out("""
							HAWK SCORECARD 3:\s
							Score 3 points for each line of sight between two hawks\s
							(i.e. not adjacent to each other and not interrupted by\s
							other wildlife or a gap in the tiles).
							(Note: multiple lines of sight may involve the same hawk).
							""");
					Display.out("IN SIGHT||   POINTS");
					Display.out("-------------------");
					Display.out("2 HAWKS ||     +3  ");
					Display.out("");
				}
				case "F1" -> {
					Display.out("""
							FOX SCORECARD 1:\s
							Score for each fox as below, based on the number of unique animal types\s
							(including other foxes) directly adjacent to it.""");
					Display.out("ANIMALS ||   POINTS");
					Display.out("-------------------");
					Display.out("  1     ||      1  ");
					Display.out("  2     ||      2  ");
					Display.out("  3     ||      3  ");
					Display.out("  4     ||      4  ");
					Display.out("  5     ||      5  ");
					Display.out("");
				}
				case "F2" -> {
					Display.out("""
							FOX SCORECARD 2:\s
							Score for each fox as below, based on the number of unique animal pairs\s
							(not including other fox pairs) directly adjacent to it.\s
							Pairs of other animals do not need to be adjacent to each other.
							The same wildlife cannot count for more than one pair.
							""");
					Display.out("PAIRS   ||   POINTS");
					Display.out("-------------------");
					Display.out("  1     ||      3  ");
					Display.out("  2     ||      5  ");
					Display.out("  3     ||      7  ");
					Display.out("");
				}
				case "F3" -> {
					Display.out("""
							FOX SCORECARD 3:\s
							Score for each fox as below, based on the number of similar animals\s
							(not including other foxes) directly adjacent to it.\s
							Only score the most abundant adjacent animal type.\s
							""");
					Display.out("SIMILAR ||   POINTS");
					Display.out("-------------------");
					Display.out("  1     ||      1  ");
					Display.out("  2     ||      2  ");
					Display.out("  3     ||      3  ");
					Display.out("  4     ||      4  ");
					Display.out("  5     ||      5  ");
					Display.out("  6     ||      6  ");
					Display.out("");
				}
				default -> throw new IllegalArgumentException("Invalid choice of scorecard to be printed");
			}
		}
	}
	

}
