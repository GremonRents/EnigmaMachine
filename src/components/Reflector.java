package components;

public class Reflector extends EnigmaComponent {
	private int[] wirings;
	protected EnigmaComponent next;
	protected EnigmaComponent previous;

	public Reflector(String wirings) {
		super(wirings);
	}

	private int reflectorConnection(int i) {
		return valueAt(calculateIndex(i, this.previous));
	}

	protected int indexOf(int value) {
		for (int i = 0; i < 26; i++) {
			if (wirings[i] == value)
				return i;
		}
		return -1;
	}
	
	protected int valueAt(int i) {
		return wirings[i];
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

	@Override
	protected void initializeComponent(String wirings) {
		this.wirings = manager.alphabetToIntegerArray(wirings);
		
	}

}
