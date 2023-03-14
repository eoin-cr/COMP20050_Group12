package cascadia;

import org.junit.Test;
import org.junit.Before;
//import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;
import java.util.*;

public class CurrentDeckTest {
    @Before
    public void setTiles() {
        List<HabitatTile> list = new ArrayList<>(Collections.nCopies(3,
                new HabitatTile(HabitatTile.Habitat.Prairie,
                        HabitatTile.Habitat.Wetland, 2)
        ));
        list.add(new HabitatTile(HabitatTile.Habitat.Mountain, HabitatTile.Habitat.River, 2));
        CurrentDeck.setDeckTiles(list);
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

    private void changeBagTiles(int F, int R, int W, int P, int M) {
        //make all possible tiles to draw from
        Bag.remainingHabitats.put(HabitatTile.Habitat.Forest, F);
        Bag.remainingHabitats.put(HabitatTile.Habitat.River, R);
        Bag.remainingHabitats.put(HabitatTile.Habitat.Wetland, W);
        Bag.remainingHabitats.put(HabitatTile.Habitat.Prairie, P);
        Bag.remainingHabitats.put(HabitatTile.Habitat.Mountain, M);
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
            changeBagTokens(0, 10, 10, 10, 10);
            CurrentDeck.setDeckTokens(clone(list));
            CurrentDeck.cullCheckThreeTokens();
            assertNotEquals(list, CurrentDeck.getDeckTokens());
        }
    }

    @Test
    public void threeTokenEdgeCase() {
        // the rules state that overpopulation control (for 3 duplicates)
        // is only done once per turn
        ArrayList<WildlifeToken> list = new ArrayList<>(Collections.nCopies(3, WildlifeToken.Bear));
        list.add(WildlifeToken.Hawk);
        changeBagTokens(100,0,0,0,0);
        CurrentDeck.setDeckTokens(clone(list));
        Display.displayDeck();
        try {
            CurrentDeck.cullCheckThreeTokens();
        } catch (Exception ignored) {
            // if there's an exception caused by running out of tokens or something,
            // which would happen when the program constantly tries to replace the 3
            // bear tiles, we want it to fail
            fail();
        }
        Display.displayDeck();
        System.out.println(list);
        assertEquals(list, CurrentDeck.getDeckTokens());
    }

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
                        HabitatTile.Habitat.Prairie, HabitatTile.Habitat.Wetland, 0)
        ));
    }
}
