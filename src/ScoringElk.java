import java.util.ArrayList;

public class ScoringElk {
	private static ArrayList<HabitatTile> visitedTiles = new ArrayList<>();
	
	public static void scoreElk(Player player, String elkOption) {
		switch (elkOption){
		case "E1" -> elkScoringOption1(player);
		case "E2" -> elkScoringOption2(player);
		case "E3" -> elkScoringOption3(player);
		case "E4" -> elkScoringOption4(player);
		default -> throw new IllegalArgumentException("Unexpected value: " + elkOption);
		}
	}
	
	private static void elkScoringOption1(Player player) {
		PlayerMap map = player.getMap();
		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getIsTokenPlaced() && tile.getPlacedToken() == WildlifeToken.Elk) {
				
			}
		}
		
	}
	private static void elkScoringOption2(Player player) {
		PlayerMap map = player.getMap();
		
	}
	private static void elkScoringOption3(Player player) {
		PlayerMap map = player.getMap();
		
	}
	private static void elkScoringOption4(Player player) {
		PlayerMap map = player.getMap();
		
	}
}
