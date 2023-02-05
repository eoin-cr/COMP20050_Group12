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
		CHOOSEPAIR {public void enumSetCommand(Player player){
			CurrentDeck.choosePair(player);
		}},
		
		DECK {public void enumSetCommand(Player player){
			Display.displayDeck();
		}},
		
		/** Displays the nature token shop */
		NATURE {public void enumSetCommand(Player player){}},
		
		/** Display the player's map of tiles */
		MAP {public void enumSetCommand(Player player){
				Display.displayTileMap(player);
			}},
		
		/** Moves to next players turn. Note that the game is exited after the last
		 * player selects this option.*/
		NEXT {public void enumSetCommand(Player player){}},

		/** Quits the game */
		QUIT {public void enumSetCommand(Player player){
			Display.endScreen();
			System.exit(0);
		}};

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
		} catch (IllegalArgumentException ex) {  // catches if the input is not an enum element
			System.out.println("Invalid input for options of commands. Please try again. \n");
		}
	}
	
	public CommandType getCommand() {
		return command;
	}

}
