public class Command { // helps model player state

	// each enum type has its own function, so we don't need to use a switch statement
	// with a function for each every time we want to call it.  The code inside the
	// function is the same as you'd put in the switch statement.
	// doing this is better practice as it reduces the amount of switch statements, and
	// if we add a value to the enum, we are less likely to forget to add it to all the
	// corresponding switch statements
	// Note: make sure that you're calling this function on an enum that exists, if it
	// doesn't exist it will throw an error.
	enum CommandType {
		NEXT {public void enumSetCommand(Player player){}},

		//display tiles on player map
		MAP {public void enumSetCommand(Player player){
//				Output.displayTileMap(player);
				Output.displayStarterHabitat(player);
			}},

		//display player tiles in inventory
		TILES {public void enumSetCommand(Player player){}},

		//display player tokens in inventory
		TOKENS {public void enumSetCommand(Player player){}},

		//places token on map and displays new map
		PLACETOKEN {public void enumSetCommand(Player player){}},

		//ben's method here (or in main game)
		QUIT {public void enumSetCommand(Player player){}};
		public abstract void enumSetCommand(Player player); // ignore this
	}
	private CommandType command;
	
	public Command() {
		this.command = null;
	}
	
	public void setCommand(Player player) {
		Output.displayCommands();
		String input = Input.getUserInput(); //this is automatically uppercase

		// automatically converts input to enum
		try {
			command = CommandType.valueOf(input);
			command.enumSetCommand(player); // calls the function represented in the enum
		} catch (IllegalArgumentException ex) { // catches if the input is not an enum element
			System.out.println("Invalid input for options of commands. Please try again.");
		}
	}
	
	public CommandType getCommand() {
		return command;
	}

}
