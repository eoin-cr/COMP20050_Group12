package main.java.cascadia.scoring;

import main.java.cascadia.HabitatTile;
import main.java.cascadia.PlayerMap;
import main.java.cascadia.WildlifeToken;

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
		visitedTiles.clear();
		int hawkCount = 0;
		int score = 0;

		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getPlacedToken() == WildlifeToken.Hawk) {
				if(checkValidHawk(map, tile)){ //i.e. no other hawks adjacent
					hawkCount++;
				}
			}
		}
		
		switch (hawkCount){
			case 0-> score = 0;
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
	
	//scores for uninterrupted lines of sight, between individual valid hawks on map without adjacent hawks
	private static int hawkScoringOption2(PlayerMap map) {
		visitedTiles.clear();
		int linesOfSight = 0;
		int score = 0;
		
		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getPlacedToken() == WildlifeToken.Hawk && !visitedTiles.contains(tile)) {
				boolean validHawk = checkValidHawk(map, tile);
				
				if (validHawk) { //check lines of sight now for a valid hawk
					linesOfSight += getLinesOfSight(map, tile);
					visitedTiles.add(tile); //already accounted for all its lines of sight
				}
			}
		}
		
		switch (linesOfSight) {
		case 0 -> score = 0;
		case 1 -> score = 2;
		case 2 -> score = 5;
		case 3 -> score = 9;
		case 4 -> score = 12;
		case 5 -> score = 16;
		case 6 -> score = 20;
		case 7 -> score = 24;
		case 8 -> score = 28;
		default -> score = 28; //more than 8
		}
		
		return score;
	}
	
	//scores for uninterrupted lines of sight, between individual valid hawks on map without adjacent hawks
	private static int hawkScoringOption3(PlayerMap map) {
		visitedTiles.clear();
		int linesOfSight = 0;
		int score = 0;
		
		for (HabitatTile tile : map.getTilesInMap()) {
			if (tile.getPlacedToken() == WildlifeToken.Hawk && !visitedTiles.contains(tile)) {
				boolean validHawk = checkValidHawk(map, tile);
				
				if (validHawk) { //check lines of sight now for a valid hawk
					linesOfSight += getLinesOfSight(map, tile);
					//System.out.println(linesOfSight);
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
	private static int getLinesOfSight(PlayerMap map, HabitatTile hawkTile) {
		int linesOfSight = 0;
		HabitatTile currTile = hawkTile;
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
