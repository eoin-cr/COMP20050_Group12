package cascadia.scoring;

import cascadia.HabitatTile;
import cascadia.PlayerMap;
import cascadia.WildlifeToken;

public class ScoringFox {

	public static int calculateScore(PlayerMap map, String foxOption) {
		int score;
		switch (foxOption){
		case "F1" -> score = foxScoringOption1(map);
		case "F2" -> score = foxScoringOption2(map);
		case "F3" -> score = foxScoringOption3(map);
		default -> throw new IllegalArgumentException("Unexpected value: " + foxOption);
		}
		//System.out.println(player.getPlayerName() + " Fox Score: " + score); //for testing
		return score;
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