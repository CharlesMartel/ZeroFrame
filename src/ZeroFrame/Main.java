/**
 * 
 */
package ZeroFrame;

/**
 * @author Hammer
 *
 */
public class Main {

	/**
	 * ZeroFrame Startup
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {		
		Config.loadConfiguration();
		Config.initializeSystemObjects();

		ZeroFrame.ExtensionsManager.Extensions.loadModules(Config.extensionFolder);
		
		ZeroFrame.EventsManager.Startup.raiseExtensionsLoadedEvent();
		
		ZeroFrame.Networking.Server Server = new ZeroFrame.Networking.Server();
		Server.start();
		
		ZeroFrame.EventsManager.Startup.raiseApplicationReadyEvent();
		
		Application App = new Application();
		App.start();
		System.out.println("Ready.");
	}
}

