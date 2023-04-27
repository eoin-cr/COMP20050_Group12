package cascadia;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import cascadia.HabitatTile.Habitat;
import cascadia.scoring.Scoring;

public class TileBot {
	public static final int NUM_TILE_STRATS = 2;
	private static int rankValue; //preference weight
	//these arrays store info per turn about each deckTile in the current deck
	private int[] preferences = new int[4];
	private boolean[] ranked = new boolean[4];
	private Habitat[] prefHabitats = new Habitat[4];
	private int[] prefNumRotations = new int[4];
	private int[][] prefTileRowsAndColumns = new int[2][4];

	public int[] chooseStrategy(Player player, Player nextPlayer) {
		List<HabitatTile> deckTiles = CurrentDeck.getDeckTiles();
		rankValue = 4; //set max preference value to 4
		Arrays.fill(preferences, 0); //clear out all arrays from last turn
		Arrays.fill(ranked, false);
		Arrays.fill(prefHabitats, null);
		Arrays.fill(prefNumRotations, 0);
		for (int[] row : prefTileRowsAndColumns) {
			Arrays.fill(row, 0);
		}
		
		rankGaps(deckTiles, player, nextPlayer); //first give preference to corridor gaps of size 1,0,-1

		Random rand = new Random();
		int strategyChoice = 0; //for testing
		//int strategyChoice = rand.nextInt(NUM_TILE_STRATS);

		switch (strategyChoice) {
			case 0 -> {
				System.out.println("\nUsing constructive tile strat!");
				constructiveGrowMinCorridorStrat(deckTiles, player);
			}
			case 1 -> {
				System.out.println("\nUsing destructive tile strat!");
				destructiveTileStrategy(deckTiles, player, nextPlayer);
			}
			default -> throw new IllegalArgumentException("Unexpected value: " + strategyChoice);
		}

		checkTilePlacementsPossible(deckTiles, player);
		return preferences;
	}
	
	public int[] getDeckTilePlacementChoice(int index) {
		int row = prefTileRowsAndColumns[0][index];
		int col = prefTileRowsAndColumns[1][index];
		return new int[]{row, col};
	}
	
	public int getDeckTileNumRotations(int index) {
		return prefNumRotations[index];
	}
	
	//prioritise gaps of size 1, then size 0, then size -1 between the two players' corridors, so you can either move ahead or catch up
	private void rankGaps(List<HabitatTile> deckTiles, Player player, Player nextPlayer) {
		int[] playerCorridorSizes = player.getLongestCorridorSizes();
		int[] nextPlayerCorridorSizes = nextPlayer.getLongestCorridorSizes();
		int[] gapsBetweenCorridors = new int[Constants.NUM_HABITAT_TYPES];
		
		for (int i = 0; i < Constants.NUM_HABITAT_TYPES; i++) {
			gapsBetweenCorridors[i] = playerCorridorSizes[i] - nextPlayerCorridorSizes[i];
		}
		
		boolean gapFound;
		LinkedHashMap<Habitat, Integer> gapMatches = new LinkedHashMap<>();
		for (int gapSize = 1; gapSize > -2; gapSize--) { //check if gap size is 1, 0 or -1
			gapFound = false;
			for (int i = 0; i < gapsBetweenCorridors.length; i++) {
				if (gapsBetweenCorridors[i] == gapSize) {
					gapFound = true;
					Habitat hab = Habitat.getHabitat(i);
					gapMatches.put(hab, gapSize);
				}
			}
			if (gapFound) {
				rankDeckTiles(deckTiles, gapMatches);
				gapMatches.clear();
			}
		}
	}

	//grows current player's smallest corridor, maintains corridor diversity
	private void constructiveGrowMinCorridorStrat(List<HabitatTile> deckTiles, Player player) {
		HashMap<Habitat, Integer> corridorPairs = hashCorridors(player.getLongestCorridorSizes());
		LinkedHashMap<Habitat, Integer> maxToMinCorridors = findMaxToMinHabitatCorridors(corridorPairs);
		rankDeckTiles(deckTiles, maxToMinCorridors);
	}
	
