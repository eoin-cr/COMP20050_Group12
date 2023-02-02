import java.util.HashMap;

/**
 * Manages the total heap of tiles and tokens in a game
 */
public class Bag {
    public static final HashMap<HabitatTile.HABITATS, Integer> remainingHabitats = new HashMap<>();
    public static final HashMap<HabitatTile.TILETYPES, Integer> remainingTypes = new HashMap<>();
    public static final HashMap<WildlifeToken.ANIMAL, Integer> remainingTokens = new HashMap<>();

    /**
     * Sets up the tiles and tokens in the bag, so they can be generated.
     */
    // TODO: Change these values to the ones that correspond to the amount of players
    public void makeBag (int numplayers) {
        remainingHabitats.put(HabitatTile.HABITATS.Forest, 20);
        remainingHabitats.put(HabitatTile.HABITATS.River, 20);
        remainingHabitats.put(HabitatTile.HABITATS.Wetland, 20);
        remainingHabitats.put(HabitatTile.HABITATS.Prairie, 20);
        remainingHabitats.put(HabitatTile.HABITATS.Mountain, 20);

        remainingTypes.put(HabitatTile.TILETYPES.NONKEYSTONE, 100);
        remainingTypes.put(HabitatTile.TILETYPES.KEYSTONE, 25);

        remainingTokens.put(WildlifeToken.ANIMAL.Bear, 20);
        remainingTokens.put(WildlifeToken.ANIMAL.Elk, 20);
        remainingTokens.put(WildlifeToken.ANIMAL.Salmon, 20);
        remainingTokens.put(WildlifeToken.ANIMAL.Hawk, 20);
        remainingTokens.put(WildlifeToken.ANIMAL.Fox, 20);

    }

}
