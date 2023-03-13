package cascadia;

/**
 * Stores information about the player, including
 * an individual player's map of tiles.
 * @see PlayerMap
 */
public class Player {
	private final String playerName;
	private int playerNatureTokens;
	private int[] longestCorridorSizes = new int[5];
	private int[] wildlifeScores = new int[5];
	//indexing -> 0: Bear score, 1: Elk score, 2: Salmon score, 3: Hawk score, 4: Fox score 
	private int corridorsPlayerScore;
	private int wildlifePlayerScore;
	private int totalPlayerScore;
	private final PlayerMap map;

	public Player(String playerName) {
		this.playerName = playerName;
		this.playerNatureTokens = 0;
		this.totalPlayerScore = 0;
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
	public int getPlayerWildlifeScore(WildlifeToken token) {
		int score;
		switch (token) {
		case Bear -> score = wildlifeScores[0];
		case Elk -> score = wildlifeScores[1];
		case Salmon -> score = wildlifeScores[2];
		case Hawk -> score = wildlifeScores[3];
		case Fox -> score = wildlifeScores[4];
		default ->
		throw new IllegalArgumentException("Unexpected nature token passed to retrieve player's score: " + token);
		}
		return score;
	}
	public void setPlayerWildlifeScore(WildlifeToken token, int score) {
		switch (token) {
		case Bear -> wildlifeScores[0] = score;
		case Elk -> wildlifeScores[1] = score;
		case Salmon -> wildlifeScores[2] = score;
		case Hawk -> wildlifeScores[3] = score;
		case Fox -> wildlifeScores[4] = score;
		default ->
		throw new IllegalArgumentException("Unexpected nature token passed to set player's score: " + token);
		}
	}
	public int calculateCorridorsPlayerScore() {
		int sum = 0;
		for (int i : longestCorridorSizes) {
			sum += i;
		}
		corridorsPlayerScore = sum;
		return sum;
	}
	public int calculateWildlifePlayerScore() {
		int sum = 0;
		for (int i : wildlifeScores) {
			sum += i;
		}
		wildlifePlayerScore = sum;
		return sum;
	}
	public int getTotalPlayerScore() {
		return totalPlayerScore;
	}
	public void addToTotalPlayerScore(int score) {
		this.totalPlayerScore += score;
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
