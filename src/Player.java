import java.util.ArrayList;
import java.util.List;

/**
 * Stores information about the player.
 */

// TODO: Change to a board class
public class Player {
	private final String playerName;
	private int playerNatureTokens;
	private int playerScore;
	private static final int BOARD_HEIGHT = 20;
	private static final int BOARD_WIDTH = 20;
	private List<HabitatTile> playerTiles = new ArrayList<>();
	private final HabitatTile[][] tileBoardPosition = new HabitatTile[BOARD_HEIGHT][BOARD_WIDTH];

	public Player(String playerName) { // constructor
		this.playerName = playerName;
		this.playerNatureTokens = 0;
		this.playerScore = 0;
	}
	
	public String getPlayerName() { //getters and setters
		return playerName;
	}
	public int getPlayerNatureTokens() {
		return playerNatureTokens;
	}
	public void setPlayerNatureTokens(int natureTokens) {
		this.playerNatureTokens = natureTokens;
	}
	public int getPlayerScore() {
		return playerScore;
	}
	public void setPlayerScore(int score) {
		this.playerScore = score;
	}
	public List<HabitatTile> getPlayerTiles() {
		return playerTiles;
	}
	public void setPlayerTiles(List<HabitatTile> tiles) {
		this.playerTiles = tiles;
	}

	/**
	 * Returns the 2d array of habitat tiles, which is used for printing the
	 * map of tiles.
	 *
	 * @return a 2d array of habitat tiles (if a tile has not been placed the
	 * el(String)(String)ement will be null)
	 */
	public HabitatTile[][] getTileBoardPosition() {
		return tileBoardPosition;
	}

	/**
	 * Adds a tile to the board array at position {@code board[x][y]}.
	 *
	 * @param tile the tile to be added to the board position array
	 * @param x the row of the board position array the tile will be added to
	 * @param y the column of the board position array the tile will be added
	 *          to
	 * @throws IllegalArgumentException if there is already a tile at that
	 * position
	 */
	public void addTileAtCoordinate(HabitatTile tile, int x, int y) throws IllegalArgumentException {
		if (tileBoardPosition[x][y] != null) {
			throw new IllegalArgumentException("There is already a tile at that position!");
		}
		tileBoardPosition[x][y] = tile;
	}
}
