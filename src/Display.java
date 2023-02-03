import java.util.Arrays;
import java.util.Map;
import java.util.Random;

/**
 * Deals with outputting to the console.
 */
public class Display {

	/**
	 * Prints a large ASCII art title screen, and then welcomes the user.
	 * Sleep calls are used to improve readability.
	 * The ASCII art is from
	 * <a href="https://patorjk.com/software/taag/#p=testall&f=Bulbhead&t=CASCADIA">this website.</a>
	 */
    public static void welcome() {
        System.out.println(
				"""
						________/\\\\\\\\\\\\\\\\\\_____/\\\\\\\\\\\\\\\\\\________/\\\\\\\\\\\\\\\\\\\\\\__________/\\\\\\\\\\\\\\\\\\_____/\\\\\\\\\\\\\\\\\\_____/\\\\\\\\\\\\\\\\\\\\\\\\_____/\\\\\\\\\\\\\\\\\\\\\\_____/\\\\\\\\\\\\\\\\\\____
						 _____/\\\\\\////////____/\\\\\\\\\\\\\\\\\\\\\\\\\\____/\\\\\\/////////\\\\\\_____/\\\\\\////////____/\\\\\\\\\\\\\\\\\\\\\\\\\\__\\/\\\\\\////////\\\\\\__\\/////\\\\\\///____/\\\\\\\\\\\\\\\\\\\\\\\\\\__
						  ___/\\\\\\/____________/\\\\\\/////////\\\\\\__\\//\\\\\\______\\///____/\\\\\\/____________/\\\\\\/////////\\\\\\_\\/\\\\\\______\\//\\\\\\_____\\/\\\\\\______/\\\\\\/////////\\\\\\_
						   __/\\\\\\_____________\\/\\\\\\_______\\/\\\\\\___\\////\\\\\\__________/\\\\\\_____________\\/\\\\\\_______\\/\\\\\\_\\/\\\\\\_______\\/\\\\\\_____\\/\\\\\\_____\\/\\\\\\_______\\/\\\\\\_
						    _\\/\\\\\\_____________\\/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\______\\////\\\\\\______\\/\\\\\\_____________\\/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\_\\/\\\\\\_______\\/\\\\\\_____\\/\\\\\\_____\\/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\_
						     _\\//\\\\\\____________\\/\\\\\\/////////\\\\\\_________\\////\\\\\\___\\//\\\\\\____________\\/\\\\\\/////////\\\\\\_\\/\\\\\\_______\\/\\\\\\_____\\/\\\\\\_____\\/\\\\\\/////////\\\\\\_
						      __\\///\\\\\\__________\\/\\\\\\_______\\/\\\\\\__/\\\\\\______\\//\\\\\\___\\///\\\\\\__________\\/\\\\\\_______\\/\\\\\\_\\/\\\\\\_______/\\\\\\______\\/\\\\\\_____\\/\\\\\\_______\\/\\\\\\_
						       ____\\////\\\\\\\\\\\\\\\\\\_\\/\\\\\\_______\\/\\\\\\_\\///\\\\\\\\\\\\\\\\\\\\\\/______\\////\\\\\\\\\\\\\\\\\\_\\/\\\\\\_______\\/\\\\\\_\\/\\\\\\\\\\\\\\\\\\\\\\\\/____/\\\\\\\\\\\\\\\\\\\\\\_\\/\\\\\\_______\\/\\\\\\_
						        _______\\/////////__\\///________\\///____\\///////////___________\\/////////__\\///________\\///__\\////////////_____\\///////////__\\///________\\///__
						"""
        );

		sleep(300);

        System.out.println("Welcome to Cascadia!");

        // another sleep call
		sleep(300);
    }

	/**
	 * Clears the terminal screen.
	 * Note: This only works on an actual terminal, not terminal emulators
	 * inside IDEs.
	 */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

