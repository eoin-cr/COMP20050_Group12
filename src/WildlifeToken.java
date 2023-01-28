import java.util.HashMap;
import java.util.Map;
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
	
	//method to randomly generate a wildlife token
	public static WildlifeToken generateWildlifeToken (HashMap<WildlifeToken.ANIMAL, Integer> wildlifeTokensRemaining) {
		int tokensLeft = 0;

		// get the total amount of tokens left of all animal types
		for (Integer value : wildlifeTokensRemaining.values()) {
			tokensLeft += value;
		}

		int index = new Random().nextInt(tokensLeft);
		ANIMAL animalType = null;

		
		for (Map.Entry<WildlifeToken.ANIMAL, Integer> entry : wildlifeTokensRemaining.entrySet()) {
			index -= entry.getValue();
			if (index <= 0 && animalType == null) {
				animalType = entry.getKey();
				wildlifeTokensRemaining.put(entry.getKey(), entry.getValue() - 1);
			}

		}
		
		return new WildlifeToken(animalType);
	}
	
	

}
