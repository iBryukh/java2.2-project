package server;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import server.room.GameRoom;

public class Server extends JFrame{

	private ServerSocket serverSocket;
	private Thread runServer;

	public Server() {
		setLayout(new GridLayout(2, 1));
		final JTextField portF = new JTextField();
		add(portF);
		JButton run = new JButton("Run");
		run.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int port = Integer.valueOf(portF.getText());
				run(port);
			}
		});
		add(run);
		setSize(200, 100);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				if(runServer != null && runServer.isAlive())
					runServer.interrupt();
			}
			
			@Override
			public void windowOpened(WindowEvent e) { }
			@Override
			public void windowIconified(WindowEvent e) { }
			@Override
			public void windowDeiconified(WindowEvent e) { }
			@Override
			public void windowDeactivated(WindowEvent e) { }
			@Override
			public void windowClosed(WindowEvent e) { }
			@Override
			public void windowActivated(WindowEvent e) { }
		});
	}

	public void run(final int port) {
		runServer = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					System.out.println("runnint server on port: " + port);
					serverSocket = new ServerSocket(port);
					GameRoom gameRoom = new GameRoom();
					gameRoom.start();
					while (true) {
						Socket socket = serverSocket.accept();
						gameRoom.add(socket);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		runServer.start();
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try {
	                new Server();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}
}
