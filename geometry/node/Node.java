package geometry.node;

import load.Load;
import constraint.Constraint;

import java.util.TreeSet;
import java.util.TreeMap;

public class Node {

	protected double x;
	protected double y;

	protected TreeSet<Dof> dofs;
	protected TreeMap<Load, Double> loads;
	protected TreeMap<Constraint, Double> constraints;
	protected TreeMap<Dof, Double> values;

	public Node(double x, double y) {
		this.x = x;
		this.y = y;
		
		this.dofs = new TreeSet<Dof>();
		this.loads = new TreeMap<Load, Double>();
		this.constraints = new TreeMap<Constraint, Double>();
		this.values = new TreeMap<Dof, Double>();
	}

	public boolean addLoad(Load l, double v) {
		boolean rc = this.dofs.contains(l.getDof());
		if (rc) {
			if (this.loads.containsKey(l)) {
				v += this.loads.get(l);
				this.loads.put(l, v);
			} else {
				this.loads.put(l, v);
			}
		}
		return rc;
	}

	public boolean addConstraint(Constraint c) {
		return this.addConstraint(c, 0);
	}

	public boolean addConstraint(Constraint c, double v) {
		return this.dofs.contains(c.getDof()) ? this.constraints.put(c, v) != null : false;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getValue(Dof d) {
		return this.values.get(d);
	}

	public void setValue(Dof d, double v) {
		this.values.put(d, v);
	}

	public TreeMap<Load, Double> getLoads() {
		return this.loads;
	}

	public TreeMap<Constraint, Double> getConstraints() {
		return this.constraints;
	}

	public TreeSet<Dof> getDofs() {
		return this.dofs;
	}

	public int getDofNum() {
		return this.dofs.size();
	}
}
