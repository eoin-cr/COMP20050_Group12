import java.util.HashMap;
import java.util.Random;

public class WildlifeToken {
	public enum ANIMAL {Bear, Elk, Salmon, Hawk, Fox}
	private ANIMAL animalType;
	private boolean inUse; //if it's been placed on a player map or stashed in bag
	
	public WildlifeToken(WildlifeToken.ANIMAL animalType) {
		this.animalType = animalType;
		this.inUse = false;
	}
	
	public ANIMAL getAnimalType() {
		return animalType;
	}

	public boolean isInUse() {
		return inUse;
	}

	public void setInUse() { //sets to true, don't forget to use this if placing token on a map!
		this.inUse = true;
	}
	
	public static WildlifeToken generateWildlifeToken (HashMap<WildlifeToken.ANIMAL, Integer> wildlifeTokensRemaining) {
		// TODO: do token random generation logic.  Make sure to use the hashmap
		// so there won't be a bunch of the same type of tile on the board.

		int index = new Random().nextInt(5);
		return new WildlifeToken(ANIMAL.values()[index]);
	}
	
	

}