	//stops min corridor of next player from growing
	private void destructiveTileStrategy(List<HabitatTile> deckTiles, Player player, Player nextPlayer) {
		HashMap<Habitat, Integer> corridorPairs = hashCorridors(nextPlayer.getLongestCorridorSizes());
		LinkedHashMap<Habitat, Integer> nextPlayerMaxToMinCorridors = findMaxToMinHabitatCorridors(corridorPairs);
		rankDeckTiles(deckTiles, nextPlayerMaxToMinCorridors);
	}
	
	HashMap<Habitat, Integer> hashCorridors(int[] corridorSizes){
		HashMap<Habitat, Integer> corridorPairs = new HashMap<>();
		for (int i = 0; i < corridorSizes.length; i++) {
			corridorPairs.put(Habitat.getHabitat(i), corridorSizes[i]);
		}
		return corridorPairs;
	}

	//helper function, finds a player's smallest to largest habitat corridors, returns a list of habitats in order
	LinkedHashMap<Habitat, Integer> findMinToMaxHabitatCorridors(HashMap<Habitat, Integer> corridorPairs){
		LinkedHashMap<Habitat, Integer> minToMaxCorridors = new LinkedHashMap();
		
		while(minToMaxCorridors.size() < Constants.NUM_HABITAT_TYPES) {
			Integer minVal = 800; //just some random high value, more than no. of possible tiles
			Habitat minKey = null;
			for (Habitat h : corridorPairs.keySet()) {
				if (corridorPairs.get(h) <= minVal) {
					minKey = h;
					minVal = corridorPairs.get(h);
				}
			}
			minToMaxCorridors.put(minKey, minVal);
			corridorPairs.remove(minKey);
		}
			
		//System.out.println("min to max corridors are: " + minToMaxCorridors.toString());
		return minToMaxCorridors;
	}

	//helper function, finds a player's smallest to largest habitat corridors, returns a list of habitats in order
	LinkedHashMap<Habitat, Integer> findMaxToMinHabitatCorridors(HashMap<Habitat, Integer> corridorPairs){
		LinkedHashMap<Habitat, Integer> maxToMinCorridors = new LinkedHashMap();

		while(maxToMinCorridors.size() < Constants.NUM_HABITAT_TYPES) {
			Integer maxVal = -1; //just some random low value
			Habitat maxKey = null;
			for (Habitat h : corridorPairs.keySet()) {
				if (corridorPairs.get(h) >= maxVal) {
					maxKey = h;
					maxVal = corridorPairs.get(h);
				}
			}
			maxToMinCorridors.put(maxKey, maxVal);
			corridorPairs.remove(maxKey);
		}
			
		//System.out.println("max to min corridors are: " + maxToMinCorridors.toString());
		return maxToMinCorridors;
	}

	//helper function, matches deckTile preferences from 4 (highest) to 1 (lowest) to the ordered list of habitats given
	//also finds which habitat of the 2 tile habitats was matched
	int[] rankDeckTiles(List<HabitatTile> deckTiles, LinkedHashMap<Habitat, Integer> matchHabitatCorridors) {
		ArrayList<Habitat> matchingValues = new ArrayList<>();
		
		//case: if 2 habitat corridors are the same size, preferences for both must be equal
		while (!matchHabitatCorridors.isEmpty()) {
			
			Entry<Habitat, Integer> firstEntry = matchHabitatCorridors.entrySet().iterator().next();
			matchingValues.add(firstEntry.getKey());
			matchHabitatCorridors.remove(firstEntry.getKey());
			
			//find all corridors with same size, put them in a list, decrement rank value by each match
			for (Entry<Habitat, Integer> entry : matchHabitatCorridors.entrySet()) {
				if (firstEntry.getValue() == entry.getValue()) {
					matchingValues.add(entry.getKey());
					rankValue--;
				}
			}
			
			//remove all matching corridors from the linked hashmap
			for (Habitat hab : matchingValues) {
				matchHabitatCorridors.remove(hab);
			}
			
			//check if deck tiles have a match in the list for this level of ranking preference
			for (int i = 0; i < deckTiles.size(); i++) {
				if (!ranked[i]) { //if tile isn't accounted for/ranked already
					Habitat tileHab1 = deckTiles.get(i).getHabitat1();
					Habitat tileHab2 = deckTiles.get(i).getHabitat2();
					for (int j = 0; j < matchingValues.size(); j++) {
						if (matchingValues.get(j) == tileHab1 || matchingValues.get(j) == tileHab2) {
							preferences[i] = rankValue; //give the tile the rank value
							prefHabitats[i] = matchingValues.get(j);
							ranked[i] = true;
							matchingValues.remove(j);
							break;
						}
					}
				}
			}
			
			matchingValues.clear();
			rankValue--;
		}
		
		//System.out.println("preferences: " + Arrays.toString(preferences));
		return preferences;
	}
	
