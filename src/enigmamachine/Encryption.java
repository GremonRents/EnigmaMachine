package enigmamachine;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import components.EnigmaComponent;
import components.MovingRotor;
import components.Plugboard;
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
	private Plugboard plugboard;

	public Encryption() throws Exception {
		configuration = new ConfigurationParser();
		manager = WiringManager.getInstance();
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
		while(configuration.getRotor() != null);
	}
	
	public void addReflector() throws ReflectorException {
		configuration.getReflector();
	}
	
	public void addInitialConfiguration() throws InitialConfigurationException {
		setRotorsPosition(configuration.getInitialConfiguration().split("|"));
	}



	public char encryptLetter(char c) {
		return manager.integerToLetter(plugboard.forwardTraversal(manager.letterToInteger(c)));

	}

	public void setRotorsPosition(String[] posizione) {
		EnigmaComponent rotor = plugboard.getRightMostRotor();
		for(int i = 1; rotor instanceof MovingRotor; i++) {
			((MovingRotor)rotor).setOffset(manager.letterToInteger(posizione[posizione.length - i].charAt(0)));
			rotor = ((MovingRotor)rotor).getNext();
		}
	}

}
