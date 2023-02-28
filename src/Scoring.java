import java.util.ArrayList;
import java.util.List;

public class Scoring {
//	indexing of cards:
//	index 0 stores Bear score card option as a string		(B1,B2,B3,B4)
//	index 1 stores Elk score card option as a string		(E1,E2,E3,E4)
//	index 2 stores Salmon score card option as a string		(S1,S2,S3,S4)
//	index 3 stores Hawk score card option as a string		(H1,H2,H3,H4)
//	index 4 stores Fox score card option as a string		(F1,F2,F3,F4)
	private static final String[] cards = ScoreCards.getScorecards();
	private static final List<Player> players = Game.getPlayers();
	private static ArrayList<Player> winners = new ArrayList<>();
	
	public Scoring() {}
	
	public static void startScoring() {
		Display.scoringScreen();
		scoreCardScoring();
		habitatCorridorScoring();
		longestOverallCorridorsBonusScoring();
		natureTokenScoring();
		findWinnerAfterScoring();
	}

	//START OF SCORING FUNCTIONS

	private static void scoreCardScoring() {
		for (Player p : players) {
			ScoringBear.scoreBear(p, cards[0]);
			ScoringElk.scoreElk(p, cards[1]);
			ScoringSalmon.scoreSalmon(p, cards[2]);
			ScoringHawk.scoreHawk(p, cards[3]);
			ScoringFox.scoreFox(p, cards[4]);
			System.out.println();
		}
	}

	/**
	 * Sets each player's score for their personal longest habitat corridors of all 5 types.
	 * Also saves the size of each longest habitat corridor in player's info
	 */
	private static void habitatCorridorScoring() {
		for (Player p : players){
			int forestCorridor = findLongestHabitatCorridor(p.getMap(), HabitatTile.Habitat.Forest).size();
			int wetlandCorridor = findLongestHabitatCorridor(p.getMap(), HabitatTile.Habitat.Wetland).size();
			int riverCorridor = findLongestHabitatCorridor(p.getMap(), HabitatTile.Habitat.River).size();
			int mountainCorridor = findLongestHabitatCorridor(p.getMap(), HabitatTile.Habitat.Mountain).size();
			int prairieCorridor = findLongestHabitatCorridor(p.getMap(), HabitatTile.Habitat.Prairie).size();

			p.setLongestCorridorSize(0, forestCorridor);
			p.setLongestCorridorSize(1, wetlandCorridor);
			p.setLongestCorridorSize(2, riverCorridor);
			p.setLongestCorridorSize(3, mountainCorridor);
			p.setLongestCorridorSize(4, prairieCorridor);

			int score = forestCorridor + wetlandCorridor + riverCorridor + mountainCorridor + prairieCorridor;
			p.addToPlayerScore(score);

			System.out.println(p.getPlayerName() + " scored " +score+ " points for Habitat Corridors.");
		}
		System.out.println();
	}

