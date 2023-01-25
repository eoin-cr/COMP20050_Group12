import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Game {
    static String[] playerNames;

    // ~~Get player names~~
    // Generate starter tiles
    // Generate 4 tiles shown to player
    // Generate 4 animal tokens
    // Print these to console
    // Allow user to place tile
    // Store placed tile
    // Allow user to place token
    // Store placed token
    // Continue until num of tokens has run out.

    public static void main(String[] args) {
        Output.Welcome();

        try {
            Thread.sleep(700);
        } catch (InterruptedException ignored) {}

        // NOTE: the clear screen command does NOT work in the IDE terminal,
        // just in an actual command prompt.
        Output.clearScreen();

        playerNames = Input.getPlayers();
//        System.out.println(Arrays.toString(playerNames));
//        System.out.println();
        AtomicInteger index = new AtomicInteger();
        System.out.println("The player list is:");
        // fancy stream stuff
        System.out.println(
                Arrays.stream(playerNames)
                        .map(name -> index.getAndIncrement() + " " + name)
                        .collect(Collectors.joining("\n"))
        );

//        Tile tile = new Tile();
//        new DrawTiles(tile);
    }

    public Game() {

    }
}