	/**
	 * Prints the player names, as well as the number showing the order they
	 * will be playing in.
	 *
	 * @param playerNames a String array of player names
	 */
	public static void printPlayers(String[] playerNames) { // used in Game class
    	System.out.println();
        System.out.println("The player list is:");
    
        for (int i = 0; i < playerNames.length; i++) {
     	   System.out.println((i+1)+ ": " + playerNames[i]);
        }
        System.out.println();
    }

	/**
	 * Displays tile token pairs.
	 * The habitat of the tile and the token will be printed, as well as the
	 * 'image' of the tile.
	 *
	 * @param tileTokenPairs a HashMap containing the tile token pairs to be
	 *                       printed
	 */
	public static void displayTileTokenPairs(Map<HabitatTile, WildlifeToken> tileTokenPairs) {
    	System.out.println();
    	System.out.println("The current Habitat Tile + Wildlife Token pairs up for selection are: ");
    	for (HabitatTile i : tileTokenPairs.keySet()) {
    		System.out.println("Tile: " + i + ", Token: " + tileTokenPairs.get(i));
    		printHalfTile(i);
    	}
    	System.out.println();
	}

	/**
	 * Displays the <i>full</i> tile map.
	 * Detects the smallest rectangular area which contains every non-null
	 * tile, and prints every tile in this area.
	 * Null tiles are represented with empty space.
	 *
	 * @param player the player whose tile map is to be displayed
	 */
	// TODO: Add actual token characters rather than the placeholders
	public static void displayTileMap(Player player) {
		System.out.println("Player " + player.getPlayerName() + "'s current map of tiles are: ");
		HabitatTile[][] board = player.getMap().getTileBoardPosition();

		int[] boundaries = tileBoundaries(player);

		/*
		 * prints all the tiles within the boundary (the area where tiles exist)
		 * Adds to string from top to bottom, right to left. We go right to left because
		 * the indent functions add the indents to the left of the string.
		 * If a value in board is null, print a full tile indent.  Every second line add
		 * a half indent to the front.
		 */
		for (int i = boundaries[0]; i < boundaries[1]+1; i++) {
			String line = " \n \n \n \n";
			// for some reason if there isn't some empty border (gotten by
			// boundaries[2]-1) there is some weird output sometimes.
			for (int j = boundaries[2]-1; j < boundaries[3]+1; j++) {
				if (board[i][j] == null) {
					line = indentFullTile(line);
				} else {
					line = combineTiles(board[i][j].toFormattedString(), line);
				}
			}
			if (i % 2 == 0) {
				line = indentHalfTile(line);
			}
			System.out.println(line);
		}
		// prints a newline after the map
		System.out.println();

	}

