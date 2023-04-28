package cascadia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A bot which plays the game cascadia.
 */
public class BotMain {
	private List<Player> players;
	private static final int NUM_BOTS = 2;
	int turn = 0;
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
	public int[] makeBestChoiceFromDeck(Player currPlayer) {
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

		//TODO: handle nature token spend here

		//case handled here if multiple maxScores exist, give pref to one with higher token score
		//eg tile: 4,0,0,1 + token: 1,0,0,4 <- give pref to index 3 not index 0
		int maxScore = 0;
		for (int i = 0; i < tilePreferences.length; i++) {
			int token = 0;
			try {
				token = tokenPreferences[i];
			} catch	(ArrayIndexOutOfBoundsException ignored) { };
			int score = tilePreferences[i] + token;
			if (score > maxScore) {
				maxScore = score;
			}
		}

		int maxTokenScore = 0;
		int maxTokenScoreIdx = 0;
		for (int i = 0; i < tokenPreferences.length; i++) {
			int token = 0;
			int tile = 0;
			try {
				token = tokenPreferences[i];
			} catch	(ArrayIndexOutOfBoundsException ignored) { };
			int score = tile + token;
			if (score == maxScore && token > maxTokenScore) {
				maxTokenScore = token;
				maxTokenScoreIdx = i;
			}
		}
		int maxScoreIdx = maxTokenScoreIdx;

		return new int[]{maxScoreIdx, maxScoreIdx};
	}

	public int bestTokenPlacement(Player player, WildlifeToken selectedToken) {
		return tokenBots[turn % 2].getBestPlacement(selectedToken, player);
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
