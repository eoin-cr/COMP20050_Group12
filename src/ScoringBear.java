import java.util.ArrayList;

public class ScoringBear {
	private static ArrayList<HabitatTile> visitedTiles = new ArrayList<>();
	
	public static void scoreBear(Player player, String bearOption) {
		switch (bearOption){
		case "B1" -> bearScoringOption1(player);
		case "B2" -> bearScoringOption2(player);
		case "B3" -> bearScoringOption3(player);
		case "B4" -> bearScoringOption4(player);
		default -> throw new IllegalArgumentException("Unexpected value: " + bearOption);
		}
	}
	
	private static void bearScoringOption1(Player player) {
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
		if (pairs == 1) {
			player.addToPlayerScore(4);
		} else if (pairs == 2) {
			player.addToPlayerScore(11);
		} else if (pairs == 3) {
			player.addToPlayerScore(19);
		} else if (pairs >= 4) { // ?? not sure about this case
			player.addToPlayerScore(27);
		}
	}
	
	private static void bearScoringOption2(Player player) {
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
		player.addToPlayerScore(triples*10);
	}
	
	private static void bearScoringOption3(Player player) {
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
		player.addToPlayerScore(singles*2);
		player.addToPlayerScore(doubles*5);
		player.addToPlayerScore(triples*8);
		//bonus of 3 points if minimum of one of each group
		if (singles > 0 && singles == doubles && singles == triples) {
			player.addToPlayerScore(3);
		}
	}
	
	private static void bearScoringOption4(Player player) {
		//score for each group of bears 2-4 in size
		int doubles = 0;
		int triples = 0;
		int quadruples = 0;
		PlayerMap map = player.getMap();
		
		for (HabitatTile tile : map.getTilesInMap()) {
			//if the tile has a bear token on it, check adjacent tiles for bears and find the size of the group of bears
			if (!visitedTiles.contains(tile) && tile.getIsTokenPlaced() && tile.getPlacedToken() == WildlifeToken.Bear) {
				ArrayList<HabitatTile> bearGroup = new ArrayList<>();
				Scoring.findTokenGroupRecursive(bearGroup, WildlifeToken.Bear, tile, map);
				if (bearGroup.size() == 2) {
					doubles++;
				} else if (bearGroup.size() == 3) {
					triples++;
				} else if (bearGroup.size() == 4) {
					quadruples++;
				}
				//add this group of bears to tiles that have been checked for scoring, regardless of size
				visitedTiles.addAll(bearGroup);
			}
		} //all groups found
		
		//scoring based on size of groups
		player.addToPlayerScore(doubles*5);
		player.addToPlayerScore(triples*8);
		player.addToPlayerScore(quadruples*13);
	}

}
