package cascadia.scoring;

import cascadia.HabitatTile;
import cascadia.Player;
import cascadia.PlayerMap;
import cascadia.WildlifeToken;

import java.util.ArrayList;

public class ScoringBear {
	private static final ArrayList<HabitatTile> visitedTiles = new ArrayList<>();
	
	public static int scoreBear(Player player, String bearOption) {
		int score;
		switch (bearOption){
		case "B1" -> score = bearScoringOption1(player);
		case "B2" -> score = bearScoringOption2(player);
		case "B3" -> score = bearScoringOption3(player);
		default -> throw new IllegalArgumentException("Unexpected value: " + bearOption);
		}
		//System.out.println(player.getPlayerName() + " Bear Score: " + score); //for testing
		return score;
	}
	
	private static int bearScoringOption1(Player player) {
		//score for pairs of bears
		int pairs = 0;
		PlayerMap map = player.getMap();
		
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
	
	private static int bearScoringOption2(Player player) {
		//score for each group of 3 bears
		int triples = 0;
		PlayerMap map = player.getMap();
		
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
	
	private static int bearScoringOption3(Player player) {
		//score for each group of bears 1-3 in size
		int singles = 0;
		int doubles = 0;
		int triples = 0;
		PlayerMap map = player.getMap();
		
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
