package ZeroFrame.Analysis;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

/**
 * @author Hammer
 *
 */
public class TextAnalyzer {
	
	private static FileInputStream tokenizerModelFile = null;	
	private static TokenizerModel tokenizerModel = null;
	private static Tokenizer tokenizer = null;
	
	public static void initialize(){
		
		try {
			tokenizerModelFile = new FileInputStream(ZeroFrame.Config.languageModelFolder + "/en-token.bin");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			tokenizerModel = new TokenizerModel(tokenizerModelFile);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (tokenizerModelFile != null) {
		    try {
		      tokenizerModelFile.close();
		    }
		    catch (IOException e) {
		    	
		    }
		}
		
		tokenizer = new TokenizerME(tokenizerModel);		
	}
	
	public static String[] tokenizeString(String toBeTokenized){
		return tokenizer.tokenize(toBeTokenized);
	}
	
	public static Boolean parameterMatchCheck(String[] arrayToCheckBy, String phraseToCheckIn){
		
		String[] arrayToCheckIn = tokenizeString(phraseToCheckIn);
		
		int countOfWords = arrayToCheckBy.length;
		int counter = 0;
		
		//convert everything in the array to lowercase
		for(int x=0; x < arrayToCheckIn.length; x++){
			arrayToCheckIn[x] = arrayToCheckIn[x].toLowerCase();
		}
		
		for(String test : arrayToCheckBy){
			if(Arrays.asList(arrayToCheckIn).contains(test.toLowerCase())){
				counter +=1;
			}
		}
		
		if(countOfWords == counter){
			return true;
		}else{
			return false;
		}
	}
	
}
