package cascadia.scoring;

import cascadia.Display;
import cascadia.HabitatTile;
import cascadia.Player;
import cascadia.PlayerMap;

import java.util.ArrayList;
import java.util.List;

public class ScoringHabitatCorridors {
	/////////////////////////
	//FUNCTIONS FOR SCORING ALL PLAYERS' HABITAT CORRIDORS AT VERY END
	/////////////////////////
	/**
	 * Sets each player's score for their personal longest habitat corridors of all 5 types.
	 * Also saves the size of each longest habitat corridor in player's info
	 */
	public static void habitatCorridorScoring(List<Player> players) {
		for (Player p : players){
			int score = 0;
			for (int i = 0; i < HabitatTile.Habitat.values().length; i++) {
				int corridorSize = findLongestHabitatCorridor(p.getMap(), HabitatTile.Habitat.values()[i]).size();
				p.setLongestCorridorSize(i, corridorSize);
				score += corridorSize;
			}
			p.addToTotalPlayerScore(score);
			Display.outln(p.getPlayerName() + " scored " +score+ " points for Habitat Corridors.");
		}
		Display.outln("");
	}

	/**
	 * Finds longest and second-longest habitat corridors amongst players, awards bonuses based on number of players
	 */
	public static void longestOverallCorridorsBonusScoring(List<Player> players) {
		//indexing:
		//0 stores forest
		//1 stores wetland
		//2 stores river
		//3 stores mountain
		//4 stores prairie

		int numPlayers = players.size();
		int[] longestCorridorSizes = getLongestCorridor(players);
		int[] secondLongestCorridorSizes = getSecondLongestCorridor(players, longestCorridorSizes);

		calculateBonusPoints(numPlayers, players, longestCorridorSizes);

		// CALCULATE 4
		if (numPlayers == 3 || numPlayers == 4) {
			calculate3PlusPlayerScoring(players, longestCorridorSizes, secondLongestCorridorSizes);
		}
		Display.outln("");
	}

	private static int[] getLongestCorridor(List<Player> players) {
		int[] longestCorridorSizes = new int[5];
		for (int i = 0; i < 5; i++) { //get longest corridors
			for (Player p : players) {
				if (p.getLongestCorridorSizes()[i] > longestCorridorSizes[i]) {
					longestCorridorSizes[i] = p.getLongestCorridorSizes()[i];
				}
			}
		}
		return longestCorridorSizes;
	}

	private static int[] getSecondLongestCorridor(List<Player> players, int[] longestCorridorSizes) {
		int[] secondLongestCorridorSizes = new int[5];
		for (int i = 0; i < 5; i++) {
			for (Player p : players) { //get second longest corridors
				if (p.getLongestCorridorSizes()[i] < longestCorridorSizes[i] && p.getLongestCorridorSizes()[i] > secondLongestCorridorSizes[i]) {
					secondLongestCorridorSizes[i] = p.getLongestCorridorSizes()[i];

				}
			}
		}
		return secondLongestCorridorSizes;
	}