	/**
	 * Finds longest and second longest habitat corridors amongst players, awards bonuses based on number of players
	 */
	private static void longestOverallCorridorsBonusScoring() {
		//indexing:
		//0 stores forest
		//1 stores wetland
		//2 stores river
		//3 stores mountain
		//4 stores prairie

		int numPlayers = players.size();
		int[] longestCorridorSizes = new int[5];
		int[] secondLongestCorridorSizes = new int[5];

		for (int i = 0; i < 5; i++) {
			longestCorridorSizes[i] = 0;
		}

		for (int i = 0; i < 5; i++) { //get longest corridors
			for (Player p : players) {
				if (p.getLongestCorridorSizes()[i] > longestCorridorSizes[i]) {
					longestCorridorSizes[i] = p.getLongestCorridorSizes()[i];
				}
			}
		}

		for (int i = 0; i < 5; i++) {
			for (Player p : players) { //get second longest corridors
				if (p.getLongestCorridorSizes()[i] < longestCorridorSizes[i] && p.getLongestCorridorSizes()[i] > secondLongestCorridorSizes[i]) {
					secondLongestCorridorSizes[i] = p.getLongestCorridorSizes()[i];

				}
			}
		}

		//bonus point scoring
		if (numPlayers == 2) {
			ArrayList<Player> corridorSizeMatchPlayers = new ArrayList<>();

			for (int i = 0; i < 5; i++) {
				corridorSizeMatchPlayers.clear();
				for (Player p : players) {
					if (p.getLongestCorridorSizes()[i] == longestCorridorSizes[i]) {
						corridorSizeMatchPlayers.add(p);
					}
				}
				if (corridorSizeMatchPlayers.size() > 1) { //tie
					System.out.print("Players: [ ");
					for (Player p1 : corridorSizeMatchPlayers) {
						p1.addToPlayerScore(1);
						System.out.print(p1.getPlayerName()+ " ");
					}
					System.out.println("] tied for longest " +HabitatTile.Habitat.getName(i)+ " corridor.");
					System.out.println("They each get 1 bonus point.");
				}
				else { //one person has longest corridor
					System.out.println(corridorSizeMatchPlayers.get(0).getPlayerName() + " made the longest "
							+ HabitatTile.Habitat.getName(i)+ " corridor. 2 bonus points.");
					corridorSizeMatchPlayers.get(0).addToPlayerScore(2);
				}
			}
		}

		if (numPlayers == 3 || numPlayers == 4) {
			ArrayList<Player> corridorSizeMatchPlayers = new ArrayList<>();
			boolean[] tieFound = new boolean[5];

			for (int i = 0; i < 5; i++) {
				corridorSizeMatchPlayers.clear();
				for (Player p : players) {
					if (p.getLongestCorridorSizes()[i] == longestCorridorSizes[i]) {
						corridorSizeMatchPlayers.add(p);
					}
				}
				if (corridorSizeMatchPlayers.size() == 1) {
					tieFound[i] = false;
					System.out.println(corridorSizeMatchPlayers.get(0).getPlayerName()  + " made the longest "
							+HabitatTile.Habitat.getName(i)+ " corridor. 3 bonus points.");
					corridorSizeMatchPlayers.get(0).addToPlayerScore(3);
				}
				else if (corridorSizeMatchPlayers.size() == 2) { //tie with 2 players
					tieFound[i] = true;
					System.out.print("Players: [ " +corridorSizeMatchPlayers.get(0).getPlayerName() + " "
							+corridorSizeMatchPlayers.get(1).getPlayerName() + " ] "
							+ "tied for longest " +HabitatTile.Habitat.getName(i)+ " corridor.");
					System.out.println("They each get 2 bonus points.");
					corridorSizeMatchPlayers.get(0).addToPlayerScore(2);
					corridorSizeMatchPlayers.get(1).addToPlayerScore(2);
				}
				else if (corridorSizeMatchPlayers.size() > 2) { //tie with 3-4 players
					tieFound[i] = true;
					System.out.print("Players: [ ");
					for (Player p1 : corridorSizeMatchPlayers) {
						p1.addToPlayerScore(1);
						System.out.print(p1.getPlayerName()+ " ");
					}
					System.out.println("] tied for longest " +HabitatTile.Habitat.getName(i)+ " corridor.");
					System.out.println("They each get 1 bonus point.");
				}
			}

			for (int i = 0; i < 5; i++) {
				corridorSizeMatchPlayers.clear();
				if (tieFound[i]) {
					for (Player p : players) {
						if (p.getLongestCorridorSizes()[i] == secondLongestCorridorSizes[i]) {
							corridorSizeMatchPlayers.add(p);
						}
					}
					if (corridorSizeMatchPlayers.size() == 1) {
						System.out.println(corridorSizeMatchPlayers.get(0).getPlayerName()  + " made the second longest "
								+HabitatTile.Habitat.getName(i)+ " corridor. 1 bonus point.");
						corridorSizeMatchPlayers.get(0).addToPlayerScore(1);
					}
					//else if more than 1 player gets second largest corridor, no bonus points
				}
			}
		}
		System.out.println();
	}

	private static void natureTokenScoring() {
		for (Player p : players) {
			if (p.getPlayerNatureTokens() > 0) {
				System.out.println(p.getPlayerName() + " has " + p.getPlayerNatureTokens() + " remaining Nature Token(s). "
						+ p.getPlayerNatureTokens() + " bonus point(s).");
				p.addToPlayerScore(p.getPlayerNatureTokens());
			}
		}
		System.out.println();
	}

