package enigmamachine;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class ConfigurationParser {

	public ConfigurationParser() {

		JSONParser parser = new JSONParser();

		try (FileReader reader = new FileReader("./configuration.json")) {
			// Read JSON file
			Object obj = parser.parse(reader);

			JSONObject configuration = (JSONObject) obj;

			parseObject(configuration);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	private void parseObject(JSONObject object) {
		// Get employee object within list
		JSONArray rotors = (JSONArray) object.get("rotors");
		rotors.forEach(System.out::println);
		String pairings = (String) object.get("plugboard");
		if(this.containsOnlySpacedPairsOfLetters(pairings) && this.arePlugboardConnectionsValid(pairings)) {
			System.out.println("OK");
		}
		else System.out.println("NOPE");
		
		System.out.println();

		// Get employee first name
		String grundstellung = (String) object.get("grundstellung");
		System.out.println(grundstellung);

		// Get employee last name
		String ringstellung = (String) object.get("ringstellung");
		System.out.println(ringstellung);

		
		String reflector = (String) object.get("reflector");
		System.out.println(reflector);
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
			if (character != reflector.charAt(character-'A'))
				return false;
		}
		return true;
	}

	private boolean containsOnlyLetters(String rotor) {
		return rotor.matches("^[A-Z]+");
	}
	
	private boolean areRotorConnectionsValid(String rotor) { // O(n)
		return containsDuplicates(rotor);
	}
	
	private boolean containsOnlySpacedPairsOfLetters(String pairings) {
		return pairings.matches("^([A-Z][A-Z](\\s|\\z))+");
	}
	
	private boolean arePlugboardConnectionsValid(String pairings) {
		return containsDuplicates(pairings.replaceAll("\\s",""));
		
	}
	
	private String toUpperCase(String alphabet) {
		return alphabet.toUpperCase();
	}

	

}
