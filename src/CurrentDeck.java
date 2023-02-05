import java.util.ArrayList;

public class CurrentDeck {
	private static ArrayList<HabitatTile> deckTiles = new ArrayList<>();
	private static ArrayList<WildlifeToken> deckTokens = new ArrayList<>();
	
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
	public static WildlifeToken getToken (int index) {
		return deckTokens.get(index);
	}

	public static void setStartTileTokenSelection() {
	    	generateTileTokenPairs(4);
	    	Display.displayDeck();
	        Display.sleep(500);
	}
	
	public static void choosePair(Player player) {
	    	int choice = Display.chooseFromDeck();
	    	int[] rowcol = Display.chooseTileRowColumn();
	    	
	    	player.getMap().addTileToMap(deckTiles.get(choice), rowcol[0], rowcol[1]);
	    	Display.displayTileMap(player);
	    	deckTiles.remove(choice);
	    	
	    	//TODO: choose what to do with token here before it is removed from the current deck
	    	//not sure how to add it to a tile, or how to choose a tile to place it on? maybe by tileID?
	    	//put these token placement methods in the map class, and the choice method in the display class
	    	deckTokens.remove(choice);
	    	generateTileTokenPairs(1); //replace the tile+token pair freshly removed to keep deck at size 4
	 }
	
	 /**
     * Generates the 'community' tile token pairs that users pick from.
     *
     * @param num the number of tile token pairs to generate
     * @return a hashmap containing tile token pairs
     */
    public static void generateTileTokenPairs(int num) {
    	if (deckTiles.size() + num > 4) {
    		throw new IllegalArgumentException("You are trying to generate more than 4 pairs for the current deck.");
    	}
    	
    	for (int i = 0; i < num; i++) {
    		deckTiles.add(Generation.generateHabitatTile());
    		deckTokens.add(Generation.generateWildlifeToken());
    	}
    	cullCheckFourTokens();
    	cullCheckThreeTokens();
    }
    	
    public static void cullCheckFourTokens() {
    	while (deckTokens.get(0) == deckTokens.get(1) && deckTokens.get(0) == deckTokens.get(2)
    			&& deckTokens.get(0) == deckTokens.get(3)) {
    		for (int i = 0; i < deckTokens.size(); i++) {
    			//adds removed tokens back to remainingTokens
    			Bag.remainingTokens.merge(getToken(i), 1, Integer::sum);
    			deckTokens.remove(i);
    			deckTokens.add(Generation.generateWildlifeToken());
    		}
    	}
    	Display.cullOccurence();
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
    					break;
    				}
    			}
    		}
    	}
    	
    	if (threeMatch == true) {
    		int choice = Display.chooseCullThree();
    		if (choice == 1) {//cull choice
    			for (int i = 0; i < deckTokens.size(); i++) {
    				if (getToken(i) == type) {
    					//adds removed tokens back to remainingTokens
    					Bag.remainingTokens.merge(getToken(i), 1, Integer::sum);
    	    			deckTokens.remove(i);
    	    			deckTokens.add(Generation.generateWildlifeToken());
    				}
    			}
    			Display.cullOccurence();
    		}
    		//if choice is 2, deck remains unchanged - message display handled in display
    	}	
    }
    
    
}
