package components;

public class NotchRotor extends MovingRotor {

	private int notchPosition;

	public NotchRotor(String wiring, char notchPosition) {
		super(wiring);
		this.notchPosition = manager.letterToInteger(notchPosition);
	}

	public int getNotchPosition() {
		return notchPosition;
	}

	public void setNotchPosition(int notchPosition) {
		this.notchPosition = notchPosition;
	}

	public boolean notchPositionReached() {
		return this.getOffset() == this.notchPosition;
	}

}