	private static void findWinnerAfterScoring() {
		int winningScore = 0;
		for (Player p : players) {
			if (p.getPlayerScore() > winningScore) {
				winningScore = p.getPlayerScore();
			}
		}

		for (Player p : players) {
			if (p.getPlayerScore() == winningScore) {
				winners.add(p);
			}
		}

		if (winners.size() == 1) {
			System.out.println("Winner is " + winners.get(0).getPlayerName() + " with score " +winningScore+ ".\n");
		}

		else {
			System.out.println("A tie break has occurred. Now checking player Nature Tokens.");
			int maxNatureTokens = 0;
			ArrayList<Player> tieBreakWinners = new ArrayList<>();
			for (Player p : winners) {
				if (p.getPlayerNatureTokens() > maxNatureTokens) {
					tieBreakWinners.add(p);
				}
			}
			if (tieBreakWinners.size() == 1) {
				System.out.println("Winner is " + tieBreakWinners.get(0).getPlayerName() + " with score "
						+ winningScore+ "and Nature Tokens " +tieBreakWinners.get(0).getPlayerNatureTokens()+ ".\n");
			}
			else {
				System.out.print("Players: [");
				for (Player p : tieBreakWinners) {
					System.out.print(p.getPlayerName() + " ");
				}
				System.out.println("] have tied for the win, with score " +winningScore+ ".\n");
			}
		}
	}
	//START OF HELPER FUNCTIONS

	/**
	 * Helper function for Habitat corridor scoring, retrieves the longest habitat corridor of a certain type on a single player's map
	 * @return Arraylist of tiles with matching habitats
	 */
	private static ArrayList<HabitatTile> findLongestHabitatCorridor(PlayerMap map, HabitatTile.Habitat habitatType) {
		ArrayList<HabitatTile> visitedTiles = new ArrayList<>();
		ArrayList<HabitatTile> longestCorridor = new ArrayList<>();
		ArrayList<HabitatTile> newCorridor = new ArrayList<>();
		int longestCorridorSize = 0;

		for (HabitatTile tile : map.getTilesInMap()) {
			newCorridor.clear();
			if (!visitedTiles.contains(tile) && (tile.getHabitat1() == habitatType || tile.getHabitat2() == habitatType)) {
				findHabitatCorridorRecursive(newCorridor, tile, habitatType, map);
				visitedTiles.addAll(newCorridor);
				if (newCorridor.size() > longestCorridorSize) {
					longestCorridor.clear();
					longestCorridor.addAll(newCorridor);
					longestCorridorSize = newCorridor.size();
				}
			}
		}

		return longestCorridor;
	}

	/**
	 * Helper function for Habitat corridor scoring, retrieves a chunk of connected tiles on the map with matching habitat types.
	 * Habitat types match based on the edges of two adjacent tiles.
	 */
	private static void findHabitatCorridorRecursive(ArrayList<HabitatTile> habitatCorridor, HabitatTile centerTile, HabitatTile.Habitat habitatType, PlayerMap map) {
		HabitatTile[] adjacentTiles = getAdjacentTiles(centerTile, map);
		HabitatTile.Habitat[] adjacentHabitats = getAdjacentHabitats(centerTile, map);

		if (!habitatCorridor.contains(centerTile) && (centerTile.getHabitat1() == habitatType || centerTile.getHabitat2() == habitatType)) {
			habitatCorridor.add(centerTile);
			for (int i = 0; i < centerTile.getEdges().size(); i++) {
				if (centerTile.getEdge(i).getHabitatType() == habitatType && adjacentHabitats[i] == habitatType) {
					findHabitatCorridorRecursive(habitatCorridor, adjacentTiles[i], habitatType, map);
				}
			}
		}
		
	}

	/**
	 * Helper function for Scorecard scoring, retrieves a chunk of connected tiles on the map with the same Wildlife token type.
	 */
	public static void findTokenGroupRecursive(ArrayList<HabitatTile> groupOfTokens, WildlifeToken tokenType, HabitatTile centerTile, PlayerMap map) {
		if (!groupOfTokens.contains(centerTile) && centerTile.getPlacedToken() == tokenType) {
			ArrayList<HabitatTile> adjacentTokens = Scoring.getAdjacentTilesWithTokenMatch(tokenType, centerTile, map);
			if (adjacentTokens.size() > 0 && adjacentTokens.size() < 3) {
				groupOfTokens.add(centerTile);
				for (HabitatTile t : adjacentTokens) {
					findTokenGroupRecursive(groupOfTokens, tokenType, t, map);
				}
			}
		}
	}
	
