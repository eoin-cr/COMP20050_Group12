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
		//resetting all info
		rankValue = 4;
		Arrays.fill(preferences, 0);
		Arrays.fill(ranked, false);
		Arrays.fill(prefHabitats, null);
		Arrays.fill(prefNumRotations, 0);
		for (int[] row : prefTileRowsAndColumns) {
			Arrays.fill(row, 0);
		}
		
		//first give preference to corridor gaps between players of size 1,0,-1 to catch up
		System.out.println("\nUsing gap ranking strat!");
		rankGaps(deckTiles, player, nextPlayer);
		
		if (!BotTimer.isTimeLeft()) { //check if time left
			checkTilePlacementsPossible(deckTiles, player);
			return preferences;
		}

		boolean allRanked = true;
		for (int i = 0; i < ranked.length; i++) {
			if (!ranked[i]) {
				allRanked = false;
				break;
			}
		}
		
		if (!allRanked) {
			Random rand = new Random();
			//int strategyChoice = 0; //for testing
			int strategyChoice = rand.nextInt(NUM_TILE_STRATS);

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
		}

		//rankings done, now see if all decktiles can be placed on player's map, find location, rotations etc.
		checkTilePlacementsPossible(deckTiles, player);
		return preferences;
	}
	
	//prioritise gaps of size 1, then size 0, then size -1 between the two players' corridors, so you can either move ahead or catch up
	int[] rankGaps(List<HabitatTile> deckTiles, Player player, Player nextPlayer) {
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
			if (!BotTimer.isTimeLeft()) {
				break;
			}
		}

		return preferences;
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
			
		return maxToMinCorridors;
	}

	//helper function, matches deckTile preferences from 4 (highest) to 1 (lowest) to the ordered list of habitats given
	//also finds which habitat of the 2 tile habitats was matched
	int[] rankDeckTiles(List<HabitatTile> deckTiles, LinkedHashMap<Habitat, Integer> matchHabitatCorridors) {
		ArrayList<Habitat> matchingValues = new ArrayList<>();
		
		//case: if 2 habitat corridors are the same size, preferences for both must be equal
		while (!matchHabitatCorridors.isEmpty() && BotTimer.isTimeLeft()) {
			
			Entry<Habitat, Integer> firstEntry = matchHabitatCorridors.entrySet().iterator().next();
			matchingValues.add(firstEntry.getKey());
			matchHabitatCorridors.remove(firstEntry.getKey());
			
			//find all corridors with same size, put them in a list, decrement rank value by each match
			for (Entry<Habitat, Integer> entry : matchHabitatCorridors.entrySet()) {
				if (firstEntry.getValue() == entry.getValue()) {
					matchingValues.add(entry.getKey());
					if (rankValue > 0)
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
			if (rankValue > 0) {
			rankValue--;
			}
		}
		
		return preferences;
	}
	
	//for each habitat type of each pref-ranked deck tile, see if there is space to put that tile down on map
	//if there is no space, set preference to -1, matching edge to -1 and row/col location to -1 for that tile
	private void checkTilePlacementsPossible(List<HabitatTile> deckTiles, Player player) {
		boolean locationFound;
		for (int i = 0; i < prefHabitats.length; i++) {
			locationFound = false;
			if (prefHabitats[i] != null) {
				List<HabitatTile> habitatCorridor = player.getLongestCorridor(prefHabitats[i]);
				
				for (int j = 0; j <= habitatCorridor.size()/2; j++) {
					HabitatTile startTile = habitatCorridor.get(j);
					locationFound = areAdjacentTilesFree(startTile, prefHabitats[i], player);
					if (locationFound) {
						findTilePosition(deckTiles, startTile, prefHabitats[i], player, i);
						break;
					}
					else if (!locationFound) {
						HabitatTile endTile = habitatCorridor.get(habitatCorridor.size()-1-j);
						locationFound = areAdjacentTilesFree(endTile, prefHabitats[i], player);
						if (locationFound) {
							findTilePosition(deckTiles, endTile, prefHabitats[i], player, i);
							break;
						}
					}
				}
			}
			if (!locationFound) {
				preferences[i] = -10; //absolutely don't pick this option from the deck
				//otherwise i'd need logic to put down a random tile anywhere without matching to a corridor
				prefNumRotations[i] = -1;
				prefTileRowsAndColumns[0][i] = -1;
				prefTileRowsAndColumns[1][i] = -1;
			}
		}
	}
	
	private boolean areAdjacentTilesFree(HabitatTile tile, Habitat habitat, Player player) {
		HabitatTile[] adjacentTiles = Scoring.getAdjacentTiles(tile, player.getMap());
		for (int i = 0; i < Constants.NUM_EDGES; i++) {
			if (tile.getEdge(i).getHabitatType() == habitat && adjacentTiles[i] == null) {
				return true;
			}
		}
		return false;
	}
	
	//you want to place this particular decktile next to the location tile, and rotate it so the habitats line up
	private void findTilePosition(List<HabitatTile> deckTiles, HabitatTile locationTile, Habitat habitat, Player player, int index) {
		HabitatTile[] adjacentTiles = Scoring.getAdjacentTiles(locationTile, player.getMap());
		List<int[]> adjacentTilePositions = Scoring.getAdjacentTileMapPositions(locationTile, player.getMap());
		
		int matchingEdge = -1;
		int[] rowcol;
		
		for (int i = 0; i < Constants.NUM_EDGES; i++) {
			if (locationTile.getEdge(i).getHabitatType() == habitat && adjacentTiles[i] == null) {
				matchingEdge = i;
				break;
			}
		}
		
		if (matchingEdge != -1) {
			rowcol = adjacentTilePositions.get(matchingEdge);
			prefTileRowsAndColumns[0][index] = rowcol[0]; //save row
			prefTileRowsAndColumns[1][index] = rowcol[1]; //save column
		}
		
		else {
			throw new IllegalArgumentException("matching edge not found for positioning decktile into map.");
		}
		
		findRotationsNeeded(deckTiles, locationTile, index, matchingEdge);
	}
	
	private void findRotationsNeeded(List<HabitatTile> deckTiles, HabitatTile locationTile, int index, int matchingEdge) {
		int rotations = 0;
		HabitatTile tileToPlace = deckTiles.get(index);
		int secondTileMatchingEdge = (matchingEdge+3) % Constants.NUM_EDGES;
		while (locationTile.getEdge(matchingEdge).getHabitatType() != tileToPlace.getEdge(secondTileMatchingEdge).getHabitatType()) {
			tileToPlace.rotateTile(1);
			rotations++;
			if (rotations > Constants.NUM_EDGES) {
				throw new IllegalArgumentException("tile doesnt have a habitat match with the tile you're putting it next to.");
			}
		}
		prefNumRotations[index] = rotations;
	}
	
	public int[] getDeckTilePlacementChoice(int index) {
		int row = prefTileRowsAndColumns[0][index];
		int col = prefTileRowsAndColumns[1][index];
		return new int[]{row, col};
	}
	
	public int getDeckTileNumRotations(int index) {
		return prefNumRotations[index];
	}

}
