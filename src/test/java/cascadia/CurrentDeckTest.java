package cascadia;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.Assert.*;

public class CurrentDeckTest {
    /*
        Mockito is a library that allows us to 'mock' method calls.
        For example, current deck takes a lot of input from the user, however,
        we can't ask the user for input in a test.  So what we can do with
        mockito is make it so when a certain input function is called, instead
        of actually taking input, it simply returns a preset value.
     */

    @Before
    public void setTiles() {
        List<HabitatTile> list = new ArrayList<>(Collections.nCopies(3,
                new HabitatTile(HabitatTile.Habitat.Prairie,
                        HabitatTile.Habitat.Wetland, 2)
        ));
        list.add(new HabitatTile(HabitatTile.Habitat.Mountain, HabitatTile.Habitat.River, 2));
        CurrentDeck.setDeckTiles(list);
        ArrayList<WildlifeToken> tokenList = new ArrayList<>(Collections.nCopies(3, WildlifeToken.Bear));
        tokenList.add(WildlifeToken.Hawk);
        CurrentDeck.setDeckTokens(clone(tokenList));
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

        WildlifeToken[] tokens = new WildlifeToken[]{WildlifeToken.Elk, WildlifeToken.Fox,
                WildlifeToken.Salmon};
        for (int i = 0; i < 3; i++) {

            // We have to mock the generation method, so we can tell for sure whether it's working.
            // For example, we could have BBBH, and have the code correctly replace them, but then
            // happen to randomly generate BBBH again which is allowed.  So instead we just force
            // a token which isn't B
            try (MockedStatic<Input> utilities = Mockito.mockStatic(Input.class);
                 MockedStatic<Generation> generationMock = Mockito.mockStatic(Generation.class)) {
                utilities.when(Input::chooseCullThreeOptions)
                        .thenReturn(1);

                generationMock.when(() -> Generation.generateWildlifeToken(true))
                        .thenReturn(tokens[i]);

                CurrentDeck.setDeckTokens(clone(list));
                CurrentDeck.cullCheckThreeTokens();
                System.out.println(list);
                System.out.println(CurrentDeck.getDeckTokens());
                assertNotEquals(list, CurrentDeck.getDeckTokens());
            }
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

    @Test
//    @Ignore
    public void testChoosePair3() {
        Player player = new Player("test");
        int tileChoice = 1;
        try (MockedStatic<Input> utilities = Mockito.mockStatic(Input.class)) {
            utilities.when(() -> Input.chooseTilePlacement(player))
                    .thenReturn(new int[]{1,1});
            utilities.when(() -> Input.chooseTokenPlaceOrReturn(CurrentDeck.getDeckTokens().get(tileChoice)))
                    .thenReturn(new int[]{2, -1});
            utilities.when(Input::chooseFromDeck)
                    .thenReturn(tileChoice);

            CurrentDeck.choosePair(player);

            HabitatTile tile = player.getMap().getTileBoardPosition()[1][1];
            System.out.println(tile);
            assertTrue(tile.equalHabitats(
                    new HabitatTile(
                            HabitatTile.Habitat.Prairie, HabitatTile.Habitat.Wetland, 0)
            ));
        }
    }

    @Test
    public void testChoosePair() {
        Player player = new Player("test");
        int tileChoice = 0;
        try (MockedStatic<Input> utilities = Mockito.mockStatic(Input.class)) {
            utilities.when(() -> Input.chooseTilePlacement(player))
                    .thenReturn(new int[]{1,1});
            utilities.when(() -> Input.chooseTokenPlaceOrReturn(CurrentDeck.getDeckTokens().get(tileChoice)))
                    .thenReturn(new int[]{2, -1});
            utilities.when(Input::chooseFromDeck)
                    .thenReturn(tileChoice);

            CurrentDeck.choosePair(player);
            HabitatTile tile = player.getMap().getTileBoardPosition()[1][1];
            System.out.println(tile);
            assertTrue(tile.equalHabitats(
                    new HabitatTile(
                            HabitatTile.Habitat.Prairie, HabitatTile.Habitat.Wetland, 0)
            ));
        }
    }

    @Test
    public void testChoosePair2() {
        int tileChoice = 3;
        Player player = new Player("test");
        try (MockedStatic<Input> utilities = Mockito.mockStatic(Input.class)) {
            utilities.when(() -> Input.chooseTilePlacement(player))
                    .thenReturn(new int[]{1,1});
            utilities.when(() -> Input.chooseTokenPlaceOrReturn(CurrentDeck.getDeckTokens().get(tileChoice)))
                    .thenReturn(new int[]{2, -1});
            utilities.when(Input::chooseFromDeck)
                    .thenReturn(tileChoice);

            CurrentDeck.choosePair(player);
            HabitatTile tile = player.getMap().getTileBoardPosition()[1][1];
            System.out.println(tile);
            assertTrue(tile.equalHabitats(
                    new HabitatTile(
                            HabitatTile.Habitat.Mountain, HabitatTile.Habitat.River, 0)
            ));
        }
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

    @Test
    public void setStartTileTokenSelectionTest() {
        System.out.println("Hi");
        try {
            CurrentDeck.setDeckTiles(new ArrayList<>());
            CurrentDeck.setDeckTokens(new ArrayList<>());
            CurrentDeck.setStartTileTokenSelection();
//            assertTrue(true);
        } catch (Exception ignored) {
            fail();
        }
    }
}
