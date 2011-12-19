package ZeroFrame.Networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

	// The server socket
	private ServerSocket serverSocket;
	// The client socket
	private Socket clientSocket;

	InetAddress ServerIP = null;

	@Override
	public void run() {
		serverSocket = null;
		clientSocket = null;

		while (true) {

			try {
				serverSocket = new ServerSocket(ZeroFrame.Config.serverPort);
			} catch (IOException e) {

			}

			try {
				clientSocket = serverSocket.accept();
			} catch (IOException e) {

			}

			Client newClient = new Client();
			newClient.setClientHost(clientSocket.getInetAddress());
			MessageClient tempclient = new MessageClient(clientSocket);
			tempclient.setClient(newClient);
			tempclient.start();
			newClient.setMessageClient(tempclient);
			ClientManager.registerClient(newClient);

		}

	}
}
