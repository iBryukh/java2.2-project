package client;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Client {
	public static void main(String[] args) throws UnknownHostException {
		new ClientThread(InetAddress.getByName(null));
		new ClientThread(InetAddress.getByName(null));
		new ClientThread(InetAddress.getByName(null));
	}
}
