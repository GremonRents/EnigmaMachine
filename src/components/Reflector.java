package components;

public class Reflector extends EnigmaComponent {

	private int wiring[];
	public EnigmaComponent next;
	public EnigmaComponent previous;

	public Reflector(String wiring) {
		this.wiring = manager.alphabetToIntegerArray(wiring);
	}

	private int reflectorConnection(int i) {
		return getMapping(calculateIndex(i, this.previous));
	}

	protected int getMapping(int i) {
		return wiring[i];
	}

	protected int getInverseMapping(int i) {
		return indexOf(i);
	}

	protected int indexOf(int value) {
		for (int i = 0; i < 26; i++) {
			if (wiring[i] == value)
				return i;
		}
		return -1;
	}

	@Override
	public int forwardTraversal(int i) {
		return previous.inverseTraversal(reflectorConnection(i));
	}

	@Override
	public int inverseTraversal(int i) {
		return 0;
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public void setNext(EnigmaComponent next) {
		this.next = next;
	}

	@Override
	public void setPrevious(EnigmaComponent previous) {
		this.previous = previous;
	}

}