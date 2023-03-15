package cascadia.scoring;

import cascadia.HabitatTile;
import cascadia.PlayerMap;
import cascadia.WildlifeToken;

import java.util.ArrayList;

public class ScoringHawk extends ScoreToken {
private static final ArrayList<HabitatTile> visitedTiles = new ArrayList<>();
	public enum Option implements Scorable {
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

	//scores for individual hawks on map
	private static int hawkScoringOption1(PlayerMap map) {
		visitedTiles.clear();
		int hawkCount = 0;
		int[] hawkScores = new int[]{0,2,5,8,11,14,18,22,26};

		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getPlacedToken() == WildlifeToken.Hawk) {
				if(checkValidHawk(map, tile)){ //i.e. no other hawks adjacent
					hawkCount++;
				}
			}
		}

		if (hawkCount < 0) {
			return 0;
		} else if (hawkCount > 8) {
			return hawkScores[8];
		} else {
			return hawkScores[hawkCount];
		}
	}
	
	//scores for uninterrupted lines of sight, between individual valid hawks on map without adjacent hawks
	private static int hawkScoringOption2(PlayerMap map) {
		visitedTiles.clear();
		int linesOfSight = 0;
		int[] hawkScores = new int[]{0,2,5,9,12,16,20,24,28};

		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getPlacedToken() == WildlifeToken.Hawk && !visitedTiles.contains(tile)) {
				boolean validHawk = checkValidHawk(map, tile);
				
				if (validHawk) { //check lines of sight now for a valid hawk
					linesOfSight += getLineOfSightOneStepOver(map, tile);
					visitedTiles.add(tile); //already accounted for all its lines of sight
				}
			}
		}

		if (linesOfSight < 0) {
			return 0;
		} else if (linesOfSight > 8) {
			return hawkScores[8];
		} else {
			return hawkScores[linesOfSight];
		}
	}


	//scores for uninterrupted lines of sight, between individual valid hawks on map without adjacent hawks
	private static int hawkScoringOption3(PlayerMap map) {
		visitedTiles.clear();
		int linesOfSight = 0;
		int score;

		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getPlacedToken() == WildlifeToken.Hawk && !visitedTiles.contains(tile)) {
				boolean validHawk = checkValidHawk(map, tile);
				
				if (validHawk) { //check lines of sight now for a valid hawk
					linesOfSight += getLinesOfSight(map, tile);
					visitedTiles.add(tile); //already accounted for all its lines of sight
				}
			}
		}

		score = 3*linesOfSight;
		
		return score;
	}
	
	//helper function, only a valid hawk if there are no other adjacent hawks
	private static boolean checkValidHawk(PlayerMap map, HabitatTile hawkTile) {
		HabitatTile[] adjacentTiles = Scoring.getAdjacentTiles(hawkTile, map);
		for (HabitatTile t : adjacentTiles) {
			if (t != null && t.getIsTokenPlaced() && t.getPlacedToken() == WildlifeToken.Hawk) { //not a valid hawk
				visitedTiles.add(t); //both invalid hawk tiles get added to visited tiles
				visitedTiles.add(hawkTile);
				return false;
			}
		}
		return true;
	}
	
	//helper function
	private static int getLineOfSightOneStepOver(PlayerMap map, HabitatTile hawkTile) {
		int linesOfSight = 0;
		HabitatTile currTile;
		for (int i = 0; i < 6; i++) { //walk from all sides of the tile, check for diagonal and horizontal lines of sight
			currTile = hawkTile;
			currTile = Scoring.walkToTileAtSide(currTile, map, i); //walk one step in a direction
			if (currTile != null && !currTile.getIsTokenPlaced()) {//walk another step while not end of map or not interrupted by wildlife
				currTile = Scoring.walkToTileAtSide(currTile, map, i);
			}
			if (currTile != null && currTile.getIsTokenPlaced() && currTile.getPlacedToken() == WildlifeToken.Hawk && !visitedTiles.contains(currTile) && checkValidHawk(map, currTile)) {
					linesOfSight++;
			}
		}
		
		return linesOfSight;
	}
	
	//helper function
	private static int getLinesOfSight(PlayerMap map, HabitatTile hawkTile) {
		int linesOfSight = 0;
		HabitatTile currTile;
		for (int i = 0; i < 6; i++) { //walk from all sides of the tile, check for diagonal and horizontal lines of sight
			currTile = hawkTile;
			currTile = Scoring.walkToTileAtSide(currTile, map, i);
			while (currTile != null && !currTile.getIsTokenPlaced()) {//keep walking in one direction while not end of map or not interrupted by wildlife (esp hawks)
				currTile = Scoring.walkToTileAtSide(currTile, map, i);
			}
			if (currTile != null && currTile.getIsTokenPlaced() && currTile.getPlacedToken() == WildlifeToken.Hawk && !visitedTiles.contains(currTile) && checkValidHawk(map, currTile)) {
					linesOfSight++;
			}
		}
		
		return linesOfSight;
	}
	
}
