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
    
    public static void decrementTilesInUse(int num) {
        tilesPlaced -= num;
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
        putTokens();
        putHabitats();
        putKeystone();
        generateMaxTiles(numPlayers);
        createPossibleStarterTiles();
    }

    private static void createPossibleStarterTiles() {
        // Creates the possible starter tiles, and adds them to a list.  The
        // starter tiles are very specific (one keystone tile, at least one of each
        // habitat, one tile with 2 possible tokens, one with 3, etc.) so we manually
        // set most of the conditions (except the possible tokens on the tiles)
        starterTileHelper(HabitatTile.Habitat.Wetland, HabitatTile.Habitat.River,
                HabitatTile.Habitat.Forest, HabitatTile.Habitat.Prairie, HabitatTile.Habitat.Mountain);
        starterTileHelper(HabitatTile.Habitat.Mountain, HabitatTile.Habitat.Forest,
                HabitatTile.Habitat.Wetland, HabitatTile.Habitat.River, HabitatTile.Habitat.Prairie);
        starterTileHelper(HabitatTile.Habitat.Forest, HabitatTile.Habitat.Mountain,
                HabitatTile.Habitat.River, HabitatTile.Habitat.Wetland, HabitatTile.Habitat.Prairie);
        starterTileHelper(HabitatTile.Habitat.River, HabitatTile.Habitat.Prairie,
                HabitatTile.Habitat.Forest, HabitatTile.Habitat.Mountain, HabitatTile.Habitat.Wetland);
        starterTileHelper(HabitatTile.Habitat.Prairie, HabitatTile.Habitat.Wetland,
                HabitatTile.Habitat.River, HabitatTile.Habitat.Forest, HabitatTile.Habitat.Mountain);
    }

    private static void starterTileHelper(HabitatTile.Habitat one, HabitatTile.Habitat two,
                                          HabitatTile.Habitat three, HabitatTile.Habitat four,
                                          HabitatTile.Habitat five) {

        starterTiles.add(new HabitatTile[]{
                new HabitatTile(one, one, 1),
                new HabitatTile(two, three, 3),
                new HabitatTile(four, five, 2)
        });
    }

    private static void generateMaxTiles(int numPlayers) {
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
    }

    private static void putKeystone() {
        remainingTypes.put(HabitatTile.TileType.NON_KEYSTONE, 75);
        remainingTypes.put(HabitatTile.TileType.KEYSTONE, 25);
    }

    private static void putHabitats() {
        //make all possible tiles to draw from
        remainingHabitats.put(HabitatTile.Habitat.Forest, 20);
        remainingHabitats.put(HabitatTile.Habitat.River, 20);
        remainingHabitats.put(HabitatTile.Habitat.Wetland, 20);
        remainingHabitats.put(HabitatTile.Habitat.Prairie, 20);
        remainingHabitats.put(HabitatTile.Habitat.Mountain, 20);
    }

    private static void putTokens() {
        //make tokens
        remainingTokens.put(WildlifeToken.Bear, 30);
        remainingTokens.put(WildlifeToken.Elk, 30);
        remainingTokens.put(WildlifeToken.Salmon, 30);
        remainingTokens.put(WildlifeToken.Hawk, 30);
        remainingTokens.put(WildlifeToken.Fox, 30);
    }

}
