import java.util.Random;

public class ScoreCard {
	private static final String[] scorecards = new String[5];
//	stores the 5 scorecard options selected,
//	each randomly generated from a choice of 4 possible options of cards
//	indexing:
//	index 0 stores Bear scorecard option as a string		(B1,B2,B3,B4)
//	index 1 stores Elk scorecard option as a string		(E1,E2,E3,E4)
//	index 2 stores Salmon scorecard option as a string		(S1,S2,S3,S4)
//	index 3 stores Hawk scorecard option as a string		(H1,H2,H3,H4)
//	index 4 stores Fox scorecard option as a string		(F1,F2,F3,F4)
	
	public ScoreCard() {}

/*
	public static String[] getScorecards() {
		return scorecards;
	}
*/

	public static void generateScorecards() {
		Random rand = new Random();
		int[] randNums = new int[5];
		
		for (int i = 0; i < 5; i++) {
			randNums[i] = 1+rand.nextInt(4); //generate a number between 1-4
		}
		BearScorecards tmp = BearScorecards.valueOf("B"+randNums[0]);
		scorecards[0] = tmp.toString();
		scorecards[1] = String.format("E%d", randNums[1]);
		scorecards[2] = String.format("S%d", randNums[2]);
		scorecards[3] = String.format("H%d", randNums[3]);
		scorecards[4] = String.format("F%d", randNums[4]);

		printCardRules();
	}

	enum BearScorecards {
		B1(
		"""
				BEAR SCORECARD 1:\s
				Score as below, based on the total number of\s
				pairs of bears placed side by side.
				PAIRS   ||   POINTS
				-------------------
				  1     ||     4  \s
				  2     ||     11 \s
				  3     ||     19 \s
				  4     ||     27 \s
				"""
		),
		B2 (
		"""
				BEAR SCORECARD 2:\s
				Score 10 points for each group of exactly three bears\s
				(not in a line, but in a triangle formation).
				TRIPLE  ||   POINTS
				-------------------
				  1     ||     10 \s
				"""
		),
		B3 (
		"""
				BEAR SCORECARD 3:\s
				Score as below for each group of bears 1-3 in size,\s
				with a bonus of 3 points for having one of each of the 3 group sizes.
				GROUP   ||   POINTS
				-------------------
				  1     ||     2  \s
				  2     ||     5  \s
				  3     ||     8  \s
				  ALL   ||    +3  \s
				 """
		),
		B4 (
		"""
				BEAR SCORECARD 4:\s
				Score as below for each group of bears 2-4 in size.
				GROUP   ||   POINTS
				-------------------
				  2     ||     5  \s
				  3     ||     8  \s
				  4     ||     13 \s
				"""
		);
		private final String description;
		BearScorecards(String s) {
			this.description = s;
		}

		public String getDescription() {
			return description;
		}
	}
	enum ElkScorecard {
		E1 (
		"""
				ELK SCORECARD 1:\s
				Score as below for lines of elk in any orientation,\s
				connected from flat side to flat side of the hexagons.

				LINE    ||   POINTS
				-------------------
				  1     ||     2  \s
				  2     ||     5  \s
				  3     ||     9  \s
				  4     ||     13 \s
				"""
		),
		E2 (
		"""
				ELK SCORECARD 2:\s
				Score for one lone elk, two elk side by side, three elk in a triangle formation,\s
				or four elk in a diamond formation.

				GROUP   ||   POINTS
				-------------------
				  1     ||     2  \s
				  2     ||     5  \s
				  3     ||     9  \s
				  4     ||     13 \s
				"""
		),
		E3 (
		"""
				ELK SCORECARD 3:\s
				Score as below for each contiguous group of elk, based on size.\s
				These groups may be of any shape or size, not necessarily in a line or group exclusively.

				GROUP   ||   POINTS
				-------------------
				  1     ||     2  \s
				  2     ||     4  \s
				  3     ||     7  \s
				  4     ||     10 \s
				  5     ||     14 \s
				  6     ||     18 \s
				  7     ||     23 \s
				  8+    ||     28 \s
				"""
		),
		E4 (
		"""
				ELK SCORECARD 4:\s
				Score as below for elk in a circular formation (surrounding a single non-elk tile.
				The circle need not be complete.

				CIRCLE   ||   POINTS
				-------------------
				  1     ||     2  \s
				  2     ||     5  \s
				  3     ||     8  \s
				  4     ||     12 \s
				  5     ||     16 \s
				  6     ||     21 \s
				"""
		);

		private final String description;
		ElkScorecard(String s) {
			this.description = s;
		}

