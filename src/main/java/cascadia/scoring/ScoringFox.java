package main.java.cascadia.scoring;

import main.java.cascadia.HabitatTile;
import main.java.cascadia.PlayerMap;
import main.java.cascadia.WildlifeToken;

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
				WildlifeToken temp= null;
				int tempRun=0;
				for (WildlifeToken animal: adjTokens) {
					if(temp == null) temp = animal;
					else if(temp != animal && animal != null || temp.equals(WildlifeToken.Fox)){
						tempRun = 0;
						break;
					}
					tempRun++;
				}
				score += tempRun;
			}
		}
		return score;
	}
}
