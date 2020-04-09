package components;

public class NotchRotor extends MovingRotor {

	private int notchPosition;

	public NotchRotor(String settings) {
		super(settings);
		
	}

	public int getNotchPosition() {
		return notchPosition;
	}

	public void setNotchPosition(int notchPosition) {
		this.notchPosition = notchPosition;
	}

	public boolean notchPositionSurpassed() {
		return this.getOffset() == this.notchPosition +1;
	}
	public boolean notchPositionReached() {
		return this.getOffset() == this.notchPosition;
	}
	@Override
	public void turnRotor() {
		super.turnRotor();
		if(notchPositionSurpassed())
			((MovingRotor)next).turnRotor();
		else if(next instanceof NotchRotor && ((NotchRotor) next).notchPositionReached()) {
			((MovingRotor)next).turnRotor();
		}
	}
	
	@Override
	protected void initializeComponent(String settings) {
		String[] parameters = settings.split("\\|");
		super.initializeComponent(parameters[0]);
		this.notchPosition = manager.letterToInteger(parameters[1].charAt(0));
	}

}
