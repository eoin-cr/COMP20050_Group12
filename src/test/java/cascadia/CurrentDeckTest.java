package cascadia;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CurrentDeckTest {
//    @BeforeClass
//    public static void setPlayer() {
//        Player player = new Player("test");
//    }
//    Player player;
    @Before
    public void setTiles() {
//        player = new Player("test");
        List<HabitatTile> list = new ArrayList<>(Collections.nCopies(3,
                new HabitatTile(HabitatTile.Habitat.Prairie,
                        HabitatTile.Habitat.Wetland, 2)
        ));
        list.add(new HabitatTile(HabitatTile.Habitat.Mountain, HabitatTile.Habitat.River, 2));
        CurrentDeck.setDeckTiles(list);
        ArrayList<WildlifeToken> tokenList = new ArrayList<>(Collections.nCopies(3, WildlifeToken.Bear));
        tokenList.add(WildlifeToken.Hawk);
        CurrentDeck.setDeckTokens(clone(tokenList));
        CurrentDeck.startTesting();
        Bag.makeBag(2);
    }

    private static <E> void removeDuplicates(List<E> list) {
        Set<E> set = new HashSet<>(list);
        list.clear();
        list.addAll(set);
    }

    private static <E> int numDuplicates(List<E> list) {
        int initialSize = list.size();
        removeDuplicates(list);
        return initialSize - list.size() + 1;
    }

    private void changeBagTokens(int B, int E, int S, int H, int F) {
        //make tokens
        Bag.remainingTokens.put(WildlifeToken.Bear, B);
        Bag.remainingTokens.put(WildlifeToken.Elk, E);
        Bag.remainingTokens.put(WildlifeToken.Salmon, S);
        Bag.remainingTokens.put(WildlifeToken.Hawk, H);
        Bag.remainingTokens.put(WildlifeToken.Fox, F);
    }

    private <E> ArrayList<E> clone(ArrayList<E> original) {
        @SuppressWarnings("unchecked cast")
        ArrayList<E> newList = (ArrayList<E>) original.clone();
        return newList;
    }

    @Test
    public void fourTokenCullTest () {
        WildlifeToken[] tokens = new WildlifeToken[]{WildlifeToken.Bear, WildlifeToken.Elk,
                WildlifeToken.Salmon, WildlifeToken.Fox, WildlifeToken.Hawk};
        for (WildlifeToken token : tokens) {
            CurrentDeck.setDeckTokens(new ArrayList<>(Collections.nCopies(4, token)));
            CurrentDeck.cullCheckFourTokens();
            assertNotEquals(numDuplicates(CurrentDeck.getDeckTokens()), 4);
        }
    }

    @Test
    public void threeTokenCullTest() {
        ArrayList<WildlifeToken> list = new ArrayList<>(Collections.nCopies(3, WildlifeToken.Bear));
        list.add(WildlifeToken.Hawk);
        // run it 10 times cos why not
        // the tokens are randomly generated so the output deck will be different
        // each time
        for (int i = 0; i < 10; i++) {

//            try (MockedStatic<Input> utilities = Mockito.mockStatic(Input.class)) {
//                utilities.when(() -> Input.boundedInt(1, 2, "Type 1 to cull and replace tokens, or 2 to leave tokens untouched: "))
//                        .thenReturn(1);
            changeBagTokens(0, 10, 10, 10, 10);
            CurrentDeck.setDeckTokens(clone(list));
            CurrentDeck.cullCheckThreeTokens();
            assertNotEquals(list, CurrentDeck.getDeckTokens());
//            }
        }
    }

    @Test
    public void threeTokenCullTest2() {
        // tests for the case where you have two groups of two tokens, e.g. BB-HH
        WildlifeToken[] tokens = new WildlifeToken[]{WildlifeToken.Bear, WildlifeToken.Elk,
                WildlifeToken.Salmon, WildlifeToken.Fox, WildlifeToken.Hawk};
        for (int i = 0; i < 3; i++) {
            ArrayList<WildlifeToken> list = new ArrayList<>(Collections.nCopies(2, tokens[i]));
            list.add(tokens[i+1]);
            list.add(tokens[i+1]);
            CurrentDeck.setDeckTokens(list);
            CurrentDeck.cullCheckFourTokens();
            assertNotEquals(numDuplicates(CurrentDeck.getDeckTokens()), 4);
        }
    }

//    @Test
////    @Ignore
//    public void testChoosePairHelper() {
////        Player player = new Player("test");
////        System.outln.println(CurrentDeck.getDeckTiles());
////        Display.displayDeck();
//
//        try (MockedStatic<Input> utilities = Mockito.mockStatic(Input.class)) {
//            utilities.when(() -> Input.chooseTilePlacement(player))
//                    .thenReturn(new int[]{1,1});
////            utilities.when(() -> Input.chooseTokenPlaceOrReturn(CurrentDeck.getDeckTiles().get(tokenCH)))
//
//            CurrentDeck.choosePairHelper(player, 1, 1);
//            HabitatTile tile = player.getMap().getTileBoardPosition()[1][1];
//            assertTrue(tile.equalHabitats(
//                    new HabitatTile(
//                            HabitatTile.Habitat.Mountain, HabitatTile.Habitat.River, 0)
//            ));
//        } catch (Exception ignored) {
//            fail();
//        }
//    }

    @Test
    public void testChoosePairHelper() {
        Player player = new Player("test");
        CurrentDeck.choosePairHelper(player, 0, 0);
        HabitatTile tile = player.getMap().getTileBoardPosition()[1][1];
        assertTrue(tile.equalHabitats(
                new HabitatTile(
                        HabitatTile.Habitat.Prairie, HabitatTile.Habitat.Wetland, 0)
        ));
    }

    @Test
    public void testChoosePairHelper2() {
        Player player = new Player("test");
        CurrentDeck.choosePairHelper(player, 3, 3);
        HabitatTile tile = player.getMap().getTileBoardPosition()[1][1];
        assertTrue(tile.equalHabitats(
                new HabitatTile(
                        HabitatTile.Habitat.Mountain, HabitatTile.Habitat.River, 0)
        ));
    }

    @Test
    public void removeDeckTokenTest() {
        try {
            CurrentDeck.removeDeckToken(-1);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            CurrentDeck.removeDeckToken(4);
            fail();
        } catch (IllegalArgumentException ignored) {}
        CurrentDeck.removeDeckToken(3);
        try {
            CurrentDeck.removeDeckToken(3);
            fail();
        } catch (IllegalArgumentException ignored) {}
        CurrentDeck.removeDeckToken(2);
    }
}
