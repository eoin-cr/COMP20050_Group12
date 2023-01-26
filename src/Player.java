import java.util.ArrayList;

public class Player {
	private final String playerName;
	private int playerNatureTokens;
	private int playerScore;
	private ArrayList<HabitatTile> playerTiles = new ArrayList<>();
//	private final HabitatTile[][] tileBoardPosition = new HabitatTile[20][20];

	public Player(String playerName) { //constructor
		//need to add error handling here for empty names or null or pre-existing names
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

//	public HabitatTile[][] getTileBoardPosition() {
//		return tileBoardPosition;
//	}
//	public void addTileAtCoordinate(HabitatTile tile, int x, int y) throws IllegalArgumentException {
//		if (tileBoardPosition[x][y] != null) {
//			throw new IllegalArgumentException("There is already a tile at that position!");
//		}
//		tileBoardPosition[x][y] = tile;
//	}
}
