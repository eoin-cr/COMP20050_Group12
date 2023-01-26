import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Game {
    private String[] playerNames;
    private ArrayList<Player> players = new ArrayList<>();
    HashMap<HabitatTile.HABITATS, Integer> remainingTiles = new HashMap<>();
    
    public Game() {} //default constructor

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

  //method 1: general start method for a game, drives action
   public void start() {
	   
       playerNames = Input.getPlayers(); //from Input class
       Output.printPlayers(playerNames); //from Output class
       makePlayers();
       setStartTileSelection();
   }
   
   //method 2: generates player objects and adds them to an array
   private void makePlayers() {
	   
       ArrayList<HabitatTile> tmp = new ArrayList<>(); //used to store tiles for player's starter tile map
       
       for (String name : playerNames) {
           Integer[] startingPositions = {9, 10, 10, 9, 10, 10};
           
           for (int i = 0; i < 3; i++) {
               HabitatTile tmpTile = HabitatTile.generateHabitatTile(remainingTiles);
               tmpTile.setPosition(startingPositions[i*2], startingPositions[i*2+1]); //??????
               tmp.add(tmpTile);

               // display habitat
               DrawTiles.PrintFullTile(tmpTile);
           }
           
           Player player = new Player(name);
           player.setPlayerTiles(tmp);
           players.add(player); //adds to game's arraylist of players
       }  
   }
   
   //still in progress
   //displays 4 sets of randomly paired habitat tiles and wildlife tokens for players to choose from
   private void setStartTileSelection() {
	   for (int i = 0; i < 3; i++) {
		   generateTokenTilePair();
	   }
	   
   }
   
   private void generateTokenTilePair() {
	   
   }
 
    
}
