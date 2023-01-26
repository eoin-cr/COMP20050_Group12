import java.util.ArrayList;

public class Player {
	private final String playerName;
	private int natureTokens;
	private int score;
	private ArrayList<HabitatTile> tiles = new ArrayList<>();
//	private final HabitatTile[][] tileBoardPosition = new HabitatTile[20][20];

	public Player(String playerName) { //constructor
		this.playerName = playerName;
		this.natureTokens = 0;
		this.score = 0;
	}
	
	public String getPlayerName() { //getters and setters
		return playerName;
	}
	public int getNatureTokens() {
		return natureTokens;
	}
	public void setNatureTokens(int natureTokens) {
		this.natureTokens = natureTokens;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	public ArrayList<HabitatTile> getTiles() {
		return tiles;
	}

	public void setTiles(ArrayList<HabitatTile> tiles) {
		this.tiles = tiles;
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
