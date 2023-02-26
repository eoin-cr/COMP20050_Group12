import java.util.ArrayList;

/**
 * Stores information about the player, including
 * an individual player's map of tiles.
 * @see PlayerMap
 */
public class Player {
	private final String playerName;
	private int playerNatureTokens;
	private int playerScore;
	private final PlayerMap map;
	private int[] longestCorridorSizes = new int[5];

	public Player(String playerName) {
		this.playerName = playerName;
		this.playerNatureTokens = 0;
		this.playerScore = 0;
		map = new PlayerMap();
	}
	
	public String getPlayerName() {
		return playerName;
	}
	public int getPlayerNatureTokens() {
		return playerNatureTokens;
	}
	public void addPlayerNatureToken() {
		this.playerNatureTokens++;
	}
	public void subPlayerNatureToken() {
		if (playerNatureTokens > 0) {
			this.playerNatureTokens--;
		}
		else {
			this.playerNatureTokens = 0;
			throw new IllegalArgumentException("Out of nature tokens, cannot subtract further");
		}
	}
	public int getPlayerScore() {
		return playerScore;
	}
	public void addToPlayerScore(int score) {
		this.playerScore += score;
	}
	public PlayerMap getMap() {
		return map; 
		//note: to modify the map, you have to use player.getMap().addTileToMap()
	}
	public int[] getLongestCorridorSizes() {
		return longestCorridorSizes;
	}
	/**
	 * Lets you save size of longest habitat corridor a player has on their map in an int array, for each habitat.
	 * 	0 stores Forest corridor size.
	 *	1 stores Wetland corridor size.
	 *	2 stores River corridor size.
	 *	3 stores Mountain corridor size.
	 *	4 stores Prairie corridor size.
	 * @param index
	 * @param size
	 */
	public void setLongestCorridorSize(int index, int size) {
		longestCorridorSizes[index] = size;
	}

}
