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

import cascadia.scoring.ScoringElk;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
       // Display.displayTileMap(map);
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
    //@Ignore // ignoring for now as this is the most complicated of the elk scoring A methods
    public void testElk1Intersect() {
        map.addTileToMap(newElkTile(), 8, 8);
        map.addTileToMap(newElkTile(), 9, 8);
        map.addTileToMap(newElkTile(), 9, 9);
        map.addTileToMap(newElkTile(), 9, 10);
        map.addTileToMap(newElkTile(), 9, 11);
        //Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 15);
        map.addTileToMap(newElkTile(), 10, 8);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 17);
        map.addTileToMap(newElkTile(), 11, 8);
//        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 20);
        map.addTileToMap(newElkTile(), 12, 8);
        //        Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 22);
        map.addTileToMap(newElkTile(), 13, 8);
        //Display.displayTileMap(map);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E1), 25);
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
    public void testElk3Version1() {
        map.addTileToMap(newElkTile(), 8, 8);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E3), 2);
        map.addTileToMap(newElkTile(), 8,9);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E3), 5);
        map.addTileToMap(newElkTile(), 7,9);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E3), 9);
        map.addTileToMap(newElkTile(), 9,9);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E3), 13);
    }
    
    @Test
    public void testElk3Version2() {
        map.addTileToMap(newElkTile(), 8, 8);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E3), 2);
        map.addTileToMap(newElkTile(), 7,8);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E3), 5);
        map.addTileToMap(newElkTile(), 7,9);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E3), 9);
        map.addTileToMap(newElkTile(), 6,8);
        assertEquals(ScoringElk.calculateScore(map, ScoringElk.Option.E3), 13);
    }
}
