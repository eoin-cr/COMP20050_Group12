package main.java.cascadia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NatureTokens {
    public static void tokenMenu(Player player) {
        int tokens = player.getPlayerNatureTokens();
        System.out.printf("You have %d token%s\n",
                tokens, tokens == 1 ? "" : "s");

        // only allows user to spend a token if they have more than 0, if they don't
        // they just get send back to the menu
        if (tokens > 0 && Input.boundedInt(1,2, "Enter 1 to spend a token." +
                "  Enter 2 to exit this menu") == 1) {
            spendToken(player);
        }
    }

    private static void spendToken(Player player) {
        int optionChoice = Input.boundedInt(1, 2,
                "Enter 1 if you want to select any tile-token combination to place\n" +
                "Enter 2 if you want to wipe any number of wildlife tokens and replace them");
        if (optionChoice == 1) {
            pickAnyTwo(player);
        } else {
            wipeTokens(player);
        }
    }

    private static void pickAnyTwo(Player player) {
        int tileChoice = Input.boundedInt(1,4, "Choose which tile you want to" +
                " place (1-4)");
        int tokenChoice = Input.boundedInt(1,4, "Choose which token you want" +
                " to place (1-4)");
        tileChoice--;
        tokenChoice--;
        System.out.println("You have chosen the pair: " +CurrentDeck.getTile(tileChoice).getHabitat1()+ " + "
                +CurrentDeck.getTile(tileChoice).getHabitat2()+ " tile, " +CurrentDeck.getToken(tokenChoice)+ " token.");

        player.subPlayerNatureToken();
        int tokens = player.getPlayerNatureTokens();
        System.out.printf("You have spent a nature token.  You now have %d token%s\n", tokens,
                tokens == 1 ? "" : "s");
        CurrentDeck.choosePairHelper(player, tileChoice, tokenChoice);
        // Game.switchTurn();
    }

    private static void wipeTokens(Player player) {
        Display.displayDeck();

        // removes the selected tokens from the current deck
        removeFromCurrentDeck(selectTokensToWipe());

        player.subPlayerNatureToken();
        int tokens = player.getPlayerNatureTokens();
        Display.displayDeck();
        System.out.printf("You have spent a nature token.  You now have %d token%s\n", tokens,
                tokens == 1 ? "" : "s");
    }

    private static List<Integer> selectTokensToWipe() {
        int choice = -1;
        int numEntered = 0;
        List<Integer> chosen = new ArrayList<>();
        while (choice != 0 && numEntered < 4) {
            choice = Input.boundedInt(0, 4, "Enter the number of a token to" +
                    " remove (1-4), or enter 0 to finish inputting");
            if (choice != 0 && !chosen.contains(choice-1)) {
                numEntered++;
                chosen.add(choice-1);
                System.out.println("Token replaced");
            } else if (chosen.contains(choice-1)) {
                System.out.println("You have already removed that token");
            }
        }
        return chosen;
    }

    private static void removeFromCurrentDeck(List<Integer> chosen) {
        // we reverse the list, so we don't have any issues with moving indices we're planning
        // on removing
        chosen.sort(Collections.reverseOrder());
        for (Integer num : chosen) {
            CurrentDeck.deckTokens.remove((int) num);
            CurrentDeck.deckTokens.add(Generation.generateWildlifeToken(true));
        }
    }
}
