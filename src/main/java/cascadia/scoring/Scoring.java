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

import cascadia.*;
import java.util.ArrayList;
import java.util.List;

public class Scoring {
    /*
    	indexing of cards:
    	index 0 stores Bear scorecard option as a string		(B1,B2,B3,B4)
    	index 1 stores Elk scorecard option as a string		(E1,E2,E3,E4)
    	index 2 stores Salmon scorecard option as a string		(S1,S2,S3,S4)
    	index 3 stores Hawk scorecard option as a string		(H1,H2,H3,H4)
    	index 4 stores Fox scorecard option as a string		(F1,F2,F3,F4)
    */

    private static final String[] cards = ScoreCards.getScorecards();
	private static final List<Player> players = Game.getPlayers();
	private static final List<Player> winners = new ArrayList<>();

	/**
	 * Starts the scoring for the game.
	 */
	public static void startScoring() {
		//mynah - change made
		Display.scoringScreen();
		//scoreCardScoring();
		//natureTokenScoring();
		ScoringHabitatCorridors.habitatCorridorScoring(players);
		ScoringHabitatCorridors.longestOverallCorridorsBonusScoring(players);
		scoreCardScoring();
		natureTokenScoring();
		findWinner();
	}

	/**
	 * Used in cascadia.CurrentDeck class, each time a player places a token on their map.
	 * That particular Wildlife token type is re-scored for that player's whole map.
	 * Keeps player's wildlife scores updated per turn.
	 * @see CurrentDeck
	 * @param player the player whose score is to be updated
	 * @param token the token to be scored
	 */
	public static void scorePlayerTokenPlacement(Player player, WildlifeToken token) {
		player.setPlayerWildlifeScore(token, ScoreToken.calculateScore(player.getMap(), token));
	}

//	mynah - change made
	//end based scoring
	public static void scoreCardScoring() {
		int score;
		for (Player p : players) {
			for (WildlifeToken tokenType : WildlifeToken.values()) {
				score = ScoreToken.calculateScore(p.getMap(), tokenType);
				p.setPlayerWildlifeScore(tokenType, score);
				Display.outln(p.getPlayerName() + " " + tokenType.name() + " Score: " + score);
			}
			Display.outln("");
		}
	}

	//end based scoring
	private static void natureTokenScoring() {
		for (Player p : players) {
			if (p.getPlayerNatureTokens() > 0) {
				Display.outln(p.getPlayerName() + " has " + p.getPlayerNatureTokens()
						+ " remaining Nature Token(s). "
						+ p.getPlayerNatureTokens() + " bonus point(s).");
			}
		}
		Display.outln("");
	}

	private static void findWinner() {
		int winningScore = 0;
		for (Player p : players) {
			p.calculateTotalEndPlayerScore();
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
			Display.outln("Winner is " + winners.get(0).getPlayerName() + " with score "
					+ winningScore + ".\n");
		} else {
			calculateTieBreak(winningScore);
		}
	}

	private static void calculateTieBreak(int winningScore) {
		Display.outln("A tie break has occurred. Now checking player Nature Tokens.");
		int maxNatureTokens = 0;
		List<Player> tieBreakWinners = new ArrayList<>();
		for (Player p : winners) {
			if (p.getPlayerNatureTokens() >= maxNatureTokens) {
				tieBreakWinners.add(p);
			}
		}
		if (tieBreakWinners.size() == 1) {
			Display.outln("Winner is " + tieBreakWinners.get(0).getPlayerName() + " with score "
					+ winningScore + "and Nature Tokens "
					+ tieBreakWinners.get(0).getPlayerNatureTokens() + ".\n");
		} else {
			Display.outf("Players: %s have tied for the win, with a score of %d\n\n",
					tieBreakWinners, winningScore);
		}
	}

	/////////////////////////////
	//START OF HELPER FUNCTIONS//
	/////////////////////////////

	/**
	 * Helper function for Scorecard scoring, retrieves a chunk of connected
	 * tiles on the map with the same Wildlife token type.
	 */
	public static void findTokenGroup(List<HabitatTile> groupOfTokens,
									  WildlifeToken tokenType, HabitatTile centerTile,
									  PlayerMap map) {
		if (!groupOfTokens.contains(centerTile) && centerTile.getPlacedToken() == tokenType) {
			groupOfTokens.add(centerTile);
			List<HabitatTile> adjacentTokens = Scoring.getAdjacentTilesWithTokenMatch(
					tokenType, centerTile, map);
			if (adjacentTokens.size() > 0) {
				for (HabitatTile t : adjacentTokens) {
					findTokenGroup(groupOfTokens, tokenType, t, map);
				}
			}
		}
	}

	/**
	 * Helper function for Scorecard scoring, retrieves a single tile's
	 * adjacent tiles with a Wildlife token match.
	 *
	 * @return Arraylist of Habitat Tiles with same token type.
	 */
	public static List<HabitatTile> getAdjacentTilesWithTokenMatch(WildlifeToken animalType,
																		HabitatTile centerTile,
																		PlayerMap map) {
		List<HabitatTile> tileMatches = new ArrayList<>();
		HabitatTile[] adjacentTiles = getAdjacentTiles(centerTile, map);

		for (HabitatTile checkTile : adjacentTiles) {
			if (checkTile != null && checkTile.getPlacedToken() == animalType) {
				tileMatches.add(checkTile);
			}
		}
		return tileMatches;
	}

