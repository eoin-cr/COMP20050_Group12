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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class PlayerMap {
	private static final int BOARD_HEIGHT = 20;
	private static final int BOARD_WIDTH = 20;
	private final List<HabitatTile> tilesInMap;
	//position of tiles on map
	private HabitatTile[][] tileBoardPosition = new HabitatTile[BOARD_HEIGHT][BOARD_WIDTH];

	public PlayerMap() { //constructor
		tilesInMap = new ArrayList<>();
		makeStarterTiles();
	}

	public void makeStarterTiles() {
		HabitatTile[] starter = Generation.generateStarterHabitat();
		addTileToMap(starter[0], 8, 9); //places tiles in the middle of the map
		addTileToMap(starter[1], 9, 9);
		addTileToMap(starter[2], 9, 10);
	}

	/**
	 * Returns the 2d array of habitat tiles, which is used for printing the
	 * map of tiles.
	 *
	 * @return a 2d array of habitat tiles (if a tile has not been placed the
	 * element will be null)
	 */
	public HabitatTile[][] getTileBoardPosition() {
		return tileBoardPosition;
	}

	public List<HabitatTile> getTilesInMap() {
		List<HabitatTile> tiles = new ArrayList<>();
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			for (int j = 0; j < BOARD_WIDTH; j++) {
				if (tileBoardPosition[i][j] != null) {
					tiles.add(tileBoardPosition[i][j]);
				}
			}
		}
		return tiles;
	}

	/**
	 * Used to set a full tile board.
	 * Do not use this method unless you are copying a full tile board into
	 * a new player.
	 * Just use the add tile to map method for adding tiles.
	 *
	 * @param board the board to set the map as
	 * @see PlayerMap#addTileToMap(HabitatTile, int, int)
	 */
	public void setTileBoard(HabitatTile[][] board) {
		tileBoardPosition = board;
	}

	public void clearTileBoard() {
		tileBoardPosition = new HabitatTile[BOARD_HEIGHT][BOARD_WIDTH]; //position of tiles on map
	}

	/**
	 * Adds a tile to the board array at position {@code board[x][y]} (0 indexed).
	 *
	 * @param tile the tile to be added to the board position array
	 * @param row the row of the board position array the tile will be added to
	 * @param col the column of the board position array the tile will be added
	 *          to
	 */
	public void addTileToMap(HabitatTile tile, int row, int col) {
		if (tileBoardPosition[row][col] != null) {
			throw new IllegalArgumentException("There is already a tile at that position!");
		}
		tileBoardPosition[row][col] = tile;
		tile.setMapPosition(row, col);
		if (!tile.isFakeTile()) {
			tilesInMap.add(tile);
		}
	}

