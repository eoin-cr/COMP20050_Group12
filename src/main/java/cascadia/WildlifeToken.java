/*
	COMP20050 Group 12
	Eoin Creavin – Student ID: 21390601
	eoin.creavin@ucdconnect.ie
	GitHub ID: eoin-cr

	Mynah Bhattacharyya – Student ID: 21201085
	malhar.bhattacharyya@ucdconnect.ie
	GitHub ID: mynah-bird

	Ben McDowell – Student ID: 21495144
	ben.mcdowell@ucdconnect.ie
	GitHub ID: Benmc1
 */

package cascadia;

/**
 * The animals that a wildlife token can be.
 * Also stores the colour associated with the animal.
 */
public enum WildlifeToken {
	Bear('B', "\033[38;2;153;102;51m", "\033[48;2;153;102;51m"),
	Elk('E', "\033[30m", "\033[40m"),
	Salmon('S', "\033[38;2;255;51;255m", "\033[48;2;255;51;255m"),
	Hawk('H', "\033[34m", "\033[44m"),
	Fox('F', "\033[38;5;202m", "\033[48;5;202m");

	private final char character;
	private final String colour;
	private final String backgroundColour;

	WildlifeToken(char character, String colour, String backgroundColour) {
		this.character = character;
		this.colour = colour;
		this.backgroundColour = backgroundColour;
	}

	/**
	 * Returns the first character of the animal token name (e.g. B for Bear)
	 */
	public char toChar() {
		return character;
	}

	public String getColour() {
		return colour;
	}

	public String getBackgroundColour() {
		return backgroundColour;
	}
}
