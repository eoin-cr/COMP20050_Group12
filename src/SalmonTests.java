import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SalmonTests {
    private HabitatTile newTile() {
        HabitatTile tile = new HabitatTile(HabitatTile.Habitat.Prairie, HabitatTile.Habitat.Prairie, 1);
        tile.setTokenForTesting(WildlifeToken.Salmon);
        return tile;
    }
    private void generalTests(List<Integer> expectedScores, String option) {
        PlayerMap map = new PlayerMap();
        map.clearTileBoard();
        map.addTileToMap(newTile(), 8, 8);

        // test whether the values for runs of 1, 2, and 3 are correct
        assertEquals(ScoringSalmon.scoreSalmon(map, option), expectedScores.get(0));

        map.addTileToMap(newTile(), 8, 9);
        assertEquals(ScoringSalmon.scoreSalmon(map, option), expectedScores.get(1));

        map.addTileToMap(newTile(), 8, 10);
        assertEquals(ScoringSalmon.scoreSalmon(map, option), expectedScores.get(2));

        // test whether it returns 0 for invalid runs
        PlayerMap badMap = new PlayerMap();
        badMap.setTileBoard(map.deepCopy(map.getTileBoardPosition()));
        badMap.addTileToMap(newTile(), 7,8);
        badMap.addTileToMap(newTile(), 9,8);
        assertEquals(ScoringSalmon.scoreSalmon(badMap, option), expectedScores.get(3));

        map.addTileToMap(newTile(), 8, 11);
        assertEquals(ScoringSalmon.scoreSalmon(map, option), expectedScores.get(4));

        badMap.setTileBoard(map.deepCopy(map.getTileBoardPosition()));
        badMap.addTileToMap(newTile(), 7,9);
        badMap.addTileToMap(newTile(), 9,10);
        assertEquals(ScoringSalmon.scoreSalmon(badMap, option), expectedScores.get(5));

        map.addTileToMap(newTile(), 9, 12);
//        map.addTileToMap(newTile(), 8, 12);
        assertEquals(ScoringSalmon.scoreSalmon(map, option), expectedScores.get(6));
//        Display.displayTileMap(map);
    }

    @Test
    void testSalmon1() {
        List<Integer> scores = new ArrayList<>();
        Collections.addAll(scores, 2,4,7,0,11,0,15);
        generalTests(scores, "S1");
    }

    @Test
    void testSalmon2() {
        List<Integer> scores = new ArrayList<>();
        Collections.addAll(scores, 2,4,8,0,12,0,12);
        generalTests(scores, "S2");
    }

    @Test
    void testSalmon3() {
        List<Integer> scores = new ArrayList<>();
        Collections.addAll(scores, 2,4,9,0,11,0,17);
        generalTests(scores, "S3");
    }
}
