public class WildlifeToken {
	/**
	 * The animals that a wildlife token can be.
	 * Also stores the colour associated with the animal.
	 */
	public enum ANIMAL {
		Bear('B', "\033[38;2;153;102;51m", "\033[48;2;153;102;51m"),
		Elk('E', "\033[30m", "\033[40m"),
		Salmon('S', "\033[38;2;255;51;255m", "\033[48;2;255;51;255m"),
		Hawk('H', "\033[34m", "\033[44m"),
		Fox('F', "\033[38;5;202m", "\033[48;5;202m");

		private final char character;
		private final String colour;
		private final String backgroundColour;
		ANIMAL(char character, String colour, String backgroundColour) {
			this.character = character;
			this.colour = colour;
			this.backgroundColour = backgroundColour;
		}

		/**
		 * @return the first character of the animal name
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
	private final ANIMAL animalType;

	public WildlifeToken(WildlifeToken.ANIMAL animalType) {
		this.animalType = animalType;
	}
	
	public ANIMAL getAnimalType() {
		return animalType;
	}

	@Override
	public String toString() {
		return animalType.name();
	}


}
