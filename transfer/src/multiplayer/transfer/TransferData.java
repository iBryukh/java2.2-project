package multiplayer.transfer;

import java.io.Serializable;

public interface TransferData extends Serializable{
	public void update();
	public int getID();
}