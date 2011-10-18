/**
 * 
 */
package ZeroFrame.Networking;

import java.net.InetAddress;

/**
 * @author Hammer
 *
 */
public class Client {
	
	private String clientName = "";
	private MessageClient messageClient;
	private AudioClient audioClient;
	private InetAddress clientHost = null;
	
	public void setClientName(String name){
		clientName = name;
	}
	
	public String getClientName(){
		return clientName;
	}
	
	public Client(){
		
	}
	
	public void initializeAudioStream(String clientPortNumber){		
		audioClient = new AudioClient();
		audioClient.run();
		int portNum = audioClient.getServerPort();
		sendMessage(ZeroFrame.Constants.MessageCodes.AUDIO_SOCKET_PARAM, Integer.toString(portNum));
	}
	
	public void sendMessage(String messageCode, String payload){
		messageClient.sendMessage(messageCode, payload);
	}
	
	public void setMessageClient(MessageClient client){
		messageClient = client;
	}
	
	public void setClientHost(InetAddress clientHost){
		this.clientHost = clientHost;
	}
	
	public InetAddress getClientHost(){
		return this.clientHost;
	}

}
