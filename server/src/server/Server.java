package server;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import server.room.GameRoom;

public class Server extends JFrame {

	private static final long serialVersionUID = 9167297228950068041L;
	private ServerSocket serverSocket;
	private Thread runServer;
	private GameRoom gameRoom;

	public Server() {
		setLayout(new GridLayout(3, 1));
		final JTextField portF = new JTextField();
		add(portF);
		JButton runGame = new JButton("Run");
		runGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (gameRoom == null) {
					try {
						int port = Integer.valueOf(portF.getText());
						run(port);
					} catch (Exception e) {
						JFrame error = new JFrame("Error");
						error.setLayout(new BorderLayout());
						JLabel label = new JLabel(
								"invalid port number",
								SwingConstants.CENTER);
						error.add(label, BorderLayout.CENTER);
						error.setSize(150, 100);
						error.setVisible(true);
						error.setResizable(false);
						Point mainFrameLocation = getLocation();
						mainFrameLocation.x += 30;
						mainFrameLocation.y += 30;
						error.setLocation(mainFrameLocation);
						portF.setText("");
					}
				} else {
					JFrame alreadyRun = new JFrame("Error");
					alreadyRun.setLayout(new BorderLayout());
					JLabel label = new JLabel(
							"server already runs",
							SwingConstants.CENTER);
					alreadyRun.add(label, BorderLayout.CENTER);
					alreadyRun.setSize(150, 100);
					alreadyRun.setVisible(true);
					alreadyRun.setResizable(false);
					Point mainFrameLocation = getLocation();
					mainFrameLocation.x += 30;
					mainFrameLocation.y += 30;
					alreadyRun.setLocation(mainFrameLocation);
				}
			}
		});
		add(runGame);
		JButton stopGame = new JButton("Stop");
		stopGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (gameRoom != null && gameRoom.isAlive()) {
					gameRoom.destroyGame();
					if (runServer != null && runServer.isAlive())
						runServer.interrupt();
					if (serverSocket != null && !serverSocket.isClosed())
						try {
							serverSocket.close();
						} catch (IOException e) {
						}
					gameRoom = null;
					runServer = null;
					serverSocket = null;
				}
			}
		});
		addWindowListener(new WindowListener() {

			@Override
			public void windowClosing(WindowEvent e) {
				if (runServer != null && runServer.isAlive())
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
		add(stopGame);
		setSize(300, 100);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public void run(final int port) {
		runServer = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println("runnint server on port: " + port);
					serverSocket = new ServerSocket(port);
					gameRoom = new GameRoom();
					gameRoom.start();
					while (true) {
						if (!serverSocket.isClosed()) {
							Socket socket = serverSocket.accept();
							gameRoom.add(socket);
						}
					}
				} catch (IOException e) { }
			}
		});
		runServer.start();
	}

	public static void main(String[] args) {
		new Server();
	}
}
