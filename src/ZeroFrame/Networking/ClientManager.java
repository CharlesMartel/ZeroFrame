package ZeroFrame.Networking;

import java.util.ArrayList;
import java.util.List;

public class ClientManager {

	public static List<Client> Clients = new ArrayList<Client> (0);
	
	public static void registerClient(Client client){
		Clients.add(client);
	}
	
}
