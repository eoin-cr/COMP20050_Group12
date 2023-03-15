package cascadia.scoring;

import cascadia.HabitatTile;
import cascadia.PlayerMap;
import cascadia.WildlifeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScoringElk implements ScoreToken {
	private static ArrayList<HabitatTile> visitedTiles = new ArrayList<>();

	public enum Option {
		E1 {public int score(PlayerMap map){
			return elkScoringOption1(map);
		}},
		E2 {public int score(PlayerMap map) {
			return elkScoringOption2(map);
		}},
		E3 {public int score(PlayerMap map) {
			return elkScoringOption3(map);
		}};
		public abstract int score(PlayerMap map);
	}

	//treats line > 4 the same as 4
	private static int elkScoringOption1(PlayerMap map) {
		visitedTiles.clear();
		int score = 0;
		for (int z = 4; z > 0  ; z--) {
			for (HabitatTile tile : map.getTilesInMap()) {
				if (tile.getPlacedToken() == WildlifeToken.Elk && !visitedTiles.contains(tile)) {

					//creates a 2d arraylist for storing the possible lines
					List<List<HabitatTile>> lines = new ArrayList<>();
					lines.add(new ArrayList<>());
					lines.add(new ArrayList<>());
					lines.add(new ArrayList<>());

					HabitatTile[] adjacentTiles = Scoring.getAdjacentTiles(tile, map);

					//loop through the adjacent tiles
					for (int i = 1; i < 4; i++) {
						lines.get(i - 1).add(tile);
						HabitatTile currTile = adjacentTiles[i];

						//moves along till it reaches a non elk tile or the edge
						while (currTile != null && currTile.getPlacedToken() == WildlifeToken.Elk
								&& !visitedTiles.contains(currTile)) {
							lines.get(i - 1).add(currTile);
							currTile = Scoring.getAdjacentTiles(currTile, map)[i];
						}
					}
					//finds the longest line
					int maxIndex = (lines.get(0).size() > lines.get(1).size()) ? 0 : (lines.get(1).size() > lines.get(2).size()) ? 1 : 2;

					if(lines.get(maxIndex).size() >= z) {
						visitedTiles.addAll(lines.get(maxIndex));

						switch (lines.get(maxIndex).size()) {
							case (1) -> score += 2;
							case (2) -> score += 5;
							case (3) -> score += 9;
							default -> score += 13;
						}
					}
				}
			}
		}
		return score;
	}

	private static int elkScoringOption2(PlayerMap map) {
		visitedTiles.clear();
		ArrayList<HabitatTile> elkGroup = new ArrayList<>();

		int score = 0;
		int[] points= {2,4,7,10,14,18,23};

		for (HabitatTile tile : map.getTilesInMap()){
			if(tile.getPlacedToken() == WildlifeToken.Elk && !visitedTiles.contains(tile)){
				elkGroup.clear();
				Scoring.findTokenGroupRecursive(elkGroup,WildlifeToken.Elk,tile,map);

				visitedTiles.addAll(elkGroup);
				score+= elkGroup.size()<8 ? points[elkGroup.size()-1] : 28;
			}
		}
		return score;
	}
	
	private static int elkScoringOption3(PlayerMap map) {
		visitedTiles.clear();
		int score = 0;
		
		for (HabitatTile tile : map.getTilesInMap()){
			if(tile.getPlacedToken() == WildlifeToken.Elk && !visitedTiles.contains(tile)) {
				score += checkShapes(tile, map);
			}
		}
		
		return score;
	}
	
	private static int checkShapes(HabitatTile elkTile, PlayerMap map) {
		int score = 0;
		if (elkTile.getPlacedToken() != WildlifeToken.Elk) {
			throw new IllegalArgumentException("elkTile passed to checkShapes doesn't have an elk on it.");
		}
		
		if (checkDiamonds(elkTile, map)) {
			score += 13;
		}
		else if (checkTriangles(elkTile, map)) {
			score += 9;
		}
		else if (checkTwos(elkTile, map)) {
			score += 5;
		}
		else { //only a single lone elk
			score += 2;
		}
		return score;
	}

	private static boolean checkDiamonds(HabitatTile elkTile, PlayerMap map) {
		HabitatTile[] adjacentTiles = Scoring.getAdjacentTiles(elkTile, map);
		ArrayList<HabitatTile> diamondShape = new ArrayList<>();
		diamondShape.add(elkTile); //top of diamond
		
		for (int i = 1; i < adjacentTiles.length; i++) {
			if (adjacentTiles[i-1] != null && adjacentTiles[i] != null) {
				//if two adjacent tokens next to each other and central token are elk, it's a triangle
				if (!visitedTiles.contains(adjacentTiles[i-1]) && adjacentTiles[i-1].getIsTokenPlaced() && adjacentTiles[i-1].getPlacedToken() == WildlifeToken.Elk &&
						!visitedTiles.contains(adjacentTiles[i]) && adjacentTiles[i].getIsTokenPlaced() && adjacentTiles[i].getPlacedToken() == WildlifeToken.Elk) {
					diamondShape.add(adjacentTiles[i-1]); //right of diamond
					diamondShape.add(adjacentTiles[i]); //left of diamond
					//now check if the triangle is a diamond by taking a step from the left tile of diamond
					HabitatTile bottomTile = Scoring.walkToTileAtSide(adjacentTiles[i], map, 2);
					if (bottomTile != null && !visitedTiles.contains(bottomTile) && bottomTile.getIsTokenPlaced() && bottomTile.getPlacedToken() == WildlifeToken.Elk) {
						diamondShape.add(bottomTile);
						visitedTiles.addAll(diamondShape);
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	private static boolean checkTriangles(HabitatTile elkTile, PlayerMap map) {
		HabitatTile[] adjacentTiles = Scoring.getAdjacentTiles(elkTile, map);
		ArrayList<HabitatTile> triangleShape = new ArrayList<>();
		triangleShape.add(elkTile); //top of triangle
		
		for (int i = 1; i < adjacentTiles.length; i++) {
			if (adjacentTiles[i-1] != null && adjacentTiles[i] != null) {
				//if two adjacent tokens next to each other and central token are elk, it's a triangle
				if (!visitedTiles.contains(adjacentTiles[i-1]) && adjacentTiles[i-1].getIsTokenPlaced() && adjacentTiles[i-1].getPlacedToken() == WildlifeToken.Elk &&
						!visitedTiles.contains(adjacentTiles[i]) && adjacentTiles[i].getIsTokenPlaced() && adjacentTiles[i].getPlacedToken() == WildlifeToken.Elk) {
					triangleShape.add(adjacentTiles[i-1]); //left of triangle
					triangleShape.add(adjacentTiles[i]); //right of triangle
					visitedTiles.addAll(triangleShape);
					return true;
				}
			}
		}
		return false;
	}

	private static boolean checkTwos(HabitatTile elkTile, PlayerMap map) {
		HabitatTile[] adjacentTiles = Scoring.getAdjacentTiles(elkTile, map);
		for (HabitatTile t : adjacentTiles) {
			if (t != null && !visitedTiles.contains(t) && t.getIsTokenPlaced() && t.getPlacedToken() == WildlifeToken.Elk) {
				visitedTiles.add(elkTile);
				visitedTiles.add(t);
				return true;
			}
		}
		return false;
	}
}
