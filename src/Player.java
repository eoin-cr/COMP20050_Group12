import java.util.ArrayList;

/**
 * Stores information about the player.
 */
public class Player {
	private final String playerName;
	private int playerNatureTokens;
	private int playerScore;
	private ArrayList<HabitatTile> playerTiles = new ArrayList<>();
	private final HabitatTile[][] tileBoardPosition = new HabitatTile[20][20];

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

	public ArrayList<HabitatTile> getPlayerTiles() {
		return playerTiles;
	}

	public void setPlayerTiles(ArrayList<HabitatTile> tiles) {
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
