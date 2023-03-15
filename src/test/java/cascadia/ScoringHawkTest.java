package cascadia;

import cascadia.scoring.ScoringHawk;
import cascadia.scoring.ScoringSalmon;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;
import org.junit.Before;
import static org.junit.Assert.*;

public class ScoringHawkTest {
    private PlayerMap map;
    
    @Before
    public void generateMap() {
        map = new PlayerMap();
        map.clearTileBoard();
    }

    protected static HabitatTile newTile(WildlifeToken token) {
        HabitatTile tile = new HabitatTile(HabitatTile.Habitat.Prairie, HabitatTile.Habitat.Prairie, 1);
        tile.setTokenForTesting(token);
        return tile;
    }
    
    protected static HabitatTile newTileNoToken() {
        HabitatTile tile = new HabitatTile(HabitatTile.Habitat.Prairie, HabitatTile.Habitat.Prairie, 1);
        return tile;
    }
    
    @Test
    public void testHawk1Valid() {
    	 map.addTileToMap(newTile(WildlifeToken.Hawk), 8, 8);
    	 assertEquals(2, ScoringHawk.calculateScore(map, ScoringHawk.Option.H1));
    	 map.addTileToMap(newTileNoToken(), 7, 8);
    	 assertEquals(2, ScoringHawk.calculateScore(map, ScoringHawk.Option.H1));
    	 map.addTileToMap(newTile(WildlifeToken.Hawk), 6, 8);
    	 assertEquals(5, ScoringHawk.calculateScore(map, ScoringHawk.Option.H1));
    	 //Display.displayTileMap(map);
    }
    
    @Test
    public void testHawk1InvalidatedHawk() {
    	 map.addTileToMap(newTile(WildlifeToken.Hawk), 8, 8);
    	 assertEquals(2, ScoringHawk.calculateScore(map, ScoringHawk.Option.H1));
    	 map.addTileToMap(newTile(WildlifeToken.Hawk), 8, 9);
    	 assertEquals(0, ScoringHawk.calculateScore(map, ScoringHawk.Option.H1));
    }
    
    @Test
    public void testHawk2Valid() {
    	map.addTileToMap(newTile(WildlifeToken.Hawk), 8, 8);
   	 	assertEquals(0, ScoringHawk.calculateScore(map, ScoringHawk.Option.H2));
   	 	map.addTileToMap(newTileNoToken(), 7, 9);
   	 	assertEquals(0, ScoringHawk.calculateScore(map, ScoringHawk.Option.H2));
   	 	map.addTileToMap(newTile(WildlifeToken.Hawk), 6, 9);
   	 	assertEquals(2, ScoringHawk.calculateScore(map, ScoringHawk.Option.H2));
   	 	//Display.displayTileMap(map);
   	 	
    }
    
    @Test
    public void testHawk2InvalidWrongDirection() {
    	map.addTileToMap(newTile(WildlifeToken.Hawk), 8, 8);
   	 	map.addTileToMap(newTileNoToken(), 7, 9);
   	 	map.addTileToMap(newTile(WildlifeToken.Hawk), 6, 8);
   	 	assertEquals(0, ScoringHawk.calculateScore(map, ScoringHawk.Option.H2));
   	 	//Display.displayTileMap(map);
   	 	
    }
    
    @Test
    public void testHawk2Invalid2Steps() {
    	map.addTileToMap(newTile(WildlifeToken.Hawk), 8, 8);
   	 	map.addTileToMap(newTileNoToken(), 7, 9);
   	 	map.addTileToMap(newTileNoToken(), 6, 9);
   	 	map.addTileToMap(newTile(WildlifeToken.Hawk), 5, 10);
   	 	assertEquals(0, ScoringHawk.calculateScore(map, ScoringHawk.Option.H2));
   	 	//Display.displayTileMap(map);
    }
    
    @Test
    public void testHawk3Valid() {
    	map.addTileToMap(newTile(WildlifeToken.Hawk), 8, 8);
   	 	map.addTileToMap(newTileNoToken(), 7, 9);
   	 	map.addTileToMap(newTileNoToken(), 6, 9);
   	 	map.addTileToMap(newTile(WildlifeToken.Hawk), 5, 10);
   	 	assertEquals(3, ScoringHawk.calculateScore(map, ScoringHawk.Option.H3));
   	 	map.addTileToMap(newTileNoToken(), 9, 9);
   	 	map.addTileToMap(newTile(WildlifeToken.Hawk), 10, 9);
   	 	assertEquals(6, ScoringHawk.calculateScore(map, ScoringHawk.Option.H3));
   	 	//Display.displayTileMap(map);
    }
    
    @Test
    public void testHawk3Invalidated() {
    	map.addTileToMap(newTile(WildlifeToken.Hawk), 8, 8);
   	 	map.addTileToMap(newTileNoToken(), 7, 9);
   	 	map.addTileToMap(newTileNoToken(), 6, 9);
   	 	map.addTileToMap(newTile(WildlifeToken.Hawk), 5, 10);
   	 	assertEquals(3, ScoringHawk.calculateScore(map, ScoringHawk.Option.H3));
   	 	map.addTileToMap(newTile(WildlifeToken.Hawk), 4, 10);
   	 	assertEquals(0, ScoringHawk.calculateScore(map, ScoringHawk.Option.H3));
   	 	//Display.displayTileMap(map);
    }
    
    @Test
    public void testHawk3Interrupted() {
    	map.addTileToMap(newTile(WildlifeToken.Hawk), 8, 8);
   	 	map.addTileToMap(newTileNoToken(), 7, 9);
   	 	map.addTileToMap(newTile(WildlifeToken.Fox), 6, 9);
   	 	map.addTileToMap(newTileNoToken(), 5, 10);
   	 	map.addTileToMap(newTile(WildlifeToken.Hawk), 4, 10);
   	 	assertEquals(0, ScoringHawk.calculateScore(map, ScoringHawk.Option.H3));
   	 	//Display.displayTileMap(map);
    }
    
    @Test
    public void testHawk3IncorrectLineOfSight() {
    	map.addTileToMap(newTile(WildlifeToken.Hawk), 8, 8);
   	 	map.addTileToMap(newTileNoToken(), 7, 9);
   	 	map.addTileToMap(newTileNoToken(), 6, 8);
   	 	map.addTileToMap(newTileNoToken(), 5, 9);
   	 	map.addTileToMap(newTile(WildlifeToken.Hawk), 4, 8);
   	 	assertEquals(0, ScoringHawk.calculateScore(map, ScoringHawk.Option.H3));
   	 	//Display.displayTileMap(map);
    }

}
