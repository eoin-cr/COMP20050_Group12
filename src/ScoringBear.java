
import java.util.ArrayList;

public class ScoringBear {
	private static ArrayList<HabitatTile> visitedTiles = new ArrayList<>();
	
	public static void scoreBear(Player player, String bearOption) {
		int score = 0;
		switch (bearOption){
		case "B1" -> score = bearScoringOption1(player);
		case "B2" -> score = bearScoringOption2(player);
		case "B3" -> score = bearScoringOption3(player);
		default -> throw new IllegalArgumentException("Unexpected value: " + bearOption);
		}
		System.out.println(player.getPlayerName() + " Bear Score: " + score); //for testing
	}
	
	private static int bearScoringOption1(Player player) {
		//score for pairs of bears
		int pairs = 0;
		PlayerMap map = player.getMap();
		
		for (HabitatTile tile : map.getTilesInMap()) {
			//if the tile has a bear token on it, check adjacent tiles for bears and find the size of the group of bears
			if (!visitedTiles.contains(tile) && tile.getIsTokenPlaced() && tile.getPlacedToken() == WildlifeToken.Bear) {
				ArrayList<HabitatTile> bearGroup = new ArrayList<>();
				Scoring.findTokenGroupRecursive(bearGroup, WildlifeToken.Bear, tile, map);
				if (bearGroup.size() == 2) {
					pairs++;
				}
				//add this group of bears to tiles that have been checked for scoring, regardless of size
				visitedTiles.addAll(bearGroup);
			}
		} //all pairs found
		
		//scoring based on pairs
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
		player.addToPlayerScore(score);
		return score;
	}
	
	private static int bearScoringOption2(Player player) {
		//score for each group of 3 bears
		int triples = 0;
		PlayerMap map = player.getMap();
		
		for (HabitatTile tile : map.getTilesInMap()) {
			//if the tile has a bear token on it, check adjacent tiles for bears and find the size of the group of bears
			if (!visitedTiles.contains(tile) && tile.getIsTokenPlaced() && tile.getPlacedToken() == WildlifeToken.Bear) {
				ArrayList<HabitatTile> bearGroup = new ArrayList<>();
				Scoring.findTokenGroupRecursive(bearGroup, WildlifeToken.Bear, tile, map);
				if (bearGroup.size() == 3) {
					triples++;
				}
				//add this group of bears to tiles that have been checked for scoring, regardless of size
				visitedTiles.addAll(bearGroup);
			}
		} //all triples found
		
		//scoring based on triples
		int score = triples*10;
		player.addToPlayerScore(score);
		return score;
	}
	
	private static int bearScoringOption3(Player player) {
		//score for each group of bears 1-3 in size
		int singles = 0;
		int doubles = 0;
		int triples = 0;
		PlayerMap map = player.getMap();
		
		for (HabitatTile tile : map.getTilesInMap()) {
			//if the tile has a bear token on it, check adjacent tiles for bears and find the size of the group of bears
			if (!visitedTiles.contains(tile) && tile.getIsTokenPlaced() && tile.getPlacedToken() == WildlifeToken.Bear) {
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
		
		//scoring based on size of groups + bonus for one of all sizes
		int score = (singles*2) + (doubles*5) + (triples*8);
		//bonus of 3 points if minimum of one of each group
		if (singles > 0 && singles == doubles && singles == triples) {
			score += 3;
		}
		player.addToPlayerScore(score);
		return score;
	}

}
