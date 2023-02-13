import java.util.ArrayList;

public class CurrentDeck {
	public static ArrayList<HabitatTile> deckTiles = new ArrayList<>();
	public static ArrayList<WildlifeToken> deckTokens = new ArrayList<>();
	
	public CurrentDeck() {}
	
	public static ArrayList<HabitatTile> getDeckTiles() { //getters and setters
		return deckTiles;
	}
	public static HabitatTile getTile (int index) {
		return deckTiles.get(index);
	}
	public static ArrayList<WildlifeToken> getDeckTokens() {
		return deckTokens;
	}
	public static void setDeckTokens(ArrayList<WildlifeToken> tokens) {
		deckTokens = tokens;
	}
	public static WildlifeToken getToken (int index) {
		return deckTokens.get(index);
	}

	public static void setStartTileTokenSelection() {
	    	generateTileTokenPairs(4);
	    	//Display.displayDeck();
	        Display.sleep(500);
	}
	
	public static void choosePair(Player player) {

		//deal with tile here, place on map after choosing which row/column to place on
		//TODO: change to map numberings later instead of coords
		int choice = Input.chooseFromDeck();
		Display.displayPlacementMap(player);
		int[] rowcol = Input.chooseTileRowColumn();
		boolean succeeded = false;

		player.getMap().addTileToMap(deckTiles.get(choice), rowcol[0], rowcol[1]);
		deckTiles.remove(choice);

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
    	Display.displayDeck();
    	cullCheckFourTokens();
    	cullCheckThreeTokens();
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
    		
    		Display.cullOccurence();
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
    		int choice = Input.chooseCullThree();
    		if (choice == 1) {//cull choice
    			for (int i = deckTokens.size()-1; i >= 0; i--) {
    				if (getToken(i) == type) {
    					//System.out.println("cull3 removing "+deckTokens.get(i).name()+ " token at "+i);
    					Bag.remainingTokens.merge(getToken(i), 1, Integer::sum);
    					deckTokens.remove(i);
    					deckTokens.add(i, Generation.generateWildlifeToken(true));
        				//System.out.println("cull3 adding "+deckTokens.get(i).name()+ " token at "+i);
    				}	
    			}
    			Display.cullOccurence();
    			cullCheckFourTokens();
    		}
    		//if choice is 2, deck remains unchanged - message display handled in display
    	}	
    }
    
    
}
