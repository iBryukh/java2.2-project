package multiplayer.transfer;


public class Coordinates implements TransferData{
	
	private static int freeID = 0;
	
	public int x;
	public int y;
	public final int ID; 
	
	public Coordinates(int x, int y){
		this.x = x;
		this.y = y;
		this.ID = ++freeID;
	}

	@Override
	public void update() {
		++x;
		++y;
	}

	@Override
	public int getID() {
		return this.ID;
	}
	
	private static final long serialVersionUID = 280339320795925936L;
}
