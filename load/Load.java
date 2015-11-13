package load;

import geometry.node.Dof;

public enum Load {
	X(Dof.X), Y(Dof.Y), M(Dof.THETA), T(Dof.T);
	
	protected Dof dof;

	private Load(Dof dof) {
		this.dof = dof;
	}

	public Dof getDof() {
		return this.dof;
	}
}
