

import java.util.ArrayList;
import java.util.List;

public class Scoring {
//	indexing:
//	index 0 stores Bear score card option as a string		(B1,B2,B3,B4)
//	index 1 stores Elk score card option as a string		(E1,E2,E3,E4)
//	index 2 stores Salmon score card option as a string		(S1,S2,S3,S4)
//	index 3 stores Hawk score card option as a string		(H1,H2,H3,H4)
//	index 4 stores Fox score card option as a string		(F1,F2,F3,F4)
	private static final String[] cards = ScoreCards.getScorecards();
	private static final List<Player> players = Game.getPlayers();
	private static Player winner = null;
	
	public Scoring() {}
	
	public static void startScoring() {
		for (Player p : players) {
			ScoringBear.scoreBear(p, cards[0]);
			ScoringElk.scoreElk(p, cards[1]);
			ScoringSalmon.scoreSalmon(p, cards[2]);
			ScoringHawk.scoreHawk(p, cards[3]);
			ScoringFox.scoreFox(p, cards[4]);
		}
	
		habitatCorridorScoring();
		natureTokenScoring();
		
		//find winner of game
		int maxScore = 0;
		for (Player p : players) {
			if (p.getPlayerScore() > maxScore) {
				maxScore = p.getPlayerScore();
				winner = p;
			}
		}
		//TODO: deal with winner ties, display winner message and exit game
		
	}
	
	//helper functions for getting all adjacent tiles of a tile
	//has cases based on its position in the 2d array/map (might be missing sides)
	// Note: Edges of the hexagonal are numbered 0 (starting from the top right edge, going clockwise) to 5 (left top edge)
	// Total 6 sides, like in the diagram below
//	 	  5   0
//	 	  -- --
//	 	4|     |1
//	 	  -- --
//	 	  3	  2
	
	public static HabitatTile[] getAdjacentTiles(HabitatTile tile, PlayerMap map) {
		HabitatTile[] adjacentTiles = new HabitatTile[6]; //initialise adjacent tiles with null
		for (int i = 0; i < 6; i++) {
			adjacentTiles[i] = null;
		}
		int row = tile.getMapPosition()[0]; //find position of particular tile to be checked
		int col = tile.getMapPosition()[1];
		
		//edge cases
		if (row == 0 && col == 0) { //missing sides 3,4,5,0 // top left on map
			adjacentTiles[1] = map.returnTileAtPositionInMap(0, 1);
			adjacentTiles[2] = map.returnTileAtPositionInMap(1, 0);
		}
		else if (row == 0 && col > 0 && col < 19) { //missing sides 5,0
			adjacentTiles[1] = map.returnTileAtPositionInMap(0, col+1);
			adjacentTiles[2] = map.returnTileAtPositionInMap(1, col+1);
			adjacentTiles[3] = map.returnTileAtPositionInMap(1, col);
			adjacentTiles[4] = map.returnTileAtPositionInMap(0, col-1);
		}
		else if (row == 0 && col == 19) { //missing sides 5,0,1 // top right on map
			adjacentTiles[2] = map.returnTileAtPositionInMap(1, 19);
			adjacentTiles[3] = map.returnTileAtPositionInMap(1, 18);
			adjacentTiles[4] = map.returnTileAtPositionInMap(0, 18);
		}
		else if (col == 0 && row > 0 && row < 19) { //alternating, missing side 4 or sides 3,4,5
			if (row%2 != 0) { //odd rows, missing side 4
				adjacentTiles[0] = map.returnTileAtPositionInMap(row-1, 1);
				adjacentTiles[1] = map.returnTileAtPositionInMap(row, 1);
				adjacentTiles[2] = map.returnTileAtPositionInMap(row+1, 1);
				adjacentTiles[3] = map.returnTileAtPositionInMap(row+1, 0);
				adjacentTiles[5] = map.returnTileAtPositionInMap(row-1, 0);
			}
			else { //even rows, missing sides 3,4,5
				adjacentTiles[0] = map.returnTileAtPositionInMap(row-1, 0);
				adjacentTiles[1] = map.returnTileAtPositionInMap(row, 1);
				adjacentTiles[2] = map.returnTileAtPositionInMap(row+1, 0);
			}
		}
		else if (col == 0 && row == 19) { //missing sides 2,3,4 //bottom left on map
			adjacentTiles[5] = map.returnTileAtPositionInMap(18, 0);
			adjacentTiles[0] = map.returnTileAtPositionInMap(18, 1);
			adjacentTiles[1] = map.returnTileAtPositionInMap(19, 1);
		}
		else if (col == 19 && row > 0 && row < 19) { //alternating, missing sides 0,1,2 or side 1
			if (row%2 != 0) { //odd rows, missing sides 0,1,2
				adjacentTiles[3] = map.returnTileAtPositionInMap(1, col);
				adjacentTiles[4] = map.returnTileAtPositionInMap(0, col-1);
				adjacentTiles[5] = map.returnTileAtPositionInMap(0, col-1);
			}
			else { //even rows, missing side 1
				adjacentTiles[0] = map.returnTileAtPositionInMap(0, col+1);
				adjacentTiles[2] = map.returnTileAtPositionInMap(1, col+1);
				adjacentTiles[3] = map.returnTileAtPositionInMap(1, col);
				adjacentTiles[4] = map.returnTileAtPositionInMap(0, col-1);
				adjacentTiles[5] = map.returnTileAtPositionInMap(0, col-1);
				
			}
		}
		else if (col == 19 && row == 19) { //missing sides 0,1,2,3 //bottom right on map
			adjacentTiles[4] = map.returnTileAtPositionInMap(19, 18);
			adjacentTiles[5] = map.returnTileAtPositionInMap(18, 19);
		}
		//non edge case
		else { //no missing sides, in the middle of the map
			adjacentTiles[0] = map.returnTileAtPositionInMap(row-1, col+1);
			adjacentTiles[1] = map.returnTileAtPositionInMap(row, col+1);
			adjacentTiles[2] = map.returnTileAtPositionInMap(row+1, col+1);
			adjacentTiles[3] = map.returnTileAtPositionInMap(row+1, col);
			adjacentTiles[4] = map.returnTileAtPositionInMap(row, col-1);
			adjacentTiles[5] = map.returnTileAtPositionInMap(row-1, col);
		}
		
		return adjacentTiles;
	}
	
