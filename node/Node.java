package node;

import load.Load;
import constraint.Constraint;

import java.util.TreeSet;

public class Node {

	protected double x;
	protected double y;

	protected TreeSet<Dof> dofs;
	protected TreeSet<Load> loads;
	protected TreeSet<Constraint> constraints; 

	public Node(double x, double y) {
		this.x = x;
		this.y = y;
		
		this.dofs = new TreeSet<Dof>();
		this.loads = new TreeSet<Load>();
		this.constraints = new TreeSet<Constraint>();

		this.dofs.add(Dof.Y);
		this.dofs.add(Dof.THETA);
	}

	public boolean addLoad(Load l) {
		return this.dofs.contains(l.getDof()) ? this.loads.add(l) : false;
	}

	public boolean addConstraint(Constraint c) {
		return this.dofs.contains(c.getDof()) ? this.constraints.add(c) : false;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public TreeSet<Load> getLoads() {
		return this.loads;
	}

	public TreeSet<Constraint> getConstraints() {
		return this.constraints;
	}

	public TreeSet<Dof> getDofs() {
		return this.dofs;
	}

	public int getDofNum() {
		return this.dofs.size();
	}
}
