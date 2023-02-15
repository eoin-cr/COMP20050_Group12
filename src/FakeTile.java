//
//public class FakeTile extends HabitatTile{
//	private int fakeTileCounter = 0;
//	
//	public FakeTile() {
//		super(null, null, 0);
//		this.setTokenOptions(0);
//	}
//	
//	@Override
//	public void setTileID() {
//		this.tileID = fakeTileCounter;
//		fakeTileCounter++;
//		tileCounter--;
//	}
//	@Override
//	public void setKeystoneType() {
//		this.keystoneType = TileType.FAKE;
//	}
//	@Override
//	public void setTokenOptions(int numTokens) {
//		this.tokenOptions = null;
//	}
//	@Override
//	public String toFormattedString() {
//			final String GREY = "\033[37m";
//			return GREY + "|||| |||| |||| ||||" + ANSI_RESET + "\n"
//					+ GREY + "||||  " + String.format("%-3s", this.getTileID()) + "      ||||" + ANSI_RESET + "\n"
//					+ GREY + "||||           ||||" + ANSI_RESET + "\n"
//					+ GREY + "|||| |||| |||| ||||" + ANSI_RESET + "\n";
//		}
//	
//	
//}
