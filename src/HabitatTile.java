import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HabitatTile {
    enum HABITATS {Forest, Wetland, River, Mountain, Prairie}
    public static int counter; //counts number of tiles instantiated, used to assign a tileID number, modified in constructor
    private int tileID; //identifying number for a tile, used in Edge class
    private HABITATS habitat1;
    private HABITATS habitat2;
	private final Integer[] position = new Integer[2]; //cartesian plane coordinates for display purposes
	private Edge[] edges; //stores what the 6 edges of the tile are connected to, if anything
    
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
}
