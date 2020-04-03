package enigmamachine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author Giuseppe
 */
public class Encryption {
    private WiringManager manager;
    private List<MovingRotor> rotors;
    private Plugboard plugboard;
    private EnigmaComponent previous;
    private EnigmaComponent next;
    
    public Encryption() throws FileNotFoundException, IOException{
    	manager = WiringManager.getInstance();
        rotors = new ArrayList<MovingRotor>();
        initializePreviousAndNext();
        initializeMachine();   
        readFile();
    }
    
    
    public void initializeMachine() throws IOException {
    	BufferedReader reader = new BufferedReader(new FileReader("./configuration.enigma"));
    	String linea = "";
    	while((linea = reader.readLine()) != null)
    		switch(linea.substring(0, 4)) {
    			case "_R0:": addNotchRotor(linea.substring(4));
    						break;
    			case "_R1:": addMovingRotor(linea.substring(4));
							break;
    			case "_Re:": addReflector(linea.substring(4));
    						break;
    			case "_Pb:": addPlugboard(linea.substring(4));
    						break;
    			case "_Cs:": setRotorsPosition(linea.substring(4).split("|"));
    						break;
    			default:
    	}
    	reader.close();
    }
    
    public void readFile() throws IOException {
    	BufferedWriter writer = new BufferedWriter(new FileWriter("./output.txt"));
    	String text = Files.readString(Path.of("./input.txt"));
    	
    	for(int i = 0; i < text.length() -1; i++) {
    		writer.append(encryptLetter(text.charAt(i)));
    		
    	}
    	writer.close();
    }
    
    public void writeFile() {
    	
    }
    
    public void addNotchRotor(String configuration) {
    	String[] parameters = configuration.split("\\|");
    	this.next = new NotchRotor(parameters[0], parameters[1].charAt(0));
    	setPreviousAndNext();
    	rotors.add((NotchRotor) next);
    }
    
    public void addMovingRotor(String wiring) {
    	this.next = new MovingRotor(wiring);
    	setPreviousAndNext();
    	rotors.add((MovingRotor) next);
    }
    
    public void addReflector(String wiring) {
    	this.next = new Rotor(wiring);
    	setPreviousAndNext();
    }
    public void addPlugboard(String pairings) {
    	this.next = new Plugboard(pairings);
    	setPreviousAndNext();
    	plugboard = (Plugboard) next;
    }
    
    public void setPreviousAndNext() {
    	if(previous != null)
    		previous.setNext(next);
    	next.setPrevious(previous);
    	previous = next;
    }
    
    public void initializePreviousAndNext() {
    	this.previous = null;
    	this.next = null;
    }

    
    public char encryptLetter(char c) {
    	updateRotorsPosition();
        resetOffsets();
        return manager.integerToLetter(plugboard.forwardTraversal(manager.letterToInteger(c)));
    	
    }
    
    public void updateRotorsPosition() {
    	NotchRotor secondToLastRotor = (NotchRotor) rotors.get(rotors.size()-2);
    	if(secondToLastRotor.notchPositionReached())
    		rotors.get(rotors.size()-1).increaseOffset();
 
    	for(int i = 1; i < rotors.size() - 1; i++) {
    		NotchRotor previous = (NotchRotor) rotors.get(i-1);
    		NotchRotor current = (NotchRotor) rotors.get(i);
    		if(previous.notchPositionReached() || current.notchPositionReached()) {
    			current.increaseOffset();
    		}
    	}
    	rotors.get(0).increaseOffset();
    }
    
    public void resetOffsets() { 
    	rotors.stream()
    		.filter(x -> x.offsetOverflow())
    		.forEach(x -> x.resetOffset());
    }
    
    
    public String getRotorsPosition() {
    	String posizione = "";
    	for(int i = rotors.size() -1 ; i > 0; i--) {
    		posizione += manager.integerToLetter(rotors.get(i).getOffset()) + "-";
    	}
    	posizione += manager.integerToLetter(rotors.get(0).getOffset());
    	return posizione;
    }
    public void setRotorsPosition(String[] posizione) {
    	for(int i = 0; i < rotors.size(); i++) {
    		rotors.get(i).setOffset(manager.letterToInteger(posizione[rotors.size()-1-i].charAt(0)));
    	}
    }
    
    
   
}
