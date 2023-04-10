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

import cascadia.HabitatTile;
import cascadia.PlayerMap;
import cascadia.WildlifeToken;
import java.util.ArrayList;
import java.util.List;

public class ScoringBear extends ScoreToken {
	private static final List<HabitatTile> visitedTiles = new ArrayList<>();

	/**
	 * Contains the scoring method for the 3 types of bear scoring, B1, B2,
	 * and B3.
	 */
	public enum Option implements Scorable {
		B1 { public int score(PlayerMap map) {
				return bearScoringOption1(map);
			}
		},
		B2 { public int score(PlayerMap map) {
				return bearScoringOption2(map);
			}
		},
		B3 { public int score(PlayerMap map) {
				return bearScoringOption3(map);
			}
		};
		public abstract int score(PlayerMap map);
	}

	private static int bearScoringOption1(PlayerMap map) {
		//score for pairs of bears
		visitedTiles.clear();
		int pairs = 0;
		int MAX_PAIRS_SCORED = 4;
		int[] bearScores = new int[]{0, 4, 11, 19, 27};
		List<HabitatTile> bearGroup = new ArrayList<>();

		for (HabitatTile tile : map.getTilesInMap()) {
			if (!visitedTiles.contains(tile) && tile.getPlacedToken() == WildlifeToken.Bear) {
				bearGroup.clear();
				Scoring.findTokenGroup(bearGroup, WildlifeToken.Bear, tile, map);
				if (bearGroup.size() == 2) {
					pairs++;
				}
				//add this group of bears to tiles that have been checked for scoring,
				// regardless of size
				visitedTiles.addAll(bearGroup);
			}
		} //all pairs now found
		
		if (pairs < 0) {
			return 0;
		} else if (pairs > MAX_PAIRS_SCORED) {
			return bearScores[MAX_PAIRS_SCORED];
		} else {
			return bearScores[pairs];
		}
	}
	
	private static int bearScoringOption2(PlayerMap map) {
		//score for each group of 3 bears
		visitedTiles.clear();
		int triples = 0;
		List<HabitatTile> bearGroup = new ArrayList<>();

		for (HabitatTile tile : map.getTilesInMap()) {
			if (!visitedTiles.contains(tile) && tile.getPlacedToken() == WildlifeToken.Bear) {
				bearGroup.clear();
				Scoring.findTokenGroup(bearGroup, WildlifeToken.Bear, tile, map);
				if (bearGroup.size() == 3) {
					triples++;
				}
				//add this group of bears to tiles that have been checked for scoring,
				// regardless of size
				visitedTiles.addAll(bearGroup);
			}
		} //all triples now found
		return triples * 10;
	}

	private static int bearScoringOption3(PlayerMap map) {
		//score for each group of bears 1-3 in size
		visitedTiles.clear();
		int singles = 0;
		int doubles = 0;
		int triples = 0;

		List<HabitatTile> bearGroup = new ArrayList<>();

		for (HabitatTile tile : map.getTilesInMap()) {
			if (!visitedTiles.contains(tile) && tile.getPlacedToken() == WildlifeToken.Bear) {
				bearGroup.clear();
				Scoring.findTokenGroup(bearGroup, WildlifeToken.Bear, tile, map);
				if (bearGroup.size() == 1) {
					singles++;
				} else if (bearGroup.size() == 2) {
					doubles++;
				} else if (bearGroup.size() == 3) {
					triples++;
				}
				//add this group of bears to tiles that have been checked for scoring,
				// regardless of size
				visitedTiles.addAll(bearGroup);
			}
		} //all groups found
		
		int score = (singles * 2) + (doubles * 5) + (triples * 8);
		//bonus of 3 points if minimum of one of each group
		if (singles > 1 && doubles > 1 && triples > 1) {
			score += 3;
		}
		return score;
	}
}
