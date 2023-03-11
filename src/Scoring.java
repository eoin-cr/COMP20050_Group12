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
	private static final ArrayList<Player> winners = new ArrayList<>();
	
	public Scoring() {}
	
	public static void startScoring() {
		Display.scoringScreen();
		ScoringHabitatCorridors.habitatCorridorScoring(players);
		ScoringHabitatCorridors.longestOverallCorridorsBonusScoring(players);
		scoreCardScoring();
		natureTokenScoring();
		findWinnerAfterScoring();
	}

	//START OF SCORING FUNCTIONS

	private static void scoreCardScoring() {
		for (Player p : players) {
			ScoringBear.scoreBear(p, cards[0]);
			ScoringElk.scoreElk(p, cards[1]);
//			ScoringSalmon.scoreSalmon(p, cards[2]);
			int salmonScore = ScoringSalmon.scoreSalmon(p.getMap(), cards[2]);
			p.addToTotalPlayerScore(salmonScore);
			System.out.println(p.getPlayerName() + " Salmon Score: " + salmonScore); //for testing
			ScoringHawk.scoreHawk(p, cards[3]);
			ScoringFox.scoreFox(p, cards[4]);
			System.out.println();
		}
	}
	
	/**
	 * Used in CurrentDeck class, each time a player places a token on their map.
	 * That particular Wildlife token type is rescored for that player's whole map.
	 * Keeps player's wildlife scores updated per turn.
	 * @see CurrentDeck
	 * @param player
	 * @param token
	 */
	public static void scorePlayerTokenPlacement(Player player, WildlifeToken token) {
		switch (token) {
		case Bear -> player.setPlayerWildlifeScore(WildlifeToken.Bear, ScoringBear.scoreBear(player, cards[0]));
		case Elk -> player.setPlayerWildlifeScore(WildlifeToken.Elk, ScoringElk.scoreElk(player, cards[1]));
		case Salmon -> player.setPlayerWildlifeScore(WildlifeToken.Salmon, ScoringSalmon.scoreSalmon(player.getMap(), cards[2]));
		case Hawk -> player.setPlayerWildlifeScore(WildlifeToken.Hawk, ScoringHawk.scoreHawk(player, cards[3]));
		case Fox -> player.setPlayerWildlifeScore(WildlifeToken.Fox, ScoringFox.scoreFox(player, cards[4]));
		default -> throw new IllegalArgumentException("Unexpected token value to be scored for player: " + token);
		}
		System.out.println("The token you placed was of type: " +token.name()+ ". Your current Wildlife score for that type is: " +player.getPlayerWildlifeScore(token));
	}

	private static void natureTokenScoring() {
		for (Player p : players) {
			if (p.getPlayerNatureTokens() > 0) {
				System.out.println(p.getPlayerName() + " has " + p.getPlayerNatureTokens() + " remaining Nature Token(s). "
						+ p.getPlayerNatureTokens() + " bonus point(s).");
				p.addToTotalPlayerScore(p.getPlayerNatureTokens());
			}
		}
		System.out.println();
	}

	private static void findWinnerAfterScoring() {
		int winningScore = 0;
		for (Player p : players) {
			if (p.getTotalPlayerScore() > winningScore) {
				winningScore = p.getTotalPlayerScore();
			}
		}

		for (Player p : players) {
			if (p.getTotalPlayerScore() == winningScore) {
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
	 * Helper function for Scorecard scoring, retrieves a chunk of connected tiles on the map with the same Wildlife token type.
	 */
	public static void findTokenGroupRecursive(ArrayList<HabitatTile> groupOfTokens, WildlifeToken tokenType, HabitatTile centerTile, PlayerMap map) {
		if (!groupOfTokens.contains(centerTile) && centerTile.getPlacedToken() == tokenType) {
			groupOfTokens.add(centerTile);
			ArrayList<HabitatTile> adjacentTokens = Scoring.getAdjacentTilesWithTokenMatch(tokenType, centerTile, map);
			if (adjacentTokens.size() > 0) {
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
	public static HabitatTile.Habitat[] getAdjacentHabitats(HabitatTile tile, PlayerMap map) {
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
	
	public static HabitatTile walkToTileAtSide(HabitatTile tile, PlayerMap map ,int edgeNum) {
		if (edgeNum < 0 || edgeNum > 5) {
			throw new IllegalArgumentException("Invalid edge number given to get a tile at edge " +edgeNum+ ". Edges must be between 0-5.");
		}
		
		HabitatTile adjacentTile = null;
		int row = tile.getMapPosition()[0];
		int col = tile.getMapPosition()[1];
		
		//edge cases
		if (row == 0 && col == 0) { //missing sides 3,4,5,0 - top left on map
			switch (edgeNum) {
			case 1 -> adjacentTile = map.returnTileAtPositionInMap(0, 1);
			case 2 -> adjacentTile = map.returnTileAtPositionInMap(1, 0);
			default -> adjacentTile = null;
			}
		}
		else if (row == 0 && col > 0 && col < 19) { //missing sides 5,0
			switch (edgeNum) {
			case 1 -> adjacentTile = map.returnTileAtPositionInMap(0, col+1);
			case 2 -> adjacentTile = map.returnTileAtPositionInMap(1, col+1);
			case 3 -> adjacentTile = map.returnTileAtPositionInMap(1, col);
			case 4 -> adjacentTile = map.returnTileAtPositionInMap(0, col-1);
			default -> adjacentTile = null;
			}
		}
		else if (row == 0 && col == 19) { //missing sides 5,0,1 - top right on map
			switch (edgeNum) {
			case 2 -> adjacentTile = map.returnTileAtPositionInMap(1, 19);
			case 3 -> adjacentTile = map.returnTileAtPositionInMap(1, 18);
			case 4 -> adjacentTile = map.returnTileAtPositionInMap(0, 18);
			default -> adjacentTile = null;
			}
		}
		else if (col == 0 && row > 0 && row < 19) { //alternating, missing side 4 or sides 3,4,5
			if (row%2 != 0) { //odd rows, missing side 4
				switch (edgeNum) {
				case 0 -> adjacentTile = map.returnTileAtPositionInMap(row-1, 1);
				case 1 -> adjacentTile = map.returnTileAtPositionInMap(row, 1);
				case 2 -> adjacentTile = map.returnTileAtPositionInMap(row+1, 1);
				case 3 -> adjacentTile = map.returnTileAtPositionInMap(row+1, 0);
				case 5 -> adjacentTile = map.returnTileAtPositionInMap(row-1, 0);
				default -> adjacentTile = null;
				}
			}
			else { //even rows, missing sides 3,4,5
				switch (edgeNum) {
				case 0 -> adjacentTile = map.returnTileAtPositionInMap(row-1, 0);
				case 1 -> adjacentTile = map.returnTileAtPositionInMap(row, 1);
				case 2 -> adjacentTile = map.returnTileAtPositionInMap(row+1, 0);
				default -> adjacentTile = null;
				}
			}
		}
		else if (col == 0 && row == 19) { //missing sides 2,3,4 - bottom left on map
			switch (edgeNum) {
			case 5 -> adjacentTile = map.returnTileAtPositionInMap(18, 0);
			case 0 -> adjacentTile = map.returnTileAtPositionInMap(18, 1);
			case 1 -> adjacentTile = map.returnTileAtPositionInMap(19, 1);
			default -> adjacentTile = null;
			}
		}
		else if (col == 19 && row > 0 && row < 19) { //alternating, missing sides 0,1,2 or side 1
			if (row%2 != 0) { //odd rows, missing sides 0,1,2
				switch (edgeNum) {
				case 3 -> adjacentTile = map.returnTileAtPositionInMap(1, col);
				case 4 -> adjacentTile = map.returnTileAtPositionInMap(0, col-1);
				case 5 -> adjacentTile = map.returnTileAtPositionInMap(0, col-1);
				default -> adjacentTile = null;
				}
			}
			else { //even rows, missing side 1
				switch (edgeNum) {
				case 0 -> adjacentTile = map.returnTileAtPositionInMap(0, col+1);
				case 2 -> adjacentTile = map.returnTileAtPositionInMap(1, col+1);
				case 3 -> adjacentTile = map.returnTileAtPositionInMap(1, col);
				case 4 -> adjacentTile = map.returnTileAtPositionInMap(0, col-1);
				case 5 -> adjacentTile = map.returnTileAtPositionInMap(0, col-1);
				default -> adjacentTile = null;
				}
				
			}
		}
		else if (col == 19 && row == 19) { //missing sides 0,1,2,3 - bottom right on map
			switch (edgeNum) {
			case 4 -> adjacentTile = map.returnTileAtPositionInMap(19, 18);
			case 5 -> adjacentTile = map.returnTileAtPositionInMap(18, 19);
			default -> adjacentTile = null;
			}
		}
		else { //non edge case, no missing sides, in the middle of the map
			switch (edgeNum) {
			case 0 -> adjacentTile = map.returnTileAtPositionInMap(row-1, col+1);
			case 1 -> adjacentTile = map.returnTileAtPositionInMap(row, col+1);
			case 2 -> adjacentTile = map.returnTileAtPositionInMap(row+1, col+1);
			case 3 -> adjacentTile = map.returnTileAtPositionInMap(row+1, col);
			case 4 -> adjacentTile = map.returnTileAtPositionInMap(row, col-1);
			case 5 -> adjacentTile = map.returnTileAtPositionInMap(row-1, col);
			default -> adjacentTile = null;
			}
		}
		
		return adjacentTile;
	}
	
	/**
	 * Helper function that walks in a direction along the map, as specified by an edge number. 
	 * Walks to the next tile at that edge number, and recursively walks to the next at that edge number, and so on
	 * until the end of the map is reached.
	 * @param tile
	 * @param map
	 * @param edgeNum
	 * @return next tile walked to
	 */
	public static HabitatTile walkInDirectionRecursive(HabitatTile tile, PlayerMap map ,int edgeNum) {
		HabitatTile nextTile = null;
		if (tile != null) {
			nextTile = walkToTileAtSide(tile, map, edgeNum);
			walkInDirectionRecursive(nextTile, map, edgeNum);
		}
		return nextTile;
	}

}
