/**
 * 
 */
package ZeroFrame.Networking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
		audioClient = new AudioClient(this);
		audioClient.run();
		int portNum = audioClient.getServerPort();
		sendMessage(ZeroFrame.Constants.MessageCodes.AUDIO_SOCKET_PARAM, Integer.toString(portNum));
	}
	
	public void sendMessage(String messageCode, String payload){
		messageClient.sendMessage(messageCode, payload);
	}
	
	public void sendMessage(String payload){
		messageClient.sendMessage(ZeroFrame.Constants.MessageCodes.GENERIC_MESSAGE, payload);
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
	
	public void playAudioFile(String audioFileLocation){
		File audioFile = new File(audioFileLocation);
		try {
			InputStream audioFileInputStream = new FileInputStream(audioFile);
			// Get the size of the file
		    long length = audioFile.length();

		    // You cannot create an array using a long type.
		    // It needs to be an int type.
		    // Before converting to an int type, check
		    // to ensure that file is not larger than Integer.MAX_VALUE.
		    if (length > Integer.MAX_VALUE) {
		    	System.out.println("File too large!");
		    	return;
		    }

		    // Create the byte array to hold the data
		    byte[] bytes = new byte[(int)length];

		    // Read in the bytes
		    int offset = 0;
		    int numRead = 0;
		    while (offset < bytes.length
		           && (numRead=audioFileInputStream.read(bytes, offset, bytes.length-offset)) >= 0) {
		        offset += numRead;
		    }

		    // Ensure all the bytes have been read in
		    if (offset < bytes.length) {
		    	System.out.println("Couldn't read file.");
		        return;
		    }
		    // Close the input stream and return bytes
		    audioFileInputStream.close();
		    audioClient.streamBytes(bytes);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void speak(String toSpeak){
		sendMessage(ZeroFrame.Constants.MessageCodes.SPEAK_PHRASE, toSpeak);
	}

}
