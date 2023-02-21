import java.util.ArrayList;

public class ScoringFox {
	private static ArrayList<HabitatTile> visitedTiles = new ArrayList<>();
	
	public static void scoreFox(Player player, String foxOption) {
		switch (foxOption){
		case "B1" -> foxScoringOption1(player);
		case "B2" -> foxScoringOption2(player);
		case "B3" -> foxScoringOption3(player);
		case "B4" -> foxScoringOption4(player);
		default -> throw new IllegalArgumentException("Unexpected value: " + foxOption);
		}
	}
	
	private static void foxScoringOption1(Player player) {
		PlayerMap map = player.getMap();
		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getIsTokenPlaced() && tile.getPlacedToken() == WildlifeToken.Fox) {
				
			}
		}
		
	}
	private static void foxScoringOption2(Player player) {
		PlayerMap map = player.getMap();
		
	}
	private static void foxScoringOption3(Player player) {
		PlayerMap map = player.getMap();
		
	}
	private static void foxScoringOption4(Player player) {
		PlayerMap map = player.getMap();
		
	}
}
