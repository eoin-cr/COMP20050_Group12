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
		PlayerMap map = player.getMap();
		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getIsTokenPlaced() && tile.getPlacedToken() == WildlifeToken.Bear) {
				
			}
		}
		
	}
	private static void bearScoringOption2(Player player) {
		PlayerMap map = player.getMap();
		
	}
	private static void bearScoringOption3(Player player) {
		PlayerMap map = player.getMap();
		
	}
	private static void bearScoringOption4(Player player) {
		PlayerMap map = player.getMap();
		
	}

}
