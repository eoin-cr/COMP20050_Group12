import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Deals with the running of the game */
public class Game {
    private String[] playerNames;
    // Note that in final ArrayLists you can modify the stored values, you
    // just can't change the address the list is pointing to.
    private final List<Player> playerList = new ArrayList<>();
    private Bag bag;

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

          bag = new Bag();
          bag.makeBag(playerNames.length); //makes a bag of tiles based on how many players there are
          populatePlayers();

    }

    public void startGame() {
    	setStartTileTokenSelection();
    	startPlayerTurns();
    }

    /**
     * Starts the player turn cycle.
     * For each player it prints the current player and their map.
     * A sleep call is made after displaying the users map.
     */
    public void startPlayerTurns() {
        // TODO: This loop exits after the player list has been iterated though.
        // Instead, we want it to finish when all the tokens have ran out.
    	for (Player player : playerList) {
    		System.out.println("Current player is: " +player.getPlayerName());
            Display.displayTileMap(player);
            Display.sleep(500);
    		// choose from tile token pairs
    		// place tile
    		// now can choose to place token, move to next player, quit etc.
    		Command command = new Command();
    		do {
    			command.setCommand(player);
    		} while (command.getCommand() != Command.CommandType.NEXT);
    		// automatically moves to next player if command type is next
    	}
    	
    }

    /**
     * Generates player objects, and their respective habitat tiles, and adds
     * them to an array.
     * A sleep call is made at the end of the tile map display.
     */
    private void populatePlayers() {
        for (String name : playerNames) {
//            HabitatTile[] generatedTiles = Generation.generateStarterHabitat();
//        	List<HabitatTile> generatedTilesList = new ArrayList<>(List.of(generatedTiles)); // used to store tiles for player's starter tile map
//            // remember that a 2d array essentially uses coords (y,x) [not (x,y)]
//            Integer[] startingPositions = {8, 10, 9, 9, 9, 10};
////            Integer[] startingPositions = {9, 11, 12, 10, 11, 11};
            Player player = new Player(name);
//
//            for (int i = 0; i < 3; i++) {
//                try {
//                	 player.getMap().addTileToMap(generatedTiles[i], startingPositions[i * 2], startingPositions[i * 2 + 1]);
//                } catch (IllegalArgumentException ignored) {}
//            }

//            player.getMap().setPlayerTiles(generatedTilesList);
            playerList.add(player); // adds to game's arraylist of players

            Display.displayTileMap(player); // displays player's current map of tiles

            // sleep so you can see the outputs, they don't just come all at once
            Display.sleep(500);
        }
    }

    /**
     * Still in progress.
     * Displays 4 sets of randomly paired habitat tiles and wildlife tokens for players to choose from.
     * A sleep call is made after displaying tile token pairs.
      */
    private void setStartTileTokenSelection() {
    	Map<HabitatTile, WildlifeToken> tileTokenPairs = generateTileTokenPairs(4);
    	Display.displayTileTokenPairs(tileTokenPairs); //eoin check this method in output class
        Display.sleep(500);
    }

    /**
     * Generates the 'community' tile token pairs that users pick from.
     *
     * @param num the number of tile token pairs to generate
     * @return a hashmap containing tile token pairs
     */
    private Map<HabitatTile, WildlifeToken> generateTileTokenPairs(int num) {
    	// need to put error handling here for putting correct animal type with correct habitat type i think?
        // I think the tile–animal matching is completely random - Eoin.
    	Map<HabitatTile, WildlifeToken> tileTokenPairs = new HashMap<>();
    	List<HabitatTile> habitats = new ArrayList<>();
    	List<WildlifeToken> tokens = new ArrayList<>();
    	WildlifeToken[] checkTokens = new WildlifeToken[num];
    	
    	
    	for (int i = 0; i < num; i++) {
    		// generate random tiles and put them in habitat tiles arraylist
        	HabitatTile tmpTile = Generation.generateHabitatTile();
        	habitats.add(tmpTile);
        	// generate random tokens and put them in tokens arraylist, and their animal type in checkTokens
        	WildlifeToken tmpWildlifeToken = Generation.generateWildlifeToken(Bag.remainingTokens);
        	tokens.add(tmpWildlifeToken);
        	checkTokens[i] = tmpWildlifeToken;
    	}
    	
    	// error handling to wipe tokens if all 4 are the same animal type and replace with 4 other ones
    	while (checkTokens[0] == checkTokens[1] && checkTokens[0] == checkTokens[2] && checkTokens[0] == checkTokens[3]) {
            tokens.clear();
    		
    		for (int i = 0; i < num; i++) {
    			WildlifeToken tmpWildlifeToken = Generation.generateWildlifeToken(Bag.remainingTokens);
            	tokens.add(tmpWildlifeToken);
            	checkTokens[i] = tmpWildlifeToken;
    		}
    	}

    	// TODO: still have to add cull clause if you draw 3 of same token type and current player decides to wipe them
    	
    	// put error-checked tile token pairs into the hashmap
    	for (int i = 0; i < num; i++) {
			tileTokenPairs.put(habitats.get(i), tokens.get(i));
		}
    	
    	
    	return tileTokenPairs;
    }
    
    
}
