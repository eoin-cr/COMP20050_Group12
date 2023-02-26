import java.util.ArrayList;

public class ScoringElk {
	private static ArrayList<HabitatTile> visitedTiles = new ArrayList<>();
	
	public static void scoreElk(Player player, String elkOption) {
		int score = 0;
		switch (elkOption){
		case "E1" -> score = elkScoringOption1(player);
		case "E2" -> score = elkScoringOption2(player);
		case "E3" -> score = elkScoringOption3(player);
		default -> throw new IllegalArgumentException("Unexpected value: " + elkOption);
		}
		System.out.println(player.getPlayerName() + " Elk Score: " + score); //for testing
	}
	
	private static int elkScoringOption1(Player player) {
		PlayerMap map = player.getMap();
		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getIsTokenPlaced() && tile.getPlacedToken() == WildlifeToken.Elk) {
				
			}
		}
		int score = 0;
		return score;
	}
	
	private static int elkScoringOption2(Player player) {
		PlayerMap map = player.getMap();
		
		int score = 0;
		return score;
	}
	
	private static int elkScoringOption3(Player player) {
		PlayerMap map = player.getMap();
		
		int score = 0;
		return score;
	}
}
