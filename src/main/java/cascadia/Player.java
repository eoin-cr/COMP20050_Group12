package cascadia;

/**
 * Stores information about the player, including
 * an individual player's map of tiles.
 *
 * @see PlayerMap
 */
public class Player {
	private final String playerName;
	private int playerNatureTokens;
	private final int[] longestCorridorSizes = new int[Constants.NUM_HABITAT_TYPES];
	//indexing -> 0: Bear score, 1: Elk score, 2: Salmon score, 3: Hawk score, 4: Fox score
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
		} else {
			this.playerNatureTokens = 0;
			throw new IllegalArgumentException("Out of nature tokens, cannot subtract further");
		}
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
	 * Lets you save size of the longest habitat corridor a player has on
	 * their map in an int array, for each habitat.
	 * 	0 stores Forest corridor size.
	 *	1 stores Wetland corridor size.
	 *	2 stores River corridor size.
	 *	3 stores Mountain corridor size.
	 *	4 stores Prairie corridor size.
	 *
	 * @param index the index of the habitat corridor to save
	 * @param size the size to save
	 */
	public void setLongestCorridorSize(int index, int size) {
		longestCorridorSizes[index] = size;
	}

	@Override
	public String toString() {
		return playerName;
	}

    /**
	 * Allows the user to select an option from the command menu.
	 * Within this method the possible commands are displayed and user input is
	 * taken and acted upon.
	 */
    public void setCommand() {
        Display.displayCommands();
        String input = Input.getUserInput();  // this is automatically uppercase

        // automatically converts input to enum
//        try {
			Command command = Command.valueOf(input);
			command.execute(this);  // calls the function represented in the enum
//        } catch (IllegalArgumentException ex) {  // catches if the input is not an enum element
//            Display.outln("Invalid input for options of commands. Please try again. \n");
//        }
//		}
    }
}
