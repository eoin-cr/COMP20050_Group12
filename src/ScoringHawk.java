import java.util.ArrayList;

public class ScoringHawk {
private static ArrayList<HabitatTile> visitedTiles = new ArrayList<>();
	
	public static void scoreHawk(Player player, String hawkOption) {
		switch (hawkOption){
		case "H1" -> hawkScoringOption1(player);
		case "H2" -> hawkScoringOption2(player);
		case "H3" -> hawkScoringOption3(player);
		case "H4" -> hawkScoringOption4(player);
		default -> throw new IllegalArgumentException("Unexpected value: " + hawkOption);
		}
	}
	
	private static void hawkScoringOption1(Player player) {
		PlayerMap map = player.getMap();
		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getIsTokenPlaced() && tile.getPlacedToken() == WildlifeToken.Hawk) {
				
			}
		}
		
	}
	private static void hawkScoringOption2(Player player) {
		PlayerMap map = player.getMap();
		
	}
	private static void hawkScoringOption3(Player player) {
		PlayerMap map = player.getMap();
		
	}
	private static void hawkScoringOption4(Player player) {
		PlayerMap map = player.getMap();
		
	}
}
