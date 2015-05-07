package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import view.ErrorScreen;

import com.mygdx.game.transfer.CellData;
import com.mygdx.game.transfer.Data;
import com.mygdx.game.transfer.PlayerData;

public class Client {
	public static final int DEFAULT_PORT = 7000;

	private Socket socket;
	private ObjectInputStream objectIS;
	private ObjectOutputStream objectOS;
	private boolean connected;

	public Client(InetAddress addr, int port) {
		connect(addr, port);
	}

	public Client() throws UnknownHostException {
		this(InetAddress.getByName(null), DEFAULT_PORT);
	}

	public void connect(InetAddress addr, int port) {
		if (!isConnected()) {
			try {
				socket = new Socket(addr, port);
				objectOS = new ObjectOutputStream(socket.getOutputStream());
				objectOS.flush();
				objectIS = new ObjectInputStream(socket.getInputStream());
				connected = true;
			} catch (IOException e) {
				connected = false;
			}
		}
	}

	public boolean isConnected() {
		return connected;
	}

	public void disconnect() {
		try {
			if (socket != null)
				socket.close();
			if (objectOS != null)
				objectOS.close();
			if (objectIS != null)
				objectIS.close();
		} catch (IOException e) {
		}
		connected = false;
	}

	public void send(final Data d) {
		try {
			objectOS.writeObject(d);
		} catch (Exception e) {
			disconnect();
			ErrorScreen.launch("");;
		}
	}

	public Data get() {
		try {
			return (Data) (objectIS.readObject());
		} catch (ClassNotFoundException | IOException e) {
			disconnect();
		}
		return null;
	}
}
