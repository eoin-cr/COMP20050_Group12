package cascadia.scoring;

import cascadia.HabitatTile;
import cascadia.PlayerMap;
import cascadia.WildlifeToken;

public class ScoringFox {
	public enum Option {
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

	public static int calculateScore(PlayerMap map, Option option) {
		return option.score(map);
	}

	private static int foxScoringOption1(PlayerMap map) {
		int score = 0;
		
		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getPlacedToken() == WildlifeToken.Fox) {
				for (WildlifeToken animal: WildlifeToken.values()) {
					if	(!Scoring.getAdjacentTilesWithTokenMatch(animal,tile,map).isEmpty()){
						score ++;
					}
				}
			}
		}
		return score;
	}
	
	private static int foxScoringOption2(PlayerMap map) {
		int score = 0;

		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getPlacedToken() == WildlifeToken.Fox) {
				int pairs = 0;
				for (WildlifeToken animal : WildlifeToken.values()) {
					if (animal != WildlifeToken.Fox) {
						if (Scoring.getAdjacentTilesWithTokenMatch(animal, tile, map).size() > 2) pairs++;
					}
				}
				if (pairs!= 0) score += (pairs*2)+1;
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
