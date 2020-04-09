package components;

public class Plugboard extends EnigmaComponent {

	private int[] pairings;
	private MovingRotor rightMostRotor;

	public Plugboard(String pairings) {
		super(pairings);
	}

	private void initializePlugboard() {
		pairings = new int[26];
		for (int i = 0; i < 26; i++) {
			pairings[i] = i;
		}
	}

	private void setPairings(int[][] pairings) {
		for (int[] couple : pairings) {
			setPairing(couple[0], couple[1]);
		}
	}

	private void setPairing(int i, int j) {
		pairings[i] = j;
		pairings[j] = i;
	}

	private int getPair(int i) {
		return pairings[i];
	}

	private int firstRotorConnection(int i) {
		return pairings[calculateIndex(i, rightMostRotor)];
	}

	public void turnRotors() {
		rightMostRotor.turnRotor();
	}
	public MovingRotor getRightMostRotor() {
		return this.rightMostRotor;
	}
	
	@Override
	protected void initializeComponent(String pairings) {
		initializePlugboard();
		if(pairings != null)
			setPairings(manager.pairingsToIntegerArrays(pairings));	
	}
	
	@Override
	public int forwardTraversal(int i) {
		this.turnRotors();
		return rightMostRotor.forwardTraversal(this.getPair(i));
	}

	@Override
	public int inverseTraversal(int i) {
		return this.firstRotorConnection(i);
	}

	@Override
	public void setNext(EnigmaComponent next) {
		this.rightMostRotor = (MovingRotor) next;
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public void setPrevious(EnigmaComponent previous) {

	}

	

}
