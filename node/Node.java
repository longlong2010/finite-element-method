package node;

import load.Load;
import constraint.Constraint;

import java.util.HashSet;

public class Node {
	protected double x;
	protected double y;
	protected HashSet<Load> loads;
	protected HashSet<Constraint> constraints; 

	public Node(double x, double y) {
		this.x = x;
		this.y = y;
		
		this.loads = new HashSet<Load>();
		this.constraints = new HashSet<Constraint>();
	}

	public boolean addLoad(Load l) {
		return this.loads.add(l);
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

	public HashSet<Load> getLoads() {
		return this.loads;
	}

	public HashSet<Constraint> getConstraints() {
		return this.constraints;
	}
}
