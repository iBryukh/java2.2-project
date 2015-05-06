package server;

import java.awt.BorderLayout;
import java.awt.EventQueue;
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
		super("Server(status: stopped)");
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
						messageFrame("Info", "Server runs on port: " + port);
						setTitle("Server(status: running)");
						run(port);
					} catch (Exception e) {
						messageFrame("Error", "Invalid port number");
						portF.setText("");
					}
				} else {
					messageFrame("Warning", "Server already runs");
				}
			}
		});
		add(runGame);
		JButton stopGame = new JButton("Stop");
		stopGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (gameRoom != null) {
					messageFrame("Info", "Server was stopped");
					setTitle("Server(status: stopped)");
				}
				stopServer();
			}
		});
		addWindowListener(new WindowListener() {

			@Override
			public void windowClosing(WindowEvent e) {
				stopServer();
			}

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
		add(stopGame);
		setSize(300, 100);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private void run(final int port) {
		runServer = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					serverSocket = new ServerSocket(port);
					gameRoom = new GameRoom();
					gameRoom.start();
					while (true) {
						if (!serverSocket.isClosed()) {
							Socket socket = serverSocket.accept();
							gameRoom.add(socket);
						}
					}
				} catch (IOException e) {
				}
			}
		});
		runServer.start();
	}

	private void messageFrame(final String title, final String message) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame(title);
					frame.setLayout(new BorderLayout());
					JLabel label = new JLabel(message, SwingConstants.CENTER);
					frame.add(label, BorderLayout.CENTER);
					frame.setSize(250, 100);
					frame.setVisible(true);
					frame.setResizable(false);
					Point mainFrameLocation = getLocation();
					mainFrameLocation.x += 30;
					mainFrameLocation.y += 30;
					frame.setLocation(mainFrameLocation);
				} catch (Exception e) {
				}
			}
		});
	}

	private void stopServer() {
		if (gameRoom != null && gameRoom.isAlive()) {
			gameRoom.destroyGame();
			if (runServer != null && runServer.isAlive()) {
				runServer.interrupt();
				runServer = null;
			}
			if (serverSocket != null && !serverSocket.isClosed()) {
				try {
					serverSocket.close();
					serverSocket = null;
				} catch (IOException e) {
					serverSocket = null;
				}
			}
			gameRoom = null;
		}
	}

	public static void main(String[] args) {
		new Server();
	}
}
