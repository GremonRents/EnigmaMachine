package components;

public class MovingRotor extends Reflector {

	private int offset;

	public MovingRotor(String settings) {
		super(settings);
		resetOffset();
	}

	private int rotorConnection(int i) {
		return valueAt(calculateIndex(i, this.previous));
	}

	private int inverseRotorConnection(int i) {
		return indexOf(calculateIndex(i, this.next));
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public boolean offsetOverflow() {
		return offset == 26;
	}

	public void resetOffset() {
		this.offset = 0;
	}

	public void increaseOffset() {
		this.offset++;
	}

	public void turnRotor() {
		increaseOffset();
		if(offsetOverflow())
			resetOffset();
	}
	
	public EnigmaComponent getNext() {
		return this.next;
	}
	@Override
	public int forwardTraversal(int i) {
		return next.forwardTraversal(this.rotorConnection(i));
	}

	@Override
	public int inverseTraversal(int i) {
		return previous.inverseTraversal(this.inverseRotorConnection(i));
	}

	@Override
	public int getOffset() {
		return offset;
	}

}
