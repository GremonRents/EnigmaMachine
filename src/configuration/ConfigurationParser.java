package configuration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import components.*;
import configuration.ConfigurationValidator.*;

public class ConfigurationParser {
	private ConfigurationValidator validator;
	private WiringManager manager;
	private JSONObject configuration;
	private EnigmaComponent previous;
	private EnigmaComponent next;
	private int rotorCounter;
	private int rotorsNumber;

	public ConfigurationParser() {
		validator = ConfigurationValidator.getInstance();
		manager = WiringManager.getInstance();
		JSONParser parser = new JSONParser();
		rotorCounter = 0;

		try (FileReader reader = new FileReader("./configuration.json")) {

			configuration = (JSONObject) parser.parse(reader);
			rotorsNumber = ((JSONArray) configuration.get("rotors")).size();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	private EnigmaComponent parsePlugboard(String pairings) throws PlugboardException {
		if (pairings == null || validator.validatePlugboardSettings(pairings)) {
			this.next = ComponentFactory.getComponent("plugboard", pairings);
			attachComponents();
		}
		return next;
	}

	private EnigmaComponent parseReflector(String wirings) throws ReflectorException {
		if (validator.validateReflectorSettings(wirings)) {
			this.next = ComponentFactory.getComponent("reflector", wirings);
			attachComponents();
		}
		return next;
	}

	public EnigmaComponent getPlugboard() throws PlugboardException {
		String pairings = (String) configuration.get("plugboard");
		return parsePlugboard(pairings);
	
	}

	public EnigmaComponent getReflector() throws ReflectorException {
		String wirings = (String) configuration.get("reflector");
		if (wirings != null)
			return parseReflector(wirings);
		else
			throw new ReflectorException("Reflector is missing");

	}

	private EnigmaComponent parseNotchRotor(String settings) throws RotorException {
		if (validator.validateNotchRotorSettings(settings)) {
			this.next = ComponentFactory.getComponent("notch", settings);
			attachComponents();
		}
		return next;
	}

	private EnigmaComponent parseMovingRotor(String wirings) throws RotorException {
		if (validator.validateRotorSettings(wirings)) {
			this.next = ComponentFactory.getComponent("rotor", wirings);
			attachComponents();
		}
		return next;
	}

	private EnigmaComponent parseRotor(JSONArray rotors) throws RotorException {
		JSONArray settings = (JSONArray)rotors.get(rotorCounter++);
		if (settings.size() == 1)
			return parseMovingRotor((String) settings.get(0));
		else if (settings.size() == 2)
			return parseNotchRotor(settings.get(0)+"|"+settings.get(1));
		else
			throw new RotorException("Invalid rotor settings");

	}

	public EnigmaComponent getRotor() throws RotorException {
		JSONArray rotors = (JSONArray) configuration.get("rotors");
		if (rotors == null)
			throw new RotorException("Missing Rotors");
		if (rotorCounter == rotorsNumber)
			return null;
		return parseRotor(rotors);
	}

	public String parseInitialConfiguration(String grundstellung, String ringstellung) throws InitialConfigurationException {
		String initialConfiguration = "";
		if (validator.validateInitialConfiguration(grundstellung, ringstellung, rotorsNumber)) {

			for (int i = 0; i < grundstellung.length(); i++) {
				int difference = manager.letterToInteger(grundstellung.charAt(i)) - manager.letterToInteger(ringstellung.charAt(i));
				initialConfiguration += manager.integerToLetter((difference + 26) % 26);
			}

		}

		return initialConfiguration;
	}

	public String getInitialConfiguration() throws InitialConfigurationException {
		String grundstellung = (String) configuration.get("grundstellung");
		String ringstellung = (String) configuration.get("ringstellung");
		if (grundstellung == null)
			grundstellung = "AAA";
		if (ringstellung == null)
			ringstellung = "AAA";
		return parseInitialConfiguration(grundstellung, ringstellung);
	}
	


	private void attachComponents() {
		if (previous != null)
			previous.setNext(next);
		next.setPrevious(previous);
		previous = next;
	}

}
