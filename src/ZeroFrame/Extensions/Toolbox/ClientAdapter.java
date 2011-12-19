package ZeroFrame.Extensions.Toolbox;

/**
 * Adapter to interact with clients.
 * 
 * @author Hammer
 * 
 */
public class ClientAdapter {

	private ZeroFrame.Networking.Client myClient = null;

	public ClientAdapter(ZeroFrame.Networking.Client client) {
		myClient = client;
	}

	// Property methods

	public String getClientName() {
		return myClient.getClientName();
	}

	// Action Methods

	public void speak(String toSpeak) {
		myClient.speak(toSpeak);
	}

	public void sendMessage(String message) {
		myClient.sendMessage(message);
	}

	public void sendPluginNotification(String pluginId, String payload) {
		myClient.sendMessage(ZeroFrame.Constants.MessageCodes.NOTIFY_PLUGIN, pluginId + "::" + payload);
	}

}
