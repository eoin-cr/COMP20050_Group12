package cascadia;

import java.util.Arrays;
import java.util.List;

public class BotMain {
	private List<Player> players;
	private final TileBot tileBot = new TileBot();
	private final TokenBot tokenBot = new TokenBot();

	public String[] makeBotPlayerNames() {
		String[] playerNames = new String[2];
		playerNames[0] = "BOT A";
		playerNames[1] = "BOT B";
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

		int[] tilePreferences = tileBot.chooseStrategy(currPlayer, nextPlayer);
		int[] tokenPreferences = tokenBot.chooseStrategy(currPlayer, nextPlayer);
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

		return new int[]{maxScoreIdx, maxScoreIdx};
	}

//	public int bestTilePlacement(Player player, List<HabitatTile> deckTiles) {
//
//	}

	public int bestTokenPlacement(Player player, WildlifeToken selectedToken) {
		return tokenBot.getBestPlacement(selectedToken, player);
	}

}
