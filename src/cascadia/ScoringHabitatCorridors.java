package cascadia;

import java.util.ArrayList;
import java.util.List;

public class ScoringHabitatCorridors {
	
	public static void scorePlayerHabitatCorridors(Player player, HabitatTile tile) {
		//for keystone tile, just check corridors twice
		HabitatTile.Habitat[] habitats = {tile.getHabitat1(), tile.getHabitat2()};
		
		for (HabitatTile.Habitat habitatType : habitats) {
			switch (habitatType) {
			case Forest:
				int forestCorridor = findLongestHabitatCorridor(player.getMap(), HabitatTile.Habitat.Forest).size();
				player.setLongestCorridorSize(0, forestCorridor);
				break;
			case Wetland:
				int wetlandCorridor = findLongestHabitatCorridor(player.getMap(), HabitatTile.Habitat.Wetland).size();
				player.setLongestCorridorSize(1, wetlandCorridor);
				break;
			case River:
				int riverCorridor = findLongestHabitatCorridor(player.getMap(), HabitatTile.Habitat.River).size();
				player.setLongestCorridorSize(2, riverCorridor);
				break;
			case Mountain:
				int mountainCorridor = findLongestHabitatCorridor(player.getMap(), HabitatTile.Habitat.Mountain).size();
				player.setLongestCorridorSize(3, mountainCorridor);
				break;
			case Prairie:
				int prairieCorridor = findLongestHabitatCorridor(player.getMap(), HabitatTile.Habitat.Prairie).size();
				player.setLongestCorridorSize(4, prairieCorridor);
				break;
			default:
				break;
			}
		}
		
		player.calculateCorridorsPlayerScore(); //change total score for habitat corridors for player
	}
	
	//FUNCTIONS FOR SCORING ALL PLAYERS' HABITAT CORRIDORS AT VERY END
	/**
	 * Sets each player's score for their personal longest habitat corridors of all 5 types.
	 * Also saves the size of each longest habitat corridor in player's info
	 */
	public static void habitatCorridorScoring(List<Player> players) {
		for (Player p : players){
			int forestCorridor = findLongestHabitatCorridor(p.getMap(), HabitatTile.Habitat.Forest).size();
			int wetlandCorridor = findLongestHabitatCorridor(p.getMap(), HabitatTile.Habitat.Wetland).size();
			int riverCorridor = findLongestHabitatCorridor(p.getMap(), HabitatTile.Habitat.River).size();
			int mountainCorridor = findLongestHabitatCorridor(p.getMap(), HabitatTile.Habitat.Mountain).size();
			int prairieCorridor = findLongestHabitatCorridor(p.getMap(), HabitatTile.Habitat.Prairie).size();

			p.setLongestCorridorSize(0, forestCorridor);
			p.setLongestCorridorSize(1, wetlandCorridor);
			p.setLongestCorridorSize(2, riverCorridor);
			p.setLongestCorridorSize(3, mountainCorridor);
			p.setLongestCorridorSize(4, prairieCorridor);

			int score = forestCorridor + wetlandCorridor + riverCorridor + mountainCorridor + prairieCorridor;
			p.addToTotalPlayerScore(score);

			System.out.println(p.getPlayerName() + " scored " +score+ " points for Habitat Corridors.");
		}
		System.out.println();
	}

