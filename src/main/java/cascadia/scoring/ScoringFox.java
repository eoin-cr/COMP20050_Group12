package cascadia.scoring;

import cascadia.HabitatTile;
import cascadia.PlayerMap;
import cascadia.WildlifeToken;

public class ScoringFox extends ScoreToken {
	public enum Option implements Scorable {
		F1 {public int score(PlayerMap map){
			return foxScoringOption1(map);
		}},
		F2 {public int score(PlayerMap map) {
			return foxScoringOption2(map);
		}},
		F3 {public int score(PlayerMap map) {
			return foxScoringOption3(map);
		}};
		public abstract int score(PlayerMap map);
	}

	private static int foxScoringOption1(PlayerMap map) {
		int score = 0;
		
		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getPlacedToken() == WildlifeToken.Fox) {
				for (WildlifeToken animal: WildlifeToken.values()) {
					if	(!Scoring.getAdjacentTilesWithTokenMatch(animal,tile,map).isEmpty()){
						score++;
					}
				}
			}
		}
		return score;
	}
	
	private static int foxScoringOption2(PlayerMap map) {
		int score = 0;
		int pairs;

		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getPlacedToken() == WildlifeToken.Fox) {
				pairs = 0;
				WildlifeToken[] adjTokens = Scoring.getAdjacentTokens(tile,map);
				int[] storeWildlifeCounts = new int[5];
				
				for (WildlifeToken token : adjTokens) {
					if (token != null && token != WildlifeToken.Fox) {
						storeWildlifeCounts[token.ordinal()]++;
					}
				}
				
				for (int count : storeWildlifeCounts) {
					if (count >= 2) pairs++;
				}
				
				switch (pairs){
				case 0 -> score += 0;
				case 1 -> score += 3;
				case 2 -> score += 5;
				case 3 -> score += 7;
				default -> throw new IllegalArgumentException("Invalid no. of adjacent pairs near fox: " + pairs);
				}
			}
		}
		
		return score;
	}
	
	private static int foxScoringOption3(PlayerMap map) {
		int score = 0;

		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getPlacedToken() == WildlifeToken.Fox) {
				WildlifeToken[] adjTokens = Scoring.getAdjacentTokens(tile,map);
				int[] storeWildlifeCounts = new int[5];
				for (WildlifeToken token : adjTokens) {
					if (token != null && token != WildlifeToken.Fox) {
						storeWildlifeCounts[token.ordinal()]++;
					}
				}
				int max = 0;
				for (int count : storeWildlifeCounts) {
					max = Math.max(max, count);
				}
				score += max;
			}
		}
		return score;
	}
	
}
