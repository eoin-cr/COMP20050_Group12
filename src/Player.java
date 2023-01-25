
public class Player {
	private String playerName;
	private int natureTokens;
	private int score;
	
	public Player(String playerName) { //constructor
		this.setPlayerName(playerName);
		this.setNatureTokens(0);
		this.setScore(0);
	}
	
	public String getPlayerName() { //getters and setters
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
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
	
	
}