	/**
	 * Finds longest and second longest habitat corridors amongst players, awards bonuses based on number of players
	 */
	public static void longestOverallCorridorsBonusScoring(List<Player> players) {
		//indexing:
		//0 stores forest
		//1 stores wetland
		//2 stores river
		//3 stores mountain
		//4 stores prairie

		int numPlayers = players.size();
		int[] longestCorridorSizes = new int[5];
		int[] secondLongestCorridorSizes = new int[5];

		for (int i = 0; i < 5; i++) {
			longestCorridorSizes[i] = 0;
		}

		for (int i = 0; i < 5; i++) { //get longest corridors
			for (Player p : players) {
				if (p.getLongestCorridorSizes()[i] > longestCorridorSizes[i]) {
					longestCorridorSizes[i] = p.getLongestCorridorSizes()[i];
				}
			}
		}

		for (int i = 0; i < 5; i++) {
			for (Player p : players) { //get second longest corridors
				if (p.getLongestCorridorSizes()[i] < longestCorridorSizes[i] && p.getLongestCorridorSizes()[i] > secondLongestCorridorSizes[i]) {
					secondLongestCorridorSizes[i] = p.getLongestCorridorSizes()[i];

				}
			}
		}

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
					System.out.print("Players: [ ");
					for (Player p1 : corridorSizeMatchPlayers) {
						p1.addToTotalPlayerScore(1);
						System.out.print(p1.getPlayerName()+ " ");
					}
					System.out.println("] tied for longest " +HabitatTile.Habitat.getName(i)+ " corridor.");
					System.out.println("They each get 1 bonus point.");
				}
				else { //one person has longest corridor
					System.out.println(corridorSizeMatchPlayers.get(0).getPlayerName() + " made the longest "
							+ HabitatTile.Habitat.getName(i)+ " corridor. 2 bonus points.");
					corridorSizeMatchPlayers.get(0).addToTotalPlayerScore(2);
				}
			}
		}

		if (numPlayers == 3 || numPlayers == 4) {
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
					System.out.println(corridorSizeMatchPlayers.get(0).getPlayerName()  + " made the longest "
							+HabitatTile.Habitat.getName(i)+ " corridor. 3 bonus points.");
					corridorSizeMatchPlayers.get(0).addToTotalPlayerScore(3);
				}
				else if (corridorSizeMatchPlayers.size() == 2) { //tie with 2 players
					tieFound[i] = true;
					System.out.print("Players: [ " +corridorSizeMatchPlayers.get(0).getPlayerName() + " "
							+corridorSizeMatchPlayers.get(1).getPlayerName() + " ] "
							+ "tied for longest " +HabitatTile.Habitat.getName(i)+ " corridor.");
					System.out.println("They each get 2 bonus points.");
					corridorSizeMatchPlayers.get(0).addToTotalPlayerScore(2);
					corridorSizeMatchPlayers.get(1).addToTotalPlayerScore(2);
				}
				else if (corridorSizeMatchPlayers.size() > 2) { //tie with 3-4 players
					tieFound[i] = true;
					System.out.print("Players: [ ");
					for (Player p1 : corridorSizeMatchPlayers) {
						p1.addToTotalPlayerScore(1);
						System.out.print(p1.getPlayerName()+ " ");
					}
					System.out.println("] tied for longest " +HabitatTile.Habitat.getName(i)+ " corridor.");
					System.out.println("They each get 1 bonus point.");
				}
			}

			for (int i = 0; i < 5; i++) {
				corridorSizeMatchPlayers.clear();
				if (tieFound[i]) {
					for (Player p : players) {
						if (p.getLongestCorridorSizes()[i] == secondLongestCorridorSizes[i]) {
							corridorSizeMatchPlayers.add(p);
						}
					}
					if (corridorSizeMatchPlayers.size() == 1) {
						System.out.println(corridorSizeMatchPlayers.get(0).getPlayerName()  + " made the second longest "
								+HabitatTile.Habitat.getName(i)+ " corridor. 1 bonus point.");
						corridorSizeMatchPlayers.get(0).addToTotalPlayerScore(1);
					}
					//else if more than 1 player gets second largest corridor, no bonus points
				}
			}
		}
		System.out.println();
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
	//TODO: make sure edges of two tiles match up, right now just going off if one of the 2 habitats match, not edge habitat match
	public static void findHabitatCorridorRecursive(ArrayList<HabitatTile> habitatCorridor, HabitatTile centerTile, HabitatTile.Habitat habitatType, PlayerMap map) {
		HabitatTile[] adjacentTiles = Scoring.getAdjacentTiles(centerTile, map);
		HabitatTile.Habitat[] adjacentHabitats = Scoring.getAdjacentHabitats(centerTile, map);

		if (!habitatCorridor.contains(centerTile) && (centerTile.getHabitat1() == habitatType || centerTile.getHabitat2() == habitatType)) {
			habitatCorridor.add(centerTile);
			for (int i = 0; i < centerTile.getEdges().size(); i++) {
				if (centerTile.getEdge(i).getHabitatType() == habitatType && adjacentHabitats[i] == habitatType) {
					findHabitatCorridorRecursive(habitatCorridor, adjacentTiles[i], habitatType, map);
				}
			}
		}
		
	}

}
