package cascadia;

import java.util.ArrayList;
import java.util.Collections;

public class NatureTokens {
    public static void tokenMenu(Player player) {
        System.out.printf("You have %d token%s\n",
                player.getPlayerNatureTokens(),
                player.getPlayerNatureTokens() == 1 ? "" : "s");
        if (Input.boundedInt(1,2, "Enter 1 to spend a token." +
                "  Enter 2 to exit this menu") == 1) {
            spendToken(player);
        }
    }

    public static void spendToken(Player player) {
        int tokens = player.getPlayerNatureTokens();
        if (tokens < 1) {
            System.out.println("You don't have any nature tokens to use.");
            return;
        }

        int optionChoice = Input.boundedInt(1, 2,
                "Enter 1 if you want to select any tile-token combination to place\n" +
                "Enter 2 if you want to wipe any number of wildlife tokens and replace them");
        if (optionChoice == 1) {
            int tileChoice = Input.boundedInt(1,4, "Choose which tile you want to" +
                    " place (1-4)");
            int tokenChoice = Input.boundedInt(1,4, "Choose which token you want" +
                    " to place (1-4)");
            tileChoice--;
            tokenChoice--;
            System.out.println("You have chosen the pair: " +CurrentDeck.getTile(tileChoice).getHabitat1()+ " + "
                    +CurrentDeck.getTile(optionChoice).getHabitat2()+ " tile, " +CurrentDeck.getToken(tokenChoice)+ " token.");
            player.subPlayerNatureToken();
            tokens--;
            System.out.printf("You have spent a nature token.  You now have %d token%s\n", tokens,
                        tokens == 1 ? "" : "s");
            CurrentDeck.choosePairHelper(player, tileChoice, tokenChoice);
            Game.switchTurn();
        } else {
            int choice = -1;
            ArrayList<Integer> chosen = new ArrayList<>();
            int numEntered = 0;
            while (choice != 0 && numEntered < 4) {
                choice = Input.boundedInt(0, 4, "Enter the number of a token to" +
                        " remove, or enter 0 to finish inputting");
                if (choice != 0 && !chosen.contains(choice-1)) {
                    numEntered++;
                    chosen.add(choice-1);
                    System.out.println("Token replaced");
                } else if (chosen.contains(choice-1)) {
                    System.out.println("You have already removed that token");
                }
            }
            // we reverse the list, so we don't have any issues with moving indices we're planning
            // on removing
            chosen.sort(Collections.reverseOrder());
            for (Integer num : chosen) {
                CurrentDeck.deckTokens.remove((int) num);
                CurrentDeck.deckTokens.add(Generation.generateWildlifeToken(true));
            }
            Display.displayDeck();
            player.subPlayerNatureToken();
            tokens--;
            System.out.printf("You have spent a nature token.  You now have %d token%s\n", tokens,
                    tokens == 1 ? "" : "s");
        }
    }
}
