/**
 * 
 */
package ZeroFrame;

import java.sql.ResultSet;

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
		
		
		
		
		//ZeroFrame.Data.DatabaseController.executeGeneric("CREATE TABLE test (id int, name varchar(255))");
		//ZeroFrame.Data.DatabaseController.executeGeneric("INSERT INTO test VALUES (1, 'Darrell')");
		ResultSet result = ZeroFrame.Data.DatabaseController.executeQuery("SELECT * FROM test WHERE id = 1");
		
		while (result.next()) {
			String user = result.getString("name");
			String number = result.getString("id");
			System.out.println("User: " + user);
			System.out.println("ID: " + number);
		}
		
		 
		
		ZeroFrame.ExtensionsManager.Extensions.loadModules(Config.extensionFolder);
		ZeroFrame.Analysis.SpeechConfiguration.Grammar.initializeGrammar();
		ZeroFrame.EventsManager.Startup.raiseExtensionsLoadedEvent();
		
		ZeroFrame.Networking.Server Server = new ZeroFrame.Networking.Server();
		Server.start();
		
		ZeroFrame.EventsManager.Startup.raiseApplicationReadyEvent();
		
		Application App = new Application();
		App.start();
		System.out.println("Ready.");
	}
}