		public String getDescription() {
			return description;
		}
	}
	enum SalmonScorecard {
		S1 (
		"""
				SALMON SCORECARD 1:\s
				Score as below for each run of salmon, based on size, up to a max of 7.

				RUN     ||   POINTS
				-------------------
				  1     ||     2  \s
				  2     ||     5  \s
				  3     ||     8  \s
				  4     ||     12 \s
				  5     ||     16 \s
				  6     ||     20 \s
				  7+    ||     25 \s
				"""
		),
		S2 (
		"""
				SALMON SCORECARD 2:\s
				Score as below for each run of salmon, based on size, up to a max of 5.

				RUN     ||   POINTS
				-------------------
				  1     ||     2  \s
				  2     ||     5  \s
				  3     ||     9  \s
				  4     ||     11 \s
				  5+    ||     17 \s
				"""
		),
		S3 (
		"""
				SALMON SCORECARD 3:\s
				Score as below for each run of salmon, based on size, between size 3-5.

				RUN     ||   POINTS
				-------------------
				  3     ||     10 \s
				  4     ||     12 \s
				  5+    ||     15 \s
				"""
		),
		S4 (
		"""
				SALMON SCORECARD 4:\s
				Score as below for each run of salmon, one point for each salmon in the run,\s
				plus one point for each adjacent animal token (type of animal does not matter).

				TYPE    ||   POINTS
				-------------------
				SALMON  ||     +1 \s
				OTHER   ||     +1 \s
				"""
		);
		private final String description;
		SalmonScorecard(String s) {
			this.description = s;
		}

		public String getDescription() {
			return description;
		}
	}
	enum HawkScorecard {
		H1 (
		"""
				HAWK SCORECARD 1:\s
				Score as below for each hawk that is not adjacent to any other hawk.

				SINGLES ||   POINTS
				-------------------
				  1     ||      2 \s
				  2     ||      5 \s
				  3     ||      8 \s
				  4     ||     11 \s
				  5     ||     14 \s
				  6     ||     18 \s
				  7     ||     22 \s
				  8+    ||     26 \s
				"""
		),
		H2 (
		"""
				HAWK SCORECARD 2:\s
				Score as below for hawks within direct, uninterrupted lines of sight\s
				to each other, ie not adjacent to each other.

				IN SIGHT||   POINTS
				-------------------
				  2     ||      5 \s
				  3     ||      9 \s
				  4     ||     12 \s
				  5     ||     16 \s
				  6     ||     20 \s
				  7     ||     24 \s
				  8+    ||     28 \s
				"""
		),
		H3 (
		"""
				HAWK SCORECARD 3:\s
				Score 3 points for each line of sight between two hawks.\s
				(Note: multiple lines of sight may involve the same hawk).

				IN SIGHT||   POINTS
				-------------------
				2 HAWKS ||     +3 \s
				"""
		),
		H4 (
		"""
				HAWK SCORECARD 4:\s
				Score as below for each pair of hawks,\s
				based on the number of unique animal types between them\s
				(not including other hawks). Each hawk may only be part of one pair.

				ANIMALS ||   POINTS
				-------------------
				  1     ||      4 \s
				  2     ||      7 \s
				  3+    ||      9 \s
				"""
		);

		private final String description;
		HawkScorecard(String s) {
			this.description = s;
		}

		public String getDescription() {
			return description;
		}
	}
	enum FoxScorecard {
		F1(
		"""
				FOX SCORECARD 1:\s
				Score for each fox as below, based on the number of unique animal types\s
				(including other foxes) directly adjacent to it.
				ANIMALS ||   POINTS
				-------------------
				  1     ||      1 \s
				  2     ||      2 \s
				  3     ||      3 \s
				  4     ||      4 \s
				  5     ||      5 \s
				"""
		),
		F2(
		"""
				FOX SCORECARD 2:\s
				Score for each fox as below, based on the number of unique animal pairs\s
				(not including other fox pairs) directly adjacent to it.\s
				Pairs of other animals do not need to be adjacent to each other.

				PAIRS   ||   POINTS
				-------------------
				  1     ||      3 \s
				  2     ||      5 \s
				  3     ||      7 \s
				"""
		),
		F3(
		"""
				FOX SCORECARD 3:\s
				Score for each fox as below, based on the number of similar animals\s
				(not including other foxes) directly adjacent to it.\s
				Only score the most abundant adjacent animal type.\s

				SIMILAR ||   POINTS
				-------------------
				  1     ||      1 \s
				  2     ||      2 \s
				  3     ||      3 \s
				  4     ||      4 \s
				  5     ||      5 \s
				  6     ||      6 \s
				"""
		),
		F4(
		"""
				FOX SCORECARD 4:\s
				Score for each fox pair as below, based on the number of\s
				unique animal pairs (not including other fox pairs) directly adjacent to it.\s
				Pairs of other animals do not need to be adjacent to each other.

				ADJACENT||   POINTS
				-------------------
				  1     ||      5 \s
				  2     ||      7 \s
				  3     ||      9 \s
				  4     ||     11 \s
				"""
		);
		private final String description;
		FoxScorecard(String s) {
			this.description = s;
		}

		public String getDescription() {
			return description;
		}
	}
	public static void printCardRules() {
		System.out.println("Below are the scorecards drawn for possible Wildlife Token placements on your board:\n\n");
		System.out.println(BearScorecards.valueOf(scorecards[0]).getDescription());
		Display.sleep(500);
		System.out.println(ElkScorecard.valueOf(scorecards[1]).getDescription());
		Display.sleep(500);
		System.out.println(SalmonScorecard.valueOf(scorecards[2]).getDescription());
		Display.sleep(500);
		System.out.println(HawkScorecard.valueOf(scorecards[3]).getDescription());
		Display.sleep(500);
		System.out.println(FoxScorecard.valueOf(scorecards[4]).getDescription());
		Display.sleep(500);
	}
}
