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
		int hawkCount=0;
		int score = 0;

		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getPlacedToken() == WildlifeToken.Hawk) {
				if(Scoring.getAdjacentTilesWithTokenMatch(WildlifeToken.Hawk,tile,map).size() == 0){
					hawkCount++;
				}
			}
		}
		switch (hawkCount){
			case 0-> {}
			case 1-> score = 2;
			case 2-> score = 5;
			case 3-> score = 8;
			case 4-> score = 11;
			case 5-> score = 14;
			case 6-> score = 18;
			case 7-> score = 22;
			default -> score = 26;
		}
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
