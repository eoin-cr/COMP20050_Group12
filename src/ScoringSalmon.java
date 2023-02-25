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
			if (tile.getIsTokenPlaced() && tile.getPlacedToken() == WildlifeToken.Salmon) {
				
			}
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
