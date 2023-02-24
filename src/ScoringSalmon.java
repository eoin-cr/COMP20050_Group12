import java.util.ArrayList;

public class ScoringSalmon {
private static ArrayList<HabitatTile> visitedTiles = new ArrayList<>();
	
	public static void scoreSalmon(Player player, String salmonOption) {
		switch (salmonOption){
		case "B1" -> salmonScoringOption1(player);
		case "B2" -> salmonScoringOption2(player);
		case "B3" -> salmonScoringOption3(player);
		case "B4" -> salmonScoringOption4(player);
		default -> throw new IllegalArgumentException("Unexpected value: " + salmonOption);
		}
	}
	
	private static void salmonScoringOption1(Player player) {
		PlayerMap map = player.getMap();
		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getIsTokenPlaced() && tile.getPlacedToken() == WildlifeToken.Salmon) {
				
			}
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
