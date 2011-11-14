package ZeroFrame;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

/**
 * The configuration options for ZeroFrame.
 * 
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
		
		/*
		try {
			File fXmlFile = new File("zfconfig.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			System.out.println(doc.getTextContent());
			System.exit(0);
			extensionFolder = doc.getElementById("extension-location").getNodeValue();
			System.out.println(extensionFolder);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		extensionFolder = "Extensions";
		languageModelFolder = "LanguageModels";
		headlessMode = true;
		serverPort = 8500;
		grammarFolder = "\\Documents and Settings\\hitsynth\\git\\ZeroFrame";
		//grammarFolder = "grammar";
		return true;
	}
	
	public static void setSystemVariables(){
		System.setProperty("java.awt.headless", headlessMode.toString());
	}
	
	public static void initializeSystemObjects(){
		ZeroFrame.Analysis.TextAnalyzer.initialize();
	}
	
	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
	 
	        Node nValue = (Node) nlList.item(0);
	 
		return nValue.getNodeValue();
	  }
}
