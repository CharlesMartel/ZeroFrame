package ZeroFrame.Analysis;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
	
	public static boolean parameterMatchCheck(String[] arrayToCheckBy, String phraseToCheckIn){
		
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
	
	public static boolean matrixMatchCheck(ZeroFrame.Extensions.GrammarMatrix grammarMatrix, String phrase){
		
		//Declare a new array list to hold the array sequence
		//then pick apart the grammar matrix and place the arrays into the sequence
		ArrayList<String[]> sequence = deconstructGrammarMatrix(grammarMatrix);

		//we need an int to hold our position in the string
		int position = 0;
		
		//now we loop through the sequence and see if the string fulfills the sequence requirements
		for(int index = 0; index < sequence.size(); index++){
			boolean satisfied = false; 
			for(int i = 0; i < sequence.get(index).length; i++){
				String test = sequence.get(index)[i];
				int testLength = test.length();				
				try{
				String phraseSlice = phrase.substring(position, position + testLength);
				if(phraseSlice.toLowerCase().contains(test.toLowerCase())){
					position += testLength + 1;
					satisfied = true;
					break;
				}
				}catch(Exception e){}				
			}
			if(satisfied){
				continue;
			}
			return false;
		}		
		return true;
	}
	
	private static ArrayList<String[]> deconstructGrammarMatrix(ZeroFrame.Extensions.GrammarMatrix grammarMatrix){
		ArrayList<String[]> arraySequence = new ArrayList<String[]>();
		ArrayList sequence = grammarMatrix.getPhraseSequence();
		for(int index = 0; index < sequence.size(); index++){
			if(sequence.get(index) instanceof ZeroFrame.Extensions.GrammarMatrix.PhraseArray){
				String[] stringArray = ((ZeroFrame.Extensions.GrammarMatrix.PhraseArray) sequence.get(index)).getPhraseArray();
				arraySequence.add(stringArray);
			} else {
				ZeroFrame.Extensions.GrammarMatrix interiorMatrix = (ZeroFrame.Extensions.GrammarMatrix)sequence.get(index);
				ArrayList<String[]> recursionResult = deconstructGrammarMatrix(interiorMatrix);
				for(int index2 = 0; index2 < recursionResult.size(); index2++){
					arraySequence.add(recursionResult.get(index2));
				}
			}
		}
		return arraySequence;
	}
}
