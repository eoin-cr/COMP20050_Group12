package cascadia;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

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
        Display.out(
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

        Display.out("Welcome to Cascadia!");

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
	public static void printPlayers(String[] playerNames) { // used in cascadia.Game class
    	Display.out("");
        Display.out("The player list is:");
    
        for (int i = 0; i < playerNames.length; i++) {
     	   Display.out((i+1)+ ": " + playerNames[i]);
        }
        Display.out("");
    }

	/**
	 * Displays tile token pairs.
	 * The habitat of the tile and the token will be printed, as well as the
	 * 'image' of the tile.
	 */
	public static void displayDeck() {
    	Display.out("");
    	Display.out("The current Habitat Tile + Wildlife Token pairs up for selection are: ");
		String output = "";

    	for (int i = 0; i < 4; i++) {
			String toAdd = " " + centerString(18, CurrentDeck.getToken(i).toString()) + "\n"
					+ CurrentDeck.getTile(i).toFormattedString();
			output = removeNewlineAndJoin(output, toAdd, "        ");
    	}
		Display.out(output);
    	Display.out("");
	}

	// https://stackoverflow.com/questions/8154366/how-to-center-a-string-using-string-format
	public static String centerString (int width, String s) {
		return String.format("%-" + width  + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
	}

	public static void cullOccurrence() {
		Display.out("A Wildlife Token cull has occurred.");
		displayDeck();
	}

	/**
	 * Displays the possible tile placements on the passed player's map.
	 *
	 * @param player the players map to be displayed with the possible tile
	 *               placements
	 */
	public static Player displayPlacementMap(Player player) {
		Player tmpPlayer = new Player("tmp");
		tmpPlayer.getMap().setTileBoard(PlayerMap.deepCopy(player.getMap().getTileBoardPosition()));
		tmpPlayer.getMap().addPossibleTiles();
		displayPlayerTileMap(tmpPlayer);
		return tmpPlayer;
	}

	/**
	 * Displays the <i>full</i> tile map.
	 * Detects the smallest rectangular area which contains every non-null
	 * tile, and prints every tile in this area.
	 * Null tiles are represented with empty space.
	 *
	 * @param player the player's tile map to be displayed
	 */
	public static void displayPlayerTileMap(Player player) {
		Display.out("Player " + player.getPlayerName() + "'s current map of tiles are: ");
		displayTileMap(player.getMap());
	}

	/**
	 * Displays the <i>full</i> tile map.
	 * Detects the smallest rectangular area which contains every non-null
	 * tile, and prints every tile in this area.
	 * Null tiles are represented with empty space.
	 *
	 * @param map the tile map to be displayed
	 */
	public static void displayTileMap(PlayerMap map) {
		HabitatTile[][] board = map.getTileBoardPosition();

		int[] boundaries = tileBoundaries(map);

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
			for (int j = boundaries[3]+1; j > boundaries[2]-1; j--) {
				if (board[i][j] == null) {
					line = indentFullTile(line);
				} else {
					line = combineTiles(board[i][j].toFormattedString(), line);
				}
			}
			if (i % 2 == 0) {
				line = indentHalfTile(line);
			}
			Display.out(line);
		}
		// prints a newline after the map
		Display.out("");

	}

	/**
	 * Finds the smallest boundary rectangle which contains every tile placed.
	 *
	 * @param map the map whose boundary to be found
	 * @return an int array containing the index of the [top, bottom, left, right]
	 * co-ords of the boundary
	 */
	// int[] boundaries are [top, bottom, left, right]
	private static int[] tileBoundaries(PlayerMap map) {
		HabitatTile[][] board = map.getTileBoardPosition();
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
			lines[i] = "         " + lines[i];
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

	public static String removeNewlineAndJoin(String first, String second, String deliminator) {
		if (Objects.equals(first, "")) {
			return second;
		} else if (Objects.equals(second, "")) {
			return first;
		}
		String[] firstLines = first.split("\n");
		String[] secondLines = second.split("\n");
		for (int i = 0; i < firstLines.length && i < secondLines.length; i++) {
			firstLines[i] += (deliminator + secondLines[i]);
		}
		return String.join("\n", firstLines);

	}
	
	public static void scoringScreen() {
		Display.out("");
		Display.out("-----------------------------------------------------------------------");
		Display.out("------------------------------SCORING START----------------------------");
		Display.out("-----------------------------------------------------------------------");
		Display.out("");
	}

	/**
	 * Displays the interactive commands the player can select from
	 */
	public static void displayCommands() {
		for (Command.CommandType command : Command.CommandType.values()) {
			Display.out(command.getDescription());
		}
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
		Display.out("Now leaving Cascadia. Thank you for playing.");
	}

    // displays the different rotation options in order, rotates tile choice to that rotation
    public static void rotateTile(HabitatTile tile) {
        String orientationOptions = "";
        if (!tile.isKeystone()) {
            for (int i = 0; i < 6; i++) {
                tile.rotateTile(1);
                    orientationOptions = removeNewlineAndJoin(
                        orientationOptions, tile.toFormattedString(), "\t\t\t"
                    );
            }
            Display.out(orientationOptions);
            // allows the user to select what rotation they want
            tile.rotateTile(-1);
        }
    }

	public static void out(@Nullable String s) {
		System.out.println(s);
	}

//	public static void outf(@Nullable String s) {
	public static void outf(String format, Object ... args) {
		System.out.printf(format, args);
	}
//	}
}