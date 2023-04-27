package cascadia;

import cascadia.scoring.ScoreToken;
import java.util.ArrayList;
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
	private final List<ValueSortedMap<Integer, Integer>> tokenScoreMap = new ArrayList<>();
	private final int[] bestPlacementIds = new int[Constants.NUM_TOKEN_TYPES];

	public TokenBot() {
		for (int j = 0; j < Constants.NUM_TOKEN_TYPES; j++) {
			tokenScoreMap.add(new ValueSortedMap<>());
		}

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
		int[] preferences;
		List<WildlifeToken> deckTokens = CurrentDeck.getDeckTokens();

		Random rand = new Random();
		int strategyChoice = rand.nextInt(NUM_TOKEN_STRATS);

			if(!BotTimer.checkTimeLeft()){
				preferences= new int[]{0,0,0,0};
				return preferences;
			}

		switch (strategyChoice) {
			case 0 -> {
				System.out.println("Using constructive token strat!");
				preferences = constructiveTokenStrat(deckTokens, player, true);
			}
			case 1 -> preferences = destructiveTokenStrat(deckTokens, nextPlayer);
			default -> throw new IllegalArgumentException("Unexpected value: " + strategyChoice);
		}

		return preferences;
	}

	private int[] constructiveTokenStrat(List<WildlifeToken> deckTokens, Player player,
										 boolean isConst) {
		int[] rankedTokens = rankWildlifeTokens(player, isConst);
		System.out.println("ranked tokens: " + Arrays.toString(rankedTokens));
		return rankDeck(rankedTokens, deckTokens);
	}

	private int[] destructiveTokenStrat(List<WildlifeToken> deckTokens, Player nextPlayer) {
		System.out.println("Using destructive token strat!");
		// well the destructive strat just needs to find the best token for the player
		// which we can do by calling the constructive token strat but for them lol
		return constructiveTokenStrat(deckTokens, nextPlayer, false);
	}

	private int[] rankDeck(int[] rankedTokens, List<WildlifeToken> deckTokens) {
		int[] output = new int[deckTokens.size()];
		for (int i = 0; i < deckTokens.size(); i++) {
			output[i] = rankedTokens[deckTokens.get(i).ordinal()];
		}
		return output;
	}

	private int[] rankWildlifeTokens(Player player, boolean isConst) {
		int[] scores = new int[Constants.NUM_TOKEN_TYPES];
		for (int i = 0; i < Constants.NUM_TOKEN_TYPES; i++) {
			tokenScoreMap.get(i).clear(); // clear the scores in the map before calculating
			// checks whether it is possible to place a certain token on the map.  If it
			// is not, the score for that token is largely decreased
			if (player.getMap().numPossibleTokenPlacements(WildlifeToken.values()[i]) == 0) {
				scores[i] = -3;
				if (!isConst) {
					bestPlacementIds[i] = -2;
				}
			} else {
				scores[i] = calculatePlacementScoresAndReturnMax(i, player, isConst);
			}
		}
		return convertToRank(scores);
	}

	/*
        Tries place the token in all possible positions and returns the greatest increase
        in score

         // TODO make the function account for tokens that can be placed
    */
	private int calculatePlacementScoresAndReturnMax(int tokenType, Player player,
													 boolean isConst) {
		int max = 0;
		WildlifeToken token = WildlifeToken.values()[tokenType];
		List<HabitatTile> possibleTiles = player.getMap().getPossibleTokenPlacements(token);

		// as a placement that may have been valid last move may not be valid this move,
		// we need to reset the best placement id value to ensure when a tile cannot be placed
		// the bot knows this
		bestPlacementIds[tokenType] = -1;

		int prevScore = ScoreToken.calculateScore(player.getMap(), token);

		// oh boy the time complexity of this is. not good.
		for (HabitatTile tile : possibleTiles) {
			Player tmp = new Player("temp");
			tmp.getMap().setTileBoard(PlayerMap.deepCopy(player.getMap().getTileBoardPosition()));

			tmp.getMap().addTokenToTileForTesting(token, tile.getTileID(), tmp);
			int scoreDiff = ScoreToken.calculateScore(tmp.getMap(), token) - prevScore;
			if (tile.isKeystone()) {
				// we get an extra point for getting a nature token from placing on a keystone tile
				scoreDiff++;
			}
			tokenScoreMap.get(tokenType).put(tile.getTileID(), scoreDiff);
			if (scoreDiff > max) {
				max = scoreDiff;
				bestPlacementIds[tokenType] = tile.getTileID();
			}

			if(!BotTimer.checkTimeLeft()) break;
		}

		// we don't want to set our placement as the other players best placement, so we
		// denote the fact that it's the other players'
		if (!isConst) {
			bestPlacementIds[tokenType] = -2;
		}

		return max;
	}

	// converts an arraylist of scores (e.g. [1,10,5,6]) to their relative
	// ranking (e.g. [4,1,3,2])
	private int[] convertToRank(int[] scores) {
		int[] output = new int[scores.length];
		for (int i = 0; i < scores.length; i++) {
			int numGreater = 0;
			for (int j = 0; j < scores.length; j++) {
				if (scores[i] > scores[j] && i != j) {
					numGreater++;
				}
			}
			output[i] = numGreater + 1;
		}
		return output;
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
	public int getBestPlacement(WildlifeToken token, Player player) {
		int id = bestPlacementIds[token.ordinal()];
		System.out.println(Arrays.toString(bestPlacementIds));

		// if we were doing the destructive method (finding the best token for the opponent
		// and taking it) we want to find where we can place that token on our map
		if (id == -2) {
			calculatePlacementScoresAndReturnMax(token.ordinal(), player, true);
			id = bestPlacementIds[token.ordinal()];
			System.out.println(Arrays.toString(bestPlacementIds));
		}
		return id;
	}
}
