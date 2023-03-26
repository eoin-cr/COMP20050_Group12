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

import java.util.List;
import java.util.Objects;

/**
 * Deals with habitat tiles.
 * Stores information such as the habitats (and their corresponding colour),
 * the tile ID, the edges of the tiles (although managed by the
 * cascadia.Edge class), and deals with the generation of habitat tiles.
 *
 * @see Edge
 */
public class HabitatTile {
	public static final String ANSI_RESET = "\033[0m";
	public static final String WHITE = "\033[38;5;231m";
	public static final String WHITE_BG = "\033[48;5;231m";
	public static final String BLACK_TEXT = "\033[30m";
	private static final int NUMBER_OF_EDGES = 6;

	/**
	 * The habitats that a tile can have.
	 * Stores also the ANSI codes for text and background colours.
	 */
    public enum Habitat {
		Forest("\033[48;2;84;130;53m"),
		Wetland("\033[48;2;198;224;180m"),
		River("\033[44m"),
		Mountain("\033[47m"),
		Prairie("\033[103m");
		private final String backgroundColour;

		Habitat(String backgroundColour) {
			this.backgroundColour = backgroundColour;
		}

		/**
		 * Returns name of Habitat enum as a string, based on its constant value.
		 *
		 * @param ord index of name to get
		 */
		public static String getName(int ord) {
			for (Habitat h : Habitat.values()) {
				if (ord == h.ordinal()) {
					return h.name();
				}
			}
			return null;
		}
	}

	enum TileType { KEYSTONE, NON_KEYSTONE, FAKE }

	private TileType tileType;
	private WildlifeToken[] tokenOptions;
	private WildlifeToken placedToken = null;
	private boolean isTokenPlaced = false;
	// counts number of tiles instantiated, used to assign a tileID number
    private static int tileCounter = 0;
    private int tileID;
    private final Habitat habitat1;
    private final Habitat habitat2;
    private final int[] mapPosition = new int[2]; //set to -1 initially to show it's not been placed
	private final List<Edge> edges;

	/**
	 * Generates a habitat tile.
	 *
	 * @param habitat1 The first habitat in the tile
	 * @param habitat2 The second habitat in the tile
	 * @param numTokens The number of tokens to place
	 */
	public HabitatTile(Habitat habitat1, Habitat habitat2, int numTokens) {
		this.setTileID();
		this.setFakeTile(false);
		this.habitat1 = habitat1;
		this.habitat2 = habitat2;
		this.setKeystoneType();
		this.setTokenOptions(numTokens);
		this.setMapPosition(-1, -1);
		edges = Edge.makeEdges(tileID, habitat1, habitat2); //used for tile rotation
	}

	/**
	 * Allows the manual setting of the token, which is usually
	 * generated randomly, for testing.
	 * DO NOT USE THIS FOR WRITING ACTUAL CODE, ONLY FOR TESTS!!!
	 */
	public void setTokenForTesting(WildlifeToken token) {
		placedToken = token;
		isTokenPlaced = true;
	}
	
	public static int getTileCounter() {
		return tileCounter;
	}

	public Habitat getHabitat1() {
		return habitat1;
	}

	public Habitat getHabitat2() {
		return habitat2;
	}

	public void setTileID() {
		this.tileID = tileCounter;
		tileCounter++;
	}

	public int getTileID() {
		return tileID;
	}

	public void setKeystoneType() {
		if (habitat1 == habitat2) {
			this.tileType = TileType.KEYSTONE;
		} else {
			this.tileType = TileType.NON_KEYSTONE;
		}
	}

	public TileType getTileType() {
		return tileType;
	}

	public boolean isKeystone() {
		return tileType == TileType.KEYSTONE;
	}

	/**
	 * Sets which possible tokens can be placed on a tile.
	 * Enter 0 if the number of tokens to be placed do not matter
	 *
	 * @param numTokens the number of tokens to place on the tile. Set as 0
	 *                  if the number does not matter.
	 */
	public void setTokenOptions(int numTokens) {
		if (this.getTileType() == TileType.KEYSTONE) {
			numTokens = 1;
		}
		this.tokenOptions = Generation.generateTokenOptionsOnTiles(numTokens);
	}

	public WildlifeToken[] getTokenOptions() {
		return tokenOptions;
	}

	public boolean getIsTokenPlaced() {
		return isTokenPlaced;
	}

	public void setTokenPlaced() {
		isTokenPlaced = true;
	}

	public void setPlacedToken(WildlifeToken placedAnimal) {
		placedToken = placedAnimal;
	}

	public WildlifeToken getPlacedToken() {
		return placedToken;
	}

	/**
	 * Sets whether the tile is a 'fake' tile (i.e. it is a grey tile that
	 * simply represents the possible places on the map that a tile can be
	 * placed)
	 *
	 * @param isFake whether the tile is 'fake' or not
	 */
	public void setFakeTile(boolean isFake) {
		if (isFake) {
			tileType = TileType.FAKE;
		}
	}

