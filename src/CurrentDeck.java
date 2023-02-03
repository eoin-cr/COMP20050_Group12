import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the deck items
 */
public class CurrentDeck {
    public static final int NUMBER_OF_NON_KEYSTONE_TILES = 60;
    public static final int NUMBER_OF_KEYSTONE_TILES = 25;
    public static final Map<HabitatTile.Habitat, Integer> remainingHabitats = new HashMap<>();
    public static final Map<HabitatTile.TileType, Integer> remainingTypes = new HashMap<>();
    public static final Map<WildlifeToken, Integer> remainingTokens = new HashMap<>();
    public static final List<HabitatTile[]> starterTiles = new ArrayList<>();
//    public static final HabitatTile[][] starterTiles = new HabitatTile[5][3];


    /**
     * Sets up the tiles and tokens in the deck, so they can be generated.
     */
    // TODO: Change these values to the ones that correspond to the amount of players
    public void startDeck () {
        remainingHabitats.put(HabitatTile.Habitat.Forest, 20);
        remainingHabitats.put(HabitatTile.Habitat.River, 20);
        remainingHabitats.put(HabitatTile.Habitat.Wetland, 20);
        remainingHabitats.put(HabitatTile.Habitat.Prairie, 20);
        remainingHabitats.put(HabitatTile.Habitat.Mountain, 20);

        remainingTypes.put(HabitatTile.TileType.NON_KEYSTONE, NUMBER_OF_NON_KEYSTONE_TILES);
        remainingTypes.put(HabitatTile.TileType.KEYSTONE, NUMBER_OF_KEYSTONE_TILES);

        remainingTokens.put(WildlifeToken.Bear, 20);
        remainingTokens.put(WildlifeToken.Elk, 20);
        remainingTokens.put(WildlifeToken.Salmon, 20);
        remainingTokens.put(WildlifeToken.Hawk, 20);
        remainingTokens.put(WildlifeToken.Fox, 20);

        // Hard coding this is simpler than running a large amount of
        // generations and checks in the starter habitat method.
        starterTiles.add(new HabitatTile[]{
                new HabitatTile(HabitatTile.Habitat.Wetland, HabitatTile.Habitat.Wetland),
                new HabitatTile(HabitatTile.Habitat.River, HabitatTile.Habitat.Forest),
                new HabitatTile(HabitatTile.Habitat.Prairie, HabitatTile.Habitat.Mountain)
        });
        starterTiles.add(new HabitatTile[]{
                new HabitatTile(HabitatTile.Habitat.Mountain, HabitatTile.Habitat.Mountain),
                new HabitatTile(HabitatTile.Habitat.Forest, HabitatTile.Habitat.Wetland),
                new HabitatTile(HabitatTile.Habitat.River, HabitatTile.Habitat.Prairie)
        });
        starterTiles.add(new HabitatTile[]{
                new HabitatTile(HabitatTile.Habitat.Forest, HabitatTile.Habitat.Forest),
                new HabitatTile(HabitatTile.Habitat.Mountain, HabitatTile.Habitat.River),
                new HabitatTile(HabitatTile.Habitat.Wetland, HabitatTile.Habitat.Prairie)
        });
        starterTiles.add(new HabitatTile[]{
                new HabitatTile(HabitatTile.Habitat.River, HabitatTile.Habitat.River),
                new HabitatTile(HabitatTile.Habitat.Prairie, HabitatTile.Habitat.Forest),
                new HabitatTile(HabitatTile.Habitat.Mountain, HabitatTile.Habitat.Wetland)
        });
        starterTiles.add(new HabitatTile[]{
                new HabitatTile(HabitatTile.Habitat.Prairie, HabitatTile.Habitat.Prairie),
                new HabitatTile(HabitatTile.Habitat.Wetland, HabitatTile.Habitat.River),
                new HabitatTile(HabitatTile.Habitat.Forest, HabitatTile.Habitat.Mountain)
        });
    }
}
