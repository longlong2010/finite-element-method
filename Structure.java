import node.*;
import load.*;
import element.*;
import constraint.*;

import matrix.Matrix;
import matrix.lu.LU;
import matrix.lu.LUException;

import java.util.ArrayList;
import java.util.HashSet;

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
		int dof = this.nodes.size() * 2;
		this.k = new Matrix(dof, dof);
		this.r = new Matrix(dof ,1);

		for (Element e:elements) {
			ArrayList<Node> ns = e.getNodes();
			Matrix ke = e.getKe();
			int row = ke.getRowSize();
			int col = ke.getColumnSize();

			int map[] = new int[ns.size() * 2];
			int l = 0;
			for (Node n:ns) {
				int k = this.nodes.indexOf(n) * 2;
				map[l++] = k;
				map[l++] = k + 1;
			}

			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					int mi = map[i];
					int mj = map[j];
					this.k.set(mi, mj, this.k.get(mi, mj) + ke.get(i, j));
				}
			}
		}
		
		int k = 0;
		for (Node n:nodes) {
			HashSet<Constraint> contains = n.getConstraints();
			for (Constraint c:contains) {
				switch (c) {
					case X:
					break;
					case Y:
					for (int i = 0; i < this.k.getColumnSize(); i++) {
						this.k.set(k, i, 0);
					}
					this.k.set(k, k, 1);
					break;
					case THETA:
					for (int i = 0; i < this.k.getColumnSize(); i++) {
						this.k.set(k + 1, i, 0);
					}
					this.k.set(k + 1, k + 1, 1);
					break;
				}
			}

			HashSet<Load> loads = n.getLoads();
			for (Load l:loads) {
				switch (l) {
					case N:
					break;
					case Q:
					this.r.set(k, 0, l.getValue());
					break;
					case M:
					this.r.set(k + 1, 0, l.getValue());
					break;
				}
			}
			k += 2;
		}

		try {
			LU lu = LU.LUDecomposition(this.k);
			lu.solve(this.r).print();
		} catch (Exception ex) {
			ex.printStackTrace();		
		}
	}
}
