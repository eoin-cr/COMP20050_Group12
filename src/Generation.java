import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Generation {
	
    public static HabitatTile[] generateStarterHabitat () {
        HabitatTile[] tokens = new HabitatTile[3];
        tokens[0] = generateKeystoneHabitatTile();
        tokens[1] = generateNonKeystoneHabitatTile();
        tokens[2] = generateNonKeystoneHabitatTile();

        return tokens;
    }

    /**
     * Randomly generates a wildlife token.
     * Uses a hashmap to decrease the probability of getting a certain animal
     * as more tokens with that animal are placed.
     *
     * @param wildlifeTokensRemaining a hashmap with the amount of each type of
     *                                token remaining
     */
    public static WildlifeToken generateWildlifeToken (HashMap<WildlifeToken.ANIMAL, Integer> wildlifeTokensRemaining) {
        int tokensLeft = 0;

        // get the total amount of tokens left of all animal types
        for (Integer value : wildlifeTokensRemaining.values()) {
            tokensLeft += value;
        }

        int index = new Random().nextInt(tokensLeft);
        WildlifeToken.ANIMAL animalType = null;


        for (Map.Entry<WildlifeToken.ANIMAL, Integer> entry : wildlifeTokensRemaining.entrySet()) {
            index -= entry.getValue();
            if (index <= 0 && animalType == null) {
                animalType = entry.getKey();
                wildlifeTokensRemaining.put(entry.getKey(), entry.getValue() - 1);
            }

        }

        return new WildlifeToken(animalType);
    }

    /**
     * Generates a new habitat tile.
     * The chance of it returning a keystone type is dependent on the amount of
     * keystone tiles already generated.
     */
    public static HabitatTile generateHabitatTile() {
        int tilesLeft = 0;

        // get the total amount of tiles left
        for (Integer value : Bag.remainingTypes.values()) {
            tilesLeft += value;
        }


        int randomNum = new Random().nextInt(1, tilesLeft);

        /*
         * We decrement the number of types left here, rather than in the
         * generate functions, as the starter habitat tiles are not counted
         * as habitat tiles.
         */
        if (randomNum <= Bag.remainingTypes.get(HabitatTile.TILETYPES.KEYSTONE)) {
            Bag.remainingTypes.put(
                    HabitatTile.TILETYPES.KEYSTONE,
                    Bag.remainingTypes.get(HabitatTile.TILETYPES.KEYSTONE) - 1
            );
            return generateKeystoneHabitatTile();
        }
        Bag.remainingTypes.put(
                HabitatTile.TILETYPES.NONKEYSTONE,
                Bag.remainingTypes.get(HabitatTile.TILETYPES.NONKEYSTONE) - 1
        );
        return generateNonKeystoneHabitatTile();
    }


    /**
     * Randomly generates a habitat tile with two different habitat types.
     *
     * @return a habitat tile with 2 randomised habitat types
     * @see Generation#generateHabitatTile()
     * @see Generation#generateNonKeystoneHabitatTile()
     */
    // TODO: Alter implementation so the starter habitat tiles always have one keystone tile.
    private static HabitatTile generateNonKeystoneHabitatTile() {
        HashMap<HabitatTile.HABITATS, Integer> habitatsRemaining = Bag.remainingHabitats;
        int tilesLeft = 0;

        // get the total amount of tiles left
        for (Integer value : habitatsRemaining.values()) {
            tilesLeft += value;
        }

        HabitatTile.HABITATS first;
        HabitatTile.HABITATS second;
        Map.Entry<HabitatTile.HABITATS, Integer> entry1 = null;
        Map.Entry<HabitatTile.HABITATS, Integer> entry2 = null;

        do {
            first = null;
            second = null;
            int num1 = new Random().nextInt(1, tilesLeft);
            int num2 = new Random().nextInt(1, tilesLeft);

            /*
             * if we imagine the hashmap to contains values formatted like [Forest, Forest, Forest, River...]
             * then this function gets the num1 th and num2 th value, and then 'removes' it from
             * the list.
             */
            for (Map.Entry<HabitatTile.HABITATS, Integer> entry : Bag.remainingHabitats.entrySet()) {
                num1 -= entry.getValue();
                num2 -= entry.getValue();
                if (num1 <= 0 && first == null) {
//                    first = entry;
                    entry1 = entry;
                    first = entry.getKey();
//                    CurrentDeck.remainingHabitats.put(entry.getKey(), entry.getValue() - 1);
                }
                if (num2 <= 0 && second == null) {
                    entry2 = entry;
                    second = entry.getKey();
                    Bag.remainingHabitats.put(entry.getKey(), entry.getValue() - 1);
                }

            }
        } while (first == second);

        assert entry1 != null;
        assert entry2 != null;
        Bag.remainingHabitats.put(entry1.getKey(), entry1.getValue() - 1);
        Bag.remainingHabitats.put(entry2.getKey(), entry2.getValue() - 1);

        return new HabitatTile(first, second);
    }


    /**
     * Generates a keystone habitat tile (i.e. a tile where both habitats are
     * the same).
     */
    private static HabitatTile generateKeystoneHabitatTile() {
        HashMap<HabitatTile.HABITATS, Integer> habitatsRemaining = Bag.remainingHabitats;
        int tilesLeft = 0;

        // get the total amount of tiles left
        for (Integer value : habitatsRemaining.values()) {
            tilesLeft += value;
        }

        int randomNum = new Random().nextInt(1, tilesLeft);
        HabitatTile.HABITATS habitat = null;

        /*
         * if we imagine the hashmap to contains values formatted like [Forest, Forest, Forest, River...]
         * then this function gets the randomNum th value, and then 'removes' it from
         * the list.
         */
        for (Map.Entry<HabitatTile.HABITATS, Integer> entry : Bag.remainingHabitats.entrySet()) {
            randomNum -= entry.getValue();
            if (randomNum <= 0) {
                habitat = entry.getKey();
                Bag.remainingHabitats.put(entry.getKey(), entry.getValue() - 2);
                break;
            }
        }

        return new HabitatTile(habitat, habitat);
    }
}
