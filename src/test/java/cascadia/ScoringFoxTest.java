package cascadia;

import cascadia.scoring.ScoringFox;
import org.junit.Before;
import org.junit.Test;
//import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;

public class ScoringFoxTest {
    private PlayerMap map;

    @Before
    public void generateMap() {
        map = new PlayerMap();
        map.clearTileBoard();
    }
    private static HabitatTile newFoxTile() {
        return ScoringSalmonTest.newTile(WildlifeToken.Fox);
    }
    private static HabitatTile newHawkTile() {
        return ScoringSalmonTest.newTile(WildlifeToken.Hawk);
    }
    private static HabitatTile newBearTile() {
        return ScoringSalmonTest.newTile(WildlifeToken.Bear);
    }

    @Test
    public void testFox1SameType() {
        map.addTileToMap(newFoxTile(), 8, 8);
        map.addTileToMap(newBearTile(), 7, 8);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F1), 1);
        map.addTileToMap(newBearTile(), 7, 9);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F1), 1);
    }

    @Test
    public void testFox1DifferentTypes() {
        map.addTileToMap(newFoxTile(), 8, 8);
        map.addTileToMap(newBearTile(), 7, 8);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F1), 1);
        map.addTileToMap(newHawkTile(), 7, 9);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F1), 2);
    }
    
    @Test
    public void testFox1AdjacentFoxesIncluded() {
        map.addTileToMap(newFoxTile(), 8, 8);
        map.addTileToMap(newBearTile(), 7, 8);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F1), 1);
        map.addTileToMap(newFoxTile(), 7, 9);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F1), 4);
    }

    @Test
    public void testFox2OnePair() {
        map.addTileToMap(newFoxTile(), 8, 8);
        map.addTileToMap(newBearTile(), 7, 8);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F2), 0);
        map.addTileToMap(newBearTile(), 7, 9);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F2), 3);
    }
    
    @Test
    public void testFox2OneFoxPair() {
        map.addTileToMap(newFoxTile(), 8, 8);
        map.addTileToMap(newFoxTile(), 7, 8);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F2), 0);
        map.addTileToMap(newFoxTile(), 7, 9);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F2), 0);
    }
    
    @Test
    public void testFox2TwoPairs() {
        map.addTileToMap(newFoxTile(), 8, 8);
        map.addTileToMap(newBearTile(), 7, 8);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F2), 0);
        map.addTileToMap(newBearTile(), 7, 9);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F2), 3);
        map.addTileToMap(newHawkTile(), 8, 7);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F2), 3);
        map.addTileToMap(newHawkTile(), 8, 9);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F2), 5);
    }
    
    @Test
    public void testFox2PairWithExtras() {
        map.addTileToMap(newFoxTile(), 8, 8);
        map.addTileToMap(newBearTile(), 7, 8);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F2), 0);
        map.addTileToMap(newBearTile(), 7, 9);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F2), 3);
        map.addTileToMap(newBearTile(), 8, 7);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F2), 3);
    }
    
    @Test
    public void testFox3SameType() {
        map.addTileToMap(newFoxTile(), 8, 8);
        map.addTileToMap(newBearTile(), 7, 8);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F3), 1);
        map.addTileToMap(newBearTile(), 7, 9);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F3), 2);
        map.addTileToMap(newBearTile(), 8, 7);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F3), 3);
    }
    
    @Test
    public void testFox3MaxOfTwoTypes() {
        map.addTileToMap(newFoxTile(), 8, 8);
        map.addTileToMap(newBearTile(), 7, 8);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F3), 1);
        map.addTileToMap(newBearTile(), 7, 9);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F3), 2);
        map.addTileToMap(newHawkTile(), 8, 7);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F3), 2);
        map.addTileToMap(newHawkTile(), 8, 9);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F3), 2);
        map.addTileToMap(newBearTile(), 9, 8);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F3), 3);
    }
    
    @Test
    public void testFox3IgnoreAdjacentFoxes() {
        map.addTileToMap(newFoxTile(), 8, 8);
        map.addTileToMap(newFoxTile(), 7, 8);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F3), 0);
        map.addTileToMap(newHawkTile(), 9, 8);
        assertEquals(ScoringFox.calculateScore(map, ScoringFox.Option.F3), 1);
       
    }

}
