package cascadia;

import java.util.List;

public class BotMain {
	private List<Player> players;
	private TileBot tileBot = new TileBot();
	private TokenBot tokenBot = new TokenBot();

	public String[] makeBotPlayerNames() {
		String[] playerNames = new String[2];
		playerNames[0] = "BOT A";
		playerNames[1] = "BOT B";
		return playerNames;
	}
	
	public void getBotPlayers(List<Player> playerList) {
		players = playerList;
	}
	
	public int[] makeBestChoiceFromDeck(Player currPlayer, List<HabitatTile> deckTiles, List<WildlifeToken> deckTokens) {
		int[] tileTokenChoice = new int[2];
		Player nextPlayer = null;
		
		for (Player p : players) {
			if (!p.equals(currPlayer)) {
				nextPlayer = p;
			}
		}
		
		int[] tilePreferences = tileBot.chooseStrategy(currPlayer, nextPlayer);
		int[] tokenPreferences = tokenBot.chooseStrategy(currPlayer, nextPlayer);
		
		return tileTokenChoice;
	}

}