	/**
	 * Helper function for Scorecard scoring, retrieves a single tile's adjacent tiles with a Wildlife token match.
	 * @return Arraylist of Habitat Tiles with same token type.
	 */
	public static ArrayList<HabitatTile> getAdjacentTilesWithTokenMatch(WildlifeToken animalType, HabitatTile centerTile, PlayerMap map){
		ArrayList<HabitatTile> tileMatches = new ArrayList<>();
		HabitatTile[] adjacentTiles = getAdjacentTiles(centerTile, map);

		for (HabitatTile checktile : adjacentTiles) {
			if (checktile != null && checktile.getPlacedToken() == animalType) {
				tileMatches.add(checktile);
			}
		}
		return tileMatches;
	}

//	//helper function that returns an int count of how many adjacent tiles have the same matching token
//	public static int countAdjacentTokenMatches(WildlifeToken animalType, HabitatTile tile, PlayerMap map) {
//		int count = 0;
//		WildlifeToken[] adjacentTokens = getAdjacentTokens(tile, map);
//		for (int i = 0; i < adjacentTokens.length; i++) {
//			if (adjacentTokens[i] == animalType) {
//				count++;
//			}
//		}
//		return count;
//	}

	/**
	 * Helper function for Scorecard scoring, retrieves the tokens of a single tile's adjacent tiles
	 * @return Array of WildlifeTokens
	 */
	public static WildlifeToken[] getAdjacentTokens(HabitatTile tile, PlayerMap map) {
		HabitatTile[] adjacentTiles = getAdjacentTiles(tile, map);
		WildlifeToken[] adjacentTokens = new WildlifeToken[6];
		for (int i = 0; i < 6; i++) {
			if (adjacentTiles[i] != null) {
				adjacentTokens[i] = adjacentTiles[i].getPlacedToken();
			}
			else {
				adjacentTokens[i] = null;
			}
		}
		return adjacentTokens;
	}

	/**
	 * Helper function for Habitat corridor scoring, retrieves the habitats adjacent to a single tile's edges.
	 * @return Array of Habitats
	 */
	private static HabitatTile.Habitat[] getAdjacentHabitats(HabitatTile tile, PlayerMap map) {
		HabitatTile[] adjacentTiles = getAdjacentTiles(tile, map);
		HabitatTile.Habitat[] adjacentHabitats = new HabitatTile.Habitat[6];
		for (int i = 0; i < 6; i++) {
			adjacentHabitats[i] = null;
		}
		//Center tile has 6 edges - each edge connects to adjacent tile's specific edge according to position on map
		//in relation to center tile. Eg. Center tile edge 0 connects to above tile's edge 3.
		if (adjacentTiles[0] != null) adjacentHabitats[0] = adjacentTiles[0].getEdge(3).getHabitatType();
		if (adjacentTiles[1] != null) adjacentHabitats[1] = adjacentTiles[1].getEdge(4).getHabitatType();
		if (adjacentTiles[2] != null) adjacentHabitats[2] = adjacentTiles[2].getEdge(5).getHabitatType();
		if (adjacentTiles[3] != null) adjacentHabitats[3] = adjacentTiles[3].getEdge(0).getHabitatType();
		if (adjacentTiles[4] != null) adjacentHabitats[4] = adjacentTiles[4].getEdge(1).getHabitatType();
		if (adjacentTiles[5] != null) adjacentHabitats[5] = adjacentTiles[5].getEdge(2).getHabitatType();
		return adjacentHabitats;
	}


	/**
	 * Helper function for general Scoring, gets a single tile's adjacent tiles.
	 * Has cases based on the tile's position in the 2d array/map (since a tile might be missing sides based on position).
	 * Missing sides are null.
	 * Note: Edges of the hexagonal are numbered 0 (starting from the top right edge, going clockwise) to 5 (left top edge).
	 * @return Array of tiles
	 */

//	  Total 6 sides, like in the diagram below
//	      5   0
//	 	  -- --
//	 	4|     |1
//	 	  -- --
//	 	  3	  2
	
