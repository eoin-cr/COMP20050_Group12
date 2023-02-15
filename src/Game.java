import java.util.ArrayList;
import java.util.List;

/** Deals with the running of the game */
public class Game {
    private final String[] playerNames;
    // Note that in final ArrayLists you can modify the stored values, you
    // just can't change the address the list is pointing to.
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

          ScoreCard.generateScorecards();
          Bag.makeBag(playerNames.length); //makes a bag of tiles based on how many players there are

          populatePlayers();

    }

    public void startGame() {
    	CurrentDeck.setStartTileTokenSelection();
    	startPlayerTurns();
    }

    /**
     * Starts the player turn cycle.
     * For each player it prints the current player and their map.
     * A sleep call is made after displaying the users map.
     */
    public void startPlayerTurns() {
        // TODO: This loop exits after the player list has been iterated though.
        //       Instead, we want it to finish when all the tokens have run out.
    	//NOTE: something wrong with placedtilecounter = check the print statements in playermap when run
    	while (HabitatTile.getPlacedTileCounter() < Bag.getMaxTiles()) {
        		for (int i = 0; i < playerList.size(); i++) {
        			Player player = playerList.get(i);
        			System.out.println("Current player is: " +player.getPlayerName());
                    Display.displayTileMap(player);
                    
            		Command command = new Command();
            		for (;;) {
            			command.setCommand(player);
            			if (command.getCommand() == Command.CommandType.NEXT ||
            					command.getCommand() == Command.CommandType.PAIR) {
            				break;
            			}
            		}
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
//            Display.sleep(500);
        }
    }
    
    public static Player getNextPlayer(Player player) {
    	int i = playerList.indexOf(player);
    	if (i == playerList.size()-1) {
    		return playerList.get(0);
    	}
    	else {
    		return playerList.get(i+1);
    	}
    }
}
