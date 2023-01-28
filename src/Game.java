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

    // method 1: general start method for a game, drives action
    public void start() {
        playerNames = Input.getPlayers(); // from Input class
        Output.printPlayers(playerNames); // from Output class
        makePlayers();
        setStartTileSelection();
    }
   
    // method 2: generates player objects and adds them to an array
    private void makePlayers() {
        remainingTiles.put(HabitatTile.HABITATS.Forest, 20);
        remainingTiles.put(HabitatTile.HABITATS.River, 20);
        remainingTiles.put(HabitatTile.HABITATS.Wetland, 20);
        remainingTiles.put(HabitatTile.HABITATS.Prairie, 20);
        remainingTiles.put(HabitatTile.HABITATS.Mountain, 20);

        ArrayList<HabitatTile> tmp = new ArrayList<>(); // used to store tiles for player's starter tile map
       
        for (String name : playerNames) {
            Integer[] startingPositions = {9, 10, 10, 9, 10, 10};

            // TODO: Check with lecturer how many tiles each player has on the board at beginning
            for (int i = 0; i < 3; i++) {
                HabitatTile tmpTile = HabitatTile.generateHabitatTile(remainingTiles);

                // just a way of setting the positions to (9,10), (10,9), (10,10) in the
                // loop, so we don't have to manually set them 3 times.
                tmpTile.setPosition(startingPositions[i*2], startingPositions[i*2+1]);
                tmp.add(tmpTile);

                // display habitat
                DrawTiles.printHalfTile(tmpTile);
            }

            Player player = new Player(name);
            player.setPlayerTiles(tmp);
            players.add(player); //adds to game's arraylist of players
        }
    }

    // still in progress
    // displays 4 sets of randomly paired habitat tiles and wildlife tokens for players to choose from
    private void setStartTileSelection() {
    	HashMap<HabitatTile, WildlifeToken> tileTokenPairs = generateTileTokenPairs(4);
     
    }

    private HashMap<HabitatTile, WildlifeToken> generateTileTokenPairs(int num) {
    	//need to put error handling here for putting correct animal type with correct habitat type i think?
    	HashMap<HabitatTile, WildlifeToken> tileTokenPairs = new HashMap<>();
    	ArrayList<HabitatTile> habitats = new ArrayList<>();
    	ArrayList<WildlifeToken> tokens = new ArrayList<>();
    	WildlifeToken.ANIMAL[] checkTokens = new WildlifeToken.ANIMAL[num]; //redo this nonsense with arraylists first actually
    	
    	
    	for (int i = 0; i < num; i++) {
        	HabitatTile tmpTile = HabitatTile.generateHabitatTile(remainingTiles);
        	habitats.add(tmpTile);
        	WildlifeToken tmpWildlifeToken = WildlifeToken.generateWildlifeToken(remainingTokens);
        	tokens.add(tmpWildlifeToken);
        	checkTokens[i] = tmpWildlifeToken.getAnimalType();
    	}
    	
    	//error handling to wipe tokens if all 4 are the same animal type
    	if (checkTokens[0] == checkTokens[1] && checkTokens[0] == checkTokens[2] && checkTokens[0] == checkTokens[3]) {
    		for (int i = 0; i < num; i++) {
    			tokens.remove(i);
    		}
    	}
    	
    	
    	return tileTokenPairs;
    }
}
