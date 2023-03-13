package cascadia.scoring;

import cascadia.HabitatTile;
import cascadia.PlayerMap;
import cascadia.WildlifeToken;

import java.util.ArrayList;

public class ScoringBear {
	private static final ArrayList<HabitatTile> visitedTiles = new ArrayList<>();
//	enum BearOptions{B1, B2, B3}
	public enum Option {
		B1 {public int score(PlayerMap map){
			return bearScoringOption1(map);
		}},
		B2 {public int score(PlayerMap map) {
			return bearScoringOption2(map);
		}},
		B3 {public int score(PlayerMap map) {
			return bearScoringOption3(map);
		}};
		public abstract int score(PlayerMap map);
	}

	public static int calculateScore(PlayerMap map, Option option) {
		return option.score(map);
	}

	private static int bearScoringOption1(PlayerMap map) {
		//score for pairs of bears
		int pairs = 0;

		for (HabitatTile tile : map.getTilesInMap()) {
			if (!visitedTiles.contains(tile) && tile.getPlacedToken() == WildlifeToken.Bear) {
				ArrayList<HabitatTile> bearGroup = new ArrayList<>();
				Scoring.findTokenGroupRecursive(bearGroup, WildlifeToken.Bear, tile, map);
				if (bearGroup.size() == 2) {
					pairs++;
				}
				//add this group of bears to tiles that have been checked for scoring, regardless of size
				visitedTiles.addAll(bearGroup);
			}
		} //all pairs now found
		
		int score = 0;
		if (pairs == 1) {
			score = 4;
		} else if (pairs == 2) {
			score = 11;
		} else if (pairs == 3) {
			score = 19;
		} else if (pairs >= 4) { // ?? not sure about this case
			score = 27;
		}
		return score;
	}
	
	private static int bearScoringOption2(PlayerMap map) {
		//score for each group of 3 bears
		int triples = 0;

		for (HabitatTile tile : map.getTilesInMap()) {
			if (!visitedTiles.contains(tile) && tile.getPlacedToken() == WildlifeToken.Bear) {
				ArrayList<HabitatTile> bearGroup = new ArrayList<>();
				Scoring.findTokenGroupRecursive(bearGroup, WildlifeToken.Bear, tile, map);
				if (bearGroup.size() == 3) {
					triples++;
				}
				//add this group of bears to tiles that have been checked for scoring, regardless of size
				visitedTiles.addAll(bearGroup);
			}
		} //all triples now found
		return triples*10;
	}
	
	private static int bearScoringOption3(PlayerMap map) {
		//score for each group of bears 1-3 in size
		int singles = 0;
		int doubles = 0;
		int triples = 0;

		for (HabitatTile tile : map.getTilesInMap()) {
			if (!visitedTiles.contains(tile) && tile.getPlacedToken() == WildlifeToken.Bear) {
				ArrayList<HabitatTile> bearGroup = new ArrayList<>();
				Scoring.findTokenGroupRecursive(bearGroup, WildlifeToken.Bear, tile, map);
				if (bearGroup.size() == 1) {
					singles++;
				} else if (bearGroup.size() == 2) {
					doubles++;
				} else if (bearGroup.size() == 3) {
					triples++;
				}
				//add this group of bears to tiles that have been checked for scoring, regardless of size
				visitedTiles.addAll(bearGroup);
			}
		} //all groups found
		
		int score = (singles*2) + (doubles*5) + (triples*8);
		//bonus of 3 points if minimum of one of each group
		if (singles > 1 && doubles > 1 && triples > 1) {
			score += 3;
		}
		return score;
	}
}
