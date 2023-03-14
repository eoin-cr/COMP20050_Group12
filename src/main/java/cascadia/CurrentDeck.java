package cascadia;
import java.util.ArrayList;
import java.util.List;

public class CurrentDeck {
	public static List<HabitatTile> deckTiles = new ArrayList<>();
	public static List<WildlifeToken> deckTokens = new ArrayList<>();
	
	public CurrentDeck() {}
	
	public static HabitatTile getTile (int index) {
		return deckTiles.get(index);
	}
	public static WildlifeToken getToken (int index) {
		return deckTokens.get(index);
	}

	public static void setStartTileTokenSelection() {
	    	generateTileTokenPairs(4); //also displays deck
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
		rotateTile(tileChoice);
		placeTileChoiceOnMap(player, tileChoice, rowAndColumn);
		placeTokenChoiceOnMap(player, tokenChoice);
		
		System.out.println("Your turn is now complete.");
		Display.sleep(300);
		if (Bag.tilesInUse() < Bag.getMaxTiles()) {
			generateTileTokenPairs(1); //replace the tile+token pair freshly removed to keep deck at size 4
		}
		
		Game.switchTurn(); //move to next player
    }
	
	// displays the different rotation options in order, rotates tile choice to that rotation
	public static void rotateTile(int tileChoice) {
		String orientationOptions = "";
		if (!deckTiles.get(tileChoice).isKeystone()) {
			for (int i = 0; i < 6; i++) {
				deckTiles.get(tileChoice).rotateTile(1);
					orientationOptions = Display.removeNewlineAndJoin(
						orientationOptions, deckTiles.get(tileChoice).toFormattedString(), "\t\t\t"
					);
			}
			System.out.println(orientationOptions);
			// allows the user to select what rotation they want
			deckTiles.get(tileChoice).rotateTile(-1);
		}
	}
	
	//places tile choice on map
	public static void placeTileChoiceOnMap(Player player, int tileChoice, int[] rowcol) {
		player.getMap().addTileToMap(deckTiles.get(tileChoice), rowcol[0], rowcol[1]);
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
