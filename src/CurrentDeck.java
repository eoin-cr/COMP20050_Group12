import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the deck items
 */
public class CurrentDeck {
    public static final Map<HabitatTile.HABITATS, Integer> remainingHabitats = new HashMap<>();
    public static final Map<HabitatTile.TILETYPES, Integer> remainingTypes = new HashMap<>();
    public static final Map<WildlifeToken.ANIMAL, Integer> remainingTokens = new HashMap<>();
    public static final List<HabitatTile[]> starterTiles = new ArrayList<>();
//    public static final HabitatTile[][] starterTiles = new HabitatTile[5][3];


    /**
     * Sets up the tiles and tokens in the deck, so they can be generated.
     */
    // TODO: Change these values to the ones that correspond to the amount of players
    public void startDeck () {
        remainingHabitats.put(HabitatTile.HABITATS.Forest, 20);
        remainingHabitats.put(HabitatTile.HABITATS.River, 20);
        remainingHabitats.put(HabitatTile.HABITATS.Wetland, 20);
        remainingHabitats.put(HabitatTile.HABITATS.Prairie, 20);
        remainingHabitats.put(HabitatTile.HABITATS.Mountain, 20);

        remainingTypes.put(HabitatTile.TILETYPES.NONKEYSTONE, 60);
        remainingTypes.put(HabitatTile.TILETYPES.KEYSTONE, 25);

        remainingTokens.put(WildlifeToken.ANIMAL.Bear, 20);
        remainingTokens.put(WildlifeToken.ANIMAL.Elk, 20);
        remainingTokens.put(WildlifeToken.ANIMAL.Salmon, 20);
        remainingTokens.put(WildlifeToken.ANIMAL.Hawk, 20);
        remainingTokens.put(WildlifeToken.ANIMAL.Fox, 20);

        // Hard coding this is simpler than running a large amount of
        // generations and checks in the starter habitat method.
        starterTiles.add(new HabitatTile[]{
                new HabitatTile(HabitatTile.HABITATS.Wetland, HabitatTile.HABITATS.Wetland),
                new HabitatTile(HabitatTile.HABITATS.River, HabitatTile.HABITATS.Forest),
                new HabitatTile(HabitatTile.HABITATS.Prairie, HabitatTile.HABITATS.Mountain)
        });
        starterTiles.add(new HabitatTile[]{
                new HabitatTile(HabitatTile.HABITATS.Mountain, HabitatTile.HABITATS.Mountain),
                new HabitatTile(HabitatTile.HABITATS.Forest, HabitatTile.HABITATS.Wetland),
                new HabitatTile(HabitatTile.HABITATS.River, HabitatTile.HABITATS.Prairie)
        });
        starterTiles.add(new HabitatTile[]{
                new HabitatTile(HabitatTile.HABITATS.Forest, HabitatTile.HABITATS.Forest),
                new HabitatTile(HabitatTile.HABITATS.Mountain, HabitatTile.HABITATS.River),
                new HabitatTile(HabitatTile.HABITATS.Wetland, HabitatTile.HABITATS.Prairie)
        });
        starterTiles.add(new HabitatTile[]{
                new HabitatTile(HabitatTile.HABITATS.River, HabitatTile.HABITATS.River),
                new HabitatTile(HabitatTile.HABITATS.Prairie, HabitatTile.HABITATS.Forest),
                new HabitatTile(HabitatTile.HABITATS.Mountain, HabitatTile.HABITATS.Wetland)
        });
        starterTiles.add(new HabitatTile[]{
                new HabitatTile(HabitatTile.HABITATS.Prairie, HabitatTile.HABITATS.Prairie),
                new HabitatTile(HabitatTile.HABITATS.Wetland, HabitatTile.HABITATS.River),
                new HabitatTile(HabitatTile.HABITATS.Forest, HabitatTile.HABITATS.Mountain)
        });
    }
}
