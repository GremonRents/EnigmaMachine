package enigmamachine;

import java.util.Arrays;
import java.util.stream.Stream;

public class WiringManager {
	
	private static WiringManager instance = new WiringManager();
	private WiringManager() {};
	
	public static WiringManager getInstance() {
		return instance;
	}
	
	
	private int normalize(int value, int offset) {
    	return value+offset;
    }
    
    public int letterToInteger(int letter) {
    	return normalize(letter, -'A');
    }
    
    public char integerToLetter(int index) {
    	return (char) normalize(index, 'A');
    }
    
    public int[] alphabetToIntegerArray(String alphabet) {
    	return alphabet.chars()
    		.map(x -> this.letterToInteger(x))
    		.toArray();
    }
    
    public int[][] pairingsToIntegerArrays(String pairings) {
    	return Arrays.stream(pairings.split(" "))
    			.map(x -> alphabetToIntegerArray(x))
    			.toArray(int[][]::new);
    }
    
    

}
