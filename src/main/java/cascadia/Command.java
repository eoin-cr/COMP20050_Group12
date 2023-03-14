package cascadia;

import cascadia.scoring.ScoreCards;
import cascadia.scoring.Scoring;

/**
 * Helps model player state.
 * Deals with interacting with the user on their turn.
 */
// TODO: make this entire class an enum, move the input stuff to the input class
public class Command {
	/*
	 * each enum type has its own function, so we don't need to use a switch statement
	 * with a function for each every time we want to call it.  The code inside the
	 * function is the same as you'd put in the switch statement.
	 * doing this is better practice as it reduces the amount of switch statements, and
	 * if we add a value to the enum, we are less likely to forget to add it to all the
	 * corresponding switch statements
	 * Note: make sure that you're calling this function on an enum that exists, if it
	 * doesn't exist it will throw an error.
	 */

	/** The commands available to the player on their turn */
	enum CommandType {

		/** Lets player choose tile+token pair and place tile down on map*/
		PAIR ("Enter PAIR to pick and place your Habitat Tile and Wildlife Token pair,")
				{public void enumSetCommand(Player player){
			CurrentDeck.choosePair(player);
		}},
		
		/** cascadia.Display the player's map of tiles */
		MAP ("Enter MAP for your current map of Tiles,")
				{public void enumSetCommand(Player player){
				Display.displayPlayerTileMap(player);
			}},
		
		DECK ("Enter DECK to see current deck of Tile + Token pairs again,")
				{public void enumSetCommand(Player player){
			Display.displayDeck();
		}},
		
		SC ("Enter SC to see the scorecard list again,")
				{public void enumSetCommand(Player player){
			ScoreCards.printScoreCardRules();
		}},
		
		/** Displays the nature token shop */
		NATURE ("Enter NATURE to see and spend your Nature Tokens,")
				{public void enumSetCommand(Player player){
					NatureTokens.tokenMenu(player);
				}},
		
//		/** Moves to next players turn */
//		NEXT {public void enumSetCommand(cascadia.Player player){
//			return;
//		}},

		/** Quits the game */
		QUIT ("Enter QUIT to quit the program."){public void enumSetCommand(Player player){
			Scoring.scoreCardScoring(); //find winner of game
			Display.endScreen();
			System.exit(0);
		}};

		private final String description;
		CommandType(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

		public abstract void enumSetCommand(Player player);  // ignore this
	}
	private CommandType command;
	
	public Command() {
		this.command = null;
	}

	/**
	 * Allows the user to select an option from the command menu.
	 * Within this method the possible commands are displayed and user input is
	 * taken and acted upon.
	 *
	 * @param player the player who is going to enter the command
	 */
	public void setCommand(Player player) {
		Display.displayCommands();
		String input = Input.getUserInput();  // this is automatically uppercase

		// automatically converts input to enum
		try {
			command = CommandType.valueOf(input);
			command.enumSetCommand(player);  // calls the function represented in the enum
//		} catch (AbstractMethodError impossibleError) {} // this should throw basically all errors for debugging
		} catch (IllegalArgumentException ex) {  // catches if the input is not an enum element
			System.out.println("Invalid input for options of commands. Please try again. \n");

			// this catches all illegal argument exceptions, so we're gonna just print them for
			// now until we make sure we've caught them all before they reach this stage
//			System.out.println(ex.toString());
		}
	}
	
//	public CommandType getCommand() {
//		return command;
//	}
}
