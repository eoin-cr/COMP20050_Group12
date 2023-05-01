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

import cascadia.scoring.ScoreToken;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Bot which calculates the optimal token to play, and where to place it,
 * based on certain strategies.
 */
public class TokenBot {
	public static final int NUM_TOKEN_STRATS = 2;
	// we don't need the token score map atm, but I'm keeping it in case we need it in the
	// future
	private final int[] bestPlacementIds = new int[Constants.MAX_DECK_SIZE];
	private final int[] rankedTokens = new int[Constants.MAX_DECK_SIZE];

	public TokenBot() {
		// initialise it to -1, so we know when we aren't able to place a token
		Arrays.fill(bestPlacementIds, -1);
	}

	/**
	 * Randomly selects a strategy to use.
	 * There are currently two strategies it randomly selects from.
	 * There is a constructive strategy, where the bot finds which token
	 * results in the best score for the current player and selects that,
	 * and there is the destructive strategy, which chooses the best token
	 * for the opponent and selects it so the opponent cannot use it.
	 * Regardless of strategy chosen, the bot will place the tile on the tile
	 * that results in the best score for itself.
	 */
	public int[] chooseStrategy(Player player, Player nextPlayer) {
		List<WildlifeToken> deckTokens = CurrentDeck.getDeckTokens();

		Random rand = new Random();
		int strategyChoice = rand.nextInt(NUM_TOKEN_STRATS);

		if (!BotTimer.isTimeLeft()) {
			System.out.println("No time left!");
			return rankedTokens;
		}

		switch (strategyChoice) {
			case 0 -> {
				System.out.println("Using constructive token strat!");
				constructiveTokenStrat(deckTokens, player, true);
			}
			case 1 -> destructiveTokenStrat(deckTokens, nextPlayer);
			default -> throw new IllegalArgumentException("Unexpected value: " + strategyChoice);
		}

		return rankedTokens;
	}

	private void constructiveTokenStrat(List<WildlifeToken> deckTokens, Player player,
										boolean isConst) {
		rankDeckTokens(player, isConst, deckTokens);
		System.out.println("ranked tokens: " + Arrays.toString(rankedTokens));
	}

	private void destructiveTokenStrat(List<WildlifeToken> deckTokens, Player nextPlayer) {
		System.out.println("Using destructive token strat!");
		// well the destructive strat just needs to find the best token for the other player
		// which we can do by calling the constructive token strat but for them lol
		constructiveTokenStrat(deckTokens, nextPlayer, false);
	}

	private void rankDeckTokens(Player player, boolean isConst, List<WildlifeToken> deckTokens) {
		int[] scores = new int[Constants.MAX_DECK_SIZE];
		for (int i = 0; i < Constants.MAX_DECK_SIZE; i++) {
			// checks whether it is possible to place a certain token on the map.  If it
			// is not, the score for that token is largely decreased
			if (player.getMap().numPossibleTokenPlacements(deckTokens.get(i)) == 0) {
				scores[i] = -3;
				if (!isConst) {
					bestPlacementIds[i] = -2;
				}
			} else {
				scores[i] = calculatePlacementScoresAndReturnMax(deckTokens.get(i),
						player, isConst, i);
			}
		}
		convertToRank(scores, deckTokens);
	}

	/*
        Tries to place the token in all possible positions and returns the greatest increase
        in score
    */
	private int calculatePlacementScoresAndReturnMax(WildlifeToken token, Player player,
													 boolean isConst, int idx) {
		int max = 0;
		List<HabitatTile> possibleTiles = player.getMap().getPossibleTokenPlacements(token);

		// as a placement that may have been valid last move may not be valid this move,
		// we need to reset the best placement id value to ensure when a tile cannot be placed
		// the bot knows this
		bestPlacementIds[idx] = -1;

		int prevScore = ScoreToken.calculateScore(player.getMap(), token);

		for (HabitatTile tile : possibleTiles) {
			Player tmp = new Player("temp");
			tmp.getMap().setTileBoard(PlayerMap.deepCopy(player.getMap().getTileBoardPosition()));

			tmp.getMap().addTokenToTileForTesting(token, tile.getTileID(), tmp);
			int scoreDiff = ScoreToken.calculateScore(tmp.getMap(), token) - prevScore;
			if (tile.isKeystone()) {
				// we get an extra point for getting a nature token from placing on a keystone tile
				scoreDiff++;
			}

			// set it to scores that are equal to as well, as for example if the
			// diff is 0 when we place a token, it's still better to place a token
			// rather than not placing it
			if (scoreDiff >= max) {
				max = scoreDiff;
				bestPlacementIds[idx] = tile.getTileID();
			}


			if (!BotTimer.isTimeLeft()) {
				break;
			}
		}

		// we don't want to set our placement as the other players best placement, so we
		// denote the fact that it's the other players'
		if (!isConst) {
			bestPlacementIds[idx] = -2;
		}

		return max;
	}

	// converts an arraylist of scores (e.g. [1,10,5,6]) to their relative
	// ranking (e.g. [0,3,1,2])
	private void convertToRank(int[] scores, List<WildlifeToken> deckTokens) {

		List<Integer> sorted = Arrays.stream(scores).sorted().boxed().toList();

		for (int i = 0; i < sorted.size(); i++) {
			scores[i] = sorted.indexOf(scores[i]);
		}

//		for (int i = 0; i < scores.length; i++) {
//			rankedTokens[i] = 4 - scores[i];
//		}
		System.arraycopy(scores, 0, rankedTokens, 0, rankedTokens.length);
	}

	/**
	 * Returns the best calculated tile ID to place the token on.
	 * Returns -1 if there are no possible spots to place the token on.
	 *
	 * @param token the token to find the best placement of
	 * @param player the player whose map will be analysed
	 * @return the tileID to place the token on, or -1 if there are no possible
	 * 				options
	 */
	public int getBestPlacement(WildlifeToken token, int deckIdx, Player player) {
		int id = bestPlacementIds[deckIdx];
//		System.out.println(Arrays.toString(bestPlacementIds));

		// if we were doing the destructive method (finding the best token for the opponent
		// and taking it) we want to find where we can place that token on our map
		if (id == -2) {
			calculatePlacementScoresAndReturnMax(token, player, true, deckIdx);
			id = bestPlacementIds[deckIdx];
//			System.out.println(Arrays.toString(bestPlacementIds));
		}
		return id;
	}
}
