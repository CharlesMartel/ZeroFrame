package ZeroFrame.EventsManager;

import ZeroFrame.Extensions.ClientAdapter;

/**
 * Static class for raising Speech events.
 * @author Hammer
 *
 */
public final class Speech {

	public static void raiseSpeechReceivedEvent(ZeroFrame.Networking.Client client){
		ClientAdapter cAdapter = new ClientAdapter(client);
	}
	
}
