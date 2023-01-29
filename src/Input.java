import java.util.*;

public class Input {
    // Note: Do *NOT* close System.in scanners.
	
	// general method for user input
	public static String getUserInput() {
		String string;
		Scanner in = new Scanner(System.in);
		string = in.nextLine().toUpperCase();
		System.out.println(string);
		return string.trim();
	}
	
	// method 1: getPlayers scans in players' names and puts them in an arraylist
    public static String[] getPlayers () {
        // Note that in this method we use an arraylist as it's simpler, but
        // we return a *String array*, which we get from the randomise method
        ArrayList<String> players = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        int num = 0;
        boolean firstRun = true;
        boolean intInputted = true;

        System.out.println("Enter number of players (must be 2-4)");

        while (num > 4 || num < 2) {
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
        in.nextLine(); // clears buffer
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

    //method 2: randomisePlayers iterates through arraylist of players and returns a string array of players' names
    //(note: might want to actually randomise arraylist itself, so we can iterate through players in correct order)
    private static String[] randomisePlayers(ArrayList<String> players) {
    	
        ArrayList<Integer> randomIndexes = new ArrayList<>();
        String[] randomisedPlayers = new String[players.size()];
        Arrays.fill(randomisedPlayers, "");

        while (randomIndexes.size() < players.size()) {
            int index = new Random().nextInt(players.size());
            if (!randomIndexes.contains(index)) {
                randomIndexes.add(index);
            }
        }

        // Random indexes now contains a list of all ints 0...num players
        // in a random order.  We can use that to put the player names in a
        // random order.
        int i = 0;
        for (Integer element : randomIndexes) {
            randomisedPlayers[element] = players.get(i++);
        }

        return randomisedPlayers;
    }


    // checks whether any of the items in the list are empty strings, or if
    // any are duplicates.
    private static boolean playerListIsInvalid(ArrayList<String> players) {
        for (int i = 0; i < players.size(); i++) {
            players.set(i, players.get(i).trim());
            if (Objects.equals(players.get(i), "")) {
                return true;
            }
        }

        // A set cannot have duplicate values, so any duplicates stored in the
        // player array list will be automatically removed when we transfer it
        // to the set.  We can use this, as if the set is smaller, an element
        // must have been removed, i.e. there is a duplicate
        Set<String> playerSet = new LinkedHashSet<>(players);
        return playerSet.size() < players.size();
    }   
    
}
