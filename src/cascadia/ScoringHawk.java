package cascadia;

import java.util.ArrayList;

public class ScoringHawk {
private static ArrayList<HabitatTile> visitedTiles = new ArrayList<>();
	
	public static int scoreHawk(Player player, String hawkOption) {
		int score = 0;
		switch (hawkOption){
		case "H1" -> score = hawkScoringOption1(player);
		case "H2" -> score = hawkScoringOption2(player);
		case "H3" -> score = hawkScoringOption3(player);
		default -> throw new IllegalArgumentException("Unexpected value: " + hawkOption);
		}
		System.out.println(player.getPlayerName() + " Hawk Score: " + score); //for testing
		
		return score;
	}
	
	//scores for individual hawks on map
	private static int hawkScoringOption1(Player player) {
		PlayerMap map = player.getMap();
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
	private static int hawkScoringOption2(Player player) {
		PlayerMap map = player.getMap();
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
	private static int hawkScoringOption3(Player player) {
		PlayerMap map = player.getMap();
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
				System.out.println(hawkTile.getTileID() + " has checkHawk: false");
				return false;
			}
		}
		System.out.println(hawkTile.getTileID() + " has checkHawk: true");
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
			} while (nextTile != null && nextTile.getIsTokenPlaced() == false);
			//only make a line of sight that has not already been accounted for, between two valid hawks only
			if (nextTile != null && nextTile.getIsTokenPlaced() && nextTile.getPlacedToken() == WildlifeToken.Hawk && !visitedTiles.contains(nextTile) && checkValidHawk(map, nextTile)) {
				linesOfSight++;
				System.out.println("lines of sight incremented to " + linesOfSight);
			}
		}
	
		return linesOfSight;
	}
	
}
