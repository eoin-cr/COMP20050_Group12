import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    private String[] playerNames;
    // Note that in final ArrayLists you can modify the stored values, you
    // just can't change the address the list is pointing to.
    private final ArrayList<Player> players = new ArrayList<>();
    HashMap<HabitatTile.HABITATS, Integer> remainingTiles = new HashMap<>();
    HashMap<WildlifeToken.ANIMAL, Integer> remainingTokens = new HashMap<>();

    public Game() {} //default constructor

    // Get player names
    // Generate starter tiles
    // Generate 4 tiles shown to player
    // Generate 4 animal tokens
    // Print these to console
    // Allow user to place tile
    // Store placed tile
    // Allow user to place token
    // Store placed token
    // Continue until num of tokens has run out.

    // method 1: start method for a game for general setup
    // sets up tiles and tokens, populates player list, and draws 4 starter tile/token pairs
    public void startGameSetup() {
    	 remainingTiles.put(HabitatTile.HABITATS.Forest, 20);
         remainingTiles.put(HabitatTile.HABITATS.River, 20);
         remainingTiles.put(HabitatTile.HABITATS.Wetland, 20);
         remainingTiles.put(HabitatTile.HABITATS.Prairie, 20);
         remainingTiles.put(HabitatTile.HABITATS.Mountain, 20);
         
         remainingTokens.put(WildlifeToken.ANIMAL.Bear, 20);
         remainingTokens.put(WildlifeToken.ANIMAL.Elk, 20);
         remainingTokens.put(WildlifeToken.ANIMAL.Salmon, 20);
         remainingTokens.put(WildlifeToken.ANIMAL.Hawk, 20);
         remainingTokens.put(WildlifeToken.ANIMAL.Fox, 20);
         
         playerNames = Input.getPlayers(); // from Input class
         Output.printPlayers(playerNames); // from Output class
         
         populatePlayers();
         setStartTileTokenSelection();
    }
    
    public void startPlayerTurns() {
    	for (Player player : players) {
    		System.out.println("Current player is: " +player.getPlayerName());
    		Output.displayTileMap(player);
    		//choose from tile token pairs
    		//place tile
    		//now can choose to place token, move to next player, quit etc.
    		Command command = new Command();
    		do {
    			command.setCommand(player);
    		} while (command.getCommand() != Command.CommandType.NEXT || command.getCommand() != Command.CommandType.QUIT);
    	
    		//if user chooses to quit program
    		if (command.getCommand() == Command.CommandType.QUIT) {
    			//ben's method here
    		}
    		//automatically moves to next player if command type is next
    	}
    	
    }
   
    // method 2: generates player objects and adds them to an array
    private void populatePlayers() {
        for (String name : playerNames) {
        	ArrayList<HabitatTile> tmp = new ArrayList<>(); // used to store tiles for player's starter tile map
            Integer[] startingPositions = {9, 10, 10, 9, 10, 10};

            // TODO: Check with lecturer how many tiles each player has on the board at beginning
            for (int i = 0; i < 3; i++) {
                HabitatTile tmpTile = HabitatTile.generateHabitatTile(remainingTiles);

                // just a way of setting the positions to (9,10), (10,9), (10,10) in the
                // loop, so we don't have to manually set them 3 times.
                tmpTile.setPosition(startingPositions[i*2], startingPositions[i*2+1]);
                tmp.add(tmpTile);
            }

            Player player = new Player(name);
            player.setPlayerTiles(tmp);
            players.add(player); //adds to game's arraylist of players
            
            Output.displayTileMap(player);; //displays player's current map of tiles
        }
    }

    // still in progress
    // displays 4 sets of randomly paired habitat tiles and wildlife tokens for players to choose from
    private void setStartTileTokenSelection() {
    	HashMap<HabitatTile, WildlifeToken> tileTokenPairs = generateTileTokenPairs(4);
    	Output.displayTileTokenPairs(tileTokenPairs); //eoin check this method in output class
    }

    private HashMap<HabitatTile, WildlifeToken> generateTileTokenPairs(int num) {
    	//need to put error handling here for putting correct animal type with correct habitat type i think?
    	HashMap<HabitatTile, WildlifeToken> tileTokenPairs = new HashMap<>();
    	ArrayList<HabitatTile> habitats = new ArrayList<>();
    	ArrayList<WildlifeToken> tokens = new ArrayList<>();
    	WildlifeToken.ANIMAL[] checkTokens = new WildlifeToken.ANIMAL[num];
    	
    	
    	for (int i = 0; i < num; i++) {
    		//generate random tiles and put them in habitat tiles arraylist
        	HabitatTile tmpTile = HabitatTile.generateHabitatTile(remainingTiles);
        	habitats.add(tmpTile);
        	//generate random tokens and put them in tokens arraylist, and their animal type in checkTokens
        	WildlifeToken tmpWildlifeToken = WildlifeToken.generateWildlifeToken(remainingTokens);
        	tokens.add(tmpWildlifeToken);
        	checkTokens[i] = tmpWildlifeToken.getAnimalType();
    	}
    	
    	//error handling to wipe tokens if all 4 are the same animal type and replace with 4 other ones
    	while (checkTokens[0] == checkTokens[1] && checkTokens[0] == checkTokens[2] && checkTokens[0] == checkTokens[3]) {
    		
    		for (int i = 0; i < num; i++) {
    			//i think i have to add the previous wiped set of tokens back to the main hashmap of tokens here??
    			//remainingTokens.put(tokens.get(i).getAnimalType(), 1);
    			tokens.remove(i);
    		}
    		
    		for (int i = 0; i < num; i++) {
    			WildlifeToken tmpWildlifeToken = WildlifeToken.generateWildlifeToken(remainingTokens);
            	tokens.add(tmpWildlifeToken);
            	checkTokens[i] = tmpWildlifeToken.getAnimalType();
    		}
    	}
    	//still have to add cull clause if you draw 3 of same token type and current player decides to wipe them
    	
    	//put error-checked tile token pairs into the hashmap
    	for (int i = 0; i < num; i++) {
			tileTokenPairs.put(habitats.get(i), tokens.get(i));
		}
    	
    	
    	return tileTokenPairs;
    }
    
    
}
