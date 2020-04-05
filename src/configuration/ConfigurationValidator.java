package configuration;

import java.util.HashSet;

public class ConfigurationValidator {

	public static ConfigurationValidator instance = new ConfigurationValidator();
	private ConfigurationValidator(){};
	
	public static ConfigurationValidator getInstance() {
		return instance;
	}
	
	private boolean checkRotors(int grundstellungLength, int ringstellungLength, int rotorsNumber) {
		return (grundstellungLength == ringstellungLength) && (grundstellungLength == rotorsNumber);

	}

	private boolean containsDuplicates(String string) {
		HashSet<Character> set = new HashSet<Character>();
		for (int i = 0; i < string.length(); i++) {
			if (!set.add(string.charAt(i)))
				return false;
		}
		return true;
	}

	private boolean areReflectorConnectionsValid(String reflector) {
		for (int i = 0; i < reflector.length(); i++) {
			char character = reflector.charAt(i);
			if (character != reflector.charAt(character - 'A'))
				return false;
		}
		return true;
	}

	private boolean containsOnlyLetters(String settings) {
		return settings.matches("^[A-Z]+");
	}

	private boolean areRotorConnectionsValid(String settings) { // O(n)
		return containsDuplicates(settings);
	}

	private boolean containsOnlySpacedPairsOfLetters(String pairings) {
		return pairings.matches("^([A-Z][A-Z](\\s|\\z))+");
	}

	private boolean arePlugboardConnectionsValid(String pairings) {
		return containsDuplicates(pairings.replaceAll("\\s", ""));

	}
	
	private boolean containsAllLetters(String settings) {
		return settings.length() == 26;
	}
	
	private boolean isLetter(String notch) {
		return notch.length() == 1 && Character.isLetter(notch.charAt(0));
		
	}
	
	protected boolean validatePlugboardSettings(String pairings) throws PlugboardException {
		if(!containsOnlySpacedPairsOfLetters(pairings))
			throw new PlugboardException("Pairings must be separated by space");
		if(!arePlugboardConnectionsValid(pairings))
			throw new PlugboardException("Pairings are not valid");
		
		return true;
	}
	
	protected boolean validateReflectorSettings(String wirings) throws ReflectorException {
		if(!containsOnlyLetters(wirings))
			throw new ReflectorException("Insert only letters");
		if(!containsAllLetters(wirings))
			throw new ReflectorException("Insert all letters");
		if(!areRotorConnectionsValid(wirings) && !areReflectorConnectionsValid(wirings))
			throw new ReflectorException("Wirings are not valid");
		
		return true;
	}
	
	protected boolean validateRotorSettings(String wirings) throws RotorException {
		if(!containsOnlyLetters(wirings))
			throw new RotorException("Insert only letters");
		if(!containsAllLetters(wirings))
			throw new RotorException("Insert all letters");
		if(!areRotorConnectionsValid(wirings))
			throw new RotorException("Wirings are not valid");
		
		return true;
	}
	
	
	
	protected boolean validateNotchRotorSettings(String settings) throws RotorException {
		String notch = settings.split("\\|")[1];
		String wirings = settings.split("\\|")[0];
		if(!isLetter(notch))
			throw new RotorException("Notch position must be a letter");
		return validateRotorSettings(wirings);
	}
	
	protected boolean validateInitialConfiguration(String grundstellung, String ringstellung, int rotorsNumber) throws InitialConfigurationException {
		if(!checkRotors(grundstellung.length(), ringstellung.length(), rotorsNumber))
			throw new InitialConfigurationException("Number of rotors doesn't much initial configuration");
		if(!(containsOnlyLetters(grundstellung) && containsOnlyLetters(ringstellung)))
			throw new InitialConfigurationException("Insert only letters");
		
		return true;
		
	}
	
	public static class InitialConfigurationException extends Exception { 
	    public InitialConfigurationException(String errorMessage) {
	        super(errorMessage);
	    }
	}
	
	public static class PlugboardException extends Exception { 
	    public PlugboardException(String errorMessage) {
	        super(errorMessage);
	    }
	}
	
	public static class RotorException  extends Exception { 
	    public RotorException(String errorMessage) {
	        super(errorMessage);
	    }
	}
	
	public static class ReflectorException  extends Exception { 
	    public ReflectorException(String errorMessage) {
	        super(errorMessage);
	    }
	}

}
