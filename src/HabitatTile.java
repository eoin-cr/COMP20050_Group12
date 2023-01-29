import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HabitatTile {
    enum HABITATS {Forest, Wetland, River, Mountain, Prairie}
    public static int counter; //counts number of tiles instantiated, used to assign a tileID number, modified in constructor
    private int tileID; //identifying number for a tile, used in Edge class
    private final HABITATS habitat1;
    private final HABITATS habitat2;
	private final Integer[] position = new Integer[2]; //cartesian plane coordinates for display purposes
	private Edge[] edges; //stores what the 6 edges of the tile are connected to, if anything


	public static final String ANSI_RESET = "\u001B[0m";
//    public static final String ANSI_BLACK = "\u001B[30m";

	// ANSI colours to colour console text.  Indexes match with the
	// Tile.habitats enum.
	static String[] HabitatColours = {
			"\u001B[32m",
			"\u001B[36m",
			"\u001B[34m",
			"\u001B[37m",
			"\u001B[33m"
	};

	static String[] backgroundHabitatColours = {
			"\u001B[42m",
			"\u001B[46m",
			"\u001B[44m",
			"\u001B[47m",
			"\u001B[43m"
	};

	public HabitatTile(HabitatTile.HABITATS habitat1, HabitatTile.HABITATS habitat2) { //constructor
		this.tileID = counter;
		counter++;
		this.habitat1 = habitat1;
		this.habitat2 = habitat2;
		this.edges = new Edge[6];
	}

	public HABITATS getHabitat1() { //getters and setters
		return habitat1;
	}

	public HABITATS getHabitat2() {
		return habitat2;
	}

	public Integer[] getPosition() {
		return position;
	}

	public void setPosition(int x, int y) {
		position[0] = x;
		position[1] = y;
	}

	@Override
	public String toString() {
		return habitat1.name() + " + " + habitat2.name();
	}


	//method to randomly generate a habitat tile with implementation for two habitat types/mixed type
	public static HabitatTile generateHabitatTile (HashMap<HabitatTile.HABITATS, Integer> habitatsRemaining) {
		int tilesLeft = 0;

		// get the total amount of tiles left
		for (Integer value : habitatsRemaining.values()) {
			tilesLeft += value;
		}

		int num1 = new Random().nextInt(tilesLeft);
		int num2 = new Random().nextInt(tilesLeft);
		HABITATS first = null;
		HABITATS second = null;

		// if we imagine the hashmap to contains values formatted like [Forest, Forest, Forest, River...
		// then this function gets the num1th and num2th value, and then 'removes' it from
		// the list.
		for (Map.Entry<HabitatTile.HABITATS, Integer> entry : habitatsRemaining.entrySet()) {
			num1 -= entry.getValue();
			num2 -= entry.getValue();
			if (num1 <= 0 && first == null) {
				first = entry.getKey();
				habitatsRemaining.put(entry.getKey(), entry.getValue() - 1);
			}
			if (num2 <= 0 && second == null) {
				second = entry.getKey();
				habitatsRemaining.put(entry.getKey(), entry.getValue() - 1);
			}

		}
		return new HabitatTile(first, second);
	}

	public String toFormattedString(char char1, char char2, char char3, char char4) {
		String first = backgroundHabitatColours[habitat1.ordinal()];
		String second = backgroundHabitatColours[habitat2.ordinal()];
		String full =  "    |    |    |    " + ANSI_RESET + "\n";
		return first + full +
				first + "    |" + ANSI_RESET + "  " + char1 + "   " + char2 + "  " +
				first  + "|    " + ANSI_RESET + "\n" +
				second + "    |" + ANSI_RESET + "  " + char3  + "   " + char4 + "  " +
				second  + "|    " + ANSI_RESET + "\n" +
				second + full;
	}
}
