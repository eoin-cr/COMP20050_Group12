public class HabitatTile {
    enum HABITATS {Forest, Wetland, River, Mountain, Prairie}
    HABITATS habitat1;
    HABITATS habitat2;
    
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
	
	
    
    
    

}
