import java.util.HashMap;
import java.util.Random;

public class HabitatTile {
    enum HABITATS {Forest, Wetland, River, Mountain, Prairie}
    HABITATS habitat1;
    HABITATS habitat2;
	private final Integer[] position = new Integer[2];
    
	public HabitatTile(HabitatTile.HABITATS habitat1, HabitatTile.HABITATS habitat2) { //constructor
		this.setHabitat1(habitat1);
		this.setHabitat2(habitat2);
	}

	public HABITATS getHabitat1() { //getters and setters
		return habitat1;
	}

	public void setHabitat1(HABITATS habitat1) {
		this.habitat1 = habitat1;
	}

	public HABITATS getHabitat2() {
		return habitat2;
	}

	public void setHabitat2(HABITATS habitat2) {
		this.habitat2 = habitat2;
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
