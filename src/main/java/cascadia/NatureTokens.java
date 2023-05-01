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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NatureTokens {
    public static void tokenMenu(Player player) {
        int tokens = player.getPlayerNatureTokens();
        Display.outf("You have %d token%s\n",
                tokens, tokens == 1 ? "" : "s");

        if (Game.botMode && tokens > 0) {
            spendToken(player);
            return;
        }
        // only allows user to spend a token if they have more than 0, if they don't
        // they just get send back to the menu
        if (tokens > 0 && Input.boundedInt(1, 2,
                "Enter 1 to spend a token." + "  Enter 2 to exit this menu") == 1) {
            spendToken(player);
        }
    }

    private static void spendToken(Player player) {
        // in the bot mode we know the player will only want to select
        // any tile token combination
        if (Game.botMode) {
            pickAnyTwo(player);
            return;
        }
        int optionChoice = Input.boundedInt(1, 2,
                "Enter 1 if you want to select any tile-token combination to place\n"
                        + "Enter 2 if you want to wipe any number of wildlife tokens "
                        + "and replace them");
        if (optionChoice == 1) {
            pickAnyTwo(player);
        } else {
            wipeTokens(player);
        }
    }

    private static void pickAnyTwo(Player player) {
        int tileChoice;
        int tokenChoice;
        if (Game.botMode) {
            int[] choice = Game.getBot().getBestChoice();
            tileChoice = choice[0];
            tokenChoice = choice[1];
        } else {
            tileChoice = Input.boundedInt(1, 4,
                    "Choose which tile you want to place (1-4)");
            tokenChoice = Input.boundedInt(1, 4,
                    "Choose which token you want to place (1-4)");
            tileChoice--;
            tokenChoice--;
            Display.outln("You have chosen the pair: "
                    + CurrentDeck.getTile(tileChoice).getHabitat1() + " + "
                    + CurrentDeck.getTile(tileChoice).getHabitat2() + " tile, "
                    + CurrentDeck.getToken(tokenChoice) + " token.");
        }
        player.subPlayerNatureToken();
        int tokens = player.getPlayerNatureTokens();
        Display.outf("You have spent a nature token.  You now have %d token%s\n", tokens,
                tokens == 1 ? "" : "s");
        CurrentDeck.choosePairHelper(player, tileChoice, tokenChoice);
    }

    private static void wipeTokens(Player player) {
        Display.displayDeck();

        // removes the selected tokens from the current deck
        removeFromCurrentDeck(selectTokensToWipe());

        player.subPlayerNatureToken();
        int tokens = player.getPlayerNatureTokens();
        Display.displayDeck();
        Display.outf("You have spent a nature token.  You now have %d token%s\n", tokens,
                tokens == 1 ? "" : "s");
    }

    private static List<Integer> selectTokensToWipe() {
        int choice = -1;
        int numEntered = 0;
        List<Integer> chosen = new ArrayList<>();
        while (choice != 0 && numEntered < 4) {
            choice = Input.boundedInt(0, 4, "Enter the number of a "
                    + "token to remove (1-4), or enter 0 to finish inputting");
            if (choice != 0 && !chosen.contains(choice - 1)) {
                numEntered++;
                chosen.add(choice - 1);
                Display.outln("Token replaced");
            } else if (chosen.contains(choice - 1)) {
                Display.outln("You have already removed that token");
            }
        }
        return chosen;
    }

    private static void removeFromCurrentDeck(List<Integer> chosen) {
        // we reverse the list, so we don't have any issues with moving indices we're planning
        // on removing
        chosen.sort(Collections.reverseOrder());
        for (Integer num : chosen) {
            CurrentDeck.removeDeckToken(num);
            CurrentDeck.addDeckToken(Generation.generateWildlifeToken(true));
        }
    }
}
