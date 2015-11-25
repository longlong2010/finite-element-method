package model;

import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.vector.dense.BasicVector;
import org.la4j.matrix.sparse.CCSMatrix;
import org.la4j.linear.GaussianSolver;

import geometry.node.Node;
import geometry.node.Dof;
import geometry.element.Element;
import load.Load;
import constraint.Constraint;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public abstract class Model {

	protected ArrayList<Node> nodes;
	protected ArrayList<Element> elements;
	protected CCSMatrix K;
	protected BasicVector R;

	public Model() {
		this.nodes = new ArrayList<Node>();
		this.elements = new ArrayList<Element>();
	}

	protected boolean addElement(Element e) {
		ArrayList<Node> ns = e.getNodes();
		for (Node n:ns) {
			if (!this.nodes.contains(n)) {
				this.nodes.add(n);
			}
		}
		return this.elements.contains(e) ? false : this.elements.add(e);
	}

	public int getDofNum() {
		int ndof = 0;
		for (Node n:nodes) {
			ndof += n.getDofNum();
		}
		return ndof;
	}

	protected void init() {
		int ndof = this.getDofNum(); 
		
		this.K = new CCSMatrix(ndof, ndof);
		this.R = new BasicVector(ndof);
	}

	protected void integrate(int[] map, Element e) {
		Matrix ke = e.getKe();
		int size = e.getDofNum();

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int mi = map[i];
				int mj = map[j];
				this.K.set(mi, mj, this.K.get(mi, mj) + ke.get(i, j));
			}
		}
	}

	protected void integrate() {
		int ndof = this.getDofNum();

		for (Element e:elements) {
			ArrayList<Node> ns = e.getNodes();
			int size = e.getDofNum();

			int map[] = new int[size];
			int l = 0;

			for (Node n:ns) {
				int k = this.nodes.indexOf(n) * n.getDofNum();
				for (int i = 0; i < n.getDofNum(); i++) {
					map[l++] = k + i;
				}
			}

			this.integrate(map, e);
		}
	}

	protected void integrateLoad() {
		int k = 0;
		for (Node n:nodes) {
			TreeSet<Dof> dofs = n.getDofs();
			
			int dofn = 0;
			for (Dof d:dofs) {
				TreeMap<Load, Double> loads = n.getLoads();
				for (Map.Entry<Load, Double> entry : loads.entrySet()) {
					Load l = entry.getKey();
					if (l.getDof() == d) {
						this.R.set(k + dofn, entry.getValue());
					}
				}
				dofn++;
			}
			k += n.getDofNum();
		}
	}

	protected void addConstraint() {
		int ndof = this.getDofNum();
		int k = 0;
		for (Node n:nodes) {
			TreeMap<Constraint, Double> constraints = n.getConstraints();
			TreeSet<Dof> dofs = n.getDofs();
			
			int dofn = 0;
			for (Dof d:dofs) {
				for (Map.Entry<Constraint, Double> entry : constraints.entrySet()) {
					Constraint c = entry.getKey();
					if (c.getDof() == d) {
						for (int i = 0; i < ndof; i++) {
							this.K.set(k + dofn, i, 0);
						}
						this.K.set(k + dofn, k + dofn, 1);
						this.R.set(k + dofn, entry.getValue());
					}
				}
				dofn++;
			}
			k += n.getDofNum();
		}
	}

	protected void solveEquations() {
		GaussianSolver solver = new GaussianSolver(this.K);
		Vector u = solver.solve(this.R);
		
		int k = 0;
		for (Node n:nodes) {
			TreeSet<Dof> dofs = n.getDofs();
			for (Dof d:dofs) {
				n.setValue(d, u.get(k));
				k++;
			}
		}
	}

	public void solve() {
		this.init();
		this.integrate();
		this.integrateLoad();
		this.addConstraint();
		this.solveEquations();
	}
}