	/**
	 * Helper function for Scorecard scoring, retrieves the tokens of a single
	 * tile's adjacent tiles.
	 *
	 * @return Array of WildlifeTokens
	 */
	public static WildlifeToken[] getAdjacentTokens(HabitatTile tile, PlayerMap map) {
		HabitatTile[] adjacentTiles = getAdjacentTiles(tile, map);
		WildlifeToken[] adjacentTokens = new WildlifeToken[Constants.NUM_EDGES];
		for (int i = 0; i < Constants.NUM_EDGES; i++) {
			if (adjacentTiles[i] != null) {
				adjacentTokens[i] = adjacentTiles[i].getPlacedToken();
			} else {
				adjacentTokens[i] = null;
			}
		}
		return adjacentTokens;
	}

	/**
	 * Helper function for Habitat corridor scoring, retrieves the habitats
	 * adjacent to a single tile's edges.
	 *
	 * @return Array of Habitats
	 */
	public static HabitatTile.Habitat[] getAdjacentHabitats(HabitatTile tile, PlayerMap map) {
		HabitatTile[] adjacentTiles = getAdjacentTiles(tile, map);
		HabitatTile.Habitat[] adjacentHabitats = new HabitatTile.Habitat[Constants.NUM_EDGES];
		int[] edgeIndexes = new int[]{3, 4, 5, 0, 1, 2};
		/*
		Center tile has 6 edges - each edge connects to adjacent tile's specific edge according
		to position on map in relation to center tile. E.g. Center tile edge 0 connects to above
		tile's edge 3.
		*/
		for (int i = 0; i < Constants.NUM_EDGES; i++) {
			if (adjacentTiles[i] != null) {
				adjacentHabitats[i] = adjacentTiles[i].getEdge(edgeIndexes[i]).getHabitatType();
			}
		}
		return adjacentHabitats;
	}


	/**
	 * Helper function for general Scoring, gets a single tile's adjacent tiles.
	 * Has cases based on the tile's position in the 2d array/map (since a tile
	 * might be missing sides based on position).
	 * Missing sides are null.
	 * Note: Edges of the hexagonal are numbered 0 (starting from the top
	 * right edge, going clockwise) to 5 (left top edge).
	 *
	 * @return array of tiles
	 */

	/*
	  Total 6 sides, like in the diagram below
	      5   0
	 	  -- --
	 	4|     |1
	 	  -- --
	 	  3	  2
	*/

	private static HabitatTile tileHelper(HabitatTile tile, PlayerMap map, int index,
										  boolean isEven) {
		int row = tile.getMapPosition()[0];
		int col = tile.getMapPosition()[1];
		int[] rowShift = new int[]{-1, 0, +1, +1, 0, -1};
		int[] colShiftEven = new int[]{1, 1, 1, 0, -1, 0};
		int[] colShiftOdd = new int[]{0, 1, 0, -1, -1, -1};

		int colShift = isEven ? colShiftEven[index] : colShiftOdd[index];
		return map.returnTileAtPositionInMap(row + rowShift[index], col + colShift);
	}

	public static HabitatTile[] getAdjacentTiles(HabitatTile tile, PlayerMap map) {
		HabitatTile[] adjacentTiles = new HabitatTile[Constants.NUM_EDGES];

		int row = tile.getMapPosition()[0];

		if (row % 2 == 0) {
			for (int i = 0; i < Constants.NUM_EDGES; i++) {
				adjacentTiles[i] = tileHelper(tile, map, i, true);
			}
		} else {
			for (int i = 0; i < Constants.NUM_EDGES; i++) {
				adjacentTiles[i] = tileHelper(tile, map, i, false);
			}
		}

		return adjacentTiles;
	}
	
	public static List<int[]> getAdjacentTileMapPositions(HabitatTile tile, PlayerMap map){
		List<int[]> rowCols = new ArrayList<>(Constants.NUM_EDGES);
		int row = tile.getMapPosition()[0];
		int col = tile.getMapPosition()[1];
		int[] rowShift = new int[]{-1, 0, +1, +1, 0, -1};
		int[] colShiftEven = new int[]{1, 1, 1, 0, -1, 0};
		int[] colShiftOdd = new int[]{0, 1, 0, -1, -1, -1};
		
		for (int i = 0; i < Constants.NUM_EDGES; i++) {
			int colShift = (row%2==0) ? colShiftEven[i] : colShiftOdd[i];
			int[] rowCol = {row + rowShift[i], col + colShift};
			rowCols.add(rowCol);
		}
		
		return rowCols;
	}

	/**
	 * Walks one tile over on player's map, following a specified edge (ie
	 * walks either left/right or diagonally).
	 */
	protected static HabitatTile walkToTileAtSide(HabitatTile tile, PlayerMap map, int edgeNum) {
		if (edgeNum < 0 || edgeNum > 5) {
			throw new IllegalArgumentException("Invalid edge number given to get a tile at edge "
					+ edgeNum + ". Edges must be between 0-5.");
		}

		int row = tile.getMapPosition()[0];

		//non-edge case, no missing sides, in the middle of the map
		return tileHelper(tile, map, edgeNum, row % 2 == 0);
	}

}
