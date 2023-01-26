import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;

public class Input {
	
	//method 1: getPlayers scans in players' names and puts them in an arraylist
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
            players.add(in.nextLine());
        }

        // Returns randomised String array version of the player names
        return randomisePlayers(players);
    }

    //method 2: randomisePlayers iterates through arraylist of players and returns a string array of players' names
    //(note: might want to actually randomise arraylist itself so we can iterate through players in correct order)
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
}
