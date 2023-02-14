import java.util.ArrayList;
import java.util.List;

public class CurrentDeck {
	public static List<HabitatTile> deckTiles = new ArrayList<>();
	public static List<WildlifeToken> deckTokens = new ArrayList<>();
	
	public CurrentDeck() {}
	
//	public static List<HabitatTile> getDeckTiles() { //getters and setters
//		return deckTiles;
//	}
	public static HabitatTile getTile (int index) {
		return deckTiles.get(index);
	}
//	public static List<WildlifeToken> getDeckTokens() {
//		return deckTokens;
//	}
//	public static void setDeckTokens(List<WildlifeToken> tokens) {
//		deckTokens = tokens;
//	}
	public static WildlifeToken getToken (int index) {
		return deckTokens.get(index);
	}

	public static void setStartTileTokenSelection() {
	    	generateTileTokenPairs(4);
	    	//Display.displayDeck();
	        Display.sleep(500);
	}

	/**
	 * Allows the user to select a habitat+token pair and add it to their map
	 *
	 * @param player The player who will be selecting the pair
	 */
	public static void choosePair(Player player) {
		//deal with tile here, place on map after choosing which row/column to place on
		int choice = Input.chooseFromDeck();
		int[] rowAndColumn = Input.chooseTilePlacement(player);

		boolean succeeded = false;

		player.getMap().addTileToMap(deckTiles.get(choice), rowAndColumn[0], rowAndColumn[1]);
		deckTiles.remove(choice);
		Display.displayTileMap(player);

		while (!succeeded) {
			//deal with token here, either place on a map tile or chuck it back in bag
			//places on correct tile based on tileID
			int[] result = Input.chooseTokenPlaceOrReturn(deckTokens.get(choice));
			if (result[0] == 2) { //put token back in bag choice
				Bag.remainingTokens.merge(deckTokens.get(choice), 1, Integer::sum);
				System.out.println("You have put the token back in the bag");
				succeeded = true;
			} else if (result[0] == 1) {//add to map choice
				succeeded = player.getMap().addTokenToTile(deckTokens.get(choice), result[1], player);
			}
		}
		deckTokens.remove(choice);
		generateTileTokenPairs(1); //replace the tile+token pair freshly removed to keep deck at size 4
	 }
	
	 /**
     * Generates the 'community' tile token pairs that users pick from.
     *
     * @param num the number of tile token pairs to generate
     */
    public static void generateTileTokenPairs(int num) {
    	if (deckTiles.size() + num > 4) {
    		throw new IllegalArgumentException("You are trying to generate more than 4 pairs for the current deck.");
    	}
    	
    	for (int i = 0; i < num; i++) {
    		deckTiles.add(Generation.generateHabitatTile());
    		deckTokens.add(Generation.generateWildlifeToken(true));
    	}
		Display.sleep(800);
    	Display.displayDeck();
    	cullCheckFourTokens();
    	cullCheckThreeTokens();
		Bag.incrementTilesInUse(num);
    }
    	
    public static void cullCheckFourTokens() {
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
    		
    		Display.cullOccurrence();
    	}
    }
    
    public static void cullCheckThreeTokens() {
    	int check;
    	WildlifeToken type = null;
    	boolean threeMatch = false;

    	for (int i = 0; i < deckTokens.size(); i++) {
    		type = getToken(i);
    		check = 0;
    		for (int j = 0; j < deckTokens.size(); j++) {
    			if (getToken(j) == type) {
    				check++;
    				if (check == 3) {
    					threeMatch = true;
    					type = getToken(i);
    					break;
    				}
    			}
    		}
    	}
    	
    	if (threeMatch) {
			System.out.println();
			System.out.println("There are three Wildlife Tokens of the same type. Would you like to cull them? ");
			int choice = Input.boundedInt(1, 2, "Type 1 to cull and replace tokens, or 2 to leave tokens untouched: ");
			if (choice == 1) {
				System.out.println("You have chosen to cull three tokens of the same type in the deck.");
			} else {
				System.out.println("You have chosen to leave the tokens untouched. The current deck remains the same.");
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
}
