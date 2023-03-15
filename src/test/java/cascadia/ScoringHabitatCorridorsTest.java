package cascadia;

import cascadia.HabitatTile.Habitat;
import cascadia.scoring.ScoringHabitatCorridors;
import org.junit.Before;
import org.junit.Test;
//import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class ScoringHabitatCorridorsTest {
	private PlayerMap map;

    @Before
    public void generateMap() {
        map = new PlayerMap();
        map.clearTileBoard();
    }
    
    protected static HabitatTile newTile(Habitat hab1, Habitat hab2) {
        HabitatTile tile = new HabitatTile(hab2, hab1, 1);
        return tile;
    }
    
    @Test
    public void testCorridorBasic() {
    	map.addTileToMap(newTile(Habitat.Forest, Habitat.River), 8, 8);
    	map.addTileToMap(newTile(Habitat.Prairie, Habitat.Forest), 7, 8);
    	map.addTileToMap(newTile(Habitat.River, Habitat.River), 7, 9);
    	map.addTileToMap(newTile(Habitat.River, Habitat.Mountain), 6, 9);
    	//Display.displayTileMap(map);
    	
    	ArrayList<HabitatTile> longestRiver = ScoringHabitatCorridors.findLongestHabitatCorridor(map, Habitat.River);
    	assertEquals(3, longestRiver.size());
    	ArrayList<HabitatTile> longestForest = ScoringHabitatCorridors.findLongestHabitatCorridor(map, Habitat.Forest);
    	assertEquals(2, longestForest.size());
    	ArrayList<HabitatTile> longestPrairie = ScoringHabitatCorridors.findLongestHabitatCorridor(map, Habitat.Prairie);
    	assertEquals(1, longestPrairie.size());
    	ArrayList<HabitatTile> longestMountain = ScoringHabitatCorridors.findLongestHabitatCorridor(map, Habitat.Mountain);
    	assertEquals(1, longestMountain.size());
    }
    
    @Test
    public void testCorridorSidesUnmatching() {
    	map.addTileToMap(newTile(Habitat.Forest, Habitat.River), 8, 8);
    	map.addTileToMap(newTile(Habitat.Forest, Habitat.Prairie), 7, 8);
    	map.addTileToMap(newTile(Habitat.Mountain, Habitat.River), 7, 9);
    	//Display.displayTileMap(map);
    	
    	ArrayList<HabitatTile> longestForest = ScoringHabitatCorridors.findLongestHabitatCorridor(map, Habitat.Forest);
    	assertEquals(1, longestForest.size());
    	ArrayList<HabitatTile> longestRiver = ScoringHabitatCorridors.findLongestHabitatCorridor(map, Habitat.River);
    	assertEquals(1, longestRiver.size());
    }
    
//    @Test
//    public void testCorridorBasic() {
//    	
//    }
    
    
    
}
