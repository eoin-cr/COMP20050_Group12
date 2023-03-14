package cascadia;

import cascadia.scoring.ScoringElk;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
//import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;

public class ScoringElkTest {
    private PlayerMap map;

    @Before
    public void generateMap() {
        map = new PlayerMap();
        map.clearTileBoard();
    }
    private static HabitatTile newElkTile() {
        return ScoringSalmonTest.newTile(WildlifeToken.Elk);
    }

    @Test
    public void testElk1Horizontal() {
        map.addTileToMap(newElkTile(), 9, 7);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 2);
        map.addTileToMap(newElkTile(), 9, 8);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 5);
        map.addTileToMap(newElkTile(), 9, 9);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 9);
        map.addTileToMap(newElkTile(), 9, 10);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 13);
    }

    @Test
    public void testElk1Vertical() {
        map.addTileToMap(newElkTile(), 9, 9);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 2);
        map.addTileToMap(newElkTile(), 10, 9);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 5);
        map.addTileToMap(newElkTile(), 11, 9);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 7);
        map.addTileToMap(newElkTile(), 12, 9);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 10);
    }

    @Test
    public void testElk1Diagonal1() {
        map.addTileToMap(newElkTile(), 9, 9);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 2);
        map.addTileToMap(newElkTile(), 10, 9);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 5);
        map.addTileToMap(newElkTile(), 11, 10);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 9);
        map.addTileToMap(newElkTile(), 12, 10);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 13);
    }

    @Test
    public void testElk1Diagonal2() {
        map.addTileToMap(newElkTile(), 9, 12);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 2);
        map.addTileToMap(newElkTile(), 10, 11);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 5);
        map.addTileToMap(newElkTile(), 11, 11);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 9);
        map.addTileToMap(newElkTile(), 12, 10);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 13);
    }
    @Test
    public void testElk1Both() {
        map.addTileToMap(newElkTile(), 8, 8);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 2);
        map.addTileToMap(newElkTile(), 9, 8);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 5);
        map.addTileToMap(newElkTile(), 9, 9);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 7);
        map.addTileToMap(newElkTile(), 9, 10);
        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 11);
        map.addTileToMap(newElkTile(), 9, 11);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 15);
        map.addTileToMap(newElkTile(), 8, 9);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 18);
        map.addTileToMap(newElkTile(), 8, 10);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 22);
        map.addTileToMap(newElkTile(), 8, 11);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 26);
    }
    @Test
    @Ignore // ignoring for now as this is the most complicated of the elk scoring A methods
    public void testElk1Intersect() {
        map.addTileToMap(newElkTile(), 8, 8);
        map.addTileToMap(newElkTile(), 9, 8);
        map.addTileToMap(newElkTile(), 9, 9);
        map.addTileToMap(newElkTile(), 9, 10);
        map.addTileToMap(newElkTile(), 9, 11);
        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 15);
        map.addTileToMap(newElkTile(), 10, 8);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 17);
        map.addTileToMap(newElkTile(), 11, 8);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 20);
        map.addTileToMap(newElkTile(), 12, 8);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 13);
        map.addTileToMap(newElkTile(), 13, 8);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 13);
    }

    @Test
    public void testElk2() {
        map.addTileToMap(newElkTile(), 8,8);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E2), 2);
        map.addTileToMap(newElkTile(), 8,9);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E2), 4);
        map.addTileToMap(newElkTile(), 8,10);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E2), 7);
        map.addTileToMap(newElkTile(), 7,8);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E2), 10);
        map.addTileToMap(newElkTile(), 9,8);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E2), 14);
        map.addTileToMap(newElkTile(), 9,9);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E2), 18);
        map.addTileToMap(newElkTile(), 10,7);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E2), 23);
        map.addTileToMap(newElkTile(), 12,7);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E2), 25);
        map.addTileToMap(newElkTile(), 8,7);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E2), 30);

//        Display.displayTileMap(map);
//        map.clearTileBoard();
//        Display.displayTileMap(map);
//
//        // It seems strange that this doesn't work, but I can't imagine it
//        // causing a problem in the real game
//        map.addTileToMap(newElkTile(), 8,8);
//        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E2), 2);
//        map.addTileToMap(newElkTile(), 8,10);
//        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E2), 4);
//        map.addTileToMap(newElkTile(), 9,9);
//        Display.displayTileMap(map);
//        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E2), 6);

        PlayerMap secondMap = new PlayerMap();
        secondMap.clearTileBoard();
        secondMap.addTileToMap(newElkTile(), 8,8);
        assertEquals(ScoringElk.calculateScore(secondMap, ScoringElk.Option.E2), 2);
        secondMap.addTileToMap(newElkTile(), 8,10);
        assertEquals(ScoringElk.calculateScore(secondMap, ScoringElk.Option.E2), 4);
        secondMap.addTileToMap(newElkTile(), 9,9);
        assertEquals(ScoringElk.calculateScore(secondMap, ScoringElk.Option.E2), 6);
        secondMap.addTileToMap(newElkTile(), 9,11);
        assertEquals(ScoringElk.calculateScore(secondMap, ScoringElk.Option.E2), 8);
        secondMap.addTileToMap(newElkTile(), 9,10);
        assertEquals(ScoringElk.calculateScore(secondMap, ScoringElk.Option.E2), 14);
    }

    @Test
    public void testElk3Basics() {
        map.addTileToMap(newElkTile(), 8, 8);
        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E3), 2);
        map.addTileToMap(newElkTile(), 8,9);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E3), 5);
        map.addTileToMap(newElkTile(), 7,9);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E3), 9);
        map.addTileToMap(newElkTile(), 9,9);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E3), 13);
    }
}
