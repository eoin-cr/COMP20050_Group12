package tests;

import cascadia.HabitatTile;
import cascadia.PlayerMap;
import cascadia.ScoringSalmon;
import cascadia.WildlifeToken;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;

public class SalmonTests {
    protected static HabitatTile newTile(WildlifeToken token) {
        HabitatTile tile = new HabitatTile(HabitatTile.Habitat.Prairie, HabitatTile.Habitat.Prairie, 1);
        tile.setTokenForTesting(token);
        return tile;
    }

    // we can use this as the scoring for salmon works the same for all the options,
    // there's just different numbers for runs
    private void generalValidRunTests(List<Integer> expectedScores, String option) {
        PlayerMap map = new PlayerMap();
        map.clearTileBoard();
        map.addTileToMap(newTile(WildlifeToken.Salmon), 8, 8);

        // test whether the values for runs of 1, 2, and 3 are correct
        assertEquals(ScoringSalmon.scoreSalmon(map, option), (int) expectedScores.get(0));

        map.addTileToMap(newTile(WildlifeToken.Salmon), 8, 9);
        assertEquals(ScoringSalmon.scoreSalmon(map, option), (int) expectedScores.get(1));

        map.addTileToMap(newTile(WildlifeToken.Salmon), 8, 10);
        assertEquals(ScoringSalmon.scoreSalmon(map, option), (int) expectedScores.get(2));

        map.addTileToMap(newTile(WildlifeToken.Salmon), 8, 11);
        assertEquals(ScoringSalmon.scoreSalmon(map, option), (int) expectedScores.get(3));

        map.addTileToMap(newTile(WildlifeToken.Salmon), 9, 12);
//        map.addTileToMap(newTile(WildlifeToken.Salmon), 8, 12);
        assertEquals(ScoringSalmon.scoreSalmon(map, option), (int) expectedScores.get(4));
//        cascadia.Display.displayTileMap(map);
    }

    // These runs should get invalidated as we can't have more than 2 salmon tiles touching
    // any one tile in a valid run
    private void generalInvalidRunTests(String option) {
        PlayerMap map = new PlayerMap();
        map.clearTileBoard();
        map.addTileToMap(newTile(WildlifeToken.Salmon), 8, 8);

        map.addTileToMap(newTile(WildlifeToken.Salmon), 8, 9);
        map.addTileToMap(newTile(WildlifeToken.Salmon), 8, 10);
        // test whether it returns 0 for invalid runs
        PlayerMap badMap = new PlayerMap();
        badMap.setTileBoard(PlayerMap.deepCopy(map.getTileBoardPosition()));
        badMap.addTileToMap(newTile(WildlifeToken.Salmon), 7,8);
        badMap.addTileToMap(newTile(WildlifeToken.Salmon), 9,8);
        assertEquals(ScoringSalmon.scoreSalmon(badMap, option), 0);

        map.addTileToMap(newTile(WildlifeToken.Salmon), 8, 11);
        badMap.setTileBoard(PlayerMap.deepCopy(map.getTileBoardPosition()));
        badMap.addTileToMap(newTile(WildlifeToken.Salmon), 7,9);
        badMap.addTileToMap(newTile(WildlifeToken.Salmon), 9,10);
        assertEquals(ScoringSalmon.scoreSalmon(badMap, option), 0);
    }

    @Test
    public void testSalmon1ValidRuns() {
        List<Integer> scores = new ArrayList<>();
        Collections.addAll(scores, 2,4,7,11,15);
        generalValidRunTests(scores, "S1");
    }

    @Test
    public void testSalmon1InvalidRuns() {
        generalInvalidRunTests("S1");
    }

    @Test
    public void testSalmon2ValidRuns() {
        List<Integer> scores = new ArrayList<>();
        Collections.addAll(scores, 2,4,8,12,12);
        generalValidRunTests(scores, "S2");
    }

    @Test
    public void testSalmon2InvalidRuns() {
        generalInvalidRunTests("S2");
    }

    @Test
    public void testSalmon3ValidRuns() {
        List<Integer> scores = new ArrayList<>();
        Collections.addAll(scores, 2,4,9,11,17);
        generalValidRunTests(scores, "S3");
    }

    @Test
    public void testSalmon3InvalidRuns() {
        generalInvalidRunTests("S3");
    }
}
