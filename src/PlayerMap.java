/*
 * TODO: make a method that checks if a position in the map is full or empty
 */
public class PlayerMap {
//	private ArrayList<HabitatTile> playerTiles = new ArrayList<>(); //stores player's placed tiles
	private final HabitatTile[][] tileBoardPosition = new HabitatTile[20][20]; //position of tiles on map
	
	public PlayerMap() { //constructor
		makeStarterTiles();
	}
	
//	public ArrayList<HabitatTile> getPlayerTiles() {
//		return playerTiles;
//	}
	
	//not sure if this method needs to exist, just currently used in game class 
//	public void setPlayerTiles(ArrayList<HabitatTile> generatedTilesList) {
//		this.playerTiles = generatedTilesList;
//
//	}

	public void makeStarterTiles() {
		HabitatTile[] starter = Generation.generateStarterHabitat();
		addTileToMap(starter[0], 8, 10); //places tiles in the middle of the map
		addTileToMap(starter[1], 9, 9);
		addTileToMap(starter[2], 9, 10);
	}

	/**
	 * Returns the 2d array of habitat tiles, which is used for printing the
	 * map of tiles.
	 *
	 * @return a 2d array of habitat tiles (if a tile has not been placed the
	 * el(String)(String)ement will be null)
	 */
	public HabitatTile[][] getTileBoardPosition() {
		return tileBoardPosition;
	}

	/**
	 * Adds a tile to the board array at position {@code board[x][y]}.
	 *
	 * @param tile the tile to be added to the board position array
	 * @param row the row of the board position array the tile will be added to
	 * @param col the column of the board position array the tile will be added
	 *          to
	 * @throws IllegalArgumentException if there is already a tile at that
	 * position
	 */
	public void addTileToMap(HabitatTile tile, int row, int col) throws IllegalArgumentException {
//		playerTiles.add(tile);
		if (tileBoardPosition[row][col] != null) {
			throw new IllegalArgumentException("There is already a tile at that position!");
		} //TODO: handle this in the method itself by asking them to place again somewhere else
		tileBoardPosition[row][col] = tile;
	}
	

}
