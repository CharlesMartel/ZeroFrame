package ZeroFrame.Networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

	//The server socket
	private ServerSocket serverSocket;
	//The client socket
	private Socket clientSocket;
	
	InetAddress ServerIP = null;
	
	public void run(){
		serverSocket = null;
		clientSocket = null;
		
		while(true){
	        
			try {
	            serverSocket = new ServerSocket(ZeroFrame.Config.serverPort);
	        } catch (IOException e) {
	        	
	        }
	        
	        try {
	            clientSocket = serverSocket.accept();
	        } catch (IOException e) {

	        }
	        
	        Client newClient = new Client();
	        newClient.messageClient = new MessageClient(clientSocket);
	        newClient.messageClient.start();
	        ClientManager.registerClient(newClient);	        
		}
		
	}
}
