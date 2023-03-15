package cascadia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the total heap of tiles and tokens in a game
 */
public class Bag {
    public static final Map<HabitatTile.Habitat, Integer> remainingHabitats = new HashMap<>();
    public static final Map<HabitatTile.TileType, Integer> remainingTypes = new HashMap<>();
    public static final Map<WildlifeToken, Integer> remainingTokens = new HashMap<>();
    public static final List<HabitatTile[]> starterTiles = new ArrayList<>();
    private static int maxTiles;
    private static int tilesPlaced = 0;

    public static void incrementTilesInUse(int num) {
        tilesPlaced += num;
    }

    public static int tilesInUse() {
        return tilesPlaced;
    }

    Bag(){} //constructor
    
    public static int getMaxTiles() {
		return maxTiles;
	}
	public static void setMaxTiles(int maxTiles) {
		Bag.maxTiles = maxTiles;
	}

	/**
     * Sets up the tiles and tokens in the bag, so they can be generated.
     */
    // TODO: Change these values to the ones that correspond to the amount of players
    public static void makeBag (int numPlayers) {
    	//make tokens
    	remainingTokens.put(WildlifeToken.Bear, 30);
    	remainingTokens.put(WildlifeToken.Elk, 30);
        remainingTokens.put(WildlifeToken.Salmon, 30);
        remainingTokens.put(WildlifeToken.Hawk, 30);
        remainingTokens.put(WildlifeToken.Fox, 30);
        
        //make all possible tiles to draw from
    	remainingHabitats.put(HabitatTile.Habitat.Forest, 20);
        remainingHabitats.put(HabitatTile.Habitat.River, 20);
        remainingHabitats.put(HabitatTile.Habitat.Wetland, 20);
        remainingHabitats.put(HabitatTile.Habitat.Prairie, 20);
        remainingHabitats.put(HabitatTile.Habitat.Mountain, 20);

        remainingTypes.put(HabitatTile.TileType.NON_KEYSTONE, 75);
        remainingTypes.put(HabitatTile.TileType.KEYSTONE, 25);
		
    	
        //make habitat tiles
        if (numPlayers == 2) {
        	setMaxTiles(43);
        }
        else if (numPlayers == 3) {
        	setMaxTiles(63);
        }
        else if (numPlayers == 4) {
        	setMaxTiles(83);
        }
        else {
        	throw new IllegalArgumentException("The number of players is not within the range 2-4.");
        }
  

        // Hard coding this is simpler than running a large amount of
        // generations and checks in the starter habitat method.
        starterTiles.add(new HabitatTile[]{
                new HabitatTile(HabitatTile.Habitat.Wetland, HabitatTile.Habitat.Wetland, 1),
                new HabitatTile(HabitatTile.Habitat.River, HabitatTile.Habitat.Forest, 3),
                new HabitatTile(HabitatTile.Habitat.Prairie, HabitatTile.Habitat.Mountain, 2)
        });
        starterTiles.add(new HabitatTile[]{
                new HabitatTile(HabitatTile.Habitat.Mountain, HabitatTile.Habitat.Mountain, 1),
                new HabitatTile(HabitatTile.Habitat.Forest, HabitatTile.Habitat.Wetland, 3),
                new HabitatTile(HabitatTile.Habitat.River, HabitatTile.Habitat.Prairie, 2)
        });
        starterTiles.add(new HabitatTile[]{
                new HabitatTile(HabitatTile.Habitat.Forest, HabitatTile.Habitat.Forest, 1),
                new HabitatTile(HabitatTile.Habitat.Mountain, HabitatTile.Habitat.River, 3),
                new HabitatTile(HabitatTile.Habitat.Wetland, HabitatTile.Habitat.Prairie, 2)
        });
        starterTiles.add(new HabitatTile[]{
                new HabitatTile(HabitatTile.Habitat.River, HabitatTile.Habitat.River, 1),
                new HabitatTile(HabitatTile.Habitat.Prairie, HabitatTile.Habitat.Forest, 3),
                new HabitatTile(HabitatTile.Habitat.Mountain, HabitatTile.Habitat.Wetland, 2)
        });
        starterTiles.add(new HabitatTile[]{
                new HabitatTile(HabitatTile.Habitat.Prairie, HabitatTile.Habitat.Prairie, 1),
                new HabitatTile(HabitatTile.Habitat.Wetland, HabitatTile.Habitat.River, 3),
                new HabitatTile(HabitatTile.Habitat.Forest, HabitatTile.Habitat.Mountain, 2)
        });
    }

}
