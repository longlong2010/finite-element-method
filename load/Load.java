package load;

public enum Load {
	N,Q,M;
	protected double value;
	
	public void setValue(double v) {
		this.value = v;
	}

	public double getValue() {
		return this.value;
	}
}
