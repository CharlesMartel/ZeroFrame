package ZeroFrame.Networking;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class AudioClient extends Thread {

	private Client myClient = null;
	private ArrayList<ByteArrayOutputStream> StreamQueue = new ArrayList<ByteArrayOutputStream>(0);
	private Socket ClientSocket = null;
	private ServerSocket ServerSocket = null;
	private InputStream InputStream = null;
	private OutputStream OutputStream = null;
	private DataInputStream Input = null;
	private DataOutputStream Output = null;
	private ReceiveData receiveData = null;
	private SendData sendData = null;
	private ZeroFrame.Analysis.SpeechAnalyzer voiceAnalyzer = new ZeroFrame.Analysis.SpeechAnalyzer();

	public AudioClient(Client parentClient) {
		myClient = parentClient;
		initializeServerSocket();
	}

	@Override
	public void run() {
		receiveData = new ReceiveData();
		sendData = new SendData();

		receiveData.start();
		sendData.start();
	}

	private void initializeServerSocket() {
		try {
			// Create the server socket
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

	public int getServerPort() {
		return ServerSocket.getLocalPort();
	}

	public void receiveAudio(int byteLength) {
		receiveData.receive(byteLength);
	}

	// PrivateThreads
	private class SendData extends Thread {
		@Override
		public void run() {
			while (ClientSocket == null) {
				// loop until the client connects
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

			while (true) {
				try {
					sleep(500);
					if (StreamQueue.size() > 0) {
						ByteArrayOutputStream streamHolder = StreamQueue.get(0);
						myClient.sendMessage(ZeroFrame.Constants.MessageCodes.AUDIO_TRANSFER_NOTIFICATION, Integer.toString(streamHolder.toByteArray().length));
						Output.write(streamHolder.toByteArray());
						Output.flush();
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

	private class ReceiveData extends Thread {
		@Override
		public void run() {
			try {
				ClientSocket = ServerSocket.accept();
				InputStream = ClientSocket.getInputStream();
				Input = new DataInputStream(InputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void receive(int byteLength) {
			int lengthOfByteArray = byteLength;
			byte[] inputBytes = new byte[lengthOfByteArray];
			try {
				Input.readFully(inputBytes, 0, lengthOfByteArray);
				voiceAnalyzer.analyzeUtterance(inputBytes, myClient);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}