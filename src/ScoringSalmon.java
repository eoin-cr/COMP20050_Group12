import java.util.ArrayList;

public class ScoringSalmon {

	public static int scoreSalmon(PlayerMap map, String salmonOption) {
		int score;
		switch (salmonOption){
		case "S1" -> score = salmonScoringOption1(map);
		case "S2" -> score = salmonScoringOption2(map);
		case "S3" -> score = salmonScoringOption3(map);
		default -> throw new IllegalArgumentException("Unexpected value: " + salmonOption);
		}
//		System.out.println(player.getPlayerName() + " Salmon Score: " + score); //for testing
		return score;
	}

	private static int salmonScoringOption1(PlayerMap map) {
		int score = 0;
		ArrayList<HabitatTile> visitedTiles = new ArrayList<>();

		for (HabitatTile tile : map.getTilesInMap()) {
			//if the tile has a bear token on it, check adjacent tiles for bears and find the size of the group of bears
			if (!visitedTiles.contains(tile) && tile.getIsTokenPlaced() && tile.getPlacedToken() == WildlifeToken.Salmon) {
				ArrayList<HabitatTile> salmonGroup = new ArrayList<>();
				Scoring.findTokenGroupRecursive(salmonGroup, WildlifeToken.Salmon, tile, map);
				if (salmonGroup.size() <= 3) {
					int n = salmonGroup.size();
					score += (1 + (n * (n+1))/2);
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
						if (runSize > 7) {
							score += 26;
						} else if (runSize > 4) {
							score += (runSize*(runSize+1)/2);
						} else if (runSize > 0) {
							score += (1 + (runSize*(runSize+1)/2));
						}
					}
				}
				//add this group of bears to tiles that have been checked for scoring, regardless of size
				visitedTiles.addAll(salmonGroup);
			}
		} //all runs found

		return score;
	}

	private static int salmonScoringOption2(PlayerMap map) {
//		PlayerMap map = player.getMap();
		
		int score = 0;
		return score;
	}

	private static int salmonScoringOption3(PlayerMap map) {
//		PlayerMap map = player.getMap();
		
		int score = 0;
		return score;
	}

}
