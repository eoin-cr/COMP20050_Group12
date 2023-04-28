/*
	COMP20050 Group 12
	Eoin Creavin – Student ID: 21390601
	eoin.creavin@ucdconnect.ie
	GitHub ID: eoin-cr

	Mynah Bhattacharyya – Student ID: 21201085
	malhar.bhattacharyya@ucdconnect.ie
	GitHub ID: mynah-bird

	Ben McDowell – Student ID: 21495144
	ben.mcdowell@ucdconnect.ie
	GitHub ID: Benmc1
 */

package cascadia;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class Generation {
    /**
     * Generates a starter habitat.
     * A starter habitat is 3 tiles, one of which being a keystone tile.
     * A starter habitat will contain one of every habitat.
     *
     * @return a starter habitat (habitat tile array).
     */
    public static HabitatTile[] generateStarterHabitat() {
        // this should not be reached for regular players.  I only added this
        // because the tmp player being created for the tile placement map
        // was also calling this method, and eventually we were running outln
        // of starter habitats and the program would crash
        if (Bag.starterTiles.size() == 0) {
            return new HabitatTile[]{new HabitatTile(HabitatTile.Habitat.Prairie,
                    HabitatTile.Habitat.Prairie, 1),
                    new HabitatTile(HabitatTile.Habitat.Prairie, HabitatTile.Habitat.Prairie, 1),
                    new HabitatTile(HabitatTile.Habitat.Prairie, HabitatTile.Habitat.Prairie, 1)};
        }
        int index = new Random().nextInt(Bag.starterTiles.size());
        HabitatTile[] tiles = Bag.starterTiles.get(index);
        Bag.starterTiles.remove(index);
        return tiles;
    }

    private static int getNumTokensLeft() {
        int tokenTypesLeft = 0;
        int tokensLeft = 0;

        // get the total amount of tokens left of all animal types
        for (Integer value : Bag.remainingTokens.values()) {
            if (value > 0) {
                tokenTypesLeft++;
            }
            tokensLeft += value;
        }

        // we want to always have enough different types of tokens so each token on a tile
        // and enough tokens to pair with four tiles
        if (tokenTypesLeft < 3 || tokensLeft < 4) {
            addTokens();
            tokensLeft = 50;
        }
        return tokensLeft;
    }

    private static void addTokens() {
        // if the bag is empty we put some more tokens in it
        Bag.remainingTokens.put(WildlifeToken.Bear, 10);
        Bag.remainingTokens.put(WildlifeToken.Elk, 10);
        Bag.remainingTokens.put(WildlifeToken.Salmon, 10);
        Bag.remainingTokens.put(WildlifeToken.Hawk, 10);
        Bag.remainingTokens.put(WildlifeToken.Fox, 10);
    }

    /**
     * Randomly generates a wildlife token.
     * Uses a hashmap to decrease the probability of getting a certain animal
     * as more tokens with that animal are placed.
     */
    public static WildlifeToken generateWildlifeToken(boolean removeFromRemaining) {
        int tokensLeft = getNumTokensLeft();

        int index = new Random().nextInt(tokensLeft);
        WildlifeToken animalType = null;

        for (Map.Entry<WildlifeToken, Integer> entry : Bag.remainingTokens.entrySet()) {
            index -= entry.getValue();
            if (index <= 0 && animalType == null) {
                animalType = entry.getKey();
                if (removeFromRemaining) {
                    Bag.remainingTokens.put(entry.getKey(), entry.getValue() - 1);
                }
            }
        }
        return animalType;
    }

    /**
     * Generates the options for tokens that can be placed on a tile.
     *
     * @param numTokens Set to 0 for a random amount, or a number between 1-3
     *                  to set a specified amount
     * @return wildlife tokens
     */
	public static WildlifeToken[] generateTokenOptionsOnTiles(int numTokens) {
        int MAX_TOKENS_ON_TILE = 3;
		if (numTokens < 0 || numTokens > MAX_TOKENS_ON_TILE) {
			throw new IllegalArgumentException("numTokens must be between 0-3. You entered "
                    + numTokens);
		}
        if (numTokens == 0) {
            // non keystone tiles can only have either 2 or 3 token options
            numTokens = new Random().nextInt(2, 4);
        }
		WildlifeToken[] animalTypes = new WildlifeToken[3];
		for (int i = 0; i < numTokens; i++) {
            WildlifeToken tmp;
			do {
                getNumTokensLeft();
				tmp = Generation.generateWildlifeToken(false);
				if (Arrays.asList(animalTypes).contains(tmp)) {
					Bag.remainingTokens.put(tmp, Bag.remainingTokens.get(tmp) + 1);
				}
			} while (Arrays.asList(animalTypes).contains(tmp));
			animalTypes[i] = tmp;
		}
		return animalTypes;
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
        if (randomNum <= Bag.remainingTypes.get(HabitatTile.TileType.KEYSTONE)) {
            Bag.remainingTypes.put(
                    HabitatTile.TileType.KEYSTONE,
                    Bag.remainingTypes.get(HabitatTile.TileType.KEYSTONE) - 1
            );
            return generateKeystoneHabitatTile();
        }
        Bag.remainingTypes.put(
                HabitatTile.TileType.NON_KEYSTONE,
                Bag.remainingTypes.get(HabitatTile.TileType.NON_KEYSTONE) - 1
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
    private static HabitatTile generateNonKeystoneHabitatTile() {
        Map<HabitatTile.Habitat, Integer> habitatsRemaining = Bag.remainingHabitats;
        int tilesLeft = 0;

        // get the total amount of tiles left
        for (Integer value : habitatsRemaining.values()) {
            tilesLeft += value;
        }

        HabitatTile.Habitat[] habitats = generateHabitats(tilesLeft);
        HabitatTile.Habitat first = habitats[0];
        HabitatTile.Habitat second = habitats[1];
        assert first != null;
        assert second != null;
        if (first == null || second == null) {
            throw new IllegalStateException("The habitats should not be null after "
                    + "being generated");
        }

        return new HabitatTile(first, second, 0);
    }

    private static HabitatTile.Habitat[] generateHabitats(int tilesLeft) {
        HabitatTile.Habitat first;
        HabitatTile.Habitat second;
        Map.Entry<HabitatTile.Habitat, Integer> entry1 = null;
        Map.Entry<HabitatTile.Habitat, Integer> entry2 = null;
        do {
            first = null;
            second = null;
            int num1 = new Random().nextInt(1, tilesLeft);
            int num2 = new Random().nextInt(1, tilesLeft);

            /*
             * if we imagine the hashmap to contains values formatted like [Forest, Forest, Forest,
             *  River...] then this function gets the num1 th and num2 th value, and then
             * 'removes' it from the list.
             */
            for (Map.Entry<HabitatTile.Habitat, Integer> entry : Bag.remainingHabitats.entrySet()) {
                num1 -= entry.getValue();
                num2 -= entry.getValue();
                if (num1 <= 0 && first == null) {
                    entry1 = entry;
                    first = entry.getKey();
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
        return new HabitatTile.Habitat[]{first, second};
    }

    /**
     * Generates a keystone habitat tile (i.e. a tile where both habitats are
     * the same).
     */
    private static HabitatTile generateKeystoneHabitatTile() {
        Map<HabitatTile.Habitat, Integer> habitatsRemaining = Bag.remainingHabitats;
        int tilesLeft = 0;

        // get the total amount of tiles left
        for (Integer value : habitatsRemaining.values()) {
            tilesLeft += value;
        }

        int randomNum = new Random().nextInt(1, tilesLeft);
        HabitatTile.Habitat habitat = null;

        /*
         * if we imagine the hashmap to contains values formatted like [Forest, Forest, Forest,
         *  River...] then this function gets the randomNum th value, and then 'removes' it from
         * the list.
         */
        for (Map.Entry<HabitatTile.Habitat, Integer> entry : Bag.remainingHabitats.entrySet()) {
            randomNum -= entry.getValue();
            if (randomNum <= 0) {
                habitat = entry.getKey();
                Bag.remainingHabitats.put(entry.getKey(), entry.getValue() - 2);
                break;
            }
        }
        return new HabitatTile(habitat, habitat, 0);
    }

    /**
     * Generates the 'community' tile token pairs that users pick from.
     *
     * @param num the number of tile token pairs to generate
     */
    public static void generateTileTokenPairs(int num) {
        if (CurrentDeck.getDeckTiles().size() + num > Constants.MAX_DECK_SIZE) {
            throw new IllegalArgumentException("You are trying to generate more than "
                    + Constants.MAX_DECK_SIZE + " pairs for the current deck (num given: "
                    + num + ") .");
        }

        for (int i = 0; i < num; i++) {
            CurrentDeck.addDeckTile(generateHabitatTile());
            CurrentDeck.addDeckToken(generateWildlifeToken(true));
        }

        Display.displayDeck();
        CurrentDeck.cullCheckFourTokens();
        CurrentDeck.cullCheckThreeTokens();
        Bag.incrementTilesInUse(num);
    }
}
