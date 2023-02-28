import java.util.ArrayList;

public class ScoringSalmon {
private static ArrayList<HabitatTile> visitedTiles = new ArrayList<>();
	
	public static void scoreSalmon(Player player, String salmonOption) {
		int score;
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
		int score = 0;
		int[] points = {2,5,8,11,14,18,22,26};
		ArrayList<HabitatTile> currentRun = new ArrayList<>();

		for (HabitatTile tile : map.getTilesInMap()) {
			currentRun.clear();
			if (tile.getPlacedToken() == WildlifeToken.Salmon && !visitedTiles.contains(tile) ) {
				Scoring.findTokenGroupRecursive(currentRun,WildlifeToken.Salmon,tile,map);
				visitedTiles.addAll(currentRun);

				score += currentRun.size()<8 ? points[currentRun.size()-1] : 26;
			}
		}
		return score;
	}
	
	private static int salmonScoringOption2(Player player) {
		PlayerMap map = player.getMap();
		int score = 0;

		ArrayList<HabitatTile> currentRun = new ArrayList<>();

		for (HabitatTile tile : map.getTilesInMap()) {
			currentRun.clear();
			if (tile.getPlacedToken() == WildlifeToken.Salmon && !visitedTiles.contains(tile) ) {
				Scoring.findTokenGroupRecursive(currentRun,WildlifeToken.Salmon,tile,map);
				visitedTiles.addAll(currentRun);

				score += currentRun.size()<4 ? currentRun.size()*2 : 12;
			}
		}
		return score;
	}
	
	private static int salmonScoringOption3(Player player) {
		PlayerMap map = player.getMap();
		int score = 0;
		int[] points = {2,4,9,11};
		ArrayList<HabitatTile> currentRun = new ArrayList<>();

		for (HabitatTile tile : map.getTilesInMap()) {
			currentRun.clear();
			if (tile.getPlacedToken() == WildlifeToken.Salmon && !visitedTiles.contains(tile) ) {
				Scoring.findTokenGroupRecursive(currentRun,WildlifeToken.Salmon,tile,map);
				visitedTiles.addAll(currentRun);

				score += currentRun.size()<5 ? points[currentRun.size()-1] : 17;
			}
		}
		return score;
	}
	
}
