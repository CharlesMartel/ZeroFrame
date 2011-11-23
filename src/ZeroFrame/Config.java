package ZeroFrame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * The configuration options for ZeroFrame.
 * 
 * @author Hammer
 *
 */
public class Config {
	
	//The properties file object
	private static Properties config = new Properties();
	
	//Current working directory
	private static String workingDirectory;
	
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
		
		/*FileInputStream inputStream;
		try {
			inputStream = new FileInputStream("myPropertiesFile.properties");
			config.load(inputStream);
			//String myPropValue = myProps.getProperty("propKey");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		        
		//get the current running directory
		workingDirectory = System.getProperty("user.dir");		
		extensionFolder = "Extensions";
		languageModelFolder = "LanguageModels";
		headlessMode = true;
		serverPort = 8500;
		//The grammar file gets created in the current working directory
		grammarFolder = workingDirectory;
		//grammarFolder = "grammar";
		return true;
	}
	
	public static void setSystemVariables(){
		System.setProperty("java.awt.headless", headlessMode.toString());
	}
	
	public static void initializeSystemObjects(){
		ZeroFrame.Analysis.TextAnalyzer.initialize();
	}
	
}
