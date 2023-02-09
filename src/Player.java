import java.util.ArrayList;
import java.util.List;

/**
 * Stores information about the player.
 */
public class Player {
	private final String playerName;
	private int playerNatureTokens;
	private int playerScore;
	private PlayerMap map;

	public Player(String playerName) { // constructor
		this.playerName = playerName;
		this.playerNatureTokens = 0;
		this.playerScore = 0;
		map = new PlayerMap();
		//a player's map holds all tiles+tokens currently placed by player
		//any tokens not used are returned to the bag
	}
	
	public String getPlayerName() { //getters and setters
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
	public void setPlayerScore(int score) {
		this.playerScore = score;
	}
	public PlayerMap getMap() {
		return map; //note: to change the map, you have to use player.getMap().addTileToMap()
	}

}
