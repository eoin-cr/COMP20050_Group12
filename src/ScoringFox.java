import java.util.ArrayList;

public class ScoringFox {
	private static ArrayList<HabitatTile> visitedTiles = new ArrayList<>();
	
	public static void scoreFox(Player player, String foxOption) {
		int score = 0;
		switch (foxOption){
		case "F1" -> score = foxScoringOption1(player);
		case "F2" -> score = foxScoringOption2(player);
		case "F3" -> score = foxScoringOption3(player);
		default -> throw new IllegalArgumentException("Unexpected value: " + foxOption);
		}
		System.out.println(player.getPlayerName() + " Fox Score: " + score); //for testing
	}
	
	private static int foxScoringOption1(Player player) {
		PlayerMap map = player.getMap();
		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getIsTokenPlaced() && tile.getPlacedToken() == WildlifeToken.Fox) {
				
			}
		}
		int score = 0;
		return score;
	}
	
	private static int foxScoringOption2(Player player) {
		PlayerMap map = player.getMap();
		
		int score = 0;
		return score;
	}
	
	private static int foxScoringOption3(Player player) {
		PlayerMap map = player.getMap();
		
		int score = 0;
		return score;
	}
}
