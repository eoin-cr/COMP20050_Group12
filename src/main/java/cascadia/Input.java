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

import java.util.*;

/**
 * Contains methods involved with taking input from the users.
 */
public class Input {
    // Note: Do *NOT* close System.in scanners.

    /**
     * Scans in user input and returns it converted to upper case and trimmed.
     *
     * @return a trimmed and uppercase string containing user input.
     */
	public static String getUserInput() {
		String string;
		Scanner in = new Scanner(System.in);
		string = in.nextLine().toUpperCase();
		return string.trim();
	}
	
	public static boolean getBotModeOnOrOff() {
		String choice;
		boolean output = false;
		Display.outln("\nYou can either play as users and make your own choices in game, or enter Bot Mode.");
		Display.outln("In Bot Mode, the game is automated - two bots play a game against each other.");
		Display.outln("Would you like Bot Mode ON or OFF?");
		
		do {
			choice = getUserInput();
			//Display.outln(choice);
			if (choice.equals("ON")){
				output = true;
				break;
			}
			else if (choice.equals("OFF")){
				output = false;
				break;
			}
			else {
				Display.outln("Invalid input! Please type ON or OFF");
			}
        } while(!choice.equals("ON") || !choice.equals("OFF"));
		
		return output;
	}

    /**
     * Scans in players' names, randomises them, and puts them in a String array.
     *
     * @return a randomised String array of player names
     */
    public static String[] getPlayers() {
        // Note that in this method we use an arraylist as it's simpler, but
        // we return a *String array*, which we get from the randomise method
        final int MIN_PLAYERS = 2;
        final int MAX_PLAYERS = 4;

        int num = boundedInt(MIN_PLAYERS, MAX_PLAYERS, "Enter number of players (must be "
                + MIN_PLAYERS + "-" + MAX_PLAYERS + ")");

        List<String> players = getPlayerNames(num);
        // Returns randomised String array version of the player names
        return randomisePlayers(players);
    }

    private static List<String> getPlayerNames(int num) {
        Scanner in = new Scanner(System.in);
        List<String> players = new ArrayList<>();
        Display.outf("Enter the names of the %d players.  Hit enter after each name.\n", num);
        // get n strings
        for (int i = 0; i < num; i++) {
            players.add(in.nextLine().toUpperCase());
        }

        // asks for input again every time the input was invalid
        while (playerListIsInvalid(players)) {
            Display.outln("Invalid input!  You cannot have duplicate names, nor "
                    + "empty strings. All names have been wiped, please try again.");
            players.clear();
            for (int i = 0; i < num; i++) {
                players.add(in.nextLine().toUpperCase());
            }
        }
        return players;
    }

    /**
     * Iterates through arraylist of players and returns a string array of players' names.
     *
     * @param players a <i>List</i> of player names to be randomised
     * @return a <i>String array</i> of randomised player names
     */
    private static String[] randomisePlayers(List<String> players) {
        List<Integer> randomIndexes = new ArrayList<>();
        String[] randomisedPlayers = new String[players.size()];
        Arrays.fill(randomisedPlayers, "");

        while (randomIndexes.size() < players.size()) {
            int index = new Random().nextInt(players.size());
            if (!randomIndexes.contains(index)) {
                randomIndexes.add(index);
            }
        }

        /*
         * Random indexes now contains a list of all ints 0...num players
         * in a random order.  We can use that to put the player names in a
         * random order.
         */
        int i = 0;
        for (Integer element : randomIndexes) {
            randomisedPlayers[element] = players.get(i++);
        }
        return randomisedPlayers;
    }

    /**
     * Checks whether any of the items in the list are empty strings, or if
     * any are duplicates.
     *
     * @param players the player name ArrayList to be checked for invalid names.
     * @return true if the list is invalid
     */
    private static boolean playerListIsInvalid(List<String> players) {
        for (int i = 0; i < players.size(); i++) {
            players.set(i, players.get(i).trim());
            if (Objects.equals(players.get(i), "")) {
                return true;
            }
        }

        /*
         * A set cannot have duplicate values, so any duplicates stored in the
         * player array list will be automatically removed when we transfer it
         * to the set.  We can use this, as if the set is smaller, an element
         * must have been removed, i.e. there is a duplicate
         */
        Set<String> playerSet = new LinkedHashSet<>(players);
        return playerSet.size() < players.size();
    }

