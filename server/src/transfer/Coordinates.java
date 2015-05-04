package transfer;


public class Coordinates implements TransferData{
	
	public int x;
	public int y;
	
	public Coordinates(int x, int y){
		this.x = x;
		this.y = y;
	}

	@Override
	public void update() {
		++x;
		++y;
	}
	
	private static final long serialVersionUID = 280339320795925936L;
}
