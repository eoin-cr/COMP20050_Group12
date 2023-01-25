import java.util.*;

public class Input {
    public static String[] getPlayers () {
        // note that in this method we use an arraylist as it's simpler, but
        // we return a *String array*, which we get from the randomise method
        ArrayList<String> players = new ArrayList<>();
        System.out.println("Enter number of players (must be 2-4)");
        Scanner in = new Scanner(System.in);
        int num = 0;
        boolean firstRun = true;
        boolean intInputted = true;
//        String tmp;
        while (num > 4 || num < 2) {
            if (!firstRun && intInputted) {
                System.out.println("Invalid number.  Please enter the number of players (must be between 2-4)");
            }
            try {
                num = in.nextInt();
                intInputted = true;
            } catch (InputMismatchException ex) {
                System.out.println("You must enter an integer!  Please try again");
//                tmp = in.nextLine();
                in.nextLine();
                intInputted = false;
            }
            firstRun = false;
        }
        System.out.printf("Enter the names of the %d players.  Hit enter after each name.\n", num);
//        tmp = in.nextLine();
        in.nextLine();
        for (int i = 0; i < num; i++) {
            players.add(in.nextLine());
        }
        while (players.size() > num) {
            System.out.printf("Invalid number of players.  Remember that you selected %d players!\n", num);
            players.clear();
            for (int i = 0; i < num; i++) {
                players.add(in.nextLine());
            }
        }

        return randomisePlayers(players);
    }

    private static String[] randomisePlayers(ArrayList<String> players) {
        ArrayList<Integer> randomIndexes = new ArrayList<>();
        String[] randomisedPlayers = new String[players.size()];
        Arrays.fill(randomisedPlayers, "");

//        for (int i = 0; i < players.size(); i++) {
//            randomIndexes.add(new Random().nextInt(players.size()));
//        }
        while (randomIndexes.size() < players.size()) {
            int index = new Random().nextInt(players.size());
            if (!randomIndexes.contains(index)) {
                randomIndexes.add(index);
            }
        }

//        System.out.println(randomIndexes);
        int i = 0;
        for (Integer element : randomIndexes) {
            randomisedPlayers[element] = players.get(i++);
        }
        return randomisedPlayers;
    }
}
