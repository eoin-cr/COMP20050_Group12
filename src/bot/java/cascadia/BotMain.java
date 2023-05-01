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

import java.util.Arrays;
import java.util.List;

/**
 * A bot which plays the game cascadia.
 */
public class BotMain {
	private List<Player> players;
	private static final int NUM_BOTS = 2;
	int turn = 0;
	int bestTokenIdx = 0;
	int bestTileIdx = 0;
	boolean useNatureToken = false;
	String[] playerNames = new String[NUM_BOTS];
	TileBot[] tileBots = new TileBot[NUM_BOTS];
	TokenBot[] tokenBots = new TokenBot[NUM_BOTS];

	/**
	 * Generates two tile and token bots.
	 */
	public BotMain() {
		playerNames[0] = "BOT A";
		playerNames[1] = "BOT B";
		tileBots[0] = new TileBot();
		tileBots[1] = new TileBot();
		tokenBots[0] = new TokenBot();
		tokenBots[1] = new TokenBot();
	}

	public String[] makeBotPlayerNames() {
		return playerNames;
	}

	public void getBotPlayers(List<Player> playerList) {
		players = playerList;
	}

	/**
	 * Given a players map, and the deck tiles and tokens, the bot will
	 * select the tile token pair it finds to be best based on certain
	 * strategies.
	 */
	public void makeBestChoiceFromDeck(Player currPlayer) {
		Player nextPlayer = null;

		for (Player p : players) {
			if (!p.equals(currPlayer)) {
				nextPlayer = p;
			}
		}

		BotTimer.startTimer();

		int[] tilePreferences = tileBots[turn % 2].chooseStrategy(currPlayer, nextPlayer);
		int[] tokenPreferences = tokenBots[turn % 2].chooseStrategy(currPlayer, nextPlayer);
		System.out.printf("tile: %s\n", Arrays.toString(tilePreferences));
		System.out.printf("token: %s\n", Arrays.toString(tokenPreferences));

		int maxScore = tilePreferences[0] + tokenPreferences[0];
		int maxScoreIdx = 0;
		for (int i = 0; i < tilePreferences.length; i++) {
			int score = tilePreferences[i] + tokenPreferences[i];
			if (score >= maxScore) {
				maxScore = score;
				maxScoreIdx = i;
			}
		}

		// if the maximum possible score is greater than the highest score + 2,
		// we probably want to use a nature token (e.g. [4,0,0,0], [0,0,0,4])
		int maxPossible = Arrays.stream(tilePreferences).max().getAsInt()
				+ Arrays.stream(tokenPreferences).max().getAsInt();
		if (maxPossible > maxScore + 2 && currPlayer.getPlayerNatureTokens() > 0) {
			int tileMax = tilePreferences[0];
			int tileIdx = 0;
			int tokenMax = tokenPreferences[0];
			int tokenIdx = 0;
			for (int i = 0; i < Constants.MAX_DECK_SIZE; i++) {
				if (tokenPreferences[i] >= tokenMax) {
					tokenMax = tokenPreferences[i];
					tokenIdx = i;
				}
				if (tilePreferences[i] >= tileMax) {
					tileMax = tilePreferences[i];
					tileIdx = i;
				}
			}
			bestTileIdx = tileIdx;
			bestTokenIdx = tokenIdx;
			useNatureToken = true;
			return;
		}
		bestTokenIdx = maxScoreIdx;
		bestTileIdx = maxScoreIdx;
		useNatureToken = false;
	}

	public int[] getBestChoice() {
		return new int[]{bestTileIdx, bestTokenIdx};
	}

	// we want to make sure that after the
	public boolean shouldUseNatureToken() {
		return useNatureToken;
	}

	public int bestTokenPlacement(Player player, WildlifeToken selectedToken, int deckIdx) {
		return tokenBots[turn % 2].getBestPlacement(selectedToken, deckIdx, player);
	}

	public void incrementTurn() {
		turn++;
	}

	public int[] bestTilePlacement(int index) {
		return tileBots[turn % 2].getDeckTilePlacementChoice(index);
	}

	public int getNumRotations(int index) {
		return tileBots[turn % 2].getDeckTileNumRotations(index);
	}

}
