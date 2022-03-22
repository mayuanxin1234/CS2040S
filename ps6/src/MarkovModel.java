import javax.print.attribute.standard.PrinterMakeAndModel;
import java.util.Arrays;
import java.util.Random;
import java.util.HashMap;

/**
 * This is the main class for your Markov Model.
 *
 * Assume that the text will contain ASCII characters in the range [1,255].
 * ASCII character 0 (the NULL character) will be treated as a non-character.
 *
 * Any such NULL characters in the original text should be ignored.
 */
public class MarkovModel {

	// Use this to generate random numbers as needed
	private Random generator = new Random();

	// This is a special symbol to indicate no character
	public static final char NOCHARACTER = (char) 0;

	private HashMap<String, int[]> MarkovModel;

	private final int order;

	/**
	 * Constructor for MarkovModel class.
	 *
	 * @param order the number of characters to identify for the Markov Model sequence
	 * @param seed the seed used by the random number generator
	 */
	public MarkovModel(int order, long seed) {
		// Initialize your class here
		this.order = order;
		this.MarkovModel = new HashMap<>();
		// Initialize the random number generator
		generator.setSeed(seed);
	}

	/**
	 * Builds the Markov Model based on the specified text string.
	 */
	public void initializeText(String text) {
		// Build the Markov model here
			int length = text.length();
			for (int j = 0; j < (length - order); j++) {
				int end = j + order;
				String compare = text.substring(j, end);
				if (!MarkovModel.containsKey(compare)) {
					int[] intArray = new int[256];
					int value = text.charAt(end);
					if (value != NOCHARACTER) {
						intArray[value]++;
						MarkovModel.put(compare, intArray);
					}
				} else {
					int value = text.charAt(end);
					int[] array = MarkovModel.get(compare);
					if (value != NOCHARACTER) {
						array[value]++;
					}
				}
			}
		}
	/**
	 * Returns the number of times the specified kgram appeared in the text.
	 */
	public int getFrequency(String kgram) {
		int count = 0;
		if (kgram.length() != order) {
			throw new IllegalArgumentException("kgram wrong size!");
		}
		for (int i : MarkovModel.get(kgram)) {
			count += i;
		}
		return count;
	}

	/**
	 * Returns the number of times the character c appears immediately after the specified kgram.
	 */
	public int getFrequency(String kgram, char c) {
		int count = 0;
		if (kgram.length() != order) {
			throw new IllegalArgumentException("kgram wrong size!");
		}
			for (int i = 0; i < MarkovModel.get(kgram).length; i++) {
				if (i == c) {
					count += MarkovModel.get(kgram)[i];
				}
			}
			return count;
	}

	/**
	 * Generates the next character from the Markov Model.
	 * Return NOCHARACTER if the kgram is not in the table, or if there is no
	 * valid character following the kgram.
	 */
	public char nextCharacter(String kgram) {
		// See the problem set description for details
		// on how to make the random selection.
		if (kgram.length() != order) {
			throw new IllegalArgumentException("length of kgram is wrong!");
		} else if (!MarkovModel.containsKey(kgram)) {
			return NOCHARACTER;
		}
		int totalFrequency = getFrequency(kgram);
		int randomNumber = generator.nextInt(totalFrequency);
		int count = 0;
		for (int i = 0; i < 256; i++) {
			int[] value = MarkovModel.get(kgram);
			if (value[i] != 0) {
				count += value[i];
			}
			if (count > randomNumber) {
				char character = (char) i;
				return character;
			}
		}
		return NOCHARACTER;
	}
}
