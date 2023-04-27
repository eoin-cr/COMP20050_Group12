///*
//	COMP20050 Group 12
//	Eoin Creavin – Student ID: 21390601
//	eoin.creavin@ucdconnect.ie
//	GitHub ID: eoin-cr
//
//	Mynah Bhattacharyya – Student ID: 21201085
//	malhar.bhattacharyya@ucdconnect.ie
//	GitHub ID: mynah-bird
//
//	Ben McDowell – Student ID: 21495144
//	ben.mcdowell@ucdconnect.ie
//	GitHub ID: Benmc1
// */
//
//package cascadia;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.MockedStatic;
//import org.mockito.Mockito;
//
//import java.util.*;
//
//import static org.junit.Assert.*;
//
//public class CurrentDeckTest {
//    /*
//        Mockito is a library that allows us to 'mock' method calls.
//        For example, current deck takes a lot of input from the user, however,
//        we can't ask the user for input in a test.  So what we can do with
//        mockito is make it so when a certain input function is called, instead
//        of actually taking input, it simply returns a preset value.
//     */
//
//    @Before
//    public void setTiles() {
//        List<HabitatTile> list = new ArrayList<>(Collections.nCopies(3,
//                new HabitatTile(HabitatTile.Habitat.Prairie,
//                        HabitatTile.Habitat.Wetland, 2)
//        ));
//        list.add(new HabitatTile(HabitatTile.Habitat.Mountain, HabitatTile.Habitat.River, 2));
//        CurrentDeck.setDeckTiles(list);
//        ArrayList<WildlifeToken> tokenList = new ArrayList<>(Collections.nCopies(3, WildlifeToken.Bear));
//        tokenList.add(WildlifeToken.Hawk);
//        CurrentDeck.setDeckTokens(clone(tokenList));
//        Bag.createBag(2);
//    }
//
//    private static <E> void removeDuplicates(List<E> list) {
//        Set<E> set = new HashSet<>(list);
//        list.clear();
//        list.addAll(set);
//    }
//
//    private static <E> int numDuplicates(List<E> list) {
//        int initialSize = list.size();
//        removeDuplicates(list);
//        return initialSize - list.size() + 1;
//    }
//
//    private <E> ArrayList<E> clone(ArrayList<E> original) {
//        @SuppressWarnings("unchecked cast")
//        ArrayList<E> newList = (ArrayList<E>) original.clone();
//        return newList;
//    }
//
//    @Test
//    public void testFourTokenCull() {
//        WildlifeToken[] tokens = new WildlifeToken[]{WildlifeToken.Bear, WildlifeToken.Elk,
//                WildlifeToken.Salmon, WildlifeToken.Fox, WildlifeToken.Hawk};
//        for (WildlifeToken token : tokens) {
//            CurrentDeck.setDeckTokens(new ArrayList<>(Collections.nCopies(4, token)));
//            CurrentDeck.cullCheckFourTokens();
//            assertNotEquals(numDuplicates(CurrentDeck.getDeckTokens()), 4);
//        }
//    }
//
//    @Test
//    public void testThreeTokenCull() {
//        ArrayList<WildlifeToken> list = new ArrayList<>(Collections.nCopies(3, WildlifeToken.Bear));
//        list.add(WildlifeToken.Hawk);
//
//        WildlifeToken[] tokens = new WildlifeToken[]{WildlifeToken.Elk, WildlifeToken.Fox,
//                WildlifeToken.Salmon};
//        for (int i = 0; i < 3; i++) {
//            // We have to mock the generation method, so we can tell for sure whether it's working.
//            // For example, we could have BBBH, and have the code correctly replace them, but then
//            // happen to randomly generate BBBH again which is allowed.  So instead we just force
//            // a token which isn't B
//            try (MockedStatic<Input> utilities = Mockito.mockStatic(Input.class);
//                 MockedStatic<Generation> generationMock = Mockito.mockStatic(Generation.class)) {
//                utilities.when(Input::chooseCullThreeOptions)
//                        .thenReturn(1);
//
//                generationMock.when(() -> Generation.generateWildlifeToken(true))
//                        .thenReturn(tokens[i]);
//
//                CurrentDeck.setDeckTokens(clone(list));
//                CurrentDeck.cullCheckThreeTokens();
//                assertNotEquals(list, CurrentDeck.getDeckTokens());
//            }
//        }
//    }
//
//    @Test
//    public void testThreeTokenCull2() {
//        // tests for the case where you have two groups of two tokens, e.g. BB-HH
//        WildlifeToken[] tokens = new WildlifeToken[]{WildlifeToken.Bear, WildlifeToken.Elk,
//                WildlifeToken.Salmon, WildlifeToken.Fox, WildlifeToken.Hawk};
//        for (int i = 0; i < 3; i++) {
//            ArrayList<WildlifeToken> list = new ArrayList<>(Collections.nCopies(2, tokens[i]));
//            list.add(tokens[i+1]);
//            list.add(tokens[i+1]);
//            CurrentDeck.setDeckTokens(list);
//            CurrentDeck.cullCheckFourTokens();
//            assertNotEquals(numDuplicates(CurrentDeck.getDeckTokens()), 4);
//        }
//    }
//
//    @Test
//    public void testChoosePair() {
//        Player player = new Player("test");
//        int tileChoice = 0;
//        try (MockedStatic<Input> utilities = Mockito.mockStatic(Input.class)) {
//            utilities.when(() -> Input.chooseTilePlacement(player))
//                    .thenReturn(new int[]{1,1});
//            utilities.when(() -> Input.chooseTokenPlaceOrReturn(CurrentDeck.getDeckTokens().get(tileChoice)))
//                    .thenReturn(new int[]{2, -1});
//            utilities.when(Input::chooseFromDeck)
//                    .thenReturn(tileChoice);
//
//            CurrentDeck.choosePair(player);
//            HabitatTile tile = player.getMap().getTileBoardPosition()[1][1];
//            assertTrue(tile.equalHabitats(
//                    new HabitatTile(
//                            HabitatTile.Habitat.Prairie, HabitatTile.Habitat.Wetland, 0)
//            ));
//        }
//    }
//
//    @Test
//    public void testChoosePair2() {
//        int tileChoice = 3;
//        Player player = new Player("test");
//        try (MockedStatic<Input> utilities = Mockito.mockStatic(Input.class)) {
//            utilities.when(() -> Input.chooseTilePlacement(player))
//                    .thenReturn(new int[]{1,1});
//            utilities.when(() -> Input.chooseTokenPlaceOrReturn(CurrentDeck.getDeckTokens().get(tileChoice)))
//                    .thenReturn(new int[]{2, -1});
//            utilities.when(Input::chooseFromDeck)
//                    .thenReturn(tileChoice);
//
//            CurrentDeck.choosePair(player);
//            HabitatTile tile = player.getMap().getTileBoardPosition()[1][1];
//            assertTrue(tile.equalHabitats(
//                    new HabitatTile(
//                            HabitatTile.Habitat.Mountain, HabitatTile.Habitat.River, 0)
//            ));
//        }
//    }
//
//    @Test
//    public void testChoosePair3() {
//        Player player = new Player("test");
//        int tileChoice = 1;
//        try (MockedStatic<Input> utilities = Mockito.mockStatic(Input.class)) {
//            utilities.when(() -> Input.chooseTilePlacement(player))
//                    .thenReturn(new int[]{1,1});
//            utilities.when(() -> Input.chooseTokenPlaceOrReturn(CurrentDeck.getDeckTokens().get(tileChoice)))
//                    .thenReturn(new int[]{2, -1});
//            utilities.when(Input::chooseFromDeck)
//                    .thenReturn(tileChoice);
//
//            CurrentDeck.choosePair(player);
//
//            HabitatTile tile = player.getMap().getTileBoardPosition()[1][1];
//            assertTrue(tile.equalHabitats(
//                    new HabitatTile(
//                            HabitatTile.Habitat.Prairie, HabitatTile.Habitat.Wetland, 0)
//            ));
//        }
//    }
//
//    @Test(timeout = 1000)
//    public void testChoosePairHelperExceptions() {
//        Player test = new Player("test");
//        try {
//            CurrentDeck.choosePairHelper(null, 1, 1);
//            fail();
//        } catch (IllegalArgumentException ignored) { }
//        try {
//            CurrentDeck.choosePairHelper(test, -1, 1);
//            fail();
//        } catch (IllegalArgumentException ignored) { }
//        try {
//            CurrentDeck.choosePairHelper(test, 4, 1);
//            fail();
//        } catch (IllegalArgumentException ignored) { }
//        try {
//            CurrentDeck.choosePairHelper(test, 1, -1);
//            fail();
//        } catch (IllegalArgumentException ignored) { }
//        try {
//            CurrentDeck.choosePairHelper(test, 1, 4);
//            fail();
//        } catch (IllegalArgumentException ignored) { }
//        try {
//            CurrentDeck.choosePairHelper(null, -1, -1);
//            fail();
//        } catch (IllegalArgumentException ignored) { }
//
//        CurrentDeck.removeDeckToken(1);
//
//        try {
//            CurrentDeck.choosePairHelper(test, 2, 3);
//            fail();
//        } catch (IllegalArgumentException ignored) { }
//        try {
//            CurrentDeck.choosePairHelper(test, 3, 3);
//            fail();
//        } catch (IllegalArgumentException ignored) { }
//    }
//
//    @Test
//    public void testRemoveDeckToken() {
//        try {
//            CurrentDeck.removeDeckToken(-1);
//            fail();
//        } catch (IllegalArgumentException ignored) {}
//        try {
//            CurrentDeck.removeDeckToken(4);
//            fail();
//        } catch (IllegalArgumentException ignored) {}
//        CurrentDeck.removeDeckToken(3);
//        try {
//            CurrentDeck.removeDeckToken(3);
//            fail();
//        } catch (IllegalArgumentException ignored) {}
//        CurrentDeck.removeDeckToken(2);
//    }
//
//    @Test
//    public void testSetStartTileTokenSelection() {
//        try {
//            CurrentDeck.setDeckTiles(new ArrayList<>());
//            CurrentDeck.setDeckTokens(new ArrayList<>());
//            CurrentDeck.setStartTileTokenSelection();
//            assertTrue(true);
//        } catch (Exception ignored) {
//            fail();
//        }
//    }
//
//    @Test
//    public void testAddDeckToken() {
//        try {
//            CurrentDeck.addDeckToken(WildlifeToken.Salmon);
//            fail();
//        } catch (IllegalArgumentException ignored) {}
//        // set token list to just one token and then try adding a token 3 times
//        ArrayList<WildlifeToken> tokenArrayList = new ArrayList<>();
//        tokenArrayList.add(WildlifeToken.Salmon);
//        CurrentDeck.setDeckTokens(tokenArrayList);
//
//        CurrentDeck.addDeckToken(WildlifeToken.Bear);
//        CurrentDeck.addDeckToken(WildlifeToken.Bear);
//        CurrentDeck.addDeckToken(WildlifeToken.Bear);
//        assertEquals(CurrentDeck.getDeckTokens().size(), 4);
//    }
//
//    @Test
//    public void testAddDeckTile() {
//        try {
//            CurrentDeck.addDeckTile(new HabitatTile(HabitatTile.Habitat.Mountain,
//                    HabitatTile.Habitat.Prairie, 1));
//            fail();
//        } catch (IllegalArgumentException ignored) {}
//        // set token list to just one token and then try adding a token 3 times
//        List<HabitatTile> tileArrayList = new ArrayList<>();
//        tileArrayList.add(new HabitatTile(HabitatTile.Habitat.River, HabitatTile.Habitat.Forest, 1));
//        CurrentDeck.setDeckTiles(tileArrayList);
//
//        CurrentDeck.addDeckTile(new HabitatTile(HabitatTile.Habitat.River, HabitatTile.Habitat.Forest, 1));
//        CurrentDeck.addDeckTile(new HabitatTile(HabitatTile.Habitat.River, HabitatTile.Habitat.Forest, 1));
//        CurrentDeck.addDeckTile(new HabitatTile(HabitatTile.Habitat.River, HabitatTile.Habitat.Forest, 1));
//        assertEquals(CurrentDeck.getDeckTiles().size(), 4);
//    }
//}
