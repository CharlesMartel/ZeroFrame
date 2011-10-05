/**
 * 
 */
package ZeroFrame.Networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import ZeroFrame.Constants.MessageCodes;

/**
 * @author Hammer
 *
 */
public class MessageClient extends Thread {
	
	private Client myClient;
	public Socket ClientSocket = null;
	public PrintWriter output = null;
	public BufferedReader input = null;

	public MessageClient(Socket clientSocket){
		ClientSocket = clientSocket;
	}
	
	public void run(){
		
		try {
			output = new PrintWriter(ClientSocket.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		output.println(prepareMessage(MessageCodes.WELCOME, "Welcome to ZeroFrame. Please Identify Yourself."));
		
		String inputString;
		String clientIdentity = "";
        Boolean disconnectClient = false;
        try {
			while ((inputString = input.readLine()) != null) {
				if(getMessageType(inputString).equals(MessageCodes.IDENTIFY_CLIENT)){
					clientIdentity = getPayload(inputString);
					output.println(prepareMessage(MessageCodes.ACCEPTING_REQUESTS, "You are authorized. You are now able to make requests."));	
					break;
				}else if (getMessageType(inputString).equals(MessageCodes.CLOSE_CONNECTION)){
					disconnectClient = true;
					break;
			    } else {
			    	output.println(prepareMessage(MessageCodes.IDENTITY_NOT_KNOWN, "A client must identify themself before beginning transactions."));
			    }
			}				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		myClient.setClientName(clientIdentity);
        
        if(!disconnectClient){
        	ZeroFrame.EventsManager.Messaging.raiseClientConnectedEvent();
        	runSocket();
        }else{
        	try {
				ClientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	
	private void runSocket(){
		
		try {
			output = new PrintWriter(ClientSocket.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String inputString;
        
        try {
			while ((inputString = input.readLine()) != null) {
				String code = getMessageType(inputString);
				String pay = getPayload(inputString);
				messageDispatch(code, pay);
			}
        } catch (SocketException e) {
				//TODO: Call a function to destroy the current client
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		try {
			ClientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private String getMessageType(String message){
		if(message.length() >= 4){
			return message.substring(0, 4);
		}else{
			return ZeroFrame.Constants.MessageCodes.BAD_MESSAGE;
		}
		
	}
	
	private String getPayload(String message){
		return message.substring(5);
	}
	
	private String prepareMessage(String messageCode, String payload){
		return messageCode + ":" + payload;
	}
	
	private void messageDispatch(String messageCode, String payload){
		if(messageCode.equals(ZeroFrame.Constants.MessageCodes.REQUEST_AUDIO_SOCKET)){
			myClient.initializeAudioStream();
		} else {
			ZeroFrame.EventsManager.Messaging.raiseMessageReceivedEvent(messageCode, payload);
			ZeroFrame.EventsManager.Messaging.raiseMessageReceivedParameterizedEvent(messageCode, payload);
			output.println(prepareMessage("9000", "Acknowledged."));
		}	
	}
	
	public void sendMessage(String messageCode, String payload){
		String message = prepareMessage(messageCode, payload);
		output.println(message);
	}
	
	public void setClient(Client client){
		this.myClient = client;
	}
}
