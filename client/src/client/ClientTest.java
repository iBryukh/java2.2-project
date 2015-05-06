package client;

import java.net.UnknownHostException;
import java.util.HashMap;

import com.mygdx.game.transfer.CellData;
import com.mygdx.game.transfer.Data;
import com.mygdx.game.transfer.PlayerData;

public class ClientTest implements Runnable{

	private Client client;
	private Data d;
	
	public ClientTest(Data d) throws UnknownHostException{
		client = new Client();
		this.d = d;
		new Thread(this).start();
	}
	
	public Data get(){
		return client.get();
	}
	
	public void send(Data d){
		client.send(d);
	}
	
	@Override
	public void run(){
		while(client.isConnected()){
			client.send(this.d);
			//Data 
			d = client.get();
			System.out.println(d);
			System.out.println(d.getCells());
			System.out.println(d.getPlayers());
			System.out.println(d.isNewGame());
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws UnknownHostException {
		PlayerData pd = new PlayerData(1, 1, 1, true, null);
		HashMap<Integer, CellData> cells = new HashMap<>();
		cells.put(1, new CellData(1, 2, 3));
		cells.put(2, new CellData(2, 2, 3));
		cells.put(3, new CellData(3, 2, 3));
		new ClientTest(new Data(pd, cells));
		cells.put(2, new CellData(2, 3, 3));
		cells.put(3, new CellData(3, 2, 3));
		new ClientTest(new Data(pd, cells));
		cells.put(2, new CellData(2, 3, 3));
		cells.put(4, new CellData(4, 2, 3));
		//new ClientTest(new Data(pd, cells));
	}
	
}