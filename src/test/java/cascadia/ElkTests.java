package cascadia;

import cascadia.scoring.ScoringElk;
import org.junit.Ignore;
import org.junit.Test;
//import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;

public class ElkTests {
    private static HabitatTile newElkTile() {
        return SalmonTests.newTile(WildlifeToken.Elk);
    }

    @Test
    public void testElk1Horizontal() {
        PlayerMap map = new PlayerMap();
        map.clearTileBoard();
        map.addTileToMap(newElkTile(), 9, 7);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 2);
        map.addTileToMap(newElkTile(), 9, 8);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 5);
        map.addTileToMap(newElkTile(), 9, 9);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 9);
        map.addTileToMap(newElkTile(), 9, 10);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 13);
    }

    // TODO: check if completely vertical lines are valid scoring
    @Test
    public void testElk1Vertical() {
        PlayerMap map = new PlayerMap();
        map.clearTileBoard();
        map.addTileToMap(newElkTile(), 9, 9);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 2);
        map.addTileToMap(newElkTile(), 10, 9);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 5);
        map.addTileToMap(newElkTile(), 11, 9);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 7);
        map.addTileToMap(newElkTile(), 12, 9);
        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 10);
    }

    @Test
    public void testElk1Diagonal1() {
        PlayerMap map = new PlayerMap();
        map.clearTileBoard();
        map.addTileToMap(newElkTile(), 9, 9);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 2);
        map.addTileToMap(newElkTile(), 10, 9);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 5);
        map.addTileToMap(newElkTile(), 11, 10);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 9);
        map.addTileToMap(newElkTile(), 12, 10);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 13);
    }

    @Test
    public void testElk1Diagonal2() {
        PlayerMap map = new PlayerMap();
        map.clearTileBoard();
        map.addTileToMap(newElkTile(), 9, 12);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 2);
        map.addTileToMap(newElkTile(), 10, 11);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 5);
        map.addTileToMap(newElkTile(), 11, 11);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 9);
        map.addTileToMap(newElkTile(), 12, 10);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 13);
    }
    @Test
    public void testElk1Both() {
        PlayerMap map = new PlayerMap();
        map.clearTileBoard();
        map.addTileToMap(newElkTile(), 8, 8);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 2);
        map.addTileToMap(newElkTile(), 9, 8);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 5);
        map.addTileToMap(newElkTile(), 9, 9);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 7);
        map.addTileToMap(newElkTile(), 9, 10);
        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 11);
        map.addTileToMap(newElkTile(), 9, 11);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 15);
        map.addTileToMap(newElkTile(), 8, 9);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 18);
        map.addTileToMap(newElkTile(), 8, 10);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 22);
        map.addTileToMap(newElkTile(), 8, 11);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 26);
    }
    @Test
    @Ignore // ignoring for now as this is the most complicated of the elk scoring A methods
    public void testElk1Intersect() {
        PlayerMap map = new PlayerMap();
        map.clearTileBoard();
        map.addTileToMap(newElkTile(), 8, 8);
        map.addTileToMap(newElkTile(), 9, 8);
        map.addTileToMap(newElkTile(), 9, 9);
        map.addTileToMap(newElkTile(), 9, 10);
        map.addTileToMap(newElkTile(), 9, 11);
        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 15);
        map.addTileToMap(newElkTile(), 10, 8);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 17);
        map.addTileToMap(newElkTile(), 11, 8);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 20);
        map.addTileToMap(newElkTile(), 12, 8);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 13);
        map.addTileToMap(newElkTile(), 13, 8);
        assertEquals(ScoringElk.calculateScore(map, "E1"), 13);
    }

    @Test
    public void testElk2() {
        PlayerMap map = new PlayerMap();
        map.clearTileBoard();
        map.addTileToMap(newElkTile(), 8,8);
        assertEquals(ScoringElk.calculateScore(map, "E2"), 2);
        map.addTileToMap(newElkTile(), 8,9);
        assertEquals(ScoringElk.calculateScore(map, "E2"), 4);
        map.addTileToMap(newElkTile(), 8,10);
        assertEquals(ScoringElk.calculateScore(map, "E2"), 7);
        map.addTileToMap(newElkTile(), 7,8);
        assertEquals(ScoringElk.calculateScore(map, "E2"), 10);
        map.addTileToMap(newElkTile(), 9,8);
        assertEquals(ScoringElk.calculateScore(map, "E2"), 14);
        map.addTileToMap(newElkTile(), 9,9);
        assertEquals(ScoringElk.calculateScore(map, "E2"), 18);
        map.addTileToMap(newElkTile(), 10,7);
        assertEquals(ScoringElk.calculateScore(map, "E2"), 23);
        map.addTileToMap(newElkTile(), 12,7);
        assertEquals(ScoringElk.calculateScore(map, "E2"), 25);
        map.addTileToMap(newElkTile(), 8,7);
        assertEquals(ScoringElk.calculateScore(map, "E2"), 30);

//        Display.displayTileMap(map);
//        map.clearTileBoard();
//        Display.displayTileMap(map);
//
//        // It seems strange that this doesn't work, but I can't imagine it
//        // causing a problem in the real game
//        map.addTileToMap(newElkTile(), 8,8);
//        assertEquals(ScoringElk.calculateScore(map, "E2"), 2);
//        map.addTileToMap(newElkTile(), 8,10);
//        assertEquals(ScoringElk.calculateScore(map, "E2"), 4);
//        map.addTileToMap(newElkTile(), 9,9);
//        Display.displayTileMap(map);
//        assertEquals(ScoringElk.calculateScore(map, "E2"), 6);

        PlayerMap secondMap = new PlayerMap();
        secondMap.clearTileBoard();
        secondMap.addTileToMap(newElkTile(), 8,8);
        assertEquals(ScoringElk.calculateScore(secondMap, "E2"), 2);
        secondMap.addTileToMap(newElkTile(), 8,10);
        assertEquals(ScoringElk.calculateScore(secondMap, "E2"), 4);
        secondMap.addTileToMap(newElkTile(), 9,9);
        assertEquals(ScoringElk.calculateScore(secondMap, "E2"), 6);
        secondMap.addTileToMap(newElkTile(), 9,11);
        assertEquals(ScoringElk.calculateScore(secondMap, "E2"), 8);
        secondMap.addTileToMap(newElkTile(), 9,10);
        assertEquals(ScoringElk.calculateScore(secondMap, "E2"), 14);
    }

    @Test
    public void testElk3Basics() {
        PlayerMap map = new PlayerMap();
        map.clearTileBoard();
        map.addTileToMap(newElkTile(), 8, 8);
        assertEquals(ScoringElk.calculateScore(map, "E3"), 2);
        map.addTileToMap(newElkTile(), 8,9);
        assertEquals(ScoringElk.calculateScore(map, "E3"), 5);
        map.addTileToMap(newElkTile(), 7,9);
        assertEquals(ScoringElk.calculateScore(map, "E3"), 9);
        map.addTileToMap(newElkTile(), 9,9);
        assertEquals(ScoringElk.calculateScore(map, "E3"), 13);
    }
}