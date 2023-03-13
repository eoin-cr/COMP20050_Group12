package cascadia;

import java.util.Random;

public class ScoreCards {
	private final static String[] scorecards = new String[5];
//	stores the 5 score card options selected, 
//	each randomly generated from a choice of 4 possible options of cards
//	indexing:
//	index 0 stores Bear score card option as a string		(B1,B2,B3,B4)
//	index 1 stores Elk score card option as a string		(E1,E2,E3,E4)
//	index 2 stores Salmon score card option as a string		(S1,S2,S3,S4)
//	index 3 stores Hawk score card option as a string		(H1,H2,H3,H4)
//	index 4 stores Fox score card option as a string		(F1,F2,F3,F4)
	
	public ScoreCards() {}

	public static String[] getScorecards() {
		return scorecards;
	}

	public static void generateScorecards() {
		Random rand = new Random();
		int[] randnums = new int[5];
		
		for (int i = 0; i < 5; i++) {
			randnums[i] = 1+rand.nextInt(3); //generate a number between 1-4
		}
		bearCards(randnums[0]);
		elkCards(randnums[1]);
		salmonCards(randnums[2]);
		hawkCards(randnums[3]);
		foxCards(randnums[4]);
		
		printScoreCardRules();
	}
	
	private static void bearCards(int randnum) {
		switch(randnum) {
		case 1 -> scorecards[0] = "B1";
		case 2 -> scorecards[0] = "B2";
		case 3 -> scorecards[0] = "B3";
		default -> throw new IllegalArgumentException("Invalid randnum choice for Bear scorecard");
		}	
	}
	
	private static void elkCards(int randnum) {
		switch(randnum) {
		case 1 -> scorecards[1] = "E1";
		case 2 -> scorecards[1] = "E2";
		case 3 -> scorecards[1] = "E3";
		default -> throw new IllegalArgumentException("Invalid randnum choice for Elk scorecard");
		}	
	}
	
	private static void salmonCards(int randnum) {
		switch(randnum) {
		case 1 -> scorecards[2] = "S1";
		case 2 -> scorecards[2] = "S2";
		case 3 -> scorecards[2] = "S3";
		default -> throw new IllegalArgumentException("Invalid randnum choice for Salmon scorecard");
		}	
	}
	
	private static void hawkCards(int randnum) {
		switch(randnum) {
		case 1 -> scorecards[3] = "H1";
		case 2 -> scorecards[3] = "H2";
		case 3 -> scorecards[3] = "H3";
		default -> throw new IllegalArgumentException("Invalid randnum choice for Hawk scorecard");
		}	
	}
	
	private static void foxCards(int randnum) {
		switch(randnum) {
		case 1 -> scorecards[4] = "F1";
		case 2 -> scorecards[4] = "F2";
		case 3 -> scorecards[4] = "F3";
		default -> throw new IllegalArgumentException("Invalid randnum choice for Fox scorecard");
		}	
	}
	
