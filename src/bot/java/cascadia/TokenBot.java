package cascadia;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TokenBot {
	// TODO: for sprint 5 make a method which calculates the best token
	// 		placement on the map given a certain token.
	public static final int NUM_TOKEN_STRATS = 2;

	public int[] chooseStrategy(Player player, Player nextPlayer) {
//		int[] preferences = new int[4];
		List<WildlifeToken> deckTokens = CurrentDeck.getDeckTokens();

//		Random rand = new Random();
//		int strategyChoice = rand.nextInt(NUM_TOKEN_STRATS);

//		switch (strategyChoice) {
//			case 0 -> preferences = constructiveTokenStrat(deckTokens, player);
//			case 1 -> preferences = destructiveTokenStrat(deckTokens, nextPlayer);
//			default -> throw new IllegalArgumentException("Unexpected value: " + strategyChoice);
//		}

//		return preferences;
		return constructiveTokenStrat(deckTokens, player);
	}

	private int[] constructiveTokenStrat(List<WildlifeToken> deckTokens, Player player) {
		// if it's the first move, all the scores will be 0, so we need to do
		// something different
		if (player.getTotalPlayerScore() == 0) {
			return firstScoring(deckTokens, player);
		}

		int[] rankedTokens = rankWildlifeTokens(player);
//		setBestPlacement(deckTokens.get(num), player);
		System.out.println("ranked tokens: " + Arrays.toString(rankedTokens));
		return rankDeck(rankedTokens, deckTokens);
	}

	private int[] destructiveTokenStrat(List<WildlifeToken> deckTokens, Player nextPlayer) {
		int[] preferences = new int[4];

		return preferences;
	}

	private int[] firstScoring(List<WildlifeToken> deckTokens, Player player) {
		List<HabitatTile> keystones = player.getMap().getKeystoneTiles();
		int[] output = new int[Constants.MAX_DECK_SIZE];
		// just put the first keystone token option in the variable (there
		// should only be one at the start of the game anyway)
		WildlifeToken keystoneToken = keystones.get(0).getTokenOptions()[0];

		// check whether we have the matching token for the keystone tile
		if (deckTokens.contains(keystoneToken)) {
			// can just set the one which has the matching option for the
			// keystone tile as 2, and all the rest to 0
			output[deckTokens.indexOf(keystoneToken)] = 2;
		} else {
			// otherwise we really can just pick any random wildlife token,
			// as long as it appears in the map, it doesn't make too much
			// of a difference
			for (int i = 0; i < output.length; i++) {
				if (player.getMap().numPossibleTokenPlacements(deckTokens.get(i)) > 0) {
					output[i] = 1;
				}
			}
		}
		return output;
	}

	private int[] rankDeck(int[] rankedTokens, List<WildlifeToken> deckTokens) {
		int[] output = new int[deckTokens.size()];
		for (int i = 0; i < deckTokens.size(); i++) {
			output[i] = rankedTokens[deckTokens.get(i).ordinal()];
		}
		return output;
	}

	private int[] rankWildlifeTokens(Player player) {
		int[] scores = new int[Constants.NUM_TOKEN_TYPES];
		for (int i = 0; i < Constants.NUM_TOKEN_TYPES; i++) {
			// checks whether it is possible to place a certain token on the map.  If it
			// is not, the score for that token is largely decreased
			if (player.getMap().numPossibleTokenPlacements(WildlifeToken.values()[i]) == 0) {
				scores[i] = -3;
			} else {
				scores[i] = player.getPlayerWildlifeScore(WildlifeToken.values()[i]);
			}
		}
		return convertToRank(scores);
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

	//	private void setBestPlacement(WildlifeToken token, Player player) {

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
		// for now we're just going to place the token on the first keystone
		// tile with the token option, and if there are none, just place it on
		// the first tile with the token option.  This will need to be changed
		// later on.
		List<HabitatTile> possibleTiles = player.getMap().getPossibleTokenPlacements(token);
		List<HabitatTile> possibleKeystones = possibleTiles.stream().filter(HabitatTile::isKeystone)
				.toList();
		System.out.printf("Possible %s keystone placement ids: ", token.toString());
		for (HabitatTile tile : possibleKeystones) {
			System.out.printf("%d ", tile.getTileID());
		}
		System.out.println(" ");

		System.out.printf("Possible %s placement ids: ", token.toString());
		for (HabitatTile tile : possibleTiles) {
			System.out.printf("%d ", tile.getTileID());
		}
		System.out.println(" ");
		if (possibleKeystones.size() > 0) {
			return possibleKeystones.get(0).getTileID();
		}
		if (possibleTiles.size() == 0) {
			return -1;
		}
		return possibleTiles.get(0).getTileID();
	}
}