	//for each habitat type of each pref-ranked deck tile, see if there is space to put that tile down on map
	//if there is no space, set preference to -1, matching edge to -1 and row/col location to -1 for that tile
	private void checkTilePlacementsPossible(List<HabitatTile> deckTiles, Player player) {
		boolean locationFound;
		System.out.println(Arrays.toString(prefHabitats));
		for (int i = 0; i < prefHabitats.length; i++) {
			locationFound = false;
			List<HabitatTile> habitatCorridor = player.getLongestCorridor(prefHabitats[i]);
			
			for (int j = 0; j < habitatCorridor.size()/2; j++) {
				HabitatTile startTile = habitatCorridor.get(j);
				locationFound = areAdjacentTilesFree(startTile, player);
				if (locationFound) {
					System.out.println("location found: tileid " +startTile.getTileID());
					findTilePosition(deckTiles, startTile, player, i);
					break;
				}
				else if (!locationFound) {
					HabitatTile endTile = habitatCorridor.get(habitatCorridor.size()-j-1);
					locationFound = areAdjacentTilesFree(endTile, player);
					if (locationFound) {
						System.out.println("location found: tileid " +startTile.getTileID());
						findTilePosition(deckTiles, endTile, player, i);
						break;
					}
				}
			}
			if (!locationFound) {
				System.out.println("location not found");
				preferences[i] = -1;
				prefNumRotations[i] = -1;
				prefTileRowsAndColumns[0][i] = -1;
				prefTileRowsAndColumns[1][i] = -1;
			}
		}
	}
	
	private boolean areAdjacentTilesFree(HabitatTile tile, Player player) {
		HabitatTile[] adjacentTiles = Scoring.getAdjacentTiles(tile, player.getMap());
		for (HabitatTile adjacentTile : adjacentTiles) {
			if (adjacentTile == null) {
				return true;
			}
		}
		return false;
	}
	
	//you want to place this particular decktile next to the location tile, and rotate it so the habitats line up
	private void findTilePosition(List<HabitatTile> deckTiles, HabitatTile locationTile, Player player, int index) {
		int row = locationTile.getMapPosition()[0]; //set these initially to where we are going from
		int col = locationTile.getMapPosition()[1];
		int[] rowShift = new int[]{-1, 0, +1, +1, 0, -1};
		int[] colShiftEven = new int[]{1, 1, 1, 0, -1, 0};
		int[] colShiftOdd = new int[]{0, 1, 0, -1, -1, -1};
		HabitatTile[] adjacentTiles = Scoring.getAdjacentTiles(locationTile, player.getMap());
		int matchingEdge = 0;
		
		for (int i = 0; i < adjacentTiles.length; i++) {
			if (adjacentTiles[i] == null) {
				matchingEdge = i;
				if (row % 2 == 0) { //if it's an even row
					col += colShiftEven[i];
				}
				else { //if it's an odd row
					col += colShiftOdd[i];
				}
				row += rowShift[i];
			}
		}
		
		prefTileRowsAndColumns[0][index] = row;
		prefTileRowsAndColumns[1][index] = col;
		System.out.println("row: " +row+ ", col: " +col);
		findRotationsNeeded(deckTiles, locationTile, index, matchingEdge);
		
	}
	
	private void findRotationsNeeded(List<HabitatTile> deckTiles, HabitatTile locationTile, int index, int matchingEdge) {
		int rotations = 0;
		HabitatTile tileToPlace = deckTiles.get(index);
		int secondTileMatchingEdge = (matchingEdge+3)%6;
		while (locationTile.getEdge(matchingEdge).getHabitatType() != tileToPlace.getEdge(secondTileMatchingEdge).getHabitatType()) {
			tileToPlace.rotateTile(1);
			rotations++;
			if (rotations > 6) {
				throw new IllegalArgumentException("tile doesnt have a habitat match with the tile you're putting it next to.");
			}
		}
		System.out.println("numrotations: " +rotations);
		prefNumRotations[index] = rotations;
	}

}