	//helper function that gets a tile's adjacent tiles' tokens in an array
	public static WildlifeToken[] getAdjacentTokens(HabitatTile tile, PlayerMap map) {
		HabitatTile[] adjacentTiles = getAdjacentTiles(tile, map);
		WildlifeToken[] adjacentTokens = new WildlifeToken[6];
		for (int i = 0; i < 6; i++) {
			if (adjacentTiles[i] != null) {
				adjacentTokens[i] = adjacentTiles[i].getPlacedToken();
			}
			else {
				adjacentTokens[i] = null;
			}
		}
		return adjacentTokens;
	}
	
	//helper function that gets a tile's adjacent tiles' habitats in an array
	public static HabitatTile.Habitat[] getAdjacentHabitats(HabitatTile tile, PlayerMap map) {
		HabitatTile[] adjacentTiles = getAdjacentTiles(tile, map);
		HabitatTile.Habitat adjacentHabitats[] = new HabitatTile.Habitat[6];
		for (int i = 0; i < 6; i++) {
			adjacentHabitats[i] = null;
		}
		if (adjacentTiles[0] != null) adjacentHabitats[0] = adjacentTiles[0].getEdge(3).getHabitatType();
		if (adjacentTiles[1] != null) adjacentHabitats[1] = adjacentTiles[1].getEdge(4).getHabitatType();
		if (adjacentTiles[2] != null) adjacentHabitats[2] = adjacentTiles[2].getEdge(5).getHabitatType();
		if (adjacentTiles[3] != null) adjacentHabitats[3] = adjacentTiles[3].getEdge(0).getHabitatType();
		if (adjacentTiles[4] != null) adjacentHabitats[4] = adjacentTiles[4].getEdge(1).getHabitatType();
		if (adjacentTiles[5] != null) adjacentHabitats[5] = adjacentTiles[5].getEdge(2).getHabitatType();
		return adjacentHabitats;
	}
	
	public static void findHabitatCorridorRecursive(ArrayList<HabitatTile> habitatCorridor, HabitatTile centerTile, HabitatTile.Habitat habitatType, PlayerMap map) {
		HabitatTile[] adjacentTiles = getAdjacentTiles(centerTile, map);
		HabitatTile.Habitat[] adjacentHabitats = getAdjacentHabitats(centerTile, map);
		
		if (!habitatCorridor.contains(centerTile) && (centerTile.getHabitat1() == habitatType || centerTile.getHabitat2() == habitatType)) {
			habitatCorridor.add(centerTile);
			for (int i = 0; i < centerTile.getEdges().size(); i++) {
				if (centerTile.getEdge(i).getHabitatType() == habitatType && adjacentHabitats[i] == habitatType) {
					findHabitatCorridorRecursive(habitatCorridor, adjacentTiles[i], habitatType, map);
				}
			}
		}
		
	}
	
