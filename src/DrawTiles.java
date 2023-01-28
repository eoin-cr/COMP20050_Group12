public class DrawTiles {
//    HabitatTile tile;
//    public DrawTiles(HabitatTile tile) {
//        this.tile = tile;
//        PrintFullTile();
//    }

    public static final String ANSI_RESET = "\u001B[0m";
//    public static final String ANSI_BLACK = "\u001B[30m";

    // ANSI colours to colour console text.  Indexes match with the
    // Tile.habitats enum.
    static String[] HabitatColours = {
            "\u001B[32m",
            "\u001B[36m",
            "\u001B[34m",
            "\u001B[37m",
            "\u001B[33m"
    };

//    public static void PrintFullTile (HabitatTile tile) {
//        // NOTE: only draws the first habitat colour for now
////        System.out.println(tile.habitat1.ordinal());
//        System.out.println(HabitatColours[tile.getHabitat1().ordinal()] +
//                "|||| |||| |||| ||||\n" +
//                "||||           ||||\n" +
//                "||||           ||||\n" +
//                "|||| |||| |||| ||||\n" +
//                ANSI_RESET
//                );
//    }

    // note that these printing things are all pretty primitive atm
    public static void printHalfTile (HabitatTile tile) {
        System.out.println(HabitatColours[tile.getHabitat1().ordinal()] +
                "|||| |||| |||| ||||\n" +
                "||||           ||||\n" +
                ANSI_RESET +
                HabitatColours[tile.getHabitat2().ordinal()] +
                "||||           ||||\n" +
                "|||| |||| |||| ||||\n" +
                ANSI_RESET
        );


    }
}
