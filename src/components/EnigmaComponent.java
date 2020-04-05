package components;

import configuration.WiringManager;

public abstract class EnigmaComponent {
	protected WiringManager manager;
	

	public EnigmaComponent(String settings) {
		this.manager = WiringManager.getInstance();
		initializeComponent(settings);
	}

	protected int calculateIndex(int i, EnigmaComponent component) {
		return (i + this.getOffset() - component.getOffset() + 26) % 26;
	}
	
	public abstract int forwardTraversal(int i);

	public abstract int inverseTraversal(int i);

	public abstract void setNext(EnigmaComponent next);

	public abstract void setPrevious(EnigmaComponent previous);

	protected abstract int getOffset();
	
	protected abstract void initializeComponent(String settings);
	
	
	
}
