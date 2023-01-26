public class DrawTiles {
//    HabitatTile tile;
//    public DrawTiles(HabitatTile tile) {
//        this.tile = tile;
//        PrintFullTile();
//    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";

    // ANSI colours to colour console text.  Indexes match with the
    // Tile.habitats enum.
    static String[] HabitatColours = {
            "\u001B[32m",
            "\u001B[36m",
            "\u001B[34m",
            "\u001B[37m",
            "\u001B[33m"
    };

    public static void PrintFullTile (HabitatTile tile) {
        // NOTE: only draws the first habitat colour for now
//        System.out.println(tile.habitat1.ordinal());
        System.out.println(HabitatColours[tile.habitat1.ordinal()] +
                "|||| |||| |||| ||||\n" +
                "||||           ||||\n" +
                "||||           ||||\n" +
                "|||| |||| |||| ||||\n" +
                ANSI_RESET
                );
    }

//    public static void main(String[] args) {
//        for (Tile.habitats Element : Tile.habitats.values()) {
//            System.out.println(HabitatColours[Element.ordinal()] + Element + ANSI_RESET);
//        }
//    }
}
