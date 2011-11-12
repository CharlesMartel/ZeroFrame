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
	
	//The folder where the text language models are held
	public static String languageModelFolder;
	
	//The port to bind to
	public static int serverPort;
	
	//whether or not the server will run in headless mode
	public static Boolean headlessMode;
	
	//grammar file folder
	public static String grammarFolder;
	
	public static Boolean loadConfiguration(){		
		extensionFolder = "Extensions";
		languageModelFolder = "LanguageModels";
		headlessMode = true;
		serverPort = 8500;
		grammarFolder = "\\Documents and Settings\\hitsynth\\git\\ZeroFrame";
		return true;
	}
	
	public static void setSystemVariables(){
		System.setProperty("java.awt.headless", headlessMode.toString());
	}
	
	public static void initializeSystemObjects(){
		ZeroFrame.Analysis.TextAnalyzer.initialize();
	}
}
