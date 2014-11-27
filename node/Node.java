package node;

import java.util.HashSet;
import constraint.Constraint;

public class Node {
	protected double x;
	protected double y;
	protected HashSet<Constraint> constraints;  

	public Node(double x, double y) {
		this.x = x;
		this.y = y;
		this.constraints = new HashSet<Constraint>();
	}

	public boolean addConstraint(Constraint c) {
		return this.constraints.add(c);
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public HashSet<Constraint> getConstraints() {
		return this.constraints;
	}
}