	public boolean isFakeTile() {
		return tileType == TileType.FAKE;
	}

	public void setMapPosition(int x, int y) {
		mapPosition[0] = x;
		mapPosition[1] = y;
	}

	public int[] getMapPosition() {
		return mapPosition;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public Edge getEdge(int index) {
		return edges.get(index);
	}

	@Override
	public String toString() {
		if (tileType == TileType.KEYSTONE) {
			return habitat1.name() + " Keystone";
		}
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
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		HabitatTile tile = (HabitatTile) o;
		return tileID == tile.tileID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tileID);
	}

	public void rotateTile(int input) {
		// we don't want to rotate a keystone tile
		if (isKeystone()) {
			return;
		}

		// if the input is -1 we want the user to select an orientation,
		// otherwise, we just rotate the tile by the amount given
		if (input == -1) {
			input = Input.boundedInt(1, NUMBER_OF_EDGES,
					"Which position would you like to rotate "
							+ "to? (Type a number between 1-6): ");
		}

		HabitatTile.Habitat[] temp = new Habitat[NUMBER_OF_EDGES];

		for (int i = 0; i < NUMBER_OF_EDGES; i++) {
			temp[(i + input) % NUMBER_OF_EDGES] = edges.get(i).getHabitatType();
		}

		for (int j = 0; j < NUMBER_OF_EDGES; j++) {
			edges.get(j).setHabitatType(temp[j]);
		}
	}

	/**
	 * Returns a formatted string version of the habitat tile, so that it can
	 * be altered easily before printing.
	 *
	 * @return a string with ANSI colours.
	 */	
	public String toFormattedString() {
		final int MAX_TOKENS_ON_TILE = 3;
		final int NUM_COLOURS_IN_TILE = 4;
		if (isFakeTile()) {
			final String GREY = "\033[37m";
			return GREY + "|||| |||| |||| ||||" + ANSI_RESET + "\n"
					+ GREY + "||||  " + String.format("%-3s", tileID)
					+ "      ||||" + ANSI_RESET + "\n"
					+ GREY + "||||           ||||" + ANSI_RESET + "\n"
					+ GREY + "|||| |||| |||| ||||" + ANSI_RESET + "\n";
		}

		char[] animal = new char[MAX_TOKENS_ON_TILE];
		String[] colour = new String[NUM_COLOURS_IN_TILE];

		String ID_COLOUR = setColours(animal, colour);

		return 	edges.get(5).getHabitatType().backgroundColour + "    |    |" + ANSI_RESET
				+ edges.get(0).getHabitatType().backgroundColour +  "    |    " + ANSI_RESET + "\n"
				+ edges.get(4).getHabitatType().backgroundColour + "    |" + ANSI_RESET
				+ colour[0] + "  " + animal[0] + "  " + ANSI_RESET
				+ colour[1] + " " + animal[1] + "  " + ANSI_RESET
				+ edges.get(1).getHabitatType().backgroundColour  + "|    " + ANSI_RESET + "\n"
				+ edges.get(4).getHabitatType().backgroundColour + "    |" + ANSI_RESET
				+ colour[2] + "  " + animal[2] + ANSI_RESET
				// inserts tile number and adds padding
				+ colour[3] + "   " + ID_COLOUR + String.format("%-3s", tileID) + ANSI_RESET
				+ edges.get(1).getHabitatType().backgroundColour + "|    " + ANSI_RESET + "\n"
				+ edges.get(3).getHabitatType().backgroundColour + "    |    " + ANSI_RESET
				+ edges.get(2).getHabitatType().backgroundColour + "|    |    " + ANSI_RESET + "\n";
	}

	public boolean equalHabitats(HabitatTile tile) {
		return (habitat1 == tile.habitat1 && habitat2 == tile.habitat2
				|| habitat1 == tile.habitat2 && habitat2 == tile.habitat1);
	}

	private String setColours(char[] animal, String[] colour) {
		String ID_COLOUR;  // the colour of the ID text, so it's always easily visible
		if (!isTokenPlaced) {
			ID_COLOUR = BLACK_TEXT;
			for (int i = 0; i < tokenOptions.length; i++) {
				if (tokenOptions[i] != null) {
					animal[i] = tokenOptions[i].toChar();
					colour[i] = tokenOptions[i].getColour() + WHITE_BG;
				} else {
					animal[i] = ' ';
					colour[i] = "" + WHITE_BG;
				}
			}
			colour[3] = WHITE_BG;

		} else {
			ID_COLOUR = WHITE;
			animal[0] = placedToken.toChar();
			colour[0] = placedToken.getBackgroundColour() + WHITE;
			for (int i = 1; i < 4; i++) {
				if (i < 3) {
					animal[i] = ' ';
				}
				colour[i] = WHITE;
			}
		}
		return ID_COLOUR;
	}
}