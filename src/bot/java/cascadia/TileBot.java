package cascadia;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cascadia.HabitatTile.Habitat;

public class TileBot {
	public static final int NUM_TILE_STRATS = 2;

	public int[] chooseStrategy(Player player, Player nextPlayer) {
		int[] preferences = new int[4];
		List<HabitatTile> deckTiles = CurrentDeck.getDeckTiles();

		Random rand = new Random();
		int strategyChoice = rand.nextInt(NUM_TILE_STRATS);

		switch (strategyChoice) {
			case 0 -> preferences = constructiveGrowMinCorridorStrat(deckTiles, player);
			case 1 -> preferences = destructiveTileStrategy(deckTiles, nextPlayer);
			default -> throw new IllegalArgumentException("Unexpected value: " + strategyChoice);
		}
//		System.out.printf("TB pref: %s\n", Arrays.toString(preferences));

		return preferences;
	}

	//grows current player's smallest corridor, maintains corridor diversity
	private int[] constructiveGrowMinCorridorStrat(List<HabitatTile> deckTiles, Player player) {
		ArrayList<Habitat> minToMaxCorridors = findMinToMaxHabitatCorridors(player.getLongestCorridorSizes());
		int[] preferences = matchPreferences(deckTiles, minToMaxCorridors);
		return preferences;
	}

	//stops min corridor of next player from growing
	private int[] destructiveTileStrategy(List<HabitatTile> deckTiles, Player nextPlayer) {
		ArrayList<Habitat> nextPlayerMinToMaxCorridors = findMinToMaxHabitatCorridors(nextPlayer.getLongestCorridorSizes());
		int[] preferences = matchPreferences(deckTiles, nextPlayerMinToMaxCorridors);
		return preferences;
	}

	//helper function, finds a player's smallest to largest habitat corridors, returns a list of habitats in order
	private ArrayList<Habitat> findMinToMaxHabitatCorridors(int[] corridorSizes){
		ArrayList<Habitat> minToMaxCorridors = new ArrayList<>();
		int minIndex = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (corridorSizes[j] != -1 && corridorSizes[j] < corridorSizes[minIndex]) {
					minIndex = j;
				}
			}
			minToMaxCorridors.add(Habitat.getHabitat(minIndex));
			corridorSizes[minIndex] = -1; //habitat accounted for
		}
		return minToMaxCorridors;
	}

	//helper function, finds a player's smallest to largest habitat corridors, returns a list of habitats in order
	private ArrayList<Habitat> findMaxToMinHabitatCorridors(int[] corridorSizes){
		ArrayList<Habitat> maxToMinCorridors = new ArrayList<>();
		int maxIndex = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (corridorSizes[j] != -1 && corridorSizes[j] > corridorSizes[maxIndex]) {
					maxIndex = j;
				}
			}
			maxToMinCorridors.add(Habitat.getHabitat(maxIndex));
			corridorSizes[maxIndex] = -1; //habitat accounted for
		}
		return maxToMinCorridors;
	}

	//helper function, matches deckTile preferences from 4 (highest) to 1 (lowest) to the ordered list of habitats given
	private int[] matchPreferences(List<HabitatTile> deckTiles, ArrayList<Habitat> matchHabitatCorridors) {
		int[] preferences = new int[4]; //each decktile index has a preference rating attached to it, 4 being highest preference and 1 being lowest
		int pref = 4;

		for (Habitat hab : matchHabitatCorridors) {
			for (int i = 0; i < deckTiles.size(); i++) {
				if (deckTiles.get(i).getHabitat1() == hab || deckTiles.get(i).getHabitat2() == hab) {
					preferences[i] = pref;
					pref--;
					break;
				}
			}
		}
		return preferences;
	}

//	private int placeTileInCorridor(HabitatTile tile, Player player) {
//		int initialScore = player.getTotalPlayerScore();
//		int finalScore;
//		int changedScore;
//		Habitat habitat;
//		ArrayList<HabitatTile> corridor;
//		
//		if (player.getLongestCorridorSize(tile.getHabitat1()) < player.getLongestCorridorSize(tile.getHabitat2())) {
//			habitat = tile.getHabitat1();
//		}
//		else {
//			habitat = tile.getHabitat2();
//		}
//		
//		corridor = player.getLongestCorridor(habitat);
//		
//		
//		return changedScore;	
//	}


}
