import java.util.Objects;

/**
 * Deals with habitat tiles.
 * Stores information such as the habitats (and their corresponding colour),
 * the tile ID, the edges of the tiles (although managed by the
 * Edge class), and deals with the generation of habitat tiles.
 * @see Edge
 */
public class HabitatTile {
	public static final String ANSI_RESET = "\033[0m";
	// public static final String ANSI_BLACK = "\033[30m";

	// storing the ANSI habitat colours in the enum is better practice than using ordinals

	/**
	 * The habitats that a tile can have.
	 * Stores also the ANSI codes for text and background colours.
	 */
    enum Habitat {
		Forest("\033[38;2;84;130;53m", "\033[48;2;84;130;53m"),
		Wetland("\033[48;2;198;224;180m", "\033[48;2;198;224;180m"),
		River("\033[34m", "\033[44m"),
		Mountain("\033[37m", "\033[47m"),
		Prairie("\033[93m", "\033[103m");
		private final String colour;
		private final String backgroundColour;
		Habitat(String colour, String backgroundColour) {
			this.colour = colour;
			this.backgroundColour = backgroundColour;
		}
		public String getColour() {
			return colour;
		}
		public String getBackgroundColour() {
			return backgroundColour;
		}
	}

	enum TileType {KEYSTONE, NON_KEYSTONE}

    public static int counter = 0;  // counts number of tiles instantiated, used to assign a tileID number, modified in constructor
    private final int tileID;  // identifying number for a tile, used in Edge class
    private final Habitat habitat1;
    private final Habitat habitat2;
	private Edge[] edges;  // stores what the 6 edges of the tile are connected to, if anything

	public HabitatTile(Habitat habitat1, Habitat habitat2) {  // constructor
		this.tileID = counter;
		counter++;
		this.habitat1 = habitat1;
		this.habitat2 = habitat2;
		this.edges = new Edge[6];
	}

	public Habitat getHabitat1() {  // getters and setters
		return habitat1;
	}

	public Habitat getHabitat2() {
		return habitat2;
	}

	@Override
	public String toString() {
		return habitat1.name() + " + " + habitat2.name();
	}

	/**
	 * Overrides equals method.
	 * Returns true if two tiles have the same tile ID.
	 *
	 * @param o the reference object to which to compare
	 * @return true if both tiles have the same tile ID.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		HabitatTile tile = (HabitatTile) o;
		return tileID == tile.tileID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tileID);
	}


	/**
	 * Returns a formatted string version of the habitat tile, so that it can
	 * be altered easily before printing.
	 * At the moment only the habitat is coloured, the tokens are not.
	 *
	 * @param topLeft the top left char
	 * @param topRight the top right char
	 * @param bottomLeft the bottom left char
	 * @param bottomRight the bottom right char
	 * @return a string with ANSI colours.
	 */
	public String toFormattedString(char topLeft, char topRight, char bottomLeft, char bottomRight) {
		String first = habitat1.getBackgroundColour();
		String second = habitat2.getBackgroundColour();
		String full =  "    |    |    |    " + ANSI_RESET + "\n";
		return first + full +
				first + "    |" + ANSI_RESET + "  " + topLeft + "   " + topRight + "  " +
				first  + "|    " + ANSI_RESET + "\n" +
				second + "    |" + ANSI_RESET + "  " + bottomLeft  + "   " + bottomRight + "  " +
				second  + "|    " + ANSI_RESET + "\n" +
				second + full;
	}
}