	/**
	 * Finds the smallest boundary rectangle which contains every tile placed.
	 *
	 * @param player the player whose map boundary is to be found
	 * @return an int array containing the index of the [top, bottom, left, right]
	 * co-ords of the boundary
	 */
	// int[] boundaries are [top, bottom, left, right]
	private static int[] tileBoundaries(Player player) {
		HabitatTile[][] board = player.getMap().getTileBoardPosition();
		int[] boundaries = new int[4];

		// get uppermost tile
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] != null) {
					boundaries[1] = i;
					break;
				}
			}
		}

		// get lowermost tile
		for (int i = board.length - 1; i >= 0; i--) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] != null) {
					boundaries[0] = i;
					break;
				}
			}
		}

		// get leftmost tile
		for (int i = 0; i < board[0].length; i++) {
			for (HabitatTile[] habitatTiles : board) {
				if (habitatTiles[i] != null) {
					boundaries[3] = i;
					break;
				}
			}
		}

		// get rightmost tile
		for (int i = board[0].length - 1; i >= 0; i--) {
			for (HabitatTile[] habitatTiles : board) {
				if (habitatTiles[i] != null) {
					boundaries[2] = i;
					break;
				}
			}
		}
		return boundaries;
	}

	/**
	 * Indents a string by half a tile.
	 * Do <i>not</i> use two of these to generate a full tile indent, use the
	 * indent full tile method.
	 *
	 * @param tile the tile string to be indented
	 * @return a string containing an indented tile
	 * @see Display#indentFullTile(String)
	 */
	private static String indentHalfTile(String tile) {
		String[] lines = tile.split("\n");
		// using an old for loop because I don't think changes in the new ones affect
		// the thing it's iterating through
		for (int i = 0; i < 4; i++) {
			lines[i] = repeater(' ', 9) + lines[i];
		}
		return String.join("\n", lines);
	}

	/**
	 * Indents a string by a full tile.
	 * Used to provide a gap for the null tiles.
	 *
	 * @param tile the tile string to be indented
	 * @return a string containing a tile indented by a full tile
	 */
	private static String indentFullTile(String tile) {
		String output = indentHalfTile(indentHalfTile(" " + tile.replaceAll("\n", "\n ")));
		return output.substring(0, output.length()-2); // removes the trailing \n
	}

	/**
	 * Combines two tile strings into one string (with both tiles on the same
	 * line)
	 *
	 * @param first the tile that will be on the left in the combined string
	 * @param second the tile that will be on the right in the combined string
	 * @return a string with both tiles directly next to each other
	 */
	private static String combineTiles(String first, String second) {
		String[] firstLines = first.split("\n");
		String[] secondLines = second.split("\n");
		for (int i = 0; i < 4; i++) {
			firstLines[i] += secondLines[i];
		}
		return String.join("\n", firstLines);
	}

	/**
	 * Displays the interactive commands the player can select from
	 */
	public static void displayCommands() {
		System.out.println("""
				Enter PLACE to pick and place your Habitat Tile and Wildlife Token,\s
				Enter MAP for your current map of Tiles,\s
				Enter NATURE to see and spend your Nature Tokens,\s
				Enter NEXT to move on to the next player,\s
				Enter QUIT to quit the program.
				""");
	}

	/**
	 * Prints a tile to the terminal.
	 * The top half and bottom half both have a habitat colour (can be the
	 * same).
	 * At the moment there is no way to change the orientation of the tile,
	 * i.e. the habitat colours cannot split the tile vertically.
	 * @param tile the tile to be printed
	 */
	// TODO: Allow different tile orientations

	 public static void printHalfTile (HabitatTile tile) {
		 System.out.println(tile.toFormattedString());
	 }

	/**
	 * Generates the options for tokens that can be placed on a tile.
	 *
	 * @param numTokens Set to 0 for a random amount, or a number between 1-3
	 *                  to set a specified amount
	 * @return wildlife tokens
	 */
	public static WildlifeToken[] makeTokensOptionsOnTile(int numTokens) {
		if (numTokens < 0 || numTokens > 3) {
			throw new IllegalArgumentException("numTokens must be between 0-3. You entered " + numTokens);
		}
		WildlifeToken[] animalTypes = new WildlifeToken[3];
		 if (numTokens == 0) {
			 numTokens = 1 + new Random().nextInt(3);
		 }
		for (int i = 0; i < numTokens; i++) {
			animalTypes[i] = Generation.generateWildlifeToken(Bag.remainingTokens);
		}
		return animalTypes;
	}

	/**
	 * Returns a string containing a character repeated a certain amount of
	 * times.
	 *
	 * @param a the character to be repeated
	 * @param number the amount of times the character is to be repeated
	 * @return a string containing the repeated character
	 */
	private static String repeater (char a, int number) {
		if (a < 0) {
			throw new IllegalArgumentException("a cannot be negative");
		}
		char[] repeat = new char[number];
		Arrays.fill(repeat, a);
		return new String(repeat);
	}

	/**
	 * Pauses the program for a certain amount of time.
	 *
	 * @param millis the amount of milliseconds to pause the program for
	 */
	public static void sleep (int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ignored) {}
	}

	/**
	 * Displays the end screen that will be printed once the game has finished.
	 */
	public static void endScreen() {
		clearScreen();
		System.out.println("Now leaving Cascadia. Thank you for playing.");
	}

}
