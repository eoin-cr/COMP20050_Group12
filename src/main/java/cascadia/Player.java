package cascadia;

import java.util.ArrayList;
import java.util.Arrays;

import cascadia.HabitatTile.Habitat;
import cascadia.scoring.ScoringHabitatCorridors;

//mynah - change made : changes made to whole class, maybe just copy and paste in
/**
 * Stores information about the player, including
 * an individual player's map of tiles.
 * @see PlayerMap
 */
public class Player {
	private final String playerName;
	private final PlayerMap map;
	private int playerNatureTokens;
	//indexing -> 0: Forest corridor, 1: Wetland corridor, 2: River corridor, 3: Mountain corridor, 4: Prairie corridor
	private final ArrayList<ArrayList<HabitatTile>> longestCorridors = new ArrayList<>(5); //mynah - change made
	private final int[] longestCorridorSizes = new int[5]; 
	private final int[] wildlifeScores = new int[5];
	//indexing -> 0: Bear score, 1: Elk score, 2: Salmon score, 3: Hawk score, 4: Fox score 
	private int wildlifeTotalScore;
	private int corridorTotalScore;
	private int corridorBonuses;
	private int totalPlayerScore;

	public Player(String playerName) {
		this.playerName = playerName;
		for (int i = 0; i < 5; i++) {
			ArrayList<HabitatTile> newCorridor = new ArrayList<>();
			longestCorridors.add(newCorridor);
		}
		map = new PlayerMap();
		for (HabitatTile t : map.getTilesInMap()) { //score initial corridors when starter tiles added
			ScoringHabitatCorridors.scorePlayerHabitatCorridors(this, t);
		}
	}
	
	public String getPlayerName() {
		return playerName;
	}
	public PlayerMap getMap() {
		return map; 
		//note: to modify the map, you have to use player.getMap().addTileToMap()
	}
	public int getPlayerNatureTokens() {
		return playerNatureTokens;
	}
	public void addPlayerNatureToken() {
		this.playerNatureTokens++;
	}
	public void subPlayerNatureToken() {
		if (playerNatureTokens > 0) {
			this.playerNatureTokens--;
		}
		else {
			this.playerNatureTokens = 0;
			throw new IllegalArgumentException("Out of nature tokens, cannot subtract further");
		}
	}
	
	public int getPlayerWildlifeScore(WildlifeToken token) {
		return wildlifeScores[token.ordinal()];	
	}
	public void setPlayerWildlifeScore(WildlifeToken token, int score) {
		wildlifeScores[token.ordinal()] = score;
	}
	public int[] getLongestCorridorSizes() {
		return longestCorridorSizes;
	}
	public void setLongestCorridor(int index, ArrayList<HabitatTile> corridor) {
		longestCorridors.set(index, corridor);
		longestCorridorSizes[index] = corridor.size();
	}
	public ArrayList<HabitatTile> getLongestCorridor(Habitat habitatType) {
		return longestCorridors.get(habitatType.ordinal());
	}
	public int getLongestCorridorSize(Habitat habitatType) {
		return longestCorridorSizes[habitatType.ordinal()];
	}
	
	public void addCorridorBonus(int bonus) {
		this.corridorBonuses += bonus;
	}
	public void addToTotalPlayerScore(int score) {
		this.totalPlayerScore += score;
	}
	
	public int calculateWildlifePlayerScore() {
		wildlifeTotalScore = Arrays.stream(wildlifeScores).sum();
		return wildlifeTotalScore;
	}
	public int getWildLifeScore() {
		return wildlifeTotalScore;
	}
	public int calculateCorridorsPlayerScore() {
		corridorTotalScore = Arrays.stream(longestCorridorSizes).sum();
		return corridorTotalScore;
	}
	public int getCorridorScore() {
		return corridorTotalScore;
	}
	public void calculateTurnPlayerScore() {
		totalPlayerScore = calculateWildlifePlayerScore() + calculateCorridorsPlayerScore() + playerNatureTokens;
	}
	public void calculateTotalEndPlayerScore(){
		totalPlayerScore = calculateWildlifePlayerScore() + calculateCorridorsPlayerScore() + playerNatureTokens + corridorBonuses;
	}
	public int getTotalPlayerScore() {
		return totalPlayerScore;
	}

	@Override
	public String toString() {
		return playerName;
	}

    /**
	 * Allows the user to select an option from the command menu.
	 * Within this method the possible commands are displayed and user input is
	 * taken and acted upon.
	 */
    public void setCommand() {
        Display.displayCommands();
        String input = Input.getUserInput();  // this is automatically uppercase

        // automatically converts input to enum
        try {
			Command command = Command.valueOf(input);
            command.enumSetCommand(this);  // calls the function represented in the enum
//		} catch (AbstractMethodError impossibleError) {} // this should throw basically all errors for debugging
        } catch (IllegalArgumentException ex) {  // catches if the input is not an enum element
            Display.outln("Invalid input for options of commands. Please try again. \n");

            // this catches all illegal argument exceptions, so we're gonna just print them for
            // now until we make sure we've caught them all before they reach this stage
//			Display.outln(ex.toString());
        }
    }
}
