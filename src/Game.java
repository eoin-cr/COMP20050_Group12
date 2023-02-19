import java.util.ArrayList;
import java.util.List;

/** Deals with the running of the game */
public class Game {
    private final String[] playerNames;
    private final static List<Player> playerList = new ArrayList<>();

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
     * Continue until num of tokens has run out.
     */

    /**
     * General setup required for a game. Sets up tiles and tokens, populates
     * player list, and draws 4 starter tile/token pairs.
     * A sleep call is made after printing the player names to improve
     * readability.
     */

    public Game() { //constructor
    	  playerNames = Input.getPlayers();  // from Input class
          Display.printPlayers(playerNames);  // from Display class
          Display.sleep(500);

          ScoreCards.generateScorecards();
          Bag.makeBag(playerNames.length); //makes a bag of tiles based on how many players there are

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
        		System.out.println("Current player is: " +player.getPlayerName());
                Display.displayTileMap(player);
//                Display.sleep(500);
        		// choose from tile token pairs
        		// place tile
        		// now can choose to place token, move to next player, quit etc.
        		Command command = new Command();

                do {
                    command.setCommand(player);
//                } while (command.getCommand() != Command.CommandType.NEXT &&
//                        command.getCommand() != Command.CommandType.PAIR);
                } while (command.getCommand() != Command.CommandType.PAIR);
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
            Display.displayTileMap(player); // displays player's current map of tiles

            // sleep so you can see the outputs, they don't just come all at once
            Display.sleep(500);
        }
    }
    
}
