package tests;

import cascadia.*;
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
        assertEquals(ScoringElk.scoreElk(map, "E1"), 2);
        map.addTileToMap(newElkTile(), 9, 8);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 5);
        map.addTileToMap(newElkTile(), 9, 9);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 9);
        map.addTileToMap(newElkTile(), 9, 10);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 13);
    }

    // TODO: check if completely vertical lines are valid scoring
    @Test
    public void testElk1Vertical() {
        PlayerMap map = new PlayerMap();
        map.clearTileBoard();
        map.addTileToMap(newElkTile(), 9, 9);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 2);
        map.addTileToMap(newElkTile(), 10, 9);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 5);
        map.addTileToMap(newElkTile(), 11, 9);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 9);
        map.addTileToMap(newElkTile(), 12, 9);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 13);
    }

    @Test
    public void testElk1Diagonal1() {
        PlayerMap map = new PlayerMap();
        map.clearTileBoard();
        map.addTileToMap(newElkTile(), 9, 9);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 2);
        map.addTileToMap(newElkTile(), 10, 10);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 5);
        map.addTileToMap(newElkTile(), 11, 11);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 9);
        map.addTileToMap(newElkTile(), 12, 12);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 13);
    }

    @Test
    public void testElk1Diagonal2() {
        PlayerMap map = new PlayerMap();
        map.clearTileBoard();
        map.addTileToMap(newElkTile(), 9, 12);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 2);
        map.addTileToMap(newElkTile(), 10, 11);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 5);
        map.addTileToMap(newElkTile(), 11, 11);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 9);
        map.addTileToMap(newElkTile(), 12, 10);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 13);
    }
    @Test
    public void testElk1Both() {
        PlayerMap map = new PlayerMap();
        map.clearTileBoard();
        map.addTileToMap(newElkTile(), 8, 8);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 2);
        map.addTileToMap(newElkTile(), 9, 8);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 5);
        map.addTileToMap(newElkTile(), 9, 9);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 7);
        map.addTileToMap(newElkTile(), 9, 10);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 11);
        map.addTileToMap(newElkTile(), 9, 11);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 15);
        map.addTileToMap(newElkTile(), 8, 9);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 18);
        map.addTileToMap(newElkTile(), 8, 10);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 22);
        map.addTileToMap(newElkTile(), 8, 11);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 26);
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
//        Display.displayTileMap(map);
//        assertEquals(ScoringElk.scoreElk(map, "E1"), 11);
        map.addTileToMap(newElkTile(), 9, 11);
//        Display.displayTileMap(map);
//        assertEquals(ScoringElk.scoreElk(map, "E1"), 15);
        map.addTileToMap(newElkTile(), 10, 8);
//        Display.displayTileMap(map);
//        assertEquals(ScoringElk.scoreElk(map, "E1"), 17);
        map.addTileToMap(newElkTile(), 11, 8);
//        Display.displayTileMap(map);
//        assertEquals(ScoringElk.scoreElk(map, "E1"), 20);
        map.addTileToMap(newElkTile(), 12, 8);
        Display.displayTileMap(map);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 13);
        map.addTileToMap(newElkTile(), 13, 8);
        assertEquals(ScoringElk.scoreElk(map, "E1"), 13);
    }

}
