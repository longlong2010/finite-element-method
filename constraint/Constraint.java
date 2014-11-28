package constraint;

import node.Dof;

public enum Constraint {
	X(Dof.X), Y(Dof.Y), THETA(Dof.THETA);

	protected Dof dof;

	private Constraint(Dof dof) {
		this.dof = dof;
	}

	public Dof getDof() {
		return this.dof;
	}
}
