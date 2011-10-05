/**
 * 
 */
package ZeroFrame;

/**
 * @author Hammer
 *
 */
public class Config {
	
	//The folder where all extensions are to be held
	public static String extensionFolder;
	
	//The port to bind to
	public static int serverPort;
	
	//whether or not the server will run in headless mode
	public static Boolean headlessMode;
	
	public static Boolean loadConfiguration(){		
		extensionFolder = "Extensions";
		headlessMode = true;
		serverPort = 8500;
		return true;
	}
	
	public static void setSystemVariables(){
		System.setProperty("java.awt.headless", headlessMode.toString());
	}
}
