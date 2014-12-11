package node;

import constraint.Constraint;

public class BeamNode extends Node {
	
	protected double theta;

	public BeamNode(double x, double y) {
		super(x, y);
		this.dofs.add(Dof.THETA);
		//hack
		this.dofs.remove(Dof.X);
	}

	public double getTheta() {
		return this.theta;
	}

	public boolean setTheta(double theta) {
		boolean rc = !this.constraints.contains(Constraint.THETA);
		if (rc) {
			this.theta = theta;
		}
		return rc;
	}
}
