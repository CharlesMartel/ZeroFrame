/**
 * 
 */
package ZeroFrame;

import java.sql.ResultSet;

import ZeroFrame.Extensions.Toolbox.DataFactory;
import ZeroFrame.Extensions.Toolbox.DataFactory.BooleanDataType;
import ZeroFrame.Extensions.Toolbox.DataFactory.DataRecord;
import ZeroFrame.Extensions.Toolbox.DataFactory.DataSet;
import ZeroFrame.Extensions.Toolbox.DataFactory.DataSetLockedException;
import ZeroFrame.Extensions.Toolbox.DataFactory.DataSetNotInitializedException;
import ZeroFrame.Extensions.Toolbox.DataFactory.DataTypes;
import ZeroFrame.Extensions.Toolbox.DataFactory.FieldNotFoundException;
import ZeroFrame.Extensions.Toolbox.DataFactory.IntegerDataType;
import ZeroFrame.Extensions.Toolbox.DataFactory.StringDataType;

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

