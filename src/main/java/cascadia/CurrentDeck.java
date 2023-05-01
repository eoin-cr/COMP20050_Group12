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

import cascadia.scoring.Scoring;
import cascadia.scoring.ScoringHabitatCorridors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Stores information related to the tiles and tokens in the communal deck.
 */
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
		//mynah - change made
		//choose a tile/token pair from options on deck displayed
		if (!Game.botMode) {
			int choice = Input.chooseFromDeck();
			choosePairHelper(player, choice, choice);
		} else {
//			int[] choices = Game.getBot().makeBestChoiceFromDeck(player);
			int[] choices = Game.getBot().getBestChoice();
			System.out.println(Arrays.toString(choices));
			choosePairHelper(player, choices[0], choices[1]);
		}
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
		if (!Game.botMode) {
			rowAndColumn = Input.chooseTilePlacement(player);
			Display.selectTileRotation(deckTiles.get(tileChoice));
		} else {
			rowAndColumn = Game.getBot().bestTilePlacement(tileChoice);
			System.out.println("Chosen row and column: " + Arrays.toString(rowAndColumn));
//			int rotations = Game.getBot().getNumRotations(tileChoice);
//			deckTiles.get(tileChoice).rotateTile(rotations);
		}
		placeTileChoiceOnMap(player, tileChoice, rowAndColumn);
		placeTokenChoiceOnMap(player, tokenChoice);

		Display.outln("Your turn is now complete.");
		player.calculateTurnPlayerScore();
		Display.playerTurnStats(player);
		Display.sleep(300);

		if (Bag.tilesInUse() < Bag.getMaxTiles()) {
			//replace the tile+token pair freshly removed to keep deck at size 4
			Generation.generateTileTokenPairs(1);
		}

		Game.switchTurn(); //move to next player
	}

	/**
	 * Places the tile choice on the map and adjusts the corridor score changes.
	 */
	public static void placeTileChoiceOnMap(Player player, int tileChoice, int[] rowCol) {
		player.getMap().addTileToMap(deckTiles.get(tileChoice), rowCol[0], rowCol[1]);
		ScoringHabitatCorridors.scorePlayerHabitatCorridors(player, deckTiles.get(tileChoice));
		player.calculateCorridorsPlayerScore();
		Display.displayPlayerTileMap(player);
		deckTiles.remove(tileChoice);
	}

	/**
	 * Places token choice on map.
	 */
	public static void placeTokenChoiceOnMap(Player player, int tokenChoice) {
		WildlifeToken token = deckTokens.get(tokenChoice);
		boolean succeeded = false;
		int tries = 0;
		int maxTries = 5;

		while (!succeeded) {
			// protects against infinite loops (and really dumb players i guess)
			if (tries >= maxTries) {
				placeTokenInBag(tokenChoice);
				Display.outf("You have made %d incorrect placement attempts.  The token has been "
						+ "placed back in the bag.", maxTries);
				break;
			}
			//deal with token here, either place on a map tile or chuck it back in bag
			//places on correct tile based on tileID
			if (!player.getMap().checkAllTilesForValidToken(token)) {
				Display.outln("You cannot add this token to your current map of tiles,"
						+ " as none of the options match.");
				break;
			}
			int[] result;
			if (Game.botMode) {
				int botChoice = Game.getBot().bestTokenPlacement(player,
						deckTokens.get(tokenChoice), tokenChoice);
				Game.getBot().incrementTurn();
				System.out.printf("Tile selected (-1 means put back in bag): %d\n", botChoice);
				if (botChoice == -1) {
					result = new int[]{2, 0};
				} else {
					result = new int[]{1, botChoice};
				}
			} else {
				result = Input.chooseTokenPlaceOrReturn(token);
			}

			//put token back in bag choice
			if (result[0] == 2) {
				placeTokenInBag(tokenChoice);
				succeeded = true;
			} else { //add to map choice
				succeeded = placeTokenHelper(player, token, result[1]);
			}
			tries++;
		}
		deckTokens.remove(tokenChoice);
	}

	private static void placeTokenInBag(int tokenChoice) {
		Bag.remainingTokens.merge(deckTokens.get(tokenChoice), 1, Integer::sum);
		Display.outln("You have put the token back in the bag");
	}

	private static boolean placeTokenHelper(Player player, WildlifeToken token, int placement) {
		boolean succeeded = player.getMap().addTokenToTile(token, placement, player);
		//mynah - change made
		if (succeeded) { //get score change for player for that token type on their map
			Scoring.scorePlayerTokenPlacement(player, token);
			player.calculateWildlifePlayerScore();
		}
		return succeeded;
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

	/**
	 * Checks whether we are in the situation where there are 3 tokens of the
	 * same type in the deck.
	 * If so, asks whether the user wants to cull them.
	 */
	public static void cullCheckThreeTokens() {
		// trying to run the cull functions with less than 4 tokens will throw an
		// error, but we don't want that as there are situations where there may
		// only be 3 tokens left at the end of the game
		if (deckTokens.size() < 4 || deckTokens.contains(null)) {
			return;
		}
		// if we have 3 duplicates, and the user wants to remove them,
		// we call the cull function
		if (!Game.botMode && hasThreeDuplicates(deckTokens)
				&& Input.chooseCullThreeOptions() == 1) {
			cullThreeTokens();
		} else if (Game.botMode && hasThreeDuplicates(deckTokens)) {
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

	/**
	 * Adds a token to the current deck.
	 *
	 * @throws IllegalArgumentException if the deck size is already at its set
	 * 				maximum
	 */
	public static void addDeckToken(WildlifeToken token) {
		if (deckTokens.size() >= Constants.MAX_DECK_SIZE) {
			throw new IllegalArgumentException("Cannot add a token when there's already"
					+ Constants.MAX_DECK_SIZE + " tokens in the current deck.");
		}
		deckTokens.add(token);
	}

	/**
	 * Adds a tile to the current deck.
	 *
	 * @throws IllegalArgumentException if the deck size is already at its set
	 * 				maximum
	 */
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
