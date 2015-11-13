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
	protected CCSMatrix k;
	protected BasicVector r;

	public Model() {
		this.nodes = new ArrayList<Node>();
		this.elements = new ArrayList<Element>();
	}

	public boolean addElement(Element e) {
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

	public void solve() {
		int ndof = this.getDofNum(); 
		
		this.k = new CCSMatrix(ndof, ndof);
		this.r = new BasicVector(ndof);

		for (Element e:elements) {
			ArrayList<Node> ns = e.getNodes();
			Matrix ke = e.getKe();
			
			int size = ke.rows();

			int map[] = new int[size];
			int l = 0;

			for (Node n:ns) {
				int k = this.nodes.indexOf(n) * n.getDofNum();
				for (int i = 0; i < n.getDofNum(); i++) {
					map[l++] = k + i;
				}
			}

			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					int mi = map[i];
					int mj = map[j];
					this.k.set(mi, mj, this.k.get(mi, mj) + ke.get(i, j));
				}
			}
		}

		int k = 0;
		for (Node n:nodes) {
			TreeSet<Dof> dofs = n.getDofs();
			
			int dofn = 0;
			for (Dof d:dofs) {
				TreeMap<Load, Double> loads = n.getLoads();
				for (Map.Entry<Load, Double> entry : loads.entrySet()) {
					Load l = entry.getKey();
					if (l.getDof() == d) {
						this.r.set(k + dofn, entry.getValue());
					}
				}
				dofn++;
			}
			k += n.getDofNum();
		}

		k = 0;
		for (Node n:nodes) {
			TreeMap<Constraint, Double> constraints = n.getConstraints();
			TreeSet<Dof> dofs = n.getDofs();
			
			int dofn = 0;
			for (Dof d:dofs) {
				for (Map.Entry<Constraint, Double> entry : constraints.entrySet()) {
					Constraint c = entry.getKey();
					if (c.getDof() == d) {
						for (int i = 0; i < ndof; i++) {
							this.k.set(k + dofn, i, 0);
						}
						this.k.set(k + dofn, k + dofn, 1);
						this.r.set(k + dofn, entry.getValue());
					}
				}
				dofn++;
			}
			k += n.getDofNum();
		}
		
		GaussianSolver solver = new GaussianSolver(this.k);
		Vector u = solver.solve(this.r);
		System.out.println(u);
		
		k = 0;
		for (Node n:nodes) {
			TreeSet<Dof> dofs = n.getDofs();
			for (Dof d:dofs) {
				n.setValue(d, u.get(k));
				k++;
			}
		}
	}
}
