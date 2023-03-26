package cascadia;

import cascadia.scoring.ScoreCards;
import java.util.ArrayList;
import java.util.List;

/** Deals with the running of the game. */
public class Game {
    private final String[] playerNames;
    private final static List<Player> playerList = new ArrayList<>();
    /*
     we set this to static, so we can access it from static methods.  This does mean
     that multiple game classes cannot be run simultaneously, but this should not be
     an issue
    */
    private static boolean switchTurn = false;

    /*
     * Get player names
     * Generate starter tiles
     * Generate 4 tiles shown to player
     * Generate 4 animal tokens
     * Print these to console
     * Allow user to place tile
     * Store placed tile
     * Allow user to place token
     * Store placed token
     * Continue until num of tokens has run outln.
     */

    /**
     * General setup required for a game. Sets up tiles and tokens, populates
     * player list, and draws 4 starter tile/token pairs.
     * A sleep call is made after printing the player names to improve
     * readability.
     */

    public Game() { //constructor
        playerNames = Input.getPlayers();  // from cascadia.Input class
        Display.printPlayers(playerNames);  // from cascadia.Display class
        Display.sleep(500);

        ScoreCards.generateScorecards();
        //makes a bag of tiles based on how many players there are
        Bag.createBag(playerNames.length);

        populatePlayers();

    }
    
    public static List<Player> getPlayers() { //used in final scoring
    	return playerList;
    }

    public void startGame() {
    	CurrentDeck.setStartTileTokenSelection();
    	playerTurnCycle();
    }

    /**
     * Starts the player turn cycle.
     * For each player it prints the current player and their map.
     * A sleep call is made after displaying the users map.
     */
    private void playerTurnCycle() {
    	while (Bag.tilesInUse() < Bag.getMaxTiles()) {
    		for (Player player : playerList) {
                switchTurn = false;
        		Display.outln("Current player is: " + player.getPlayerName());
                Display.displayPlayerTileMap(player);
                /*
        		 choose from tile token pairs
        		 place tile
        		 now can choose to place token, move to next player, quit etc.
                */

                do {
                    player.setCommand();
                } while (!switchTurn);
                // automatically moves to next player if command type is next
        	}
    	}
    }

    /**
     * Generates player objects, and their respective habitat tiles, and adds
     * them to an array.
     * A sleep call is made at the end of the tile map display.
     */
    private void populatePlayers() {
        for (String name : playerNames) {
            Player player = new Player(name);
            playerList.add(player); // adds to game's arraylist of players
            Display.displayPlayerTileMap(player); // displays player's current map of tiles

            // sleep so you can see the outputs, they don't just come all at once
            Display.sleep(500);
        }
    }

    public static void switchTurn() {
        switchTurn = true;
    }
    
}
