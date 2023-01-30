import java.util.Arrays;
import java.util.HashMap;

public class Output {
    public static void Welcome () {
        // ASCII art from https://patorjk.com/software/taag/#p=testall&f=Bulbhead&t=CASCADIA
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

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    public static void printPlayers(String[] playerNames) { // used in Game class
    	System.out.println();
        System.out.println("The player list is:");
    
        for (int i = 0; i < playerNames.length; i++) {
     	   System.out.println((i+1)+ ": " + playerNames[i]);
        }
        System.out.println();
    }

	public static void displayTileTokenPairs(HashMap<HabitatTile, WildlifeToken> tileTokenPairs) {
		//not sure how to do display of tiles and tokens here ??
    	//depends on drawtile class i guess - eoin have a look whenever
    	System.out.println();
    	System.out.println("The current Habitat Tile + Wildlife Token pairs up for selection are: ");
    	for (HabitatTile i : tileTokenPairs.keySet()) {
    		System.out.println("Tile: " + i + ", Token: " + tileTokenPairs.get(i));
    		printHalfTile(i, ' ', ' ', ' ',' ');
    	}
    	System.out.println();
	}

	// displays the FULL tile map.
	// TODO: Add actual token characters rather than the placeholders
	public static void displayTileMap(Player player) {
		System.out.println("Player " + player.getPlayerName() + "'s current map of tiles are: ");
		HabitatTile[][] board = player.getTileBoardPosition();

		int[] boundaries = tileBoundaries(player);

		// prints all the tiles within the boundary (the area where tiles exist)
		// Adds to string from top to bottom, right to left. We go right to left because
		// the indent functions add the indents to the left of the string.
		// If a value in board is null, print a full tile indent.  Every second line add
		// a half indent to the front.
		for (int i = boundaries[0]; i < boundaries[1]+1; i++) {
			String line = " \n \n \n \n";
			// for some reason if there isn't some empty border (gotten by
			// boundaries[2]-1) there is some weird output sometimes.
			for (int j = boundaries[2]-1; j < boundaries[3]+1; j++) {
				if (board[i][j] == null) {
					line = indentFullTile(line);
				} else {
					line = combineTiles(board[i][j].toFormattedString('A', 'B', 'C', 'D'), line);
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

	// int[] boundaries are [top, bottom, left, right]
	private static int[] tileBoundaries(Player player) {
		HabitatTile[][] board = player.getTileBoardPosition();
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

	// Note: Do not use this function twice to indent a full tile, use the indent full
	// tile method.
	private static String indentHalfTile(String tile) {
		String[] lines = tile.split("\n");
		// using an old for loop because I don't think changes in the new ones affect
		// the thing it's iterating through
		for (int i = 0; i < 4; i++) {
			lines[i] = repeater(' ', 9) + lines[i];
		}
		return String.join("\n", lines);
	}

	private static String indentFullTile(String tile) {
		String output = indentHalfTile(indentHalfTile(" " + tile.replaceAll("\n", "\n ")));
		return output.substring(0, output.length()-2); // removes the trailing \n
	}

	private static String combineTiles(String first, String second) {
		String[] firstLines = first.split("\n");
		String[] secondLines = second.split("\n");
		for (int i = 0; i < 4; i++) {
			firstLines[i] += secondLines[i];
		}
		return String.join("\n", firstLines);
	}
	
	public static void displayCommands() {
		System.out.println("""
				Enter NEXT to move on to the next player,\s
				Enter MAP for your current map of Tiles,\s
				Enter TILES to see the Tiles you have in inventory,\s
				Enter TOKENS to see Wildlife Tokens you have in inventory,\s
				Enter PLACETOKEN to place a Wildlife Token on a Tile,
				Enter QUIT to quit the program.
				""");
//		System.out.println();
	}


	public static final String ANSI_RESET = "\u001B[0m";
//    public static final String ANSI_BLACK = "\u001B[30m";

	// new display method which is closer to what he wants
	// TODO: Change the background colour based on whether a token is selected
	public static void printHalfTile (HabitatTile tile, char char1, char char2,
									  char char3, char char4) {
		String first = tile.getHabitat1().getBackgroundColour();
		String second = tile.getHabitat2().getBackgroundColour();
		String full =  "    |    |    |    " + ANSI_RESET + "\n";
		System.out.println(
				first + full +
				first + "    |" + ANSI_RESET + "  " + char1 + "   " + char2 + "  " +
				first  + "|    " + ANSI_RESET + "\n" +
				second + "    |" + ANSI_RESET + "  " + char3  + "   " + char4 + "  " +
				second  + "|    " + ANSI_RESET + "\n" +
				second + full
		);

	}

	// repeats character 'a' number times, just nicer than using a for loop
	private static String repeater (char a, int number) {
		char[] repeat = new char[number];
		Arrays.fill(repeat, a);
		return new String(repeat);
	}

	public static void sleep (int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ignored) {}
	}
}
