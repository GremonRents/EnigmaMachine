package enigmamachine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Giuseppe
 */
public class Encryption {
	private final int ROTOR_NUMBER = 3;
	private int[] plugboard;
	private int[] reflector;
    private int[][] rotors;
    private int rotorOffset[];
    private int notchPositions[];
    
    public Encryption() throws FileNotFoundException, IOException{
        initializeComponents();
    }
    
    public void initializeComponents() throws IOException {
    	BufferedReader reader = new BufferedReader(new FileReader("rotori.txt"));       
        initializePlugboard();
        initializeRotors(reader);
        initializeReflector(reader);
        initializeNotchPositions(reader);
        initializeRotorsOffset();
    }
    private void initializeNotchPositions(BufferedReader reader) throws IOException {
    	notchPositions = new int[ROTOR_NUMBER-1];
    	for(int i = 0; i < ROTOR_NUMBER-1; i++) {
    		notchPositions[i] = letterToIndex(reader.read());
    		reader.read();
    	}
	}
    
    private void initializeRotorsOffset() {
    	rotorOffset = new int[ROTOR_NUMBER];
    }

	public void initializePlugboard() {
    	plugboard = new int[26];
    	for(int i = 0; i < 26; i++) 
    		plugboard[i] = i;
    }
    
    public void initializeRotors(BufferedReader reader) throws IOException {
    	rotors = new int[ROTOR_NUMBER][26];
    	for(int i = 0; i < ROTOR_NUMBER; i++) {
    		for(int j = 0; j < 26; j++) {
    			rotors[i][j] = letterToIndex(reader.read());
    		}
    		reader.readLine();
    	}
    }
    
    public void initializeReflector(BufferedReader reader) throws IOException {
    	reflector = new int[26];
    	for(int i = 0; i < 26; i++) reflector[i] = letterToIndex(reader.read());
    	reader.readLine();
    }
    public char encryptLetter(char c) {
        updateRotorsPosition();
        setOffsetsInRange();
        int mappedIndex = rightToLeftTraversal(reflectorMapping(leftToRightTraversal(plugboardMapping(letterToIndex(c)))));
        int plugboardIndex = plugboard[(mappedIndex-rotorOffset[0]+26)%26];
        return (char) indexToLetter(plugboardIndex);
    }
    
    public int plugboardMapping(int index) {
    	int plugboardIndex = plugboard[index];
        return rotors[0][(plugboardIndex+rotorOffset[0]+26)%26];
    	
    }
    public int reflectorMapping(int mappedIndex) {
    	int reflectedIndex = reflector[(mappedIndex-rotorOffset[ROTOR_NUMBER-1]+26)%26]; 
        return reverse(rotors[ROTOR_NUMBER-1], (reflectedIndex+rotorOffset[ROTOR_NUMBER-1]+26)%26);
    }
    public int leftToRightTraversal(int mappedIndex) {
    	for(int i = 1; i < ROTOR_NUMBER; i++) {
        	mappedIndex = rotors[i][(mappedIndex+rotorOffset[i]-rotorOffset[i-1]+26)%26];
        } 
    	return mappedIndex;
    }
    
    public int rightToLeftTraversal(int mappedIndex) {
    	for(int i = ROTOR_NUMBER-2; i >= 0; i--) {
        	mappedIndex = reverse(rotors[i], (mappedIndex-rotorOffset[i+1]+rotorOffset[i]+26)%26);
        }
    	return mappedIndex;
    }
    
    public void updateRotorsPosition() {
    	if(rotorOffset[ROTOR_NUMBER-2] == notchPositions[ROTOR_NUMBER-2])
    		rotorOffset[ROTOR_NUMBER-1]++;
    	for(int i = 1; i < ROTOR_NUMBER - 1; i++) {
    		if(rotorOffset[i-1] == notchPositions[i-1] || rotorOffset[i] == notchPositions[i]) {
    			rotorOffset[i]++;
    		}
    	}
    	rotorOffset[0]++;
    }
    
    public void setOffsetsInRange() { 
    	for(int i = 0; i < ROTOR_NUMBER; i++) {
    		if(rotorOffset[i] == 26)
    			rotorOffset[i] = 0;
    	}
    }
    
    public int reverse(int[] rotore, int valore) {
        for(int i = 0; i < 26; i++) {
            if(rotore[i] == valore)
            	return i;
        }
        return -1;
    }
    
    public String getRotorsPosition() {
    	String posizione = "";
    	for(int i = ROTOR_NUMBER -1 ; i > 0; i--) {
    		posizione += (char) indexToLetter(rotorOffset[i]) + "-";
    	}
    	posizione += (char) indexToLetter(rotorOffset[0]);
    	return posizione;
    }
    public void setRotorsPosition(String[] posizione) {
    	for(int i = 0; i < ROTOR_NUMBER; i++) {
    		rotorOffset[i] = letterToIndex(posizione[ROTOR_NUMBER-1-i].charAt(0));
    	}
    }
    
    public int map(int value, int offset) {
    	return value+offset;
    }
    
    public int letterToIndex(int letter) {
    	return map(letter, -65);
    }
    
    public int indexToLetter(int index) {
    	return map(index, 65);
    }
    
    public void setPlugBoard(String[] coppia) {
    	int index_0 = letterToIndex(coppia[0].charAt(0));
    	int index_1 = letterToIndex(coppia[1].charAt(0));
        plugboard[index_0] = index_1;
        plugboard[index_1] = index_0;
    }  
}
