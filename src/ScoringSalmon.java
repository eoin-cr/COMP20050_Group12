import java.util.ArrayList;

public class ScoringSalmon {
private static ArrayList<HabitatTile> visitedTiles = new ArrayList<>();
	
	public static void scoreSalmon(Player player, String salmonOption) {
		int score = 0;
		switch (salmonOption){
		case "S1" -> score = salmonScoringOption1(player);
		case "S2" -> score = salmonScoringOption2(player);
		case "S3" -> score = salmonScoringOption3(player);
		default -> throw new IllegalArgumentException("Unexpected value: " + salmonOption);
		}
		System.out.println(player.getPlayerName() + " Salmon Score: " + score); //for testing
	}
	
	private static int salmonScoringOption1(Player player) {
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

		int score = 0;
		return score;
	}

	private static int salmonScoringOption2(Player player) {
		PlayerMap map = player.getMap();
		
		int score = 0;
		return score;
	}

	private static int salmonScoringOption3(Player player) {
		PlayerMap map = player.getMap();
		
		int score = 0;
		return score;
	}

}
