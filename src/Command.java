
public class Command { //helps model player state
	enum CommandType {NEXT, MAP, TILES, TOKENS, PLACETOKEN, QUIT}
	private CommandType command;
	
	public Command() {
		this.command = null;
	}
	
	public void setCommand(Player player) {
		Output.displayCommands();
		String input = Input.getUserInput(); //this is automatically uppercase
			
		switch (input) {
		case "NEXT":
			command = CommandType.NEXT;
			break;
			
		case "MAP": //display tiles on player map
			command = CommandType.MAP;
			Output.displayTileMap(player);
			break;
			
		case "TILES": //display player tiles in inventory
			command = CommandType.TILES;
			
			break;
			
		case "TOKENS": //display player tokens in inventory
			command = CommandType.TOKENS;
			break;
			
		case "PLACETOKEN": //places token on map and displays new map
			command = CommandType.PLACETOKEN;
			break;
			
		case "QUIT":
			command = CommandType.QUIT;
			//ben's method here (or in main game)
			break;
			
		default:
			command = null;
			System.out.println("Invalid input for options of commands. Please try again.");
			
		}
		
	}
	
	public CommandType getCommand() {
		return command;
	}

}
