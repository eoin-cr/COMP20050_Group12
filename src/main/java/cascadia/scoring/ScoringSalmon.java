package cascadia.scoring;

import cascadia.HabitatTile;
import cascadia.PlayerMap;
import cascadia.WildlifeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoringSalmon extends ScoreToken {
	public enum Option implements Scorable {
		S1 {public int score(PlayerMap map){
				return salmonScoringOption1(map);
		}},
		S2 {public int score(PlayerMap map) {
			return salmonScoringOption2(map);
		}},
		S3 {public int score(PlayerMap map) {
			return salmonScoringOption3(map);
		}};
		public abstract int score(PlayerMap map);
	}

	private static int salmonScorer(PlayerMap map, int maxRun, List<Integer> scores) {
		int score = 0;
		ArrayList<HabitatTile> visitedTiles = new ArrayList<>();

		for (HabitatTile tile : map.getTilesInMap()) {
			// if the tile has a salmon token on it, check adjacent tiles for salmon and find the size of the group of
			// salmon
			if (!visitedTiles.contains(tile) && tile.getIsTokenPlaced() && tile.getPlacedToken() == WildlifeToken.Salmon) {
				ArrayList<HabitatTile> salmonGroup = new ArrayList<>();
				Scoring.findTokenGroupRecursive(salmonGroup, WildlifeToken.Salmon, tile, map);
				score = calculateRunScore(salmonGroup, map, maxRun, score, scores);

				// add this group of bears to tiles that have been checked for scoring, regardless of size
				visitedTiles.addAll(salmonGroup);
			}
		} // all runs found
		return score;
	}

	private static int calculateRunScore(ArrayList<HabitatTile> salmonGroup, PlayerMap map, int maxRun, int score,
										 List<Integer> scores) {
		int runSize = 0;
		boolean invalid = false;
		// calculate the size of the group, checking whether it's invalid as we go
		for (HabitatTile groupTile : salmonGroup) {
			if (Scoring.getAdjacentTilesWithTokenMatch(WildlifeToken.Salmon, groupTile, map)
					.size() > 2) {
				invalid = true;
				break;
			}
			runSize++;
		}
		// convert the amount of runs to the actual score
		if (!invalid) {
			if (runSize > maxRun) {
				score += scores.get(scores.size() - 1);
			} else if (runSize > 0) {
				score += scores.get(runSize-1);
			}
		}
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
