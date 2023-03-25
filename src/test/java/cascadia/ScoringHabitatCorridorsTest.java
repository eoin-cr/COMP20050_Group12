package cascadia;

import cascadia.HabitatTile.Habitat;
import cascadia.scoring.ScoringHabitatCorridors;
import org.junit.Before;
import org.junit.Test;
//import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class ScoringHabitatCorridorsTest {
	private PlayerMap map;

    @Before
    public void generateMap() {
        map = new PlayerMap();
        map.clearTileBoard();
    }
    
    protected static HabitatTile newTile(Habitat hab1, Habitat hab2) {
		return new HabitatTile(hab2, hab1, 1);
    }
    
    @Test
    public void testCorridorBasic() {
    	map.addTileToMap(newTile(Habitat.Forest, Habitat.River), 8, 8);
    	map.addTileToMap(newTile(Habitat.Prairie, Habitat.Forest), 7, 8);
    	map.addTileToMap(newTile(Habitat.River, Habitat.River), 7, 9);
    	map.addTileToMap(newTile(Habitat.River, Habitat.Mountain), 6, 9);

    	List<HabitatTile> longestRiver = ScoringHabitatCorridors.findLongestHabitatCorridor(map, Habitat.River);
    	assertEquals(3, longestRiver.size());
    	List<HabitatTile> longestForest = ScoringHabitatCorridors.findLongestHabitatCorridor(map, Habitat.Forest);
    	assertEquals(2, longestForest.size());
    	List<HabitatTile> longestPrairie = ScoringHabitatCorridors.findLongestHabitatCorridor(map, Habitat.Prairie);
    	assertEquals(1, longestPrairie.size());
    	List<HabitatTile> longestMountain = ScoringHabitatCorridors.findLongestHabitatCorridor(map, Habitat.Mountain);
    	assertEquals(1, longestMountain.size());
    }
    
    @Test
    public void testCorridorSidesNotMatching() {
    	map.addTileToMap(newTile(Habitat.Forest, Habitat.River), 8, 8);
    	map.addTileToMap(newTile(Habitat.Forest, Habitat.Prairie), 7, 8);
    	map.addTileToMap(newTile(Habitat.Mountain, Habitat.River), 7, 9);
    	//Display.displayTileMap(map);
    	
    	List<HabitatTile> longestForest = ScoringHabitatCorridors.findLongestHabitatCorridor(map, Habitat.Forest);
    	assertEquals(1, longestForest.size());
    	List<HabitatTile> longestRiver = ScoringHabitatCorridors.findLongestHabitatCorridor(map, Habitat.River);
    	assertEquals(1, longestRiver.size());
    }
    
    @Test
    public void testCorridorScoring() {
    	Player p1 = new Player("p1");
    	PlayerMap m1 = p1.getMap();
    	Player p2 = new Player("p2");
    	PlayerMap m2 = p2.getMap();
    	
    	List<Player> players = new ArrayList<>();
    	players.add(p1);
    	players.add(p2);
    	
    	m1.clearTileBoard();
    	m2.clearTileBoard();
    	
    	m1.addTileToMap(newTile(Habitat.Forest, Habitat.River), 8, 8);
    	m1.addTileToMap(newTile(Habitat.Forest, Habitat.Prairie), 7, 8);
    	m1.addTileToMap(newTile(Habitat.River, Habitat.Wetland), 7, 9);
    	m1.addTileToMap(newTile(Habitat.River, Habitat.River), 9, 9);
    	m1.addTileToMap(newTile(Habitat.River, Habitat.Mountain), 6, 8);
    	List<HabitatTile> longestRiver = ScoringHabitatCorridors.findLongestHabitatCorridor(m1, Habitat.River);
    	assertEquals(3, longestRiver.size());
    	Display.displayTileMap(p1.getMap());
    	
    	m2.addTileToMap(newTile(Habitat.Forest, Habitat.Prairie), 8, 8);
    	m2.addTileToMap(newTile(Habitat.Prairie, Habitat.Forest), 7, 8);
    	m2.addTileToMap(newTile(Habitat.Forest, Habitat.Wetland), 7, 9);
    	m2.addTileToMap(newTile(Habitat.Prairie, Habitat.Prairie), 9, 9);
    	m2.addTileToMap(newTile(Habitat.Forest, Habitat.River), 9, 8);
    	m2.addTileToMap(newTile(Habitat.Mountain, Habitat.Mountain), 9, 10);
    	List<HabitatTile> longestForest = ScoringHabitatCorridors.findLongestHabitatCorridor(m2, Habitat.Forest);
    	assertEquals(3, longestForest.size());
    	List<HabitatTile> longestPrairie = ScoringHabitatCorridors.findLongestHabitatCorridor(m2, Habitat.Prairie);
    	assertEquals(2, longestPrairie.size());
    	Display.displayTileMap(p2.getMap());
    	
    	ScoringHabitatCorridors.habitatCorridorScoring(players);
    	assertEquals(7, p1.getTotalPlayerScore());
    	assertEquals(8, p2.getTotalPlayerScore());
    	ScoringHabitatCorridors.longestOverallCorridorsBonusScoring(players);
    	assertEquals(11, p1.getTotalPlayerScore());
    	assertEquals(14, p2.getTotalPlayerScore());
    }
    
    
    
}
