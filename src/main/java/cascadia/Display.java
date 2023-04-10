/*
	COMP20050 Group 12
	Eoin Creavin – Student ID: 21390601
	eoin.creavin@ucdconnect.ie
	GitHub ID: eoin-cr

	Mynah Bhattacharyya – Student ID: 21201085
	malhar.bhattacharyya@ucdconnect.ie
	GitHub ID: mynah-bird

	Ben McDowell – Student ID: 21495144
	ben.mcdowell@ucdconnect.ie
	GitHub ID: Benmc1
 */

package cascadia;

import java.util.Objects;

import cascadia.HabitatTile.Habitat;

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
        Display.outln(
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

        Display.outln("Welcome to Cascadia!");

        // another sleep call
		sleep(300);
    }

	/**
	 * Prints the player names, as well as the number showing the order they
	 * will be playing in.
	 *
	 * @param playerNames a String array of player names
	 */
	public static void printPlayers(String[] playerNames) { // used in cascadia.Game class
    	Display.outln("");
        Display.outln("The player list is:");
    
        for (int i = 0; i < playerNames.length; i++) {
			Display.outln((i + 1) + ": " + playerNames[i]);
        }
        Display.outln("");
    }

	/**
	 * Displays tile token pairs.
	 * The habitat of the tile and the token will be printed, as well as the
	 * 'image' of the tile.
	 */
	public static void displayDeck() {
    	Display.outln("");
    	Display.outln("The current Habitat Tile + Wildlife Token pairs up for selection are: ");
		String output = "";

    	for (int i = 0; i < Constants.MAX_DECK_SIZE; i++) {
			String toAdd = " " + centerString(18, CurrentDeck.getToken(i).toString()) + "\n"
					+ CurrentDeck.getTile(i).toFormattedString();
			output = removeNewlineAndJoin(output, toAdd, "        ");
    	}
		Display.outln(output);
    	Display.outln("");
	}

	// https://stackoverflow.com/questions/8154366/how-to-center-a-string-using-string-format
	public static String centerString(int width, String s) {
		return String.format("%-" + width  + "s",
				String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
	}

	public static void cullOccurrence() {
		Display.outln("A Wildlife Token cull has occurred.");
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
		Display.outln("Player " + player.getPlayerName() + "'s current map of tiles are: ");
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
		for (int i = boundaries[0]; i < boundaries[1] + 1; i++) {
			String line = " \n \n \n \n";
			// for some reason if there isn't some empty border (gotten by
			// boundaries[2]-1) there is some weird output sometimes.
			for (int j = boundaries[3] + 1; j > boundaries[2] - 1; j--) {
				if (board[i][j] == null) {
					line = indentFullTile(line);
				} else {
					line = combineTiles(board[i][j].toFormattedString(), line);
				}
			}
			if (i % 2 == 0) {
				line = indentHalfTile(line);
			}
			Display.outln(line);
		}
		// prints a newline after the map
		Display.outln("");

	}

	/**
	 * Finds the smallest boundary rectangle which contains every tile placed.
	 *
	 * @param map the map whose boundary to be found
	 * @return an int array containing the index of the [top, bottom, left, right]
	 * 				co-ords of the boundary
	 */
	// int[] boundaries are [top, bottom, left, right]
	private static int[] tileBoundaries(PlayerMap map) {
		HabitatTile[][] board = map.getTileBoardPosition();
		int uppermost = board.length;
		int lowermost = 0;
		int leftmost = board[0].length;
		int rightmost = 0;

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] != null) {
					if (i < uppermost) {
						uppermost = i;
					}
					if (i > lowermost) {
						lowermost = i;
					}
					if (j < leftmost) {
						leftmost = j;
					}
					if (j > rightmost) {
						rightmost = j;
					}
				}
			}
		}
		return new int[]{uppermost, lowermost, leftmost, rightmost};
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
		return output.substring(0, output.length() - 2); // removes the trailing \n
	}

	/**
	 * Combines two tile strings into one string (with both tiles on the same
	 * line).
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
		Display.outln("");
		Display.outln("-----------------------------------------------------------------------");
		Display.outln("------------------------------SCORING START----------------------------");
		Display.outln("-----------------------------------------------------------------------");
		Display.outln("");
	}

	/**
	 * Displays the interactive commands the player can select from.
	 */
	public static void displayCommands() {
		for (Command command : Command.values()) {
			Display.outln(command.getDescription());
		}
	}

	/**
	 * Pauses the program for a certain amount of time.
	 *
	 * @param millis the amount of milliseconds to pause the program for
	 */
	public static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ignored) { }
	}

	/**
	 * Displays the end screen that will be printed once the game has finished.
	 */
	public static void endScreen() {
		Display.outln("Now leaving Cascadia. Thank you for playing.");
	}

    // displays the different rotation options in order, rotates tile choice to that rotation
    public static void selectTileRotation(HabitatTile tile) {
        String orientationOptions = "";
		// we don't want to bother with rotating a keystone tile as they only have one orientation
		if (tile.isKeystone()) {
			return;
		}

		for (int i = 0; i < Constants.NUM_EDGES; i++) {
			tile.rotateTile(1);
			orientationOptions = removeNewlineAndJoin(
					orientationOptions, tile.toFormattedString(), "        "
			);
		}
		Display.outln(orientationOptions);
		// allows the user to select what rotation they want
		tile.rotateTile(-1);
    }
    
    public static void playerTurnStats(Player p) {
    	Display.outln("\nPlayer " +p.getPlayerName()+ "'s turn stats are: ");
    	Display.outln("Total Turn Score = " + p.getTotalPlayerScore());
    	Display.outln("Wildlife Scores: Total = " + p.getWildLifeScore());
    	Display.outln("Bear \t\tElk \t\tSalmon \t\tHawk \t\tFox");
    	Display.outln(p.getPlayerWildlifeScore(WildlifeToken.Bear)+ " \t\t"
    			+ p.getPlayerWildlifeScore(WildlifeToken.Elk)+ " \t\t"
    			+ p.getPlayerWildlifeScore(WildlifeToken.Salmon)+ " \t\t"
    			+ p.getPlayerWildlifeScore(WildlifeToken.Hawk)+ " \t\t"
    			+ p.getPlayerWildlifeScore(WildlifeToken.Fox)+ " \t\t"
    			);
    	Display.outln("Habitat Corridor Scores: Total = " + p.getCorridorScore());
    	Display.outln("Forest \t\tWetland \tRiver \t\tMountain \tPrairie");
    	Display.outln(p.getLongestCorridorSize(Habitat.Forest)+ " \t\t"
    			+ p.getLongestCorridorSize(Habitat.Wetland)+ " \t\t"
    			+ p.getLongestCorridorSize(Habitat.River)+ " \t\t"
    			+ p.getLongestCorridorSize(Habitat.Mountain)+ " \t\t"
    			+ p.getLongestCorridorSize(Habitat.Prairie)+ " \t\t"
    			);
    	Display.outln("Nature Token Scores: Total = " + p.getPlayerNatureTokens());
    	
    }

	// Allows us to easily change the output method (e.g. If we needed to change
	// it to output to a file, we can just change these 3 methods, rather than
	// every single System.out call)
	public static void outln(String s) {
		System.out.println(s);
	}

	public static void outf(String format, Object ... args) {
		System.out.printf(format, args);
	}

	public static void out(String s) {
		System.out.print(s);
	}
}