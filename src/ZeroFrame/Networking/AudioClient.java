package ZeroFrame.Networking;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class AudioClient extends Thread {

	private ArrayList<ByteArrayOutputStream> StreamQueue = new ArrayList<ByteArrayOutputStream>(0);
	private Socket ClientSocket = null;
	private ServerSocket ServerSocket = null;
	private InputStream InputStream = null;
	private OutputStream OutputStream = null;
	private DataInputStream Input = null;
	private DataOutputStream Output = null;
	private ReceiveData receiveData = null;
	private SendData sendData = null;

	public AudioClient() {
		initializeServerSocket();
	}

	public void run() {
		receiveData = new ReceiveData();
		sendData = new SendData();
		
		receiveData.start();
		sendData.start();
	}

	private void initializeServerSocket() {
		try {
			//Create the server socket
			ServerSocket = new ServerSocket(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public void streamBytes(byte[] streamArray) {
		ByteArrayOutputStream tempStream = new ByteArrayOutputStream();
		
		try {
			tempStream.write(streamArray);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StreamQueue.add(tempStream);
	}
	
	public int getServerPort(){
		return ServerSocket.getLocalPort();
	}

	
	//PrivateThreads
	private class SendData extends Thread{
		public void run(){
			while(ClientSocket == null){
				//loop until the client connects
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("Client Connected!");
			try {
				OutputStream = ClientSocket.getOutputStream();
				Output = new DataOutputStream(OutputStream);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			while(true){
				try {
					sleep(500);
					if(StreamQueue.size() > 0){
						ByteArrayOutputStream streamHolder = StreamQueue.get(0);
						for(byte currentByte : streamHolder.toByteArray()){
							Output.writeByte((int)currentByte);
						}
						StreamQueue.remove(0);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
	}
	
	private class ReceiveData extends Thread{
		public void run(){
			try {
				ClientSocket = ServerSocket.accept();
				InputStream = ClientSocket.getInputStream();
				Input = new DataInputStream(InputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			byte inputByte;
			
			//while((inputByte = Input.readByte()) != null){
				
			//}
			
		}
	}
}