	public static ArrayList<HabitatTile> findLongestHabitatCorridor(PlayerMap map, HabitatTile.Habitat habitatType) {
		ArrayList<HabitatTile> visitedTiles = new ArrayList<>();
		ArrayList<HabitatTile> longestCorridor = new ArrayList<>();
		ArrayList<HabitatTile> newCorridor = new ArrayList<>();
		int longestCorridorSize = 0;
		
		for (HabitatTile tile : map.getTilesInMap()) {
			newCorridor.clear();
			if (!visitedTiles.contains(tile) && (tile.getHabitat1() == habitatType || tile.getHabitat2() == habitatType)) {
				findHabitatCorridorRecursive(newCorridor, tile, habitatType, map);
				visitedTiles.addAll(newCorridor);
				if (newCorridor.size() > longestCorridorSize) {
					longestCorridor.clear();
					longestCorridor.addAll(newCorridor);
					longestCorridorSize = newCorridor.size();
				}
			}
		}
		
		return longestCorridor;
	}
	
	private static void habitatCorridorScoring() {
		
		//indexing:
		//0 stores forest
		//1 stores wetland
		//2 stores river
		//3 stores mountain
		//4 stores prairie
		
		int[] longestCorridorSizes = new int[5];
		ArrayList<Player> longestCorridorPlayers = new ArrayList<>();
		
		//each player's score for their personal longest habitat corridors of all 5 types
		for (Player p : players) {
			int playerForestCorridor = findLongestHabitatCorridor(p.getMap(), HabitatTile.Habitat.Forest).size();
			int playerWetlandCorridor = findLongestHabitatCorridor(p.getMap(), HabitatTile.Habitat.Wetland).size();
			int playerRiverCorridor = findLongestHabitatCorridor(p.getMap(), HabitatTile.Habitat.River).size();
			int playerMountainCorridor = findLongestHabitatCorridor(p.getMap(), HabitatTile.Habitat.Mountain).size();
			int playerPrairieCorridor = findLongestHabitatCorridor(p.getMap(), HabitatTile.Habitat.Prairie).size();
			
			p.addToPlayerScore(playerForestCorridor);
			p.addToPlayerScore(playerWetlandCorridor);
			p.addToPlayerScore(playerRiverCorridor);
			p.addToPlayerScore(playerMountainCorridor);
			p.addToPlayerScore(playerPrairieCorridor);
			 
		}
	}
	
	public static void natureTokenScoring() {
		for (Player p : players) {
			if (p.getPlayerNatureTokens() > 0) {
				System.out.println(p.getPlayerName() + " has " + p.getPlayerNatureTokens() + " remaining Nature Tokens./s"
						+ "These have been added to your score total.");
				p.addToPlayerScore(p.getPlayerNatureTokens());
			}
		}
	}
	
	//helper function that returns an int count of how many adjacent tiles have the same matching token
	public static int countAdjacentTokenMatches(WildlifeToken animalType, HabitatTile tile, PlayerMap map) {
		int count = 0;
		WildlifeToken[] adjacentTokens = getAdjacentTokens(tile, map);
		for (int i = 0; i < adjacentTokens.length; i++) {
			if (adjacentTokens[i] == animalType) {
				count++;
			}
		}
		return count;
	}
	
	//helper function to get one tile's adjacent tiles which match the wildlife token on the central tile, in an arraylist
	public static ArrayList<HabitatTile> getAdjacentTilesWithTokenMatch(WildlifeToken animalType, HabitatTile centerTile, PlayerMap map){
		ArrayList<HabitatTile> tileMatches = new ArrayList<>();
		HabitatTile[] adjacentTiles = getAdjacentTiles(centerTile, map);
		for (HabitatTile checktile : adjacentTiles) {
			if (checktile.getPlacedToken() == animalType) {
				tileMatches.add(checktile);
			}
		}
		return tileMatches;
	}
	
	//helper function to recursively get a group of same token types all connected on a map, in an arraylist
	public static void findTokenGroupRecursive(ArrayList<HabitatTile> groupOfTokens, WildlifeToken tokenType, HabitatTile centerTile, PlayerMap map) {
		if (!groupOfTokens.contains(centerTile) && centerTile.getPlacedToken() == tokenType) {
			groupOfTokens.add(centerTile);
			ArrayList<HabitatTile> adjacentTokens = Scoring.getAdjacentTilesWithTokenMatch(tokenType, centerTile, map);
			if (adjacentTokens.size() > 0) {
				for (HabitatTile t : adjacentTokens) {
					findTokenGroupRecursive(groupOfTokens, tokenType, t, map);
				}
			}
		}
	}
	
	
}
