import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScoringElk {

	public static int scoreElk(Player player, String elkOption) {
		int score;
		switch (elkOption){
		case "E1" -> score = elkScoringOption1(player);
		case "E2" -> score = elkScoringOption2(player);
		case "E3" -> score = elkScoringOption3(player);
		default -> throw new IllegalArgumentException("Unexpected value: " + elkOption);
		}
		//System.out.println(player.getPlayerName() + " Elk Score: " + score); //for testing
		return score;
	}

	//treats line > 4 the same as 4
	private static int elkScoringOption1(Player player) {

		int score = 0;
		ArrayList<HabitatTile> usedTiles = new ArrayList<>();
		PlayerMap map = player.getMap();

		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getPlacedToken() == WildlifeToken.Elk && !usedTiles.contains(tile)) {

				//creates a 2d arraylist for storing the possible lines
				List<List<HabitatTile>> lines = new ArrayList<>();
				lines.add(new ArrayList<>());
				lines.add(new ArrayList<>());
				lines.add(new ArrayList<>());

				HabitatTile[] adjacentTiles = Scoring.getAdjacentTiles(tile, map);

				//loop through the adjacent tiles
				for (int i = 1;i < 4;i++) {
					lines.get(i-1).add(tile);
					HabitatTile currTile = adjacentTiles[i];

					//moves along till it reaches a non elk tile or the edge
					while (currTile !=  null && currTile.getPlacedToken() ==  WildlifeToken.Elk
							&& !usedTiles.contains(currTile)){
						lines.get(i-1).add(currTile);
						currTile = Scoring.getAdjacentTiles(currTile,map)[i];
					}
				}
				//finds the longest line
				int maxIndex = ( lines.get(0).size() > lines.get(1).size()) ? 0 : ( lines.get(1).size() > lines.get(2).size()) ? 1 : 2;

				usedTiles.addAll(lines.get(maxIndex));

				switch (lines.get(maxIndex).size()) {
					case (1) -> score += 2;
					case (2) -> score += 5;
					case (3) -> score += 9;
					default -> score +=13;
				}
			}
		}
		return score;
	}

	private static int elkScoringOption2(Player player) {
		PlayerMap map = player.getMap();
		ArrayList<HabitatTile> elkGroup = new ArrayList<>();
		ArrayList<HabitatTile> usedTiles = new ArrayList<>();
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
		int[] elkShape = {1,0,2};
		int[] points = {2,5,9,13};

		for (int i = 4; i > 0; i--) {
			for (HabitatTile tile : map.getTilesInMap()) {
				if(tile.getPlacedToken() == WildlifeToken.Elk && !usedTiles.contains(tile)) {

					HabitatTile[] temp = new HabitatTile[4];
					temp[0] = tile;
					boolean VaildTiles = true;

					for (int j = 1; j < i; j++) {
						temp[j] = Scoring.getAdjacentTiles(tile, map)[elkShape[j]];
						if (temp[j].getPlacedToken() != WildlifeToken.Elk || usedTiles.contains(temp[j])) {
							VaildTiles = false;
						}
					}

					if (VaildTiles) {
						score += points[i];
						usedTiles.addAll(Arrays.stream(temp).toList());
					}
				}
			}
		}
		return score;
	}
}
