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

import components.EnigmaComponent;
import components.MovingRotor;
import components.NotchRotor;
import components.Plugboard;
import components.Reflector;
import configuration.ConfigurationParser;
import configuration.ConfigurationValidator.InitialConfigurationException;
import configuration.ConfigurationValidator.PlugboardException;
import configuration.ConfigurationValidator.ReflectorException;
import configuration.ConfigurationValidator.RotorException;
import configuration.WiringManager;

/**
 *
 * @author Giuseppe
 */
public class Encryption {
	private ConfigurationParser configuration;
	private WiringManager manager;
	private List<MovingRotor> rotors;
	private Plugboard plugboard;

	public Encryption() throws Exception {
		configuration = new ConfigurationParser();
		manager = WiringManager.getInstance();
		rotors = new ArrayList<MovingRotor>();
		initializeMachine();
		readFile();
	}

	public void initializeMachine() throws Exception {
		addPlugboard();
		addRotors();
		addReflector();
		addInitialConfiguration();
		
	}

	public void readFile() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("./output.txt"));
		String text = Files.readString(Path.of("./input.txt")).toUpperCase();

		for (int i = 0; i < text.length() - 1; i++) {
			writer.append(encryptLetter(text.charAt(i)));

		}
		writer.close();
	}


	public void addPlugboard() throws PlugboardException {
		this.plugboard = (Plugboard) configuration.getPlugboard();
	}
	
	public void addRotors() throws RotorException  {
		MovingRotor rotor = null;
		while((rotor = (MovingRotor) configuration.getRotor()) != null)
			rotors.add(rotor);
	}
	
	public void addReflector() throws ReflectorException {
		configuration.getReflector();
	}
	
	public void addInitialConfiguration() throws InitialConfigurationException {
		setRotorsPosition(configuration.getInitialConfiguration().split("|"));
	}



	public char encryptLetter(char c) {
		updateRotorsPosition();
		resetOffsets();
		return manager.integerToLetter(plugboard.forwardTraversal(manager.letterToInteger(c)));

	}

	public void updateRotorsPosition() {
		NotchRotor secondToLastRotor = (NotchRotor) rotors.get(rotors.size() - 2);
		if (secondToLastRotor.notchPositionReached())
			rotors.get(rotors.size() - 1).increaseOffset();

		for (int i = 1; i < rotors.size() - 1; i++) {
			NotchRotor previous = (NotchRotor) rotors.get(i - 1);
			NotchRotor current = (NotchRotor) rotors.get(i);
			if (previous.notchPositionReached() || current.notchPositionReached()) {
				current.increaseOffset();
			}
		}
		rotors.get(0).increaseOffset();
	}

	public void resetOffsets() {
		rotors.stream().filter(x -> x.offsetOverflow()).forEach(x -> x.resetOffset());
	}

	public String getRotorsPosition() {
		String posizione = "";
		for (int i = rotors.size() - 1; i > 0; i--) {
			posizione += manager.integerToLetter(rotors.get(i).getOffset()) + "-";
		}
		posizione += manager.integerToLetter(rotors.get(0).getOffset());
		return posizione;
	}

	public void setRotorsPosition(String[] posizione) {
		for (int i = 0; i < rotors.size(); i++) {
			rotors.get(i).setOffset(manager.letterToInteger(posizione[rotors.size() - 1 - i].charAt(0)));
		}
	}

}
