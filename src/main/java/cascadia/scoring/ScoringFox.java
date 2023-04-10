/*
	COMP20050 Group 12
	Eoin Creavin – Student ID: 21390601
	eoin.creavin@ucdconnect.ie
	GitHub ID: eoin-cr

	Mynah Bhattacharyya – Student ID: 21201085
	malhar.bhattacharyya@ucdconnect.ie
	GitHub ID: mynah-bird

	Ben McDowell – Student ID: 21495144
	ben.mcdowell@ucdconnect.ie
	GitHub ID: Benmc1
 */

package cascadia.scoring;

import cascadia.Constants;
import cascadia.HabitatTile;
import cascadia.PlayerMap;
import cascadia.WildlifeToken;

public class ScoringFox extends ScoreToken {
	/**
	 * Contains the scoring method for the 3 types of fox scoring, F1, F2,
	 * and F3.
	 */
	public enum Option implements Scorable {
		F1 { public int score(PlayerMap map) {
				return foxScoringOption1(map);
			}
		},
		F2 { public int score(PlayerMap map) {
				return foxScoringOption2(map);
			}
		},
		F3 { public int score(PlayerMap map) {
				return foxScoringOption3(map);
			}
		};

		public abstract int score(PlayerMap map);
	}

	private static int foxScoringOption1(PlayerMap map) {
		int score = 0;
		
		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getPlacedToken() == WildlifeToken.Fox) {
				for (WildlifeToken animal : WildlifeToken.values()) {
					if (!Scoring.getAdjacentTilesWithTokenMatch(animal, tile, map).isEmpty()) {
						score++;
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
				score = calculatePairs(tile, map, score);
			}
		}
		return score;
	}

	private static int calculatePairs(HabitatTile tile, PlayerMap map, int score) {
		int pairs = 0;
		WildlifeToken[] adjTokens = Scoring.getAdjacentTokens(tile, map);
		int[] storeWildlifeCounts = new int[5];

		for (WildlifeToken token : adjTokens) {
			if (token != null && token != WildlifeToken.Fox) {
				storeWildlifeCounts[token.ordinal()]++;
			}
		}

		for (int count : storeWildlifeCounts) {
			if (count >= 2) {
				pairs++;
			}
		}
		return calculateScoreFromPairs(pairs, score);
	}

	private static int calculateScoreFromPairs(int pairs, int score) {
		switch (pairs) {
			case 0 -> { }
			case 1 -> score += 3;
			case 2 -> score += 5;
			case 3 -> score += 7;
			default -> throw new IllegalArgumentException("Invalid no. of adjacent pairs near fox: "
					+ pairs);
		}
		return score;
	}

	private static int foxScoringOption3(PlayerMap map) {
		int score = 0;

		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getPlacedToken() == WildlifeToken.Fox) {
				WildlifeToken[] adjTokens = Scoring.getAdjacentTokens(tile, map);
				int[] storeWildlifeCounts = new int[Constants.NUM_TOKEN_TYPES];
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
