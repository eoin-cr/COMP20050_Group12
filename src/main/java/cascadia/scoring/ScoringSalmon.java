package cascadia.scoring;

import cascadia.HabitatTile;
import cascadia.PlayerMap;
import cascadia.WildlifeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoringSalmon {

	public static int scoreSalmon(PlayerMap map, String salmonOption) {
		int score;
		switch (salmonOption){
		case "S1" -> score = salmonScoringOption1(map);
		case "S2" -> score = salmonScoringOption2(map);
		case "S3" -> score = salmonScoringOption3(map);
		default -> throw new IllegalArgumentException("Unexpected value: " + salmonOption);
		}
		return score;
	}

	private static int salmonScorer(PlayerMap map, int maxRun, List<Integer> scores) {
		int score = 0;
		ArrayList<HabitatTile> visitedTiles = new ArrayList<>();

		for (HabitatTile tile : map.getTilesInMap()) {
			//if the tile has a bear token on it, check adjacent tiles for bears and find the size of the group of bears
			if (!visitedTiles.contains(tile) && tile.getIsTokenPlaced() && tile.getPlacedToken() == WildlifeToken.Salmon) {
				ArrayList<HabitatTile> salmonGroup = new ArrayList<>();
				Scoring.findTokenGroupRecursive(salmonGroup, WildlifeToken.Salmon, tile, map);
				if (salmonGroup.size() <= 3 && salmonGroup.size() > 0) {
					score += scores.get(salmonGroup.size()-1);
				} else {
					int runSize = 0;
					boolean invalid = false;
					for (HabitatTile groupTile : salmonGroup) {
						if (Scoring.getAdjacentTilesWithTokenMatch(WildlifeToken.Salmon, groupTile, map)
								.size() > 2) {
							invalid = true;
							break;
						}
						runSize++;
					}
					if (!invalid) {
						if (runSize > maxRun) {
							score += scores.get(scores.size() - 1);
						} else if (runSize > 0) {
							score += scores.get(runSize-1);
						}
					}
				}
				//add this group of bears to tiles that have been checked for scoring, regardless of size
				visitedTiles.addAll(salmonGroup);
			}
		} //all runs found
		return score;
	}

	private static int salmonScoringOption1(PlayerMap map) {
		List<Integer> scores = new ArrayList<>();
		Collections.addAll(scores, 2, 4, 7, 11, 15, 20, 26);
		return salmonScorer(map, 7, scores);
	}

	private static int salmonScoringOption2(PlayerMap map) {
		List<Integer> scores = new ArrayList<>();
		Collections.addAll(scores, 2, 4, 8, 12);
		return salmonScorer(map, 4, scores);
	}

	private static int salmonScoringOption3(PlayerMap map) {
		List<Integer> scores = new ArrayList<>();
		Collections.addAll(scores, 2, 4, 9, 11, 17);
		return salmonScorer(map, 5, scores);
	}
}
