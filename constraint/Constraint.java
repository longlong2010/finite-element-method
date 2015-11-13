package constraint;

import geometry.node.Dof;

public enum Constraint {
	X(Dof.X), Y(Dof.Y), THETA(Dof.THETA), T(Dof.T);

	protected Dof dof;

	private Constraint(Dof dof) {
		this.dof = dof;
	}

	public Dof getDof() {
		return this.dof;
	}
}
