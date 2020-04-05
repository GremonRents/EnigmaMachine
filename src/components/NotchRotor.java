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

	public boolean notchPositionReached() {
		return this.getOffset() == this.notchPosition;
	}
	
	@Override
	protected void initializeComponent(String settings) {
		String[] parameters = settings.split("\\|");
		super.initializeComponent(parameters[0]);
		this.notchPosition = manager.letterToInteger(notchPosition);
	}

}
