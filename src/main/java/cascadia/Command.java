package cascadia;

import cascadia.scoring.ScoreCards;
import cascadia.scoring.Scoring;

/**
 * Helps model player state.
 * Deals with interacting with the user on their turn.
 */

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
enum Command {

	/** Lets player choose tile+token pair and place tile down on map. */
	PAIR("Enter PAIR to pick and place your Habitat Tile and Wildlife Token pair,") {
		public void execute(Player player) {
			CurrentDeck.choosePair(player);
		}
	},

	/** cascadia.Display the player's map of tiles */
	MAP("Enter MAP for your current map of Tiles,") {
		public void execute(Player player) {
			Display.displayPlayerTileMap(player);
		}
	},

	DECK("Enter DECK to see current deck of Tile + Token pairs again,") {
		public void execute(Player player) {
			Display.displayDeck();
		}
	},

	SC("Enter SC to see the scorecard list again,") {
		public void execute(Player player) {
			ScoreCards.printScoreCardRules();
		}
	},

	/** Displays the nature token shop. */
	NATURE("Enter NATURE to see and spend your Nature Tokens,") {
		public void execute(Player player) {
			NatureTokens.tokenMenu(player);
		}
	},

	/** Quits the game. */
	QUIT("Enter QUIT to quit the program.") {
		public void execute(Player player) {
			Scoring.startScoring(); //find winner of game
			Display.endScreen();
			System.exit(0);
		}
	};

	private final String description;

	Command(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public abstract void execute(Player player);  // ignore this
}
