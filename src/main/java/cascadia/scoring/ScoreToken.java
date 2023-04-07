package cascadia.scoring;

import cascadia.PlayerMap;
import cascadia.WildlifeToken;

public abstract class ScoreToken {
//	indexing of cards:
//	index 0 stores Bear score card option as a string		(B1,B2,B3,B4)
//	index 1 stores Elk score card option as a string			(E1,E2,E3,E4)
//	index 2 stores Salmon score card option as a string		(S1,S2,S3,S4)
//	index 3 stores Hawk score card option as a string		(H1,H2,H3,H4)
//	index 4 stores Fox score card option as a string			(F1,F2,F3,F4)
	private static final String[] cards = ScoreCards.getScorecards();
	
	
	//overloaded method name: this one used with specified option in testing
    public static int calculateScore(PlayerMap map, Scorable option) {
        return option.score(map);
    }
    
    //mynah - change made
    //overloaded method name: this one used with game option in scoring
    public static int calculateScore(PlayerMap map, WildlifeToken token) {
    	Scorable option;
    	switch (token) {
    	case Bear -> option = ScoringBear.Option.valueOf(cards[0]);
    	case Elk -> option = ScoringElk.Option.valueOf(cards[1]);
    	case Salmon -> option = ScoringSalmon.Option.valueOf(cards[2]);
    	case Hawk -> option = ScoringHawk.Option.valueOf(cards[3]);
    	case Fox -> option = ScoringFox.Option.valueOf(cards[4]);
    	default -> throw new IllegalArgumentException("Unexpected token value to be scored for player: " + token);
    	}
    	return option.score(map);
    }
}
