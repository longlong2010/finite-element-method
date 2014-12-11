import node.*;
import load.*;
import element.*;
import constraint.*;

import matrix.Matrix;
import matrix.lu.LU;
import matrix.lu.LUException;

import java.util.ArrayList;
import java.util.TreeSet;

public class Structure {

	protected ArrayList<Node> nodes;
	protected ArrayList<Element> elements;
	protected Matrix k;
	protected Matrix r;
	
	public Structure() {
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

	public void solve() {
		int dof = 0;
		for (Node n:nodes) {
			dof += n.getDofNum();
		}

		this.k = new Matrix(dof, dof);
		this.r = new Matrix(dof ,1);

		for (Element e:elements) {
			ArrayList<Node> ns = e.getNodes();
			Matrix ke = e.getKe();
			int size = ke.getColumnSize();

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
			TreeSet<Constraint> contains = n.getConstraints();
			TreeSet<Dof> dofs = n.getDofs();
			
			int dofn = 0;
			for (Dof d:dofs) {
				for (Constraint c:contains) {
					if (c.getDof() == d) {
						for (int i = 0; i < dof; i++) {
							this.k.set(k + dofn, i, 0);
						}
						this.k.set(k + dofn, k + dofn, 1);
					}
				}
				
				TreeSet<Load> loads = n.getLoads();
				for (Load l:loads) {
					if (l.getDof() == d) {
						this.r.set(k + dofn, 0, l.getValue());
					}
				}
				dofn++;
			}
			k += n.getDofNum();
		}
		
		try {
			LU lu = LU.LUDecomposition(this.k);
			Matrix u = lu.solve(this.r);
			u.print();

			k = 0;
			for (Node n:nodes) {
				TreeSet<Dof> dofs = n.getDofs();
				for (Dof d:dofs) {
					double v = u.get(k, 0);
					switch (d) {
						case X:
						n.setU(v);
						break;
						case Y:
						n.setV(v);
						break;
						case THETA:
						if (n instanceof BeamNode) {
							BeamNode bn = (BeamNode) n;
							bn.setTheta(v);
						}
						break;
					}
					k++;
				}
			}

			for (Element e:elements) {

			}
		} catch (Exception ex) {
			ex.printStackTrace();		
		}
	}
}