    /**
     * Allows the user to select a tile token pair from the deck.
     * Returns a number from 0-3, representing where in the deck the chosen
     * tile was.
     *
     * @return an int from 0-3 (inclusive)
     */
    public static int chooseFromDeck() {
        Display.outln("");
        int choice = Input.boundedInt(1, Constants.MAX_DECK_SIZE,
                "Please choose a Habitat Tile + Wildlife Token pair from the selection "
                        + "above.\nType 1 for the first pair, 2 for the second, 3 for the third, "
                        + "4 for the fourth: ");

        choice--;
        Display.outln("You have chosen the pair: " + CurrentDeck.getTile(choice).getHabitat1()
                + " + " + CurrentDeck.getTile(choice).getHabitat2() + " tile, "
                + CurrentDeck.getToken(choice) + " token.");

        return choice;
    }

    public static int[] chooseTokenPlaceOrReturn(WildlifeToken token) {
        int tileID = -1;

        Display.outln("");
        Display.outln("Choose what to do with the " + token.name() + " token you have drawn.");

        int choice = Input.boundedInt(1, 2, "Type 1 to place the "
                + "token on one of your tiles, or 2 to put the token back in the bag: ");

        if (choice == 1) {
            tileID = Input.boundedInt(0, HabitatTile.getTileCounter(),
                    "Choose the tile number where you want to place the "
                            + token.name() + " token: ");
        }

        int[] result = new int[2];
        result[0] = choice;
        result[1] = tileID;

        return result;
    }
    
    public static int chooseCullThreeOptions() {
		Display.outln("");
		Display.outln("There are three Wildlife Tokens of the same type. Would you like "
                + "to cull them? ");
		int choice = boundedInt(1, 2, "Type 1 to cull and replace "
                + "tokens, or 2 to leave tokens untouched: ");
		if (choice == 1) {
			Display.outln("You have chosen to cull three tokens of the same type in the deck.");
		} else {
			Display.outln("You have chosen to leave the tokens untouched. The current deck "
                    + "remains the same.");
		}
		return choice;
    }

    /**
     * Takes input of an int in a range and returns it.
     *
     * @param lowerBound {@code input >= lowerbound}
     * @param upperBound {@code input <= upperbound}
     * @param firstMessage {@code the message to print}
     * @return an int such that {@code lowerbound <= input <= upperbound}
     */
    public static int boundedInt(int lowerBound, int upperBound, String firstMessage) {
        int choice = lowerBound - 1;
        do {
            Display.outln(firstMessage);
            try {
                choice = Integer.parseInt(getUserInput());
            } catch (NumberFormatException e) {
                Display.outln("You did not enter a number. Please try again.");
            }
        } while (choice < lowerBound || choice > upperBound);

        return choice;
    }

    /**
     * Allows the user to choose the tile placement.
     * Checks are done to ensure that tiles can only be placed according to the
     * rules of the game
     */
    public static int[] chooseTilePlacement(Player player) {
        // display tile placement map
        Player tmpMap = Display.displayPlacementMap(player);
        int input;

        int[] coords = new int[]{-1, -1};
        while (coords[0] == -1 && coords[1] == -1) {
            /*
             we won't do any of the checking whether a tile exists in the
             bounded int method, we'll just make sure it's a valid int.
             We can just set the upper bound to some ridiculous number.
            */
            input = boundedInt(-1, 9999999,
                    "Enter the tile ID (number at bottom right of tile) where you "
                            + "want the tile to be placed");
            coords = tmpMap.getMap().returnPositionOfID(input);
        }
        return coords;
    }
}
