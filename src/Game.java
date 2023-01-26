import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
        ArrayList<Player> players = new ArrayList<>();
        HashMap<HabitatTile.HABITATS, Integer> remainingTiles = new HashMap<>();

        Output.Welcome();

        try {
            Thread.sleep(700);
        } catch (InterruptedException ignored) {}

        // NOTE: the clear screen command does NOT work in the IDE terminal,
        // just in an actual command prompt.
        Output.clearScreen();

        playerNames = Input.getPlayers();
        System.out.println("The player list is:");
        // fancy stream stuff
        AtomicInteger index = new AtomicInteger();
        System.out.println(
                Arrays.stream(playerNames)
                        .map(name -> index.getAndIncrement() + " " + name)
                        .collect(Collectors.joining("\n"))
        );


        // Generates player objects and adds them to an array
        ArrayList<HabitatTile> tmp = new ArrayList<>();
        for (String name : playerNames) {
            Integer[] startingPositions = {9, 10, 10, 9, 10, 10};
            for (int i = 0; i < 3; i++) {
                HabitatTile tmpTile = HabitatTile.generateHabitatTile(remainingTiles);
                tmpTile.setPosition(startingPositions[i*2], startingPositions[i*2+1]);
                tmp.add(tmpTile);

                // display habitat
                DrawTiles.PrintFullTile(tmpTile);
            }
            Player player = new Player(name);
            player.setTiles(tmp);
            players.add(player);
        }
    }
}
