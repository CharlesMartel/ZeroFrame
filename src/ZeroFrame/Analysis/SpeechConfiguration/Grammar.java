/**
 * 
 */
package ZeroFrame.Analysis.SpeechConfiguration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.mozilla.javascript.tools.shell.Environment;

import ZeroFrame.Extensions.GrammarMatrix;
import ZeroFrame.Extensions.GrammarMatrix.PhraseArray;

/**
 * @author Hammer
 *
 */
public final class Grammar {
	
	private static ArrayList<ZeroFrame.Extensions.GrammarMatrix> matrices = new ArrayList<ZeroFrame.Extensions.GrammarMatrix>(0);
	
	private static int ruleCount = 0;
	
	public static void addGrammarMatrix(ZeroFrame.Extensions.GrammarMatrix grammarMatrix){
		matrices.add(grammarMatrix);
	}
	
	public static void initializeGrammar(){
		
		if(matrices.size() < 1){
			return;
		}
		GrammarWriter.initialize();
		for(int index = 0; index < matrices.size(); index++){
			GrammarWriter.writeGrammarMatrix(matrices.get(index));
		}
		GrammarWriter.commit();
		
	}
	
	private static class GrammarWriter{
		
		private static String newLine = System.getProperty("line.separator");
		private static String fileHeader = "#JSGF V1.0;" + newLine + "grammar zfgrammar;" + newLine;
		
		private static String fileString = "";
		
		public static void initialize(){
			fileString += fileHeader;
		}
		
		public static int writeGrammarMatrix(ZeroFrame.Extensions.GrammarMatrix grammarMatrix){
			ArrayList matrixSequence = grammarMatrix.getPhraseSequence();
			String topDef = "public <rule" + Integer.toString(ruleCount) + "> = ";
			int topDefRuleID = ruleCount;
			ruleCount++;
			for(int i = 0; i < matrixSequence.size(); i++){
				if(matrixSequence.get(i) instanceof ZeroFrame.Extensions.GrammarMatrix.PhraseArray){
					ZeroFrame.Extensions.GrammarMatrix.PhraseArray phraseArray = (PhraseArray) matrixSequence.get(i);
					String ruledef = "public <rule" + Integer.toString(ruleCount) + "> = ";
					topDef += " <rule" + Integer.toString(ruleCount) + ">";
					ruleCount++;					
					String[] rules = phraseArray.getPhraseArray();
					for(int j = 0; j < rules.length; j++){
						if((j+1) < rules.length){
							ruledef += rules[j].toLowerCase() + " | ";
						}else{
							ruledef += rules[j].toLowerCase();
						}
					}
					ruledef += ";";
					fileString += ruledef + newLine;
				}else{
					//its a grammar matrix, loop unto thyself... recursion 
					int matrixRuleID = writeGrammarMatrix((GrammarMatrix) matrixSequence.get(i));
					topDef += " <rule" + Integer.toString(matrixRuleID) + ">";
				}
			}
			
			topDef += ";";
			fileString += topDef + newLine;	
			return topDefRuleID;
		}
		
		public static void commit(){
			File gramFile = new File(ZeroFrame.Config.grammarFolder + "\\zfgrammar.gram");
			if(gramFile.exists()){
				gramFile.delete();
			}
			try {
				FileWriter fstream = new FileWriter("zfgrammar.gram");
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(fileString);
				//Close the output stream
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			System.out.println(fileString);
		}
	}
	
}
