package cascadia;
import java.util.ArrayList;
import java.util.List;

public class CurrentDeck {
	private static List<HabitatTile> deckTiles = new ArrayList<>();
	private static List<WildlifeToken> deckTokens = new ArrayList<>();

	public static HabitatTile getTile(int index) {
		return deckTiles.get(index);
	}

	public static WildlifeToken getToken(int index) {
		return deckTokens.get(index);
	}

	public static void setStartTileTokenSelection() {
		Generation.generateTileTokenPairs(Constants.MAX_DECK_SIZE); //also displays deck
		Display.sleep(500);
	}

	/**
	 * Allows the user to select a habitat+token pair and add it to their map.
	 *
	 * @param player The player who will be selecting the pair
	 */
	public static void choosePair(Player player) {
		//choose a tile/token pair from options on deck displayed
		int choice = Input.chooseFromDeck();
		choosePairHelper(player, choice, choice);
	}

	protected static void choosePairHelper(Player player, int tileChoice, int tokenChoice) {
		if (player == null) {
			throw new IllegalArgumentException("Player cannot be null");
		} else if (tileChoice >= deckTiles.size() || tileChoice < 0) {
			throw new IllegalArgumentException(String.format("Tile choice must be at least "
					+ "0 and less than the amount of deck tiles.  Tile choice was "
					+ "%d and the amount of deck tiles is %d", tileChoice, deckTiles.size() - 1));
		} else if (tokenChoice >= deckTokens.size() || tokenChoice < 0) {
			throw new IllegalArgumentException(String.format("Token choice must be at least 0"
					+ "and less than the number of deck tokens.  Token choice was "
					+ "%d and the amount of deck tokens is %d", tokenChoice,
					deckTokens.size() - 1));
		}
		int[] rowAndColumn;
		rowAndColumn = Input.chooseTilePlacement(player);
		Display.selectTileRotation(deckTiles.get(tileChoice));
		placeTileChoiceOnMap(player, tileChoice, rowAndColumn);
		placeTokenChoiceOnMap(player, tokenChoice);

		Display.outln("Your turn is now complete.");
		Display.sleep(300);
		if (Bag.tilesInUse() < Bag.getMaxTiles()) {
			//replace the tile+token pair freshly removed to keep deck at size 4
			Generation.generateTileTokenPairs(1);
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
				Display.outln("You cannot add this token to your current map of tiles, as none of the options match.");
				break;
			}
			int[] result = Input.chooseTokenPlaceOrReturn(deckTokens.get(tokenChoice));
			if (result[0] == 2) { //put token back in bag choice
				Bag.remainingTokens.merge(deckTokens.get(tokenChoice), 1, Integer::sum);
				Display.outln("You have put the token back in the bag");
				succeeded = true;
			} else { //add to map choice
				succeeded = player.getMap().addTokenToTile(token, result[1], player);
			}
		}
		deckTokens.remove(tokenChoice);
	}

	// we set this to protected so we can test it
	protected static void cullCheckFourTokens() {
		while (deckTokens.get(0) == deckTokens
				.get(1) && deckTokens.get(0) == deckTokens.get(2)
				&& deckTokens.get(0) == deckTokens.get(3)) {

			for (int i = deckTokens.size() - 1; i >= 0; i--) {
				Bag.remainingTokens.merge(getToken(i), 1, Integer::sum);
				deckTokens.remove(i);
				deckTokens.add(i, Generation.generateWildlifeToken(true));
			}
			Display.cullOccurrence();
		}
	}

	public static void cullCheckThreeTokens() {
		// trying to run the cull functions with less than 4 tokens will throw an
		// error, but we don't want that as there are situations where there may
		// only be 3 tokens left at the end of the game
		if (deckTokens.size() < 4 || deckTokens.contains(null)) {
			return;
		}
		// if we have 3 duplicates, and the user wants to remove them,
		// we call the cull function
		if (hasThreeDuplicates(deckTokens)
				&& Input.chooseCullThreeOptions() == 1) {
			cullThreeTokens();
		}
	}

	private static void cullThreeTokens() {
		// we find which token was tripled, and remove it from the deck and replace
		WildlifeToken type = tripledToken(deckTokens);
		// we work from the back of the list to the front, so we don't have issues
		// with tokens we're planning on removing moving to a different spot after we
		// remove one
		for (int i = deckTokens.size() - 1; i >= 0; i--) {
			if (getToken(i) == type) {
				Bag.remainingTokens.merge(getToken(i), 1, Integer::sum);
				deckTokens.remove(i);
				deckTokens.add(i, Generation.generateWildlifeToken(true));
			}
		}
		Display.cullOccurrence();
		cullCheckFourTokens();
	}

	private static boolean hasThreeDuplicates(List<WildlifeToken> list) {
		int[] counts = new int[Constants.NUM_TOKEN_TYPES];

		for (WildlifeToken t : list) {
			counts[t.ordinal()]++;
			if (counts[t.ordinal()] == 3) {
				return true;
			}
		}
		return false;
	}

	private static WildlifeToken tripledToken(List<WildlifeToken> list) {
		/*
		  if we have 3 of one token and 1 of another, if the first 2 are
		  the same, they must both be the tripled one.  Otherwise, if they're
		  different, the 3rd and 4th tokens must both be the tripled one
		 */
		if (list.get(0) == list.get(1)) {
			return list.get(0);
		}
		return list.get(2);
	}

	public static List<HabitatTile> getDeckTiles() {
		return deckTiles;
	}

	public static List<WildlifeToken> getDeckTokens() {
		return deckTokens;
	}

	public static void addDeckToken(WildlifeToken token) {
		if (deckTokens.size() >= Constants.MAX_DECK_SIZE) {
			throw new IllegalArgumentException("Cannot add a token when there's already"
					+ Constants.MAX_DECK_SIZE + " tokens in the current deck.");
		}
		deckTokens.add(token);
	}

	public static void addDeckTile(HabitatTile tile) {
		if (deckTiles.size() >= Constants.MAX_DECK_SIZE) {
			throw new IllegalArgumentException("Cannot add a tile when there's already"
					+ Constants.MAX_DECK_SIZE + " tiles in the current deck.");
		}
		deckTiles.add(tile);
	}

	/**
	 * Removes a deck token, 0 indexed.
	 *
	 * @param index the index of the token to remove
	 */
	public static void removeDeckToken(int index) {
		if (index < 0 || index >= deckTokens.size()) {
			throw new IllegalArgumentException(String.format("index cannot be < 0 or >= "
							+ "deckTokens.size.  The index is %d and deckTiles size is %d",
					index, deckTokens.size()));
		}
		deckTokens.remove(index);
	}

	// Only used for testing DO NOT USE IN ACTUAL CODE
	protected static void setDeckTiles(List<HabitatTile> tiles) {
		deckTiles = tiles;
	}

	protected static void setDeckTokens(List<WildlifeToken> tokens) {
		deckTokens = tokens;
	}
}
