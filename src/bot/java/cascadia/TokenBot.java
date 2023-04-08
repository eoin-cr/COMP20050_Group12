package cascadia;
import java.util.List;
import java.util.Random;

import cascadia.*;

public class TokenBot {
	public static final int NUM_TOKEN_STRATS = 2;
	
	public int[] chooseStrategy(Player player, Player nextPlayer) {
		int[] preferences = new int[4];
		List<WildlifeToken> deckTokens = CurrentDeck.getDeckTokens();
		
		Random rand = new Random();
		int strategyChoice = rand.nextInt(NUM_TOKEN_STRATS);
		
		switch (strategyChoice){
		case 0 -> preferences = constructiveTokenStrat(deckTokens, player);
		case 1 -> preferences = destructiveTokenStrat(deckTokens, nextPlayer);
		default -> throw new IllegalArgumentException("Unexpected value: " + strategyChoice);
		}
		
		return preferences;
	}
	
	private int[] constructiveTokenStrat(List<WildlifeToken> deckTokens, Player player) {
		int[] preferences = new int[4];
		
		return preferences;
	}
	
	private int[] destructiveTokenStrat(List<WildlifeToken> deckTokens, Player nextPlayer) {
		int[] preferences = new int[4];
		
		return preferences;
	}

}
