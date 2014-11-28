package load;

import node.Dof;

public enum Load {
	X(Dof.X), Y(Dof.Y), M(Dof.THETA);
	
	protected double value;
	protected Dof dof;

	private Load(Dof dof) {
		this.dof = dof;
	}

	public void setValue(double v) {
		this.value = v;
	}

	public double getValue() {
		return this.value;
	}

	public Dof getDof() {
		return this.dof;
	}
}
