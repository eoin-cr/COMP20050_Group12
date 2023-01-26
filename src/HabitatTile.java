import java.util.HashMap;
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
		// TODO: do habitat random generation logic.  Make sure to use the hashmap
		// so there won't be a bunch of the same type of tile on the board.

		// just going to return a random full tile for now
		int index = new Random().nextInt(5);
		return new HabitatTile(HABITATS.values()[index], HABITATS.values()[index]);
	}
}
