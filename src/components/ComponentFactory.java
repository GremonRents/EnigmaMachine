package components;

import java.util.Map;

public class ComponentFactory {

	private static final Map<String, Factory<EnigmaComponent>> factoryMap = Map.of(
			"plugboard", Plugboard::new,
			"notch", NotchRotor::new,
			"reflector", Reflector::new,
			"rotor", MovingRotor::new
	);

	public static EnigmaComponent getComponent(String type, String settings) {
		return factoryMap.get(type).create(settings);

	}

	@FunctionalInterface
	private interface Factory<S> {
		S create(String settings);
	}
}
