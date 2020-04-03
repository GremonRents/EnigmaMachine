package enigmamachine;

public class MovingRotor extends Rotor{
	
	private int offset;
	
	public MovingRotor(String wiring) {
		super(wiring);
		resetOffset();
	}
	
	private int rotorConnection(int i) {
		return getMapping(calculateIndex(i, this.previous));
	}
	
	private int inverseRotorConnection(int i) {
		return getInverseMapping(calculateIndex(i, this.next));
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
