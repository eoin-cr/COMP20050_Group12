import java.util.ArrayList;

/** Stores information about the habitat type of a single edge of a tile.
 * Assists with the orientation of tile, for rotation.
 * Also assists with scoring habitat corridors. */
public class Edge {
	private HabitatTile.Habitat habitatType;
	//private boolean isConnected; //for later use for habitat corridors

	// Note: Edges of the hexagonal are numbered 0 (starting from the top right edge, going clockwise) to 5 (left top edge)
	// Total 6 sides, like in the diagram below
//	 	  5   0
//	 	  -- --
//	 	4|     |1
//	 	  -- --
//	 	  3	  2
	
	public Edge(int tileID, HabitatTile.Habitat habitatType) {
		if (tileID < 0 || tileID > HabitatTile.getTileCounter()) {
			throw new IllegalArgumentException("You are trying to make edges for a non-existent tile");
		}
		this.habitatType =habitatType;
	}
	
	public static ArrayList<Edge> makeEdges(int tileID, HabitatTile.Habitat habitat1, HabitatTile.Habitat habitat2) {
		ArrayList<Edge> edges = new ArrayList<>();
		//base orientation before rotation
		//sets sides 0,1,2 to habitat 1
		for (int i = 0; i < 3; i++) {
			Edge e = new Edge(tileID, habitat1);
			edges.add(e);
		}
		//sets sides 3,4,5 to habitat 2
		for (int i = 3; i < 6; i++) {
			Edge e = new Edge(tileID, habitat2);
			edges.add(e);
		}
		return edges;
	}
	
	public HabitatTile.Habitat getHabitatType() {
		return habitatType;
	}

	public void setHabitatType(HabitatTile.Habitat habitatType) {
		this.habitatType = habitatType;
	}
}