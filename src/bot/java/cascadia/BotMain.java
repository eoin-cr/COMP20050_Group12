package cascadia;

import java.util.Arrays;
import java.util.List;

public class BotMain {
	private List<Player> players;
	private static final int NUM_BOTS = 2;
//	private final TileBot tileBot = new TileBot();
//	private final TokenBot tokenBot = new TokenBot();
	int turn = 0;
	String[] playerNames = new String[NUM_BOTS];
	TileBot[] tileBots = new TileBot[NUM_BOTS];
	TokenBot[] tokenBots = new TokenBot[NUM_BOTS];

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

	public int[] makeBestChoiceFromDeck(Player currPlayer, List<HabitatTile> deckTiles,
										List<WildlifeToken> deckTokens) {
//		int[] tileTokenChoice = new int[2];
		Player nextPlayer = null;

		for (Player p : players) {
			if (!p.equals(currPlayer)) {
				nextPlayer = p;
			}
		}

		int[] tilePreferences = tileBots[turn % 2].chooseStrategy(currPlayer, nextPlayer);
		int[] tokenPreferences = tokenBots[turn % 2].chooseStrategy(currPlayer, nextPlayer);
		System.out.printf("tile: %s\n", Arrays.toString(tilePreferences));
		System.out.printf("token: %s\n", Arrays.toString(tokenPreferences));

		int maxScore = 0;
		int maxScoreIdx = 0;
		for (int i = 0; i < tilePreferences.length; i++) {
			int score = tilePreferences[i] + tokenPreferences[i];
			if (score > maxScore) {
				maxScore = score;
				maxScoreIdx = i;
			}
		}

		turn++;
		return new int[]{maxScoreIdx, maxScoreIdx};
	}

//	public int bestTilePlacement(Player player, List<HabitatTile> deckTiles) {
//
//	}

	public int bestTokenPlacement(Player player, WildlifeToken selectedToken) {
		return tokenBots[turn % 2].getBestPlacement(selectedToken, player);
	}

}