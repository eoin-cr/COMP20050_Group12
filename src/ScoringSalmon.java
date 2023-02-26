import java.util.ArrayList;

public class ScoringSalmon {
private static ArrayList<HabitatTile> visitedTiles = new ArrayList<>();
	
	public static void scoreSalmon(Player player, String salmonOption) {
		switch (salmonOption){
		case "S1" -> salmonScoringOption1(player);
		case "S2" -> salmonScoringOption2(player);
		case "S3" -> salmonScoringOption3(player);
		case "S4" -> salmonScoringOption4(player);
		default -> throw new IllegalArgumentException("Unexpected value: " + salmonOption);
		}
	}
	
	private static void salmonScoringOption1(Player player) {
//		PlayerMap map = player.getMap();
//		for (HabitatTile tile : map.getTilesInMap()) {
//			if (tile.getIsTokenPlaced() && tile.getPlacedToken() == WildlifeToken.Salmon) {
//
//			}
//		}

		//score for runs of bears
		int runs = 0;
		PlayerMap map = player.getMap();

		for (HabitatTile tile : map.getTilesInMap()) {
			//if the tile has a bear token on it, check adjacent tiles for bears and find the size of the group of bears
			if (!visitedTiles.contains(tile) && tile.getIsTokenPlaced() && tile.getPlacedToken() == WildlifeToken.Bear) {
				ArrayList<HabitatTile> bearGroup = new ArrayList<>();
				Scoring.findTokenGroupRecursive(bearGroup, WildlifeToken.Bear, tile, map);
				if (bearGroup.size() == 2) {
					runs++;
				}
				//add this group of bears to tiles that have been checked for scoring, regardless of size
				visitedTiles.addAll(bearGroup);
			}
		} //all runs found

		//scoring based on runs
		if (runs == 1) {
			player.addToPlayerScore(4);
		} else if (runs == 2) {
			player.addToPlayerScore(11);
		} else if (runs == 3) {
			player.addToPlayerScore(19);
		} else if (runs >= 4) { // ?? not sure about this case
			player.addToPlayerScore(27);
		}

	}
	private static void salmonScoringOption2(Player player) {
		PlayerMap map = player.getMap();
		
	}
	private static void salmonScoringOption3(Player player) {
		PlayerMap map = player.getMap();
		
	}
	private static void salmonScoringOption4(Player player) {
		PlayerMap map = player.getMap();
		
	}
}
