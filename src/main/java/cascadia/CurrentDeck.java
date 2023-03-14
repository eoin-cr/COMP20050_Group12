package cascadia;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CurrentDeck {
	private static List<HabitatTile> deckTiles = new ArrayList<>();
	private static List<WildlifeToken> deckTokens = new ArrayList<>();
	private static boolean testing = false;
	
	public CurrentDeck() {}

	public static void startTesting() {
		testing = true;
	}
	
	public static HabitatTile getTile (int index) {
		return deckTiles.get(index);
	}

	public static WildlifeToken getToken (int index) {
		return deckTokens.get(index);
	}

	public static void setStartTileTokenSelection() {
	    	Generation.generateTileTokenPairs(4); //also displays deck
	        Display.sleep(500);
	}

	/**
	 * Allows the user to select a habitat+token pair and add it to their map
	 *
	 * @param player The player who will be selecting the pair
	 */
	public static void choosePair(Player player) {
		//choose a tile/token pair from options on deck displayed
		int choice = Input.chooseFromDeck();
		choosePairHelper(player, choice, choice);
	}

	public static void choosePairHelper(Player player, int tileChoice, int tokenChoice) {
		int[] rowAndColumn = Input.chooseTilePlacement(player);
		Display.rotateTile(deckTiles.get(tileChoice));
		placeTileChoiceOnMap(player, tileChoice, rowAndColumn);
		placeTokenChoiceOnMap(player, tokenChoice);
		
		System.out.println("Your turn is now complete.");
		Display.sleep(300);
		if (Bag.tilesInUse() < Bag.getMaxTiles()) {
			Generation.generateTileTokenPairs(1); //replace the tile+token pair freshly removed to keep deck at size 4
		}
		
		Game.switchTurn(); //move to next player
    }

	//places tile choice on map
	public static void placeTileChoiceOnMap(Player player, int tileChoice, int[] rowCol) {
		player.getMap().addTileToMap(deckTiles.get(tileChoice), rowCol[0], rowCol[1]);
		Display.displayPlayerTileMap(player);
		deckTiles.remove(tileChoice);
	}
	
	//places token choice on map
	public static void placeTokenChoiceOnMap(Player player, int tokenChoice) {
		WildlifeToken token = deckTokens.get(tokenChoice);
		boolean succeeded = false;
		while (!succeeded) {
			//deal with token here, either place on a map tile or chuck it back in bag
			//places on correct tile based on tileID
			if (!player.getMap().checkAllTilesForValidToken(token)) {
				System.out.println("You cannot add this token to your current map of tiles, as none of the options match.");
				break;
			}
			int[] result = Input.chooseTokenPlaceOrReturn(deckTokens.get(tokenChoice));
			if (result[0] == 2) { //put token back in bag choice
				Bag.remainingTokens.merge(deckTokens.get(tokenChoice), 1, Integer::sum);
				System.out.println("You have put the token back in the bag");
				succeeded = true;
			} else if (result[0] == 1) {//add to map choice
				succeeded = player.getMap().addTokenToTile(token, result[1], player);
//				if (succeeded) { //get score change for player for that token type on their map
//					Scoring.scorePlayerTokenPlacement(player, token);
//				}
			}
		}
		deckTokens.remove(tokenChoice);
	}

	// we set this to protected so we can test it
    protected static void cullCheckFourTokens() {
    	while (deckTokens.get(0) == deckTokens
    			.get(1) && deckTokens.get(0) == deckTokens.get(2)
    			&& deckTokens.get(0) == deckTokens.get(3)) {

			for (int i = deckTokens.size()-1; i >= 0; i--) {
					//System.out.println("cull4 removing "+deckTokens.get(i).name()+ " token at "+i);
					Bag.remainingTokens.merge(getToken(i), 1, Integer::sum);
					deckTokens.remove(i);
					deckTokens.add(i, Generation.generateWildlifeToken(true));
    				//System.out.println("cull4 adding "+deckTokens.get(i).name()+ " token at "+i);
				}
			// don't bother printing if we're testing
    		if (!testing) {
				Display.cullOccurrence();
			}
    	}
    }


	private static <E> void removeDuplicates(List<E> list) {
		// sets can't have duplicate values, so by converting our
		// list to one and then converting back we easily remove
		// the duplicate values
		Set<E> set = new HashSet<>(list);
		list.clear();
		list.addAll(set);
	}

	private static <E> boolean hasThreeDuplicates(ArrayList<E> list) {
		@SuppressWarnings("unchecked cast")
		ArrayList<E> clonedList = (ArrayList<E>) list.clone();
		removeDuplicates(clonedList);
		return clonedList.size() == 2; // if it had H,H,H,B it will now just have H,B
	}

	private static WildlifeToken tripledToken(List<WildlifeToken> list) {
		// if we have 3 of one token and 1 of another, if the first 2 are
		// the same, they must both be the tripled one.  Otherwise, if they're
		// different, the 3rd and 4th tokens must both be the tripled one
		if (list.get(0) == list.get(1)) {
			return list.get(0);
		}
		return list.get(2);
	}

    protected static void cullCheckThreeTokens() {
    	boolean threeMatch = hasThreeDuplicates((ArrayList<WildlifeToken>) deckTokens);

    	if (threeMatch) {
			WildlifeToken type = tripledToken(deckTokens);
			System.out.println(type);
			int choice;
			if (!testing) {
				System.out.println();
				System.out.println("There are three Wildlife Tokens of the same type. Would you like to cull them? ");
				choice = Input.boundedInt(1, 2, "Type 1 to cull and replace tokens, or 2 to leave tokens untouched: ");
				if (choice == 1) {
					System.out.println("You have chosen to cull three tokens of the same type in the deck.");
				} else {
					System.out.println("You have chosen to leave the tokens untouched. The current deck remains the same.");
				}
			} else {
				choice = 1;
			}
    		if (choice == 1) { //cull choice
    			for (int i = deckTokens.size()-1; i >= 0; i--) {
    				if (getToken(i) == type) {
    					//System.out.println("cull3 removing "+deckTokens.get(i).name()+ " token at "+i);
    					Bag.remainingTokens.merge(getToken(i), 1, Integer::sum);
    					deckTokens.remove(i);
    					deckTokens.add(i, Generation.generateWildlifeToken(true));
        				//System.out.println("cull3 adding "+deckTokens.get(i).name()+ " token at "+i);
    				}	
    			}
    			Display.cullOccurrence();
    			cullCheckFourTokens();
    		}
    		//if choice is 2, deck remains unchanged - message display handled in display
    	}	
    }

	public static List<HabitatTile> getDeckTiles() {
		return deckTiles;
	}

	public static List<WildlifeToken> getDeckTokens() {
		return deckTokens;
	}

	public static void addDeckToken(WildlifeToken token) {
		if (deckTokens.size() > 4) {
			throw new IllegalArgumentException("Cannot add a token when there's already" +
					" 4 tokens in the current deck.");
		}
		deckTokens.add(token);
	}

	public static void addDeckTile(HabitatTile tile) {
		if (deckTiles.size() > 4) {
			throw new IllegalArgumentException("Cannot add a tile when there's already" +
					" 4 tiles in the current deck.");
		}
		deckTiles.add(tile);
	}

	public static void removeDeckToken(int index) {
		if (index < 0 || index > deckTokens.size()) {
			throw new IllegalArgumentException(String.format("index cannot be < 0 or > " +
					"deckTokens.size.  The index is %d and deckTiles size is %d",
					index, deckTokens.size()));
		}
		deckTokens.remove(index);
	}

	// Only used for testing DO NOT USE IN ACTUAL CODE
	protected static void setDeckTiles(List<HabitatTile> tiles) {
		deckTiles = tiles;
	}

	protected static void setDeckTokens(ArrayList<WildlifeToken> tokens) {
		deckTokens = tokens;
	}
}
