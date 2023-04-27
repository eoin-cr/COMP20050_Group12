/*
	COMP20050 Group 12
	Eoin Creavin – Student ID: 21390601
	eoin.creavin@ucdconnect.ie
	GitHub ID: eoin-cr

	Mynah Bhattacharyya – Student ID: 21201085
	malhar.bhattacharyya@ucdconnect.ie
	GitHub ID: mynah-bird

	Ben McDowell – Student ID: 21495144
	ben.mcdowell@ucdconnect.ie
	GitHub ID: Benmc1
 */

package cascadia;

import org.junit.Ignore;
import org.junit.Test;

import cascadia.HabitatTile.Habitat;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class TileBotTest {
	
	protected static HabitatTile newTile(Habitat hab1, Habitat hab2, WildlifeToken token) {
        HabitatTile tile = new HabitatTile(hab1, hab2, 1);
        tile.setTokenForTesting(token);
        return tile;
    }

	@Test
	public void testMaxMinCorrs1() {
		TileBot bot = new TileBot();
		int[] corridorSizes = {3,2,1,5,4};
		HashMap<Habitat, Integer> corridorPairs = bot.hashCorridors(corridorSizes);
		//indexing -> 0: Forest corridor, 1: Wetland corridor, 2: River corridor, 3: Mountain corridor, 4: Prairie corridor
		LinkedHashMap<Habitat, Integer> maxMinCorrs = bot.findMaxToMinHabitatCorridors(corridorPairs);
		String properOrder = "{Mountain=5, Prairie=4, Forest=3, Wetland=2, River=1}";
		assertEquals(properOrder, maxMinCorrs.toString());
	}
	
	@Test
	public void testMinMaxCorrs2() {
		TileBot bot = new TileBot();
		int[] corridorSizes = {1,2,1,5,4};
		HashMap<Habitat, Integer> corridorPairs = bot.hashCorridors(corridorSizes);
		//indexing -> 0: Forest corridor, 1: Wetland corridor, 2: River corridor, 3: Mountain corridor, 4: Prairie corridor
		LinkedHashMap<Habitat, Integer> minMaxCorrs = bot.findMinToMaxHabitatCorridors(corridorPairs);
		String properOrder = "{River=1, Forest=1, Wetland=2, Prairie=4, Mountain=5}";
		assertEquals(properOrder, minMaxCorrs.toString());
		LinkedHashMap<Habitat, Integer> maxMinCorrs = bot.findMaxToMinHabitatCorridors(corridorPairs);
		String properOrder2 = "{Mountain=5, Prairie=4, Wetland=2, Forest=1, River=1,}";
		assertEquals(properOrder2, maxMinCorrs.toString());
	}
	@Ignore
	@Test
	public void rankGaps() {
		TileBot bot = new TileBot();
		Player p1 = new Player("p1");
		Player p2 = new Player("p2");
		int[] p1corrs = {1,1,1,1,1};
		int[] p2corrs = {1,2,3,4,5};
		p1.setLongestCorridorSizes(p1corrs);
		p2.setLongestCorridorSizes(p2corrs);
		
		ArrayList<HabitatTile> deckTiles = new ArrayList<>();
		deckTiles.add(newTile(Habitat.River, Habitat.Forest, WildlifeToken.Fox));
		deckTiles.add(newTile(Habitat.Forest, Habitat.Forest, WildlifeToken.Fox));
		deckTiles.add(newTile(Habitat.Prairie, Habitat.Wetland, WildlifeToken.Fox));
		deckTiles.add(newTile(Habitat.Mountain, Habitat.Wetland, WildlifeToken.Fox));
		
		int[] rankGaps = bot.rankGaps(deckTiles, p1, p2);
	}
	@Ignore
	@Test
	public void testRankDeckTiles1() {
		TileBot bot = new TileBot();
		ArrayList<HabitatTile> deckTiles = new ArrayList<>();
		deckTiles.add(newTile(Habitat.River, Habitat.Forest, WildlifeToken.Fox));
		deckTiles.add(newTile(Habitat.Forest, Habitat.Forest, WildlifeToken.Fox));
		deckTiles.add(newTile(Habitat.Prairie, Habitat.Wetland, WildlifeToken.Fox));
		deckTiles.add(newTile(Habitat.Mountain, Habitat.Wetland, WildlifeToken.Fox));
		LinkedHashMap<Habitat, Integer> matchHabitatCorridors = new LinkedHashMap<>();
		//indexing -> 0: Forest corridor, 1: Wetland corridor, 2: River corridor, 3: Mountain corridor, 4: Prairie corridor
		matchHabitatCorridors.put(Habitat.Forest, 1); //4
		matchHabitatCorridors.put(Habitat.Wetland, 2); //3
		matchHabitatCorridors.put(Habitat.River, 3); //2
		matchHabitatCorridors.put(Habitat.Mountain, 3); //2
		matchHabitatCorridors.put(Habitat.Prairie, 4); //1
		
		int[] rankedDeck = bot.rankDeckTiles(deckTiles, matchHabitatCorridors);
		String ranked = "[4, 0, 3, 1]";
		assertEquals(ranked, Arrays.toString(rankedDeck));
	}
	@Ignore
	@Test
	public void testRankDeckTiles2() {
		TileBot bot = new TileBot();
		ArrayList<HabitatTile> deckTiles = new ArrayList<>();
		deckTiles.add(newTile(Habitat.River, Habitat.Forest, WildlifeToken.Fox));
		deckTiles.add(newTile(Habitat.Wetland, Habitat.Forest, WildlifeToken.Fox));
		deckTiles.add(newTile(Habitat.Mountain, Habitat.Wetland, WildlifeToken.Fox));
		deckTiles.add(newTile(Habitat.Mountain, Habitat.River, WildlifeToken.Fox));
		LinkedHashMap<Habitat, Integer> matchHabitatCorridors = new LinkedHashMap<>();
		//indexing -> 0: Forest corridor, 1: Wetland corridor, 2: River corridor, 3: Mountain corridor, 4: Prairie corridor
		matchHabitatCorridors.put(Habitat.Forest, 1); //4
		matchHabitatCorridors.put(Habitat.Wetland, 2); //3
		matchHabitatCorridors.put(Habitat.River, 3); //2
		matchHabitatCorridors.put(Habitat.Mountain, 3); //2
		matchHabitatCorridors.put(Habitat.Prairie, 4); //1
		
		int[] rankedDeck = bot.rankDeckTiles(deckTiles, matchHabitatCorridors);
		String ranked = "[4, 3, 1, 1]";
		assertEquals(ranked, Arrays.toString(rankedDeck));
	}
	@Ignore
	@Test
	public void testRankDeckTiles3() {
		TileBot bot = new TileBot();
		ArrayList<HabitatTile> deckTiles = new ArrayList<>();
		deckTiles.add(newTile(Habitat.River, Habitat.Forest, WildlifeToken.Fox));
		deckTiles.add(newTile(Habitat.Wetland, Habitat.Forest, WildlifeToken.Fox));
		deckTiles.add(newTile(Habitat.Prairie, Habitat.Wetland, WildlifeToken.Fox));
		deckTiles.add(newTile(Habitat.Mountain, Habitat.River, WildlifeToken.Fox));
		LinkedHashMap<Habitat, Integer> matchHabitatCorridors = new LinkedHashMap<>();
		//indexing -> 0: Forest corridor, 1: Wetland corridor, 2: River corridor, 3: Mountain corridor, 4: Prairie corridor
		matchHabitatCorridors.put(Habitat.Forest, 2); //4
		matchHabitatCorridors.put(Habitat.Wetland, 2); //3
		matchHabitatCorridors.put(Habitat.River, 2); //2
		matchHabitatCorridors.put(Habitat.Mountain, 2); //2
		matchHabitatCorridors.put(Habitat.Prairie, 4); //1
		
		int[] rankedDeck = bot.rankDeckTiles(deckTiles, matchHabitatCorridors);
		String ranked = "[1, 1, 0, 1]";
		assertEquals(ranked, Arrays.toString(rankedDeck));
	}
	@Ignore
	@Test
	public void testRankDeckTiles4() {
		TileBot bot = new TileBot();
		ArrayList<HabitatTile> deckTiles = new ArrayList<>();
		deckTiles.add(newTile(Habitat.Forest, Habitat.Forest, WildlifeToken.Fox));
		deckTiles.add(newTile(Habitat.Wetland, Habitat.Wetland, WildlifeToken.Fox));
		deckTiles.add(newTile(Habitat.River, Habitat.River, WildlifeToken.Fox));
		deckTiles.add(newTile(Habitat.Mountain, Habitat.Mountain, WildlifeToken.Fox));
		LinkedHashMap<Habitat, Integer> matchHabitatCorridors = new LinkedHashMap<>();
		//indexing -> 0: Forest corridor, 1: Wetland corridor, 2: River corridor, 3: Mountain corridor, 4: Prairie corridor
		matchHabitatCorridors.put(Habitat.Forest, 1); //4
		matchHabitatCorridors.put(Habitat.Wetland, 2); //3
		matchHabitatCorridors.put(Habitat.River, 3); //2
		matchHabitatCorridors.put(Habitat.Mountain, 4); //2
		matchHabitatCorridors.put(Habitat.Prairie, 5); //1
		
		int[] rankedDeck = bot.rankDeckTiles(deckTiles, matchHabitatCorridors);
		String ranked = "[4, 3, 2, 1]";
		assertEquals(ranked, Arrays.toString(rankedDeck));
	}
}
