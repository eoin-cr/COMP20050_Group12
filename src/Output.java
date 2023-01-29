import java.util.Arrays;
import java.util.HashMap;

public class Output {
    public static void Welcome () {
        // ASCII art from https://patorjk.com/software/taag/#p=testall&f=Bulbhead&t=CASCADIA
//        System.out.println(
//            "___/\\/\\/\\/\\/\\______/\\/\\________/\\/\\/\\/\\/\\____/\\/\\/\\/\\/\\______/\\/\\______/\\/\\/\\/\\/\\____/\\/\\/\\/\\______/\\/\\____\n" +
//            "/\\/\\____________/\\/\\/\\/\\____/\\/\\__________/\\/\\____________/\\/\\/\\/\\____/\\/\\____/\\/\\____/\\/\\______/\\/\\/\\/\\___        \n" +
//            "/\\/\\__________/\\/\\____/\\/\\____/\\/\\/\\/\\____/\\/\\__________/\\/\\____/\\/\\__/\\/\\____/\\/\\____/\\/\\____/\\/\\____/\\/\\_        \n" +
//            "/\\/\\__________/\\/\\/\\/\\/\\/\\__________/\\/\\__/\\/\\__________/\\/\\/\\/\\/\\/\\__/\\/\\____/\\/\\____/\\/\\____/\\/\\/\\/\\/\\/\\_        \n" +
//            "__/\\/\\/\\/\\/\\__/\\/\\____/\\/\\__/\\/\\/\\/\\/\\______/\\/\\/\\/\\/\\__/\\/\\____/\\/\\__/\\/\\/\\/\\/\\____/\\/\\/\\/\\__/\\/\\____/\\/\\_        \n"
////            "____________________________________________________________________________________________________________\n"
//        );

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

//        System.out.println(
//                """
//                                  _____                    _____                    _____                    _____                    _____                    _____                    _____                    _____          \s
//                                 /\\    \\                  /\\    \\                  /\\    \\                  /\\    \\                  /\\    \\                  /\\    \\                  /\\    \\                  /\\    \\         \s
//                                /::\\    \\                /::\\    \\                /::\\    \\                /::\\    \\                /::\\    \\                /::\\    \\                /::\\    \\                /::\\    \\        \s
//                               /::::\\    \\              /::::\\    \\              /::::\\    \\              /::::\\    \\              /::::\\    \\              /::::\\    \\               \\:::\\    \\              /::::\\    \\       \s
//                              /::::::\\    \\            /::::::\\    \\            /::::::\\    \\            /::::::\\    \\            /::::::\\    \\            /::::::\\    \\               \\:::\\    \\            /::::::\\    \\      \s
//                             /:::/\\:::\\    \\          /:::/\\:::\\    \\          /:::/\\:::\\    \\          /:::/\\:::\\    \\          /:::/\\:::\\    \\          /:::/\\:::\\    \\               \\:::\\    \\          /:::/\\:::\\    \\     \s
//                            /:::/  \\:::\\    \\        /:::/__\\:::\\    \\        /:::/__\\:::\\    \\        /:::/  \\:::\\    \\        /:::/__\\:::\\    \\        /:::/  \\:::\\    \\               \\:::\\    \\        /:::/__\\:::\\    \\    \s
//                           /:::/    \\:::\\    \\      /::::\\   \\:::\\    \\       \\:::\\   \\:::\\    \\      /:::/    \\:::\\    \\      /::::\\   \\:::\\    \\      /:::/    \\:::\\    \\              /::::\\    \\      /::::\\   \\:::\\    \\   \s
//                          /:::/    / \\:::\\    \\    /::::::\\   \\:::\\    \\    ___\\:::\\   \\:::\\    \\    /:::/    / \\:::\\    \\    /::::::\\   \\:::\\    \\    /:::/    / \\:::\\    \\    ____    /::::::\\    \\    /::::::\\   \\:::\\    \\  \s
//                         /:::/    /   \\:::\\    \\  /:::/\\:::\\   \\:::\\    \\  /\\   \\:::\\   \\:::\\    \\  /:::/    /   \\:::\\    \\  /:::/\\:::\\   \\:::\\    \\  /:::/    /   \\:::\\ ___\\  /\\   \\  /:::/\\:::\\    \\  /:::/\\:::\\   \\:::\\    \\ \s
//                        /:::/____/     \\:::\\____\\/:::/  \\:::\\   \\:::\\____\\/::\\   \\:::\\   \\:::\\____\\/:::/____/     \\:::\\____\\/:::/  \\:::\\   \\:::\\____\\/:::/____/     \\:::|    |/::\\   \\/:::/  \\:::\\____\\/:::/  \\:::\\   \\:::\\____\\\s
//                        \\:::\\    \\      \\::/    /\\::/    \\:::\\  /:::/    /\\:::\\   \\:::\\   \\::/    /\\:::\\    \\      \\::/    /\\::/    \\:::\\  /:::/    /\\:::\\    \\     /:::|____|\\:::\\  /:::/    \\::/    /\\::/    \\:::\\  /:::/    /\s
//                         \\:::\\    \\      \\/____/  \\/____/ \\:::\\/:::/    /  \\:::\\   \\:::\\   \\/____/  \\:::\\    \\      \\/____/  \\/____/ \\:::\\/:::/    /  \\:::\\    \\   /:::/    /  \\:::\\/:::/    / \\/____/  \\/____/ \\:::\\/:::/    / \s
//                          \\:::\\    \\                       \\::::::/    /    \\:::\\   \\:::\\    \\       \\:::\\    \\                       \\::::::/    /    \\:::\\    \\ /:::/    /    \\::::::/    /                    \\::::::/    /  \s
//                           \\:::\\    \\                       \\::::/    /      \\:::\\   \\:::\\____\\       \\:::\\    \\                       \\::::/    /      \\:::\\    /:::/    /      \\::::/____/                      \\::::/    /   \s
//                            \\:::\\    \\                      /:::/    /        \\:::\\  /:::/    /        \\:::\\    \\                      /:::/    /        \\:::\\  /:::/    /        \\:::\\    \\                      /:::/    /    \s
//                             \\:::\\    \\                    /:::/    /          \\:::\\/:::/    /          \\:::\\    \\                    /:::/    /          \\:::\\/:::/    /          \\:::\\    \\                    /:::/    /     \s
//                              \\:::\\    \\                  /:::/    /            \\::::::/    /            \\:::\\    \\                  /:::/    /            \\::::::/    /            \\:::\\    \\                  /:::/    /      \s
//                               \\:::\\____\\                /:::/    /              \\::::/    /              \\:::\\____\\                /:::/    /              \\::::/    /              \\:::\\____\\                /:::/    /       \s
//                                \\::/    /                \\::/    /                \\::/    /                \\::/    /                \\::/    /                \\::/____/                \\::/    /                \\::/    /        \s
//                                 \\/____/                  \\/____/                  \\/____/                  \\/____/                  \\/____/                  ~~                       \\/____/                  \\/____/         \s""".indent(1)
//        );
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

//	public static void displayTileMap(Player player) {
//		System.out.println("Player " + player.getPlayerName() + "'s current map of tiles are: \n");
//		for (HabitatTile tile : player.getPlayerTiles()) {
//			printHalfTile(tile, ' ', ' ', ' ',' ');
//		}
//	}

	// creating a new method for the starter habitat for now as this method will not be
	// scalable.  Will create a scalable one later.
	// TODO: Add actual token characters rather than the placeholders
	public static void displayStarterHabitat(Player player) {
		System.out.println("Player " + player.getPlayerName() + "'s current map of tiles are: ");
//		for (HabitatTile tile : player.getPlayerTiles()) {
//			printHalfTile(tile, ' ', ' ', ' ',' ');
//		}
		System.out.println(indentTile(player.getPlayerTiles().get(0).toFormattedString('A', 'B', 'C', 'D')));
		System.out.println(combineTiles(player.getPlayerTiles().get(1).toFormattedString('A','B', 'C', 'D'),
							player.getPlayerTiles().get(2).toFormattedString('A','B','C', 'D')) + "\n"
		);
	}

	private static String indentTile(String tile) {
		String[] lines = tile.split("\n");
		// using an old for loop because I don't think changes in the new ones affect
		// the thing it's iterating through
		for (int i = 0; i < 4; i++) {
			lines[i] = repeater(' ', 9) + lines[i];
		}
		return String.join("\n", lines);
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

	// ANSI colours to colour console text.  Indexes match with the
	// Tile.habitats enum.
	static String[] HabitatColours = {
			"\u001B[32m",
			"\u001B[36m",
			"\u001B[34m",
			"\u001B[37m",
			"\u001B[33m"
	};

	static String[] backgroundHabitatColours = {
			"\u001B[42m",
			"\u001B[46m",
			"\u001B[44m",
			"\u001B[47m",
			"\u001B[43m"
	};

	// new display method which is closer to what he wants
	// TODO: Change the background colour based on whether a token is selected
	public static void printHalfTile (HabitatTile tile, char char1, char char2,
									  char char3, char char4) {
		String first = backgroundHabitatColours[tile.getHabitat1().ordinal()];
		String second = backgroundHabitatColours[tile.getHabitat2().ordinal()];
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
