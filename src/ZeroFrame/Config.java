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

	
	
	public static Boolean loadConfiguration(){
		//extensionFolder = "/home/hitsynth/workspace/ZeroFrame/bin/ZeroFrame/Extensions";
		extensionFolder = "Extensions";
		serverPort = 8500;
		return true;
	}
}