	public static void printScoreCardRules() {
		System.out.println("Below are the scorecards drawn for possible Wildlife Token placements on your board:\n");
		for (String s : scorecards) {
			switch (s) {
			case "B1":
				System.out.println("BEAR SCORECARD 1: \n"
						+ "Score as below, based on the total number of \n"
						+ "pairs of exactly 2 bears placed side by side.\n");
				System.out.println("PAIRS   ||   POINTS");
				System.out.println("-------------------");
				System.out.println("  1     ||     4   ");
				System.out.println("  2     ||     11  ");
				System.out.println("  3     ||     19  ");
				System.out.println("  4     ||     27  ");
				System.out.println();
				break;
				
			case "B2":
				System.out.println("BEAR SCORECARD 2: \n"
						+ "Score 10 points for each group of exactly three bears.\n");
				System.out.println("TRIPLE  ||   POINTS");
				System.out.println("-------------------");
				System.out.println("  1     ||     10  ");
				System.out.println();
				break;
				
			case "B3":
				System.out.println("BEAR SCORECARD 3: \n"
						+ "Score as below for each group of bears 1-3 in size, \n"
						+ "with a bonus of 3 points for having one of each of the 3 group sizes.\n");
				System.out.println("GROUP   ||   POINTS");
				System.out.println("-------------------");
				System.out.println("  1     ||     2   ");
				System.out.println("  2     ||     5   ");
				System.out.println("  3     ||     8   ");
				System.out.println("  ALL   ||    +3   ");
				System.out.println();
				break;
				
			case "E1":
				System.out.println("ELK SCORECARD 1: \n"
						+ "Score as below for lines of elk in any orientation, \n"
						+ "connected from flat side to flat side of the hexagons.\n");
				System.out.println("LINE    ||   POINTS");
				System.out.println("-------------------");
				System.out.println("  1     ||     2   ");
				System.out.println("  2     ||     5   ");
				System.out.println("  3     ||     9   ");
				System.out.println("  4     ||     13  ");
				System.out.println();
				break;
				
			case "E2":
				System.out.println("ELK SCORECARD 2: \n"
						+ "Score as below for each contiguous group of elk, based on size. \n"
						+ "These groups may be of any shape or size, not necessarily in a line or group exclusively.\n");
				System.out.println("GROUP   ||   POINTS");
				System.out.println("-------------------");
				System.out.println("  1     ||     2   ");
				System.out.println("  2     ||     4   ");
				System.out.println("  3     ||     7   ");
				System.out.println("  4     ||     10  ");
				System.out.println("  5     ||     14  ");
				System.out.println("  6     ||     18  ");
				System.out.println("  7     ||     23  ");
				System.out.println("  8+    ||     28  ");
				System.out.println();
				break;
				
			case "E3":
				System.out.println("ELK SCORECARD 3: \n"
						+ "Score for one lone elk, two elk side by side, three elk in a triangle formation, \n"
						+ "or four elk in a diamond formation.\n");
				System.out.println("GROUP   ||   POINTS");
				System.out.println("-------------------");
				System.out.println("  1     ||     2   ");
				System.out.println("  2     ||     5   ");
				System.out.println("  3     ||     9   ");
				System.out.println("  4     ||     13  ");
				System.out.println();
				break;

			case "S1":
				System.out.println("SALMON SCORECARD 1: \n"
						+ "Score as below for each run of salmon (where a run is a group of adjacent salmon\n"
						+ "where each salmon is connected to no more than 2 other salmon), based on size, \n"
						+ "up to a max of 7.\n");
				System.out.println("RUN     ||   POINTS");
				System.out.println("-------------------");
				System.out.println("  1     ||     2   ");
				System.out.println("  2     ||     4   ");
				System.out.println("  3     ||     7   ");
				System.out.println("  4     ||     11  ");
				System.out.println("  5     ||     15  ");
				System.out.println("  6     ||     20  ");
				System.out.println("  7+    ||     26  ");
				System.out.println();
				break;
				
			case "S2":
				System.out.println("SALMON SCORECARD 2: \n"
						+ "Score as below for each run of salmon (where a run is a group of adjacent salmon\n"
						+ "where each salmon is connected to no more than 2 other salmon), based on size, \n"
						+ "up to a max of 4.\n");
				System.out.println("RUN     ||   POINTS");
				System.out.println("-------------------");
				System.out.println("  1     ||     2   ");
				System.out.println("  2     ||     4   ");
				System.out.println("  3     ||     8   ");
				System.out.println("  4+    ||     12  ");
				System.out.println();
				break;
				
			case "S3":
				System.out.println("SALMON SCORECARD 3: \n"
						+ "Score as below for each run of salmon (where a run is a group of adjacent salmon\n"
						+ "where each salmon is connected to no more than 2 other salmon), based on size, \n"
						+ "up to a max of 5.\n");
						System.out.println("RUN     ||   POINTS");
						System.out.println("-------------------");
						System.out.println("  1     ||     2   ");
						System.out.println("  2     ||     5   ");
						System.out.println("  3     ||     9   ");
						System.out.println("  4     ||     11  ");
						System.out.println("  5+    ||     17  ");
						System.out.println();
						break;
				
			case "H1":
				System.out.println("HAWK SCORECARD 1: \n"
						+ "Score as below for each hawk that is not adjacent to any other hawk\n.");
				System.out.println("SINGLES ||   POINTS");
				System.out.println("-------------------");
				System.out.println("  1     ||      2  ");
				System.out.println("  2     ||      5  ");
				System.out.println("  3     ||      8  ");
				System.out.println("  4     ||     11  ");
				System.out.println("  5     ||     14  ");
				System.out.println("  6     ||     18  ");
				System.out.println("  7     ||     22  ");
				System.out.println("  8+    ||     26  ");
				System.out.println();
				
				break;
				
			case "H2":
				System.out.println("HAWK SCORECARD 2: \n"
						+ "Score as below for each hawk based on its line of sight \n"
						+ "to other hawks, i.e. not adjacent to each other and not interrupted by \n"
						+ "other wildlife or a gap in the tiles.\n");
				System.out.println("IN SIGHT||   POINTS");
				System.out.println("-------------------");
				System.out.println("  1     ||      2  ");
				System.out.println("  2     ||      5  ");
				System.out.println("  3     ||      9  ");
				System.out.println("  4     ||     12  ");
				System.out.println("  5     ||     16  ");
				System.out.println("  6     ||     20  ");
				System.out.println("  7     ||     24  ");
				System.out.println("  8     ||     28  ");
				System.out.println();
				break;
				
			case "H3":
				System.out.println("HAWK SCORECARD 3: \n"
						+ "Score 3 points for each line of sight between two hawks \n"
						+ "(i.e. not adjacent to each other and not interrupted by \n"
						+ "other wildlife or a gap in the tiles).\n"
						+ "(Note: multiple lines of sight may involve the same hawk).\n");
				System.out.println("IN SIGHT||   POINTS");
				System.out.println("-------------------");
				System.out.println("2 HAWKS ||     +3  ");
				System.out.println();
				break;
			
			case "F1":
				System.out.println("FOX SCORECARD 1: \n"
						+ "Score for each fox as below, based on the number of unique animal types \n"
						+ "(including other foxes) directly adjacent to it.");
				System.out.println("ANIMALS ||   POINTS");
				System.out.println("-------------------");
				System.out.println("  1     ||      1  ");
				System.out.println("  2     ||      2  ");
				System.out.println("  3     ||      3  ");
				System.out.println("  4     ||      4  ");
				System.out.println("  5     ||      5  ");
				System.out.println();
				break;
				
			case "F2":
				System.out.println("FOX SCORECARD 2: \n"
						+ "Score for each fox as below, based on the number of unique animal pairs \n"
						+ "(not including other fox pairs) directly adjacent to it. \n"
						+ "Pairs of other animals do not need to be adjacent to each other.\n"
						+ "The same wildlife cannot count for more than one pair.\n");
				System.out.println("PAIRS   ||   POINTS");
				System.out.println("-------------------");
				System.out.println("  1     ||      3  ");
				System.out.println("  2     ||      5  ");
				System.out.println("  3     ||      7  ");
				System.out.println();
				break;
				
			case "F3":
				System.out.println("FOX SCORECARD 3: \n"
						+ "Score for each fox as below, based on the number of similar animals \n"
						+ "(not including other foxes) directly adjacent to it. \n"
						+ "Only score the most abundant adjacent animal type. \n");
				System.out.println("SIMILAR ||   POINTS");
				System.out.println("-------------------");
				System.out.println("  1     ||      1  ");
				System.out.println("  2     ||      2  ");
				System.out.println("  3     ||      3  ");
				System.out.println("  4     ||      4  ");
				System.out.println("  5     ||      5  ");
				System.out.println("  6     ||      6  ");
				System.out.println();
				break;
				
				
			default:
					throw new IllegalArgumentException("Invalid choice of scorecard to be printed");
			}
		}
	}
	

}
