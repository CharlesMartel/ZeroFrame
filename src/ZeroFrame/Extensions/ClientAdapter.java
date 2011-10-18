package ZeroFrame.Extensions;

/**
 * @author Hammer
 *
 */
public class ClientAdapter {
	
	public static ClientInstance getClientByName(String clientName){
		return null;
	}
	
	
	
	
	public class ClientInstance{
		
		private ZeroFrame.Networking.Client myClient;
		
		
		//Constructors
		public ClientInstance(ZeroFrame.Networking.Client client){
			myClient = client;
		}
		
		
		
		//Access methods
		
		
		//Action Methods
		
		public void speak(){
			
		}
	}
	
}
