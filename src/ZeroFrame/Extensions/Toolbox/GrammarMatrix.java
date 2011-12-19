package ZeroFrame.Extensions.Toolbox;

import java.util.ArrayList;

/**
 * Allows for creation of more advanced phrasing for audio commands.
 * 
 * A grammar matrix can combine text and other grammar matrices, this allows for
 * a tree structure of grammars and can be very powerful in creating dynamic and
 * flexible phrase constructs.
 * 
 * These matrices are directly related to the JSGF spec. They are essentially
 * wrappers to JSGF Rules.
 * 
 * @author Hammer
 * 
 */
public class GrammarMatrix {

	ArrayList sequence = new ArrayList();

	/**
	 * Add a phrase array obect to the phrase sequence
	 * 
	 * @param phraseArray
	 */
	public void addToSequence(String[] phraseArray) {
		sequence.add(new PhraseArray(phraseArray));
	}

	/**
	 * Add a phrase array obect to the phrase sequence
	 * 
	 * @param phraseArray
	 */
	public void addToSequence(PhraseArray phraseArray) {
		sequence.add(phraseArray);
	}

	/**
	 * Add an entire grammar matrix to the phrase sequence
	 * 
	 * @param grammarMatrix
	 */
	public void addToSequence(GrammarMatrix grammarMatrix) {
		sequence.add(grammarMatrix);
	}

	/**
	 * Get the entire phrase sequence as an ArrayList of String Array and
	 * Grammar Matrix objects
	 * 
	 * @return Phrase Sequence
	 */
	public ArrayList getPhraseSequence() {
		return sequence;
	}

	/**
	 * Internal class that replaces simple arrays of phrases
	 * 
	 * @author Hammer *
	 */
	public class PhraseArray {

		String[] phraseStrings;
		String phraseName;

		public PhraseArray(String[] phraseArray) {
			phraseStrings = phraseArray;
		}

		public String[] getPhraseArray() {
			return phraseStrings;
		}
	}

}
