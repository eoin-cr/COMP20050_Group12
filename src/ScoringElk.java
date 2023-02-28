import java.util.ArrayList;
import java.util.List;

public class ScoringElk {

	public static void scoreElk(Player player, String elkOption) {
		int score;
		switch (elkOption){
		case "E1" -> score = elkScoringOption1(player);
		case "E2" -> score = elkScoringOption2(player);
		case "E3" -> score = elkScoringOption3(player);
		default -> throw new IllegalArgumentException("Unexpected value: " + elkOption);
		}
		System.out.println(player.getPlayerName() + " Elk Score: " + score); //for testing
	}

	//pretty sure the max length on the straight line is 4 doesn't seem to be any more points for anything longer
	private static int elkScoringOption1(Player player) {

		int score = 0;
		ArrayList<HabitatTile> usedTiles = new ArrayList<>();
		ArrayList<HabitatTile> currLine = new ArrayList<>();
		PlayerMap map = player.getMap();

		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getPlacedToken() == WildlifeToken.Elk && !usedTiles.contains(tile)) {

				HabitatTile[] adjecentTiles = Scoring.getAdjacentTiles(tile, map);
				for (HabitatTile adj : adjecentTiles) {
					if (adj.getPlacedToken() == WildlifeToken.Elk) {

					}
				}
			}

			switch (currLine.size()) {
				case (1) -> score += 2;
				case (2) -> score += 5;
				case (3) -> score += 9;
				case (4) -> score += 13;
			}
		}
		return score;
	}

	
	private static int elkScoringOption2(Player player) {
		PlayerMap map = player.getMap();
		ArrayList<HabitatTile> usedTiles = new ArrayList<>();
		ArrayList<HabitatTile> elkGroup = new ArrayList<>();
		int score = 0;
		int[] points= {2,4,7,10,14,18,23};

		for (HabitatTile tile : map.getTilesInMap()){
			if(tile.getPlacedToken() == WildlifeToken.Elk && !usedTiles.contains(tile)){
				elkGroup.clear();
				Scoring.findTokenGroupRecursive(elkGroup,WildlifeToken.Elk,tile,map);

				usedTiles.addAll(elkGroup);
				score+= elkGroup.size()<8 ? points[elkGroup.size()-1] : 28;
			}
		}
		return score;
	}
	
	private static int elkScoringOption3(Player player) {
		PlayerMap map = player.getMap();
		ArrayList<HabitatTile> usedTiles = new ArrayList<>();
		int score = 0;

		for (int i =4; i > 0; i--) {
			for (HabitatTile tile : map.getTilesInMap()) {
				if(tile.getPlacedToken() == WildlifeToken.Elk && !usedTiles.contains(tile)){
					HabitatTile[] temp = new HabitatTile[4];
					temp[0]=tile;
					if(i >=2) {
						temp[1]=Scoring.getAdjacentTiles(tile,map)[1];
						if (temp[1].getPlacedToken() == WildlifeToken.Elk  && !usedTiles.contains(temp[1])) {
							if(i>=3) {
								temp[2]=Scoring.getAdjacentTiles(tile,map)[0];
								if (temp[2].getPlacedToken() == WildlifeToken.Elk  && !usedTiles.contains(temp[2])) {
									if(i == 4) {
										temp[3]=Scoring.getAdjacentTiles(tile,map)[2];
										if (temp[3].getPlacedToken() == WildlifeToken.Elk  && !usedTiles.contains(temp[3])) {
											score += 13;
											usedTiles.addAll(List.of(temp));
										}
									}else{
										score += 9;
										usedTiles.addAll(List.of(temp));
									}
								}
							}else{
								score += 5;
								usedTiles.addAll(List.of(temp));
							}
						}
					}else{
						score += 2;
						usedTiles.addAll(List.of(temp));
					}
				}
				//end of for each loop
			}
		}
		return score;
	}
}