	public static HabitatTile[] getAdjacentTiles(HabitatTile tile, PlayerMap map) {
		HabitatTile[] adjacentTiles = new HabitatTile[6];
		for (int i = 0; i < 6; i++) {
			adjacentTiles[i] = null;
		}

		int row = tile.getMapPosition()[0];
		int col = tile.getMapPosition()[1];
		
		//edge cases
		if (row == 0 && col == 0) { //missing sides 3,4,5,0 - top left on map
			adjacentTiles[1] = map.returnTileAtPositionInMap(0, 1);
			adjacentTiles[2] = map.returnTileAtPositionInMap(1, 0);
		}
		else if (row == 0 && col > 0 && col < 19) { //missing sides 5,0
			adjacentTiles[1] = map.returnTileAtPositionInMap(0, col+1);
			adjacentTiles[2] = map.returnTileAtPositionInMap(1, col+1);
			adjacentTiles[3] = map.returnTileAtPositionInMap(1, col);
			adjacentTiles[4] = map.returnTileAtPositionInMap(0, col-1);
		}
		else if (row == 0 && col == 19) { //missing sides 5,0,1 - top right on map
			adjacentTiles[2] = map.returnTileAtPositionInMap(1, 19);
			adjacentTiles[3] = map.returnTileAtPositionInMap(1, 18);
			adjacentTiles[4] = map.returnTileAtPositionInMap(0, 18);
		}
		else if (col == 0 && row > 0 && row < 19) { //alternating, missing side 4 or sides 3,4,5
			if (row%2 != 0) { //odd rows, missing side 4
				adjacentTiles[0] = map.returnTileAtPositionInMap(row-1, 1);
				adjacentTiles[1] = map.returnTileAtPositionInMap(row, 1);
				adjacentTiles[2] = map.returnTileAtPositionInMap(row+1, 1);
				adjacentTiles[3] = map.returnTileAtPositionInMap(row+1, 0);
				adjacentTiles[5] = map.returnTileAtPositionInMap(row-1, 0);
			}
			else { //even rows, missing sides 3,4,5
				adjacentTiles[0] = map.returnTileAtPositionInMap(row-1, 0);
				adjacentTiles[1] = map.returnTileAtPositionInMap(row, 1);
				adjacentTiles[2] = map.returnTileAtPositionInMap(row+1, 0);
			}
		}
		else if (col == 0 && row == 19) { //missing sides 2,3,4 - bottom left on map
			adjacentTiles[5] = map.returnTileAtPositionInMap(18, 0);
			adjacentTiles[0] = map.returnTileAtPositionInMap(18, 1);
			adjacentTiles[1] = map.returnTileAtPositionInMap(19, 1);
		}
		else if (col == 19 && row > 0 && row < 19) { //alternating, missing sides 0,1,2 or side 1
			if (row%2 != 0) { //odd rows, missing sides 0,1,2
				adjacentTiles[3] = map.returnTileAtPositionInMap(1, col);
				adjacentTiles[4] = map.returnTileAtPositionInMap(0, col-1);
				adjacentTiles[5] = map.returnTileAtPositionInMap(0, col-1);
			}
			else { //even rows, missing side 1
				adjacentTiles[0] = map.returnTileAtPositionInMap(0, col+1);
				adjacentTiles[2] = map.returnTileAtPositionInMap(1, col+1);
				adjacentTiles[3] = map.returnTileAtPositionInMap(1, col);
				adjacentTiles[4] = map.returnTileAtPositionInMap(0, col-1);
				adjacentTiles[5] = map.returnTileAtPositionInMap(0, col-1);
				
			}
		}
		else if (col == 19 && row == 19) { //missing sides 0,1,2,3 - bottom right on map
			adjacentTiles[4] = map.returnTileAtPositionInMap(19, 18);
			adjacentTiles[5] = map.returnTileAtPositionInMap(18, 19);
		}
		else { //non edge case, no missing sides, in the middle of the map
			adjacentTiles[0] = map.returnTileAtPositionInMap(row-1, col+1);
			adjacentTiles[1] = map.returnTileAtPositionInMap(row, col+1);
			adjacentTiles[2] = map.returnTileAtPositionInMap(row+1, col+1);
			adjacentTiles[3] = map.returnTileAtPositionInMap(row+1, col);
			adjacentTiles[4] = map.returnTileAtPositionInMap(row, col-1);
			adjacentTiles[5] = map.returnTileAtPositionInMap(row-1, col);
		}
		
		return adjacentTiles;
	}

}
