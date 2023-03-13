package cascadia.scoring;

import cascadia.HabitatTile;
import cascadia.PlayerMap;
import cascadia.WildlifeToken;

import java.util.ArrayList;

public class ScoringHawk {
private static final ArrayList<HabitatTile> visitedTiles = new ArrayList<>();
	public enum Option {
		H1 {public int score(PlayerMap map){
			return hawkScoringOption1(map);
		}},
		H2 {public int score(PlayerMap map) {
			return hawkScoringOption2(map);
		}},
		H3 {public int score(PlayerMap map) {
			return hawkScoringOption3(map);
		}};
		public abstract int score(PlayerMap map);
	}

	public static int calculateScore(PlayerMap map, Option option) {
		return option.score(map);
	}

	//scores for individual hawks on map
	private static int hawkScoringOption1(PlayerMap map) {
		int hawkCount = 0;
		int score;
		int[] hawkScores = new int[]{0,2,5,8,11,14,18,22,26};

		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getPlacedToken() == WildlifeToken.Hawk) {
				if(checkValidHawk(map, tile)){ //i.e. no other hawks adjacent
					hawkCount++;
				}
			}
		}
		score = (hawkCount < 8) ? hawkScores[hawkCount] : hawkScores[8];
		return score;
	}
	
	//scores for uninterrupted lines of sight, between individual valid hawks on map without adjacent hawks
	private static int hawkScoringOption2(PlayerMap map) {
		int linesOfSight = 0;
		int score;
		int[] hawkScore = new int[]{0,2,5,9,12,16,20,24,28};


		linesOfSight = getMapLinesOfSight(map, linesOfSight);
		score = linesOfSight < 8 ? hawkScore[linesOfSight] : hawkScore[8];
		return score;
	}
	
	//scores for uninterrupted lines of sight, between individual valid hawks on map without adjacent hawks
	private static int hawkScoringOption3(PlayerMap map) {
		int linesOfSight = 0;
		int score;

		linesOfSight = getMapLinesOfSight(map, linesOfSight);
		score = 3*linesOfSight;
		return score;
	}

	private static int getMapLinesOfSight(PlayerMap map, int linesOfSight) {
		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getPlacedToken() == WildlifeToken.Hawk && !visitedTiles.contains(tile)) {
				boolean validHawk = checkValidHawk(map, tile);

				if (validHawk) { //check lines of sight now for a valid hawk
					linesOfSight += getLinesOfSight(map, tile);
					visitedTiles.add(tile); //already accounted for all its lines of sight
				}
			}
		}
		return linesOfSight;
	}

	//helper function, only a valid hawk if there are no other adjacent hawks
	private static boolean checkValidHawk(PlayerMap map, HabitatTile hawkTile) {
		HabitatTile[] adjacentTiles = Scoring.getAdjacentTiles(hawkTile, map);
		for (HabitatTile t : adjacentTiles) {
			if (t != null && t.getIsTokenPlaced() && t.getPlacedToken() == WildlifeToken.Hawk) { //not a valid hawk
				visitedTiles.add(t); //both invalid hawk tiles get added to visited tiles
				visitedTiles.add(hawkTile);
//				System.out.println(hawkTile.getTileID() + " has checkHawk: false");
				return false;
			}
		}
//		System.out.println(hawkTile.getTileID() + " has checkHawk: true");
		return true;
	}
	
	//helper function
	private static int getLinesOfSight(PlayerMap map, HabitatTile hawkTile) {
		int linesOfSight = 0;
		HabitatTile[] adjacentTiles = Scoring.getAdjacentTiles(hawkTile, map);
		
		for (int i = 0; i < 6; i++) { //walk through all 6 directions from all sides of the tile
			HabitatTile nextTile = adjacentTiles[i];
			if (nextTile != null) System.out.println("tile at side: " +i+ " is: " +nextTile.getTileID());
			//keep walking along line while not end of map or not interrupted by wildlife or until you hit a hawk
			do {
				nextTile = Scoring.walkToTileAtSide(nextTile, map, i);
				if (nextTile != null) System.out.println("tile at side: " +i+ " is: " +nextTile.getTileID());
			} while (nextTile != null && !nextTile.getIsTokenPlaced());
			//only make a line of sight that has not already been accounted for, between two valid hawks only
			if (nextTile != null && nextTile.getIsTokenPlaced() && nextTile.getPlacedToken() == WildlifeToken.Hawk && !visitedTiles.contains(nextTile) && checkValidHawk(map, nextTile)) {
				linesOfSight++;
				System.out.println("lines of sight incremented to " + linesOfSight);
			}
		}
		return linesOfSight;
	}
	
}