//	/**
//	 * Used only in Bot testing to add and then remove a tile from map to get difference in scores.
//	 * @param tile
//	 * @param row
//	 * @param col
//	 */
//	public void removeTileFromMap(HabitatTile tile, int row, int col) {
//		if (tileBoardPosition[row][col] != null) {
//			tileBoardPosition[row][col] = null;
//			Bag.decrementTilesInUse(1);
//			Bag.remainingHabitats.merge(tile.getHabitat1(), 1, Integer::sum);
//			Bag.remainingHabitats.merge(tile.getHabitat2(), 1, Integer::sum);
//			for (int i = 0; i < tilesInMap.size(); i++) {
//				if (tilesInMap.get(i) == tile) {
//					tilesInMap.remove(i);
//				}
//			}
//		}
//	}

	//used to check if there's no tiles in the players map that have a valid option for token drawn
	//used in current deck class for check
	public boolean checkAllTilesForValidToken(WildlifeToken token) {
		for (HabitatTile tile : tilesInMap) {
			for (WildlifeToken w : tile.getTokenOptions()) {
				if (w == token) {
					return true; // returns true as it found at least one valid token
				}
			}
		}
		return false;
	}

	//replaces token options with placed token, inverts colours, turns boolean to true
	public boolean addTokenToTile(WildlifeToken token, int tileID, Player p) {
		//place it on the correct tile
		boolean placed = false;

		for (HabitatTile tile : tilesInMap) {
			if (tile.getTileID() == tileID)	{
				//check if the token type matches options
				if (!tile.isFakeTile()) {
					placed = checkTokenOptionsMatch(token, tile);
				}
				if (placed) {
					tile.setPlacedToken(token);
					tile.setTokenPlaced();
					Display.outln("You have successfully placed your token.");
					Display.displayPlayerTileMap(p);
					checkIfKeystoneTokenMatch(token, tile, p); //check if player gets a nature token
					break;
				}
			}
		}

		if (!placed) {
			Display.outln("You are trying to add a token to an invalid tile.");
			Display.outln("Please try again.");
		}

		// returns whether the tile was successfully placed
		return placed;
	}


	/**
	 * Check whether the chosen token can be placed on a certain tile.
	 *
	 * @param token the token to check
	 * @param tile the tile to check whether the token can be placed on
	 * @return whether a token can be placed
	 */
	private boolean checkTokenOptionsMatch(WildlifeToken token, HabitatTile tile) {
		if (tile.getIsTokenPlaced()) {
			Display.outln("There is already a token on this tile.");
		} else {
			for (WildlifeToken w : tile.getTokenOptions()) {
				if (token == w) {
					return true;
				}
			}
			Display.outln("The tile's options for valid tokens do not match.");
		}
		return false;
	}

	//check if player gets a nature token once token is placed
	private void checkIfKeystoneTokenMatch(WildlifeToken token, HabitatTile tile, Player p) {
		if (tile.getTileType() == HabitatTile.TileType.KEYSTONE
				&& tile.getTokenOptions()[0] == token) {
			p.addPlayerNatureToken(); //increments player's nature tokens
			Display.outln("Nature token added to " + p.getPlayerName()
					+ ". You now have nature tokens: " + p.getPlayerNatureTokens());
		}
	}

	/**
	 * Adds all the possible tiles that can be placed according to the rules of
	 * the game to the map.
	 */
	public void addPossibleTiles() {
		HabitatTile[][] tmpBoard = deepCopy(tileBoardPosition); //position of tiles on map
		for (int i = 1; i < BOARD_HEIGHT - 1; i++) {
			for (int j = 1; j < BOARD_WIDTH - 1; j++) {
				if (tmpBoard[i][j] == null & surroundingTokensNonNull(i, j, tmpBoard)) {
					HabitatTile tile = new HabitatTile(HabitatTile.Habitat.Prairie,
							HabitatTile.Habitat.River, 3);
					tile.setFakeTile(true);
					addTileToMap(tile, i, j);
				}
			}
		}
	}

	private static boolean surroundingTokensNonNull(int i, int j, HabitatTile[][] board) {
		int indent = i % 2 == 0 ? 1 : -1;
		int[] rowShift = new int[]{0, 0, -1, -1, 1, 1};
		int[] colShift = new int[]{-1, +1, 0, indent, 0, indent};
		for (int k = 0; k < Constants.NUM_EDGES; k++) {
			if (board[i + rowShift[k]] [j + colShift[k]] != null) {
				return true;
			}
		}
		return false;
	}

	public int[] returnPositionOfID(int ID) {
		for (int i = 0; i < tileBoardPosition.length; i++) {
			for (int j = 0; j < tileBoardPosition[0].length; j++) {
				if (tileBoardPosition[i][j] != null && tileBoardPosition[i][j].getTileID() == ID) {
					if (tileBoardPosition[i][j].isFakeTile()) {
						return new int[]{i, j};
					} else {
						return new int[]{-1, -1};
					}
				}
			}
		}
		return new int[]{-1, -1};
	}

	public HabitatTile returnTileAtPositionInMap(int row, int col) {
		if (row < 0 || col > BOARD_HEIGHT) {
			return null;
		} else if (tileBoardPosition[row][col] == null) {
			return null;
		} else {
			return tileBoardPosition[row][col];
		}
	}

	public static HabitatTile[][] deepCopy(HabitatTile[][] original) {
		final HabitatTile[][] result = new HabitatTile[original.length][];
		for (int i = 0; i < original.length; i++) {
			result[i] = Arrays.copyOf(original[i], original[i].length);
		}
		return result;
	}

	public List<HabitatTile> getKeystoneTiles() {
		List<HabitatTile> output = new ArrayList<>();
		for (HabitatTile tile : tilesInMap) {
			if (tile.isKeystone()) {
				output.add(tile);
			}
		}
		return output;
	}

	/**
	 * Goes through the players map, and returns a list of the IDs
	 * of every tile which has the option to place the given token on it
	 * (and doesn't already have a token on it).
	 *
	 * @param token the token whose options will be returned.
	 * @return a list of tile IDs where the token can be placed
	 */
	public List<HabitatTile> getPossibleTokenPlacements(WildlifeToken token) {
		return tilesInMap.stream()
				.filter(tile -> !tile.getIsTokenPlaced()
						&& Arrays.asList(tile.getTokenOptions()).contains(token))
				.collect(Collectors.toList());
	}

	/**
	 * Returns the number of tiles on a players map where a given token can
	 * be placed.
	 */
	public int numPossibleTokenPlacements(WildlifeToken token) {
		return getPossibleTokenPlacements(token).size();
	}
}
