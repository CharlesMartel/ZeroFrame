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

		ZeroFrame.ExtensionsManager.Extensions.loadModules(Config.extensionFolder);
		
		ZeroFrame.EventsManager.Startup.raiseExtensionsLoadedEvent();
		
		System.out.println("Modules Loaded");
		
		ZeroFrame.Networking.Server Server = new ZeroFrame.Networking.Server();
		Server.start();
		
		ZeroFrame.EventsManager.Startup.raiseApplicationReadyEvent();
		
		Application App = new Application();
		App.start();
	}
}

