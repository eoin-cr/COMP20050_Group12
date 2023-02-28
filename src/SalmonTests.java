import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SalmonTests {
    private HabitatTile newTile() {
        HabitatTile tile = new HabitatTile(HabitatTile.Habitat.Prairie, HabitatTile.Habitat.Prairie, 1);
        tile.setTokenForTesting(WildlifeToken.Salmon);
        return tile;
    }

    @Test
    void testSalmon1() {
        PlayerMap map = new PlayerMap();
        map.clearTileBoard();
        map.addTileToMap(newTile(), 8, 8);

        // test whether the values for runs of 1, 2, and 3 are correct
        assertEquals(ScoringSalmon.scoreSalmon(map, "S1"), 2);

        map.addTileToMap(newTile(), 8, 9);
        assertEquals(ScoringSalmon.scoreSalmon(map, "S1"), 4);

        map.addTileToMap(newTile(), 8, 10);
        assertEquals(ScoringSalmon.scoreSalmon(map, "S1"), 7);

        // test whether it returns 0 for invalid runs
        PlayerMap badMap = new PlayerMap();
        badMap.setTileBoard(map.deepCopy(map.getTileBoardPosition()));
        badMap.addTileToMap(newTile(), 7,8);
        badMap.addTileToMap(newTile(), 9,8);
        assertEquals(ScoringSalmon.scoreSalmon(badMap, "S1"), 0);

        map.addTileToMap(newTile(), 8, 11);
        assertEquals(ScoringSalmon.scoreSalmon(map, "S1"), 11);

        badMap.setTileBoard(map.deepCopy(map.getTileBoardPosition()));
        badMap.addTileToMap(newTile(), 7,9);
        badMap.addTileToMap(newTile(), 9,10);
        assertEquals(ScoringSalmon.scoreSalmon(badMap, "S1"), 0);

        map.addTileToMap(newTile(), 9, 12);
//        map.addTileToMap(newTile(), 8, 12);
        assertEquals(ScoringSalmon.scoreSalmon(map, "S1"), 15);
//        Display.displayTileMap(map);
    }

    //    testing the conditions for triangle and crosses construction
//    2 marks (/4) for each set of construction tests (not individual test cases) that pass.
//    @Test
//    void testTriangleConstruction() {
//        try {
//            Draw.triangle(6, 13, 'x', 'x');
//            fail("Allows characters to be the same");
//        } catch (IllegalArgumentException ex) {
//            assertTrue(true);
//        }
//        try {
//            Draw.triangle(4, 13, 'x', 'o');
//            fail("Height < 5");
//        } catch (IllegalArgumentException ex) {
//            assertTrue(true);
//        }
//        try {
//            Draw.triangle(5, 4, 'x', 'o');
//            fail("Width < 5");
//        } catch (IllegalArgumentException ex) {
//            assertTrue(true);
//        }
//        try {
//            Draw.triangle(21, 13, 'x', 'o');
//            fail("Height > 20");
//        } catch (IllegalArgumentException ex) {
//            assertTrue(true);
//        }
//        try {
//            Draw.triangle(7, 21, 'x', 'o');
//            fail("Width > 20");
//        } catch (IllegalArgumentException ex) {
//            assertTrue(true);
//        }
//        try {
//            Draw.triangle(13, 19, 'x', 'o');
//            fail("Width < (2*h)-1");
//        } catch (IllegalArgumentException ex) {
//            assertTrue(true);
//        }
//        try {
//            Draw.triangle(5, 24, 'x', 'o');
//            fail("Width is even");
//        } catch (IllegalArgumentException ex) {
//            assertTrue(true);
//        }
//    }
//
//    @Test
//    void testCrossesConstruction() {
//        try {
//            Draw.crosses(6, 13, 'x', 'x', true);
//            fail("Allows characters to be the same");
//        } catch (IllegalArgumentException ex) {
//            assertTrue(true);
//        }
//        try {
//            Draw.crosses(4, 13, 'x', 'o', true);
//            fail("Height < 5");
//        } catch (IllegalArgumentException ex) {
//            assertTrue(true);
//        }
//        try {
//            Draw.crosses(26, 13, 'x', 'o', true);
//            fail("Height > 25");
//        } catch (IllegalArgumentException ex) {
//            assertTrue(true);
//        }
//        try {
//            Draw.crosses(5, 4, 'x', 'o', true);
//            fail("Width < 5");
//        } catch (IllegalArgumentException ex) {
//            assertTrue(true);
//        }
//        try {
//            Draw.crosses(5, 26, 'x', 'o', true);
//            fail("Width > 25");
//        } catch (IllegalArgumentException ex) {
//            assertTrue(true);
//        }
//        try {
//            Draw.crosses(5, 6, 'x', 'o', false);
//            fail("Width and height not equal for diagonal cross");
//        } catch (IllegalArgumentException ex) {
//            assertTrue(true);
//        }
//    }
//
////    if this returns true, the student likely has the method printing the answer, rather
////    than returning the answer as a string.  If you check the code and this is the case,
////    deduct 3 marks for part A, but if you check and the printed outputs match the tests
////    for part B, you can give full marks for part B, as marking them down twice for the
////    same mistake would be unfair.
//
//    //    references: https://stackoverflow.com/questions/21512738/how-to-test-method-that-only-print-out-message
////    just copied and modified the way they used ByteArrayOutputStream
//    @Test
//    void testIfPrintNotReturn() {
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//        String s1 = Draw.triangle(6, 13, 'x', '.');
//
//        if (s1.equals("") && (outContent.toString().contains("x") || outContent.toString().contains("."))) {
//            assertTrue(true);
//        } else {
//            fail();
//        }
//    }
//
////    ////////////
////    // PART B //
////    ////////////
//
//    //    tests to see if the user has tried to draw something in the right shape and characters
////    1/8 marks can be given per set of tests that pass
//    @Test
//    void testTrianglesBasicAttempt1() {
////        tests whether the triangle is the right height and width
//        String s1 = Draw.triangle(6,13,'x','.');
//        String s2 = Draw.triangle(9,19,'?','1');
//
////        tests height
//        assertEquals(s1.lines().count(), 6);
//        assertEquals(s2.lines().count(), 9);
//
////        tests width
//        assertEquals(s1.split("\n")[0].length(), 13);
//        assertEquals(s2.split("\n")[0].length(), 19);
//    }
//
//    @Test
//    void testTrianglesBasicAttempt2() {
////        tests whether the triangle has the right characters
//        String s1 = Draw.triangle(6, 13, 'x', '.');
//        String s2 = Draw.triangle(9, 19, '?', '1');
//
//        assertTrue(s1.contains("x") && s1.contains("."));
//        assertTrue(s2.contains("?") && s2.contains("1"));
//    }
//
////    ////////////
//
//    //    one of these next three tests returning true would indicate that a triangle is drawn
////    however, the orientation is wrong or the characters are the wrong way around.  It seems
////    appropriate to give 5/8 if any of these tests pass.  (However no points should be deducted
////    for these tests failing as that is the intended behaviour for a correct question).
//    @Test
//    void testTrianglesFurtherAttempt1() {
////        check if characters are the wrong way around
//        assertEquals(Draw.triangle(6, 13, '.', 'x'), "......x......\n.....xxx.....\n....xxxxx....\n...xxxxxxx...\n..xxxxxxxxx..\n.xxxxxxxxxxx.\n");
//        assertEquals(Draw.triangle(10, 19, '?', 'a'), "?????????a?????????\n????????aaa????????\n???????aaaaa???????\n??????aaaaaaa??????\n?????aaaaaaaaa?????\n????aaaaaaaaaaa????\n???aaaaaaaaaaaaa???\n??aaaaaaaaaaaaaaa??\n?aaaaaaaaaaaaaaaaa?\naaaaaaaaaaaaaaaaaaa\n");
//        assertEquals(Draw.triangle(5, 19, '.', 'v'), ".........v.........\n........vvv........\n.......vvvvv.......\n......vvvvvvv......\n.....vvvvvvvvv.....\n");
//        assertEquals(Draw.triangle(5, 9, '_', 'Y'), "____Y____\n___YYY___\n__YYYYY__\n_YYYYYYY_\nYYYYYYYYY\n");
//        assertEquals(Draw.triangle(6, 13, 'x', '.'), "xxxxxx.xxxxxx\nxxxxx...xxxxx\nxxxx.....xxxx\nxxx.......xxx\nxx.........xx\nx...........x\n");
//    }
//
//    @Test
//    void testTrianglesFurtherAttempt2() {
////        check if widths and height are the wrong way around
//        try {
//            assertEquals(Draw.triangle(13, 6, 'x', '.'), "......x......\n.....xxx.....\n....xxxxx....\n...xxxxxxx...\n..xxxxxxxxx..\n.xxxxxxxxxxx.\n");
//        } catch (Exception ignored) {fail();};
//    }
//
//    @Test
//    void testTrianglesFurtherAttempt3() {
////        check if triangle is upside down
//        assertEquals(Draw.triangle(6, 13, 'x', '.'), ".xxxxxxxxxxx.\n ..xxxxxxxxx..\n ...xxxxxxx...\n ....xxxxx....\n .....xxx.....\n ......x......");
//    }
//
////    /////////////
//
//    //    checks if the triangle matches the examples given in the question. If this test passes a score of
////    6/8 can be given (*Assuming the answers are NOT hardcoded*)
//    @Test
//    void testTriangleExamples() {
////        Example 1
//        assertEquals(Draw.triangle(6,13,'x','.'), "......x......\n.....xxx.....\n....xxxxx....\n...xxxxxxx...\n..xxxxxxxxx..\n.xxxxxxxxxxx.\n");
////        Example 2
//        assertEquals(Draw.triangle(7,19,'o','-'), "---------o---------\n--------ooo--------\n-------ooooo-------\n------ooooooo------\n-----ooooooooo-----\n----ooooooooooo----\n---ooooooooooooo---\n");
//    }
//
////    /////////////
//
//    //    just checks some other possible inputs.  If this passes it's fair to give the full 8/8 marks.
//    @Test
//    void testOtherTriangles() {
//        assertEquals(Draw.triangle(10,19,'a','?'), "?????????a?????????\n????????aaa????????\n???????aaaaa???????\n??????aaaaaaa??????\n?????aaaaaaaaa?????\n????aaaaaaaaaaa????\n???aaaaaaaaaaaaa???\n??aaaaaaaaaaaaaaa??\n?aaaaaaaaaaaaaaaaa?\naaaaaaaaaaaaaaaaaaa\n");
//        assertEquals(Draw.triangle(5,19,'v','.'), ".........v.........\n........vvv........\n.......vvvvv.......\n......vvvvvvv......\n.....vvvvvvvvv.....\n");
//        assertEquals(Draw.triangle(5,9,'Y','_'), "____Y____\n___YYY___\n__YYYYY__\n_YYYYYYY_\nYYYYYYYYY\n");
//        assertEquals(Draw.triangle(6,13,'.','x'), "xxxxxx.xxxxxx\nxxxxx...xxxxx\nxxxx.....xxxx\nxxx.......xxx\nxx.........xx\nx...........x\n");
//    }
//
////    ////////////
////    // PART C //
////    ////////////
//
////    tests to see if the user has tried to draw something in the right shape and characters
////    1/8 per correct set
//
//    //    had to be a bit stricter on the marks for this one as getting 50% for the hardest
////    part of a question for just drawing rectangles with the correct height, width, and
////    included characters seemed a bit excessive
//    @Test
//    void testCrossesAttempt1() {
////        checks if the height and width of the drawn shape is correct, as well as having the
////        correct characters
//
//        String s1 = Draw.crosses(13,13,'x','.',true);
//        String s2 = Draw.crosses(20,20,'x','.',true);
//
//        crossAttemptCheckerMethod(s1, s2);
//    }
//
//    @Test
//    void testCrossesAttempt2() {
////        same as Attempt1 but for the diagonal crosses instead
//        String s1 = Draw.crosses(13,13,'x','.',false);
//        String s2 = Draw.crosses(20,20,'x','.',false);
//
//        crossAttemptCheckerMethod(s1, s2);
//    }
//
//    //    just a method to avoid code reuse.  Ignore.
//    static void crossAttemptCheckerMethod(String s1, String s2) {
//        assertEquals(s1.lines().count(), 13);
//        assertEquals(s2.lines().count(), 20);
//
//        assertEquals(s1.split("\n")[0].length(), 13);
//        assertEquals(s2.split("\n")[0].length(), 20);
//
//        assertTrue(s1.contains("x") && s1.contains("."));
//        assertTrue(s2.contains("x") && s2.contains("."));
//    }
//
////    ////////////
//
//    //    3/8 given if the first set is correct, an *additional* 3/8 given if the second is
////    (*Assuming the answers are NOT hardcoded*)
//    @Test
//    void testCrossesExample1() {
////        tests horizontal given examples
//        assertEquals(Draw.crosses(13,13,'x','.',true), "xxxxxxxxxxxxx\nx..x..x..x..x\nx..x..x..x..x\nxxxxxxxxxxxxx\nx..x..x..x..x\nx..x..x..x..x\nxxxxxxxxxxxxx\nx..x..x..x..x\nx..x..x..x..x\nxxxxxxxxxxxxx\nx..x..x..x..x\nx..x..x..x..x\nxxxxxxxxxxxxx\n");
//    }
//    @Test
//    void testCrossesExample2() {
////        tests diagonal given examples
//        assertEquals(Draw.crosses(12, 12, 'x', '.', false), "..x......x..\n...x....x...\nx...x..x...x\n.x...xx...x.\n..x..xx..x..\n...xx..xx...\n...xx..xx...\n..x..xx..x..\n.x...xx...x.\nx...x..x...x\n...x....x...\n..x......x..\n");
//    }
//
////    ////////////
//
//    //    4/8 given for each correct set
//    @Test
//    void testOtherCrosses1() {
////        testing other horizontal crosses
//        assertEquals(Draw.crosses(7, 7, 'x', '.', true), "xxxxxxx\nx..x..x\nx..x..x\nxxxxxxx\nx..x..x\nx..x..x\nxxxxxxx\n");
//        assertEquals(Draw.crosses(7, 25, 'x', '.', true), "xxxxxxxxxxxxxxxxxxxxxxxxx\nx..x..x..x..x..x..x..x..x\nx..x..x..x..x..x..x..x..x\nxxxxxxxxxxxxxxxxxxxxxxxxx\nx..x..x..x..x..x..x..x..x\nx..x..x..x..x..x..x..x..x\nxxxxxxxxxxxxxxxxxxxxxxxxx\n");
//        assertEquals(Draw.crosses(25, 25, 'x', '.', true), "xxxxxxxxxxxxxxxxxxxxxxxxx\nx..x..x..x..x..x..x..x..x\nx..x..x..x..x..x..x..x..x\nxxxxxxxxxxxxxxxxxxxxxxxxx\nx..x..x..x..x..x..x..x..x\nx..x..x..x..x..x..x..x..x\nxxxxxxxxxxxxxxxxxxxxxxxxx\nx..x..x..x..x..x..x..x..x\nx..x..x..x..x..x..x..x..x\nxxxxxxxxxxxxxxxxxxxxxxxxx\nx..x..x..x..x..x..x..x..x\nx..x..x..x..x..x..x..x..x\nxxxxxxxxxxxxxxxxxxxxxxxxx\nx..x..x..x..x..x..x..x..x\nx..x..x..x..x..x..x..x..x\nxxxxxxxxxxxxxxxxxxxxxxxxx\nx..x..x..x..x..x..x..x..x\nx..x..x..x..x..x..x..x..x\nxxxxxxxxxxxxxxxxxxxxxxxxx\nx..x..x..x..x..x..x..x..x\nx..x..x..x..x..x..x..x..x\nxxxxxxxxxxxxxxxxxxxxxxxxx\nx..x..x..x..x..x..x..x..x\nx..x..x..x..x..x..x..x..x\nxxxxxxxxxxxxxxxxxxxxxxxxx\n");
//        assertEquals(Draw.crosses(25, 7, 'x', '.', true), "xxxxxxx\nx..x..x\nx..x..x\nxxxxxxx\nx..x..x\nx..x..x\nxxxxxxx\nx..x..x\nx..x..x\nxxxxxxx\nx..x..x\nx..x..x\nxxxxxxx\nx..x..x\nx..x..x\nxxxxxxx\nx..x..x\nx..x..x\nxxxxxxx\nx..x..x\nx..x..x\nxxxxxxx\nx..x..x\nx..x..x\nxxxxxxx\n");
//        assertEquals(Draw.crosses(13, 10, 'x', '.', true), "xxxxxxxxxx\nx..x..x..x\nx..x..x..x\nxxxxxxxxxx\nx..x..x..x\nx..x..x..x\nxxxxxxxxxx\nx..x..x..x\nx..x..x..x\nxxxxxxxxxx\nx..x..x..x\nx..x..x..x\nxxxxxxxxxx\n");
//        assertEquals(Draw.crosses(16, 10, '|', 'x', true), "||||||||||\n|xx|xx|xx|\n|xx|xx|xx|\n||||||||||\n|xx|xx|xx|\n|xx|xx|xx|\n||||||||||\n|xx|xx|xx|\n|xx|xx|xx|\n||||||||||\n|xx|xx|xx|\n|xx|xx|xx|\n||||||||||\n|xx|xx|xx|\n|xx|xx|xx|\n||||||||||\n");
//        assertEquals(Draw.crosses(7, 19, '|', 'x', true), "|||||||||||||||||||\n|xx|xx|xx|xx|xx|xx|\n|xx|xx|xx|xx|xx|xx|\n|||||||||||||||||||\n|xx|xx|xx|xx|xx|xx|\n|xx|xx|xx|xx|xx|xx|\n|||||||||||||||||||\n");
//    }
//
//    @Test
//    void testOtherCrosses2() {
////        testing other diagonal crosses
//        assertEquals(Draw.crosses(5, 5, 'x', '.', false), "..x..\n.x.x.\nx...x\n.x.x.\n..x..\n");
//        assertEquals(Draw.crosses(6, 6, 'x', '.', false), "..xx..\n..xx..\nxx..xx\nxx..xx\n..xx..\n..xx..\n");
//        assertEquals(Draw.crosses(9, 9, 'x', '.', false), "..x...x..\n...x.x...\nx...x...x\n.x.x.x.x.\n..x...x..\n.x.x.x.x.\nx...x...x\n...x.x...\n..x...x..\n");
//        assertEquals(Draw.crosses(10, 10, 'x', '.', false), "..x....x..\n...x..x...\nx...xx...x\n.x..xx..x.\n..xx..xx..\n..xx..xx..\n.x..xx..x.\nx...xx...x\n...x..x...\n..x....x..\n");
//        assertEquals(Draw.crosses(24, 24, 'x', '.', false), "..x..................x..\n...x................x...\nx...x..............x...x\n.x...x............x...x.\n..x...x..........x...x..\n...x...x........x...x...\n....x...x......x...x....\n.....x...x....x...x.....\n......x...x..x...x......\n.......x...xx...x.......\n........x..xx..x........\n.........xx..xx.........\n.........xx..xx.........\n........x..xx..x........\n.......x...xx...x.......\n......x...x..x...x......\n.....x...x....x...x.....\n....x...x......x...x....\n...x...x........x...x...\n..x...x..........x...x..\n.x...x............x...x.\nx...x..............x...x\n...x................x...\n..x..................x..\n");
//        assertEquals(Draw.crosses(25, 25, 'x', '.', false), "..x...................x..\n...x.................x...\nx...x...............x...x\n.x...x.............x...x.\n..x...x...........x...x..\n...x...x.........x...x...\n....x...x.......x...x....\n.....x...x.....x...x.....\n......x...x...x...x......\n.......x...x.x...x.......\n........x...x...x........\n.........x.x.x.x.........\n..........x...x..........\n.........x.x.x.x.........\n........x...x...x........\n.......x...x.x...x.......\n......x...x...x...x......\n.....x...x.....x...x.....\n....x...x.......x...x....\n...x...x.........x...x...\n..x...x...........x...x..\n.x...x.............x...x.\nx...x...............x...x\n...x.................x...\n..x...................x..\n");
//        assertEquals(Draw.crosses(5, 5, '.', '|', false), "||.||\n|.|.|\n.|||.\n|.|.|\n||.||\n");
//        assertEquals(Draw.crosses(10, 10, '.', '|', false), "||.||||.||\n|||.||.|||\n.|||..|||.\n|.||..||.|\n||..||..||\n||..||..||\n|.||..||.|\n.|||..|||.\n|||.||.|||\n||.||||.||\n");
//        assertEquals(Draw.crosses(6, 6, '.', '|', false), "||..||\n||..||\n..||..\n..||..\n||..||\n||..||\n");
//    }
}
