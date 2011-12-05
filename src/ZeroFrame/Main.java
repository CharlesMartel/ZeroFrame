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
		ZeroFrame.Data.DatabaseController.initialize();

		
		
		 
		
		ZeroFrame.ExtensionsManager.Extensions.loadModules(Config.extensionFolder);
		ZeroFrame.Analysis.SpeechConfiguration.Grammar.initializeGrammar();
		ZeroFrame.EventsManager.Startup.raiseExtensionsLoadedEvent();
		
		ZeroFrame.Networking.Server Server = new ZeroFrame.Networking.Server();
		Server.start();
		
		ZeroFrame.EventsManager.Startup.raiseApplicationReadyEvent();
		
		Application App = new Application();
		System.out.println("Ready.");
	}
}

