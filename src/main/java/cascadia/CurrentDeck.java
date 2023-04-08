package cascadia;
import java.util.ArrayList;
import java.util.List;

import cascadia.scoring.*;

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
		//mynah - change made
		//choose a tile/token pair from options on deck displayed
		if (!Game.botMode) {
			int choice = Input.chooseFromDeck();
			choosePairHelper(player, choice, choice);
		}
		else if (Game.botMode) {
			int[] choices = Game.getBot().makeBestChoiceFromDeck(player, deckTiles, deckTokens);
			choosePairHelper(player, choices[0], choices[1]);
		}
	}

	//mynah - change made
	public static void choosePairHelper(Player player, int tileChoice, int tokenChoice) {
		if (player == null) {
			throw new IllegalArgumentException("Player cannot be null");
		} else if (tileChoice >= deckTiles.size() || tileChoice < 0) {
			throw new IllegalArgumentException(String.format("Tile choice must be greater " +
					"than 0 and not more than the amount of deck tiles.  Tile choice was " +
					"%d and the amount of deck tiles is %d", tileChoice, deckTiles.size()));
		} else if (tokenChoice >= deckTokens.size() || tokenChoice < 0) {
			throw new IllegalArgumentException(String.format("Token choice must be greater " +
					"than 0 and not more than the amount of deck tokens.  Token choice was " +
					"%d and the amount of deck tokens is %d", tokenChoice, deckTokens.size()));
		}
		int[] rowAndColumn;
		if (!testing) {
			rowAndColumn = Input.chooseTilePlacement(player);
			Display.rotateTile(deckTiles.get(tileChoice));
		} else {
			rowAndColumn = new int[]{1,1};
		}
		placeTileChoiceOnMap(player, tileChoice, rowAndColumn);
		placeTokenChoiceOnMap(player, tokenChoice);

		Display.outln("Your turn is now complete.");
		player.calculateTurnPlayerScore();
		Display.playerTurnStats(player);
		Display.sleep(300);
		
		if (Bag.tilesInUse() < Bag.getMaxTiles()) {
			Generation.generateTileTokenPairs(1); //replace the tile+token pair freshly removed to keep deck at size 4
		}

		if (testing) {
//			Display.outln("1");
			return;
		}
		Game.switchTurn(); //move to next player
    }

	//places tile choice on map and adjusts corridor score changes
	public static void placeTileChoiceOnMap(Player player, int tileChoice, int[] rowCol) {
		player.getMap().addTileToMap(deckTiles.get(tileChoice), rowCol[0], rowCol[1]);
		ScoringHabitatCorridors.scorePlayerHabitatCorridors(player, deckTiles.get(tileChoice)); //mynah - change made
		player.calculateCorridorsPlayerScore();
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
				Display.outln("You cannot add this token to your current map of tiles, as none of the options match.");
				break;
			}
			int[] result;
			if (testing) {
				result = new int[]{2,0};
			} else {
				result = Input.chooseTokenPlaceOrReturn(deckTokens.get(tokenChoice));
			}
			if (result[0] == 2) { //put token back in bag choice
				Bag.remainingTokens.merge(deckTokens.get(tokenChoice), 1, Integer::sum);
				Display.outln("You have put the token back in the bag");
				succeeded = true;
			} else {//add to map choice
				succeeded = player.getMap().addTokenToTile(token, result[1], player);
				//mynah - change made
				if (succeeded) { //get score change for player for that token type on their map
					Scoring.scorePlayerTokenPlacement(player, token);
					player.calculateWildlifePlayerScore();
				}
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
				Bag.remainingTokens.merge(getToken(i), 1, Integer::sum);
				deckTokens.remove(i);
				deckTokens.add(i, Generation.generateWildlifeToken(true));
			}
			Display.cullOccurrence();
    	}
    }

    protected static void cullCheckThreeTokens() {
    	boolean threeMatch = hasThreeDuplicates((ArrayList<WildlifeToken>) deckTokens);

    	if (threeMatch) {
			WildlifeToken type = tripledToken(deckTokens);
			int choice;
			if (!testing) {
				choice = Input.chooseCullThreeOptions();
			} else {
				choice = 1;
			}
    		if (choice == 1) { //cull choice
    			for (int i = deckTokens.size()-1; i >= 0; i--) {
    				if (getToken(i) == type) {
    					Bag.remainingTokens.merge(getToken(i), 1, Integer::sum);
    					deckTokens.remove(i);
    					deckTokens.add(i, Generation.generateWildlifeToken(true));

    				}	
    			}
				if (!testing) {
					Display.cullOccurrence();
				}
    			cullCheckFourTokens();
    		}
    		//if choice is 2, deck remains unchanged - message display handled in display
    	}	
    }
    
    private static boolean hasThreeDuplicates(ArrayList<WildlifeToken> list) {
		int[] counts = new int[5];
		
		for (WildlifeToken t : list) {
			counts[t.ordinal()]++;
		}
		for (int num : counts) {
			if (num == 3) {
				return true;
			}
		}
		return false;
	}

	private static WildlifeToken tripledToken(List<WildlifeToken> list) {
		WildlifeToken type;
		if (list.get(0) == list.get(1) || list.get(0) == list.get(2) || list.get(0) == list.get(3)) {
			type = list.get(0);
		}
		else if (list.get(1) == list.get(2) || list.get(1) == list.get(3) ) {
			type = list.get(1);
		}
		else { // 2 == 3 and one other
			type = list.get(2);
		}
		return type;
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
		if (index < 0 || index >= deckTokens.size()) {
			throw new IllegalArgumentException(String.format("index cannot be < 0 or >= " +
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
