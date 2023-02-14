import java.util.ArrayList;
import java.util.List;

/** Assists with the orientation of tile, for rotation */
public class Edge {
	private int tileID;
	private HabitatTile.Habitat habitatType;
	//private boolean isConnected; //for later use for habitat corridors

	// Note: Edges of the hexagonal are numbered 0 (starting from the top right edge, going clockwise) to 5 (left top edge)
	// Total 6 sides, like in the diagram below
//	 	  5   0
//	 	  -- --
//	 	4|     |1
//	 	  -- --
//	 	  3	  2
	/**
	 * Constructs an Edge object.
	 *
	 * @throws IllegalArgumentException if one of the tiles is numbered less
	 * than 0, or if one of the edges is out of the range
	 * ({@code edge < 0 || edge > 5})
	 */
	public Edge(int tileID, HabitatTile.Habitat habitatType) {
		if (tileID > HabitatTile.getTileCounter()) {
			throw new IllegalArgumentException("You are trying to make edges for a non-existent tile");
		}
	}
	
	public static List<Edge> makeEdges(int tileID, HabitatTile.Habitat habitat1, HabitatTile.Habitat habitat2) {
		List<Edge> edges = new ArrayList<>();
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
}