	private static void calculateBonusPoints(int numPlayers, List<Player> players, int[] longestCorridorSizes) {
		//bonus point scoring
		if (numPlayers == 2) {
			ArrayList<Player> corridorSizeMatchPlayers = new ArrayList<>();

			for (int i = 0; i < 5; i++) {
				corridorSizeMatchPlayers.clear();
				for (Player p : players) {
					if (p.getLongestCorridorSizes()[i] == longestCorridorSizes[i]) {
						corridorSizeMatchPlayers.add(p);
					}
				}
				if (corridorSizeMatchPlayers.size() > 1) { //tie
					displayCorridorTie(corridorSizeMatchPlayers, i);
				}
				else { //one person has the longest corridor
					Display.outln(corridorSizeMatchPlayers.get(0).getPlayerName() + " made the longest "
							+ HabitatTile.Habitat.getName(i)+ " corridor. 2 bonus points.");
					corridorSizeMatchPlayers.get(0).addToTotalPlayerScore(2);
				}
			}
		}

	}
	private static void calculate3PlusPlayerScoring(List<Player> players, int[] longestCorridorSizes,
													int[] secondLongestCorridorSizes) {
		ArrayList<Player> corridorSizeMatchPlayers = new ArrayList<>();
		boolean[] tieFound = new boolean[5];

		for (int i = 0; i < 5; i++) {
			corridorSizeMatchPlayers.clear();
			for (Player p : players) {
				if (p.getLongestCorridorSizes()[i] == longestCorridorSizes[i]) {
					corridorSizeMatchPlayers.add(p);
				}
			}
			if (corridorSizeMatchPlayers.size() == 1) {
				tieFound[i] = false;
				Display.outln(corridorSizeMatchPlayers.get(0).getPlayerName()  + " made the longest "
						+HabitatTile.Habitat.getName(i)+ " corridor. 3 bonus points.");
				corridorSizeMatchPlayers.get(0).addToTotalPlayerScore(3);
			}
			else if (corridorSizeMatchPlayers.size() == 2) { //tie with 2 players
				tieFound[i] = true;
				Display.out("Players: [ " +corridorSizeMatchPlayers.get(0).getPlayerName() + " "
						+corridorSizeMatchPlayers.get(1).getPlayerName() + " ] "
						+ "tied for longest " +HabitatTile.Habitat.getName(i)+ " corridor.");
				Display.outln("They each get 2 bonus points.");
				corridorSizeMatchPlayers.get(0).addToTotalPlayerScore(2);
				corridorSizeMatchPlayers.get(1).addToTotalPlayerScore(2);
			}
			else if (corridorSizeMatchPlayers.size() > 2) { //tie with 3-4 players
				tieFound[i] = true;
				displayCorridorTie(corridorSizeMatchPlayers, i);
			}
		}
		calculateSecondLongest(corridorSizeMatchPlayers, players, tieFound, secondLongestCorridorSizes);

	}
	private static void calculateSecondLongest(ArrayList<Player> corridorSizeMatchPlayers, List<Player> players,
											   boolean[] tieFound, int[] secondLongestCorridorSizes) {
		for (int i = 0; i < 5; i++) {
			corridorSizeMatchPlayers.clear();
			if (tieFound[i]) {
				for (Player p : players) {
					if (p.getLongestCorridorSizes()[i] == secondLongestCorridorSizes[i]) {
						corridorSizeMatchPlayers.add(p);
					}
				}
				if (corridorSizeMatchPlayers.size() == 1) {
					Display.outln(corridorSizeMatchPlayers.get(0).getPlayerName() + " made the second longest "
							+ HabitatTile.Habitat.getName(i) + " corridor. 1 bonus point.");
					corridorSizeMatchPlayers.get(0).addToTotalPlayerScore(1);
				}
				//else if more than 1 player gets second-largest corridor, no bonus points
			}
		}
	}

	private static void displayCorridorTie(ArrayList<Player> corridorSizeMatchPlayers, int i) {
		Display.out("Players: [ ");
		for (Player p1 : corridorSizeMatchPlayers) {
			p1.addToTotalPlayerScore(1);
			Display.out(p1.getPlayerName()+ " ");
		}
		Display.outln("] tied for longest " + HabitatTile.Habitat.getName(i)+ " corridor.");
		Display.outln("They each get 1 bonus point.");
	}

	//HELPER FUNCTIONS FOR FINDING CORRIDORS ON A MAP
	/**
	 * Helper function for Habitat corridor scoring, retrieves the longest habitat corridor of a certain type on a single player's map
	 * @return Arraylist of tiles with matching habitats
	 */
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

	/**
	 * Helper function for Habitat corridor scoring, retrieves a chunk of connected tiles on the map with matching habitat types.
	 * Habitat types match based on the edges of two adjacent tiles.
	 */
	public static void findHabitatCorridorRecursive(ArrayList<HabitatTile> habitatCorridor, HabitatTile centerTile,
													HabitatTile.Habitat habitatType, PlayerMap map) {
		HabitatTile[] adjacentTiles = Scoring.getAdjacentTiles(centerTile, map);
		HabitatTile.Habitat[] adjacentHabitats = Scoring.getAdjacentHabitats(centerTile, map);

		if (!habitatCorridor.contains(centerTile) && (centerTile.getHabitat1() == habitatType
				|| centerTile.getHabitat2() == habitatType)) {
			habitatCorridor.add(centerTile);
			for (int i = 0; i < centerTile.getEdges().size(); i++) {
				if (centerTile.getEdge(i).getHabitatType() == habitatType && adjacentHabitats[i] == habitatType) {
					findHabitatCorridorRecursive(habitatCorridor, adjacentTiles[i], habitatType, map);
				}
			}
		}

	}

}
