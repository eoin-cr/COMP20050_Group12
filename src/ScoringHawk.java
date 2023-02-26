import java.util.ArrayList;

public class ScoringHawk {
private static ArrayList<HabitatTile> visitedTiles = new ArrayList<>();
	
	public static void scoreHawk(Player player, String hawkOption) {
		int score = 0;
		switch (hawkOption){
		case "H1" -> score = hawkScoringOption1(player);
		case "H2" -> score = hawkScoringOption2(player);
		case "H3" -> score = hawkScoringOption3(player);
		default -> throw new IllegalArgumentException("Unexpected value: " + hawkOption);
		}
		System.out.println(player.getPlayerName() + " Hawk Score: " + score); //for testing
	}
	
	private static int hawkScoringOption1(Player player) {
		PlayerMap map = player.getMap();
		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getIsTokenPlaced() && tile.getPlacedToken() == WildlifeToken.Hawk) {
				
			}
		}
		int score = 0;
		return score;
	}
	private static int hawkScoringOption2(Player player) {
		PlayerMap map = player.getMap();
		
		int score = 0;
		return score;
	}
	private static int hawkScoringOption3(Player player) {
		PlayerMap map = player.getMap();
		
		int score = 0;
		return score;
	}
}
