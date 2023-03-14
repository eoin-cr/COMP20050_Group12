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

    /**
     * Scans in players' names, randomises them, and puts them in a String array.
     *
     * @return a randomised String array of player names
     */
    public static String[] getPlayers () {
        // Note that in this method we use an arraylist as it's simpler, but
        // we return a *String array*, which we get from the randomise method
        List<String> players = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        int num = 0;
        boolean firstRun = true;
        boolean intInputted = true;
        final int MIN_PLAYERS = 2;
        final int MAX_PLAYERS = 4;

        System.out.println("Enter number of players (must be " + MIN_PLAYERS + "-" + MAX_PLAYERS + ")");

        while (num > MAX_PLAYERS || num < MIN_PLAYERS) {
            if (!firstRun && intInputted) {
                System.out.println("Invalid number.  Please enter the number of players (must be between 2-4)");
            }
            try {
                num = in.nextInt();
                intInputted = true;
            } catch (InputMismatchException ex) { // catches if user doesn't enter an int
                System.out.println("You must enter an integer!  Please try again");
                in.nextLine(); // clears buffer
                intInputted = false;
            }
            firstRun = false;
        }

        System.out.printf("Enter the names of the %d players.  Hit enter after each name.\n", num);
        in.nextLine();  // clears buffer
        for (int i = 0; i < num; i++) {
            players.add(in.nextLine().toUpperCase());
        }

        while (playerListIsInvalid(players)) {
            System.out.println("Invalid input!  You cannot have duplicate names, nor " +
                    "empty strings. All names have been wiped, please try again.");
            players.clear();
            for (int i = 0; i < num; i++) {
                players.add(in.nextLine().toUpperCase());
            }
        }
        // Returns randomised String array version of the player names
        return randomisePlayers(players);
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
        int choice;
        System.out.println();

        do {
            System.out.println("Please choose a Habitat Tile + Wildlife Token pair from the selection above.");
            System.out.println("Type 1 for the first pair, 2 for the second, 3 for the third, 4 for the fourth: ");
            try {
                choice = Integer.parseInt(getUserInput());
            } catch (NumberFormatException e) {
                System.out.println("You did not input a number. Please try again.");
                choice = Integer.parseInt(getUserInput());
            }
        } while (choice < 1 || choice > 4);

        choice--;
        System.out.println("You have chosen the pair: " +CurrentDeck.getTile(choice).getHabitat1()+ " + "
                +CurrentDeck.getTile(choice).getHabitat2()+ " tile, " +CurrentDeck.getToken(choice)+ " token.");

        return choice;
    }

    public static int[] chooseTokenPlaceOrReturn(WildlifeToken token) {
        int[] result = new int[2];
        int choice;
        int tileID = -1;

        System.out.println();
        System.out.println("Choose what to do with the " +token.name()+ " token you have drawn.");

        do {
            System.out.println("Type 1 to place the token on one of your tiles, or 2 to put the token back in the bag: ");
            try {
                choice = Integer.parseInt(getUserInput());
                //System.out.println(choice);
            } catch (NumberFormatException e) {
                System.out.println("You did not input a number. Please try again.");
                choice = Integer.parseInt(getUserInput());
            }
        } while(choice < 1 || choice > 2);


        if (choice == 1) {
            do {
                System.out.println("Choose the tile number where you want to place the " +token.name()+ " token: ");
                try {
                    tileID = Integer.parseInt(getUserInput());
                    //System.out.println(choice);
                } catch (NumberFormatException e) {
                    System.out.println("You did not input a number. Please try again.");
                    tileID = Integer.parseInt(getUserInput());
                }
            } while(tileID < 0 || tileID > HabitatTile.getTileCounter());

        }

        result[0] = choice;
        result[1] = tileID;

        return result;
    }

    /**
     * Takes input of an int in a range and returns it.
     *
     * @param lowerBound {@code input >= lowerbound}
     * @param upperBound {@code input <= upperbound}
     * @param firstMessage {@code the message to print}
     * @return an int such that {@code lowerbound <= input <= upperbound}
     */
    public static int boundedInt(int lowerBound, int upperBound, String firstMessage)  {
        int choice;
        do {
            System.out.println(firstMessage);
            try {
                choice = Integer.parseInt(getUserInput());
                //System.out.println(choice);
            } catch (NumberFormatException e) {
                System.out.println("You did not enter a number. Please try again.");
                choice = Integer.parseInt(getUserInput());
            }
        }while(choice < lowerBound || choice > upperBound);

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
        System.out.println("Enter the tile ID where you want the tile to be placed");
        Scanner in = new Scanner(System.in);
        int input=-1;

        boolean firstRun = true;
        boolean intInputted = true;

        int[] coords = new int[]{-1,-1};
        while (coords[0] == -1 && coords[1] == -1) {
            if (!firstRun && intInputted) {
                System.out.println("Invalid number.  Please enter the tileID");
            }
            try {
                input = in.nextInt();
                intInputted = true;
            } catch (InputMismatchException ex) { // catches if user doesn't enter an int
                System.out.println("You must enter an integer!  Please try again");
                in.nextLine(); // clears buffer
                intInputted = false;
            }
            firstRun = false;
            coords = tmpMap.getMap().returnPositionOfID(input);
        }
        return coords;
    }

}
