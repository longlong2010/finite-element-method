package element;

import node.Node;

import matrix.Matrix;


public class QuadrangleElement extends Element {

	public QuadrangleElement(Node n1, Node n2, Node n3, Node n4, double e, double t, double nu) {
		super();
		this.addNode(n1);
		this.addNode(n2);
		this.addNode(n3);
		this.addNode(n4);

	}

	private Matrix getJacobi(double xi, double eta) {
		int dof = 2;
		int nnode = this.nodes.size();
		Matrix l = new Matrix(dof, nnode);
		Matrix r = new Matrix(nnode, dof);

		for (int i = 0; i < nnode; i++) {
			Node n = this.nodes.get(i);
			r.set(i, 0, n.getX());
			r.set(i, 1, n.getY());
		}
		
		l.set(0, 0, -0.25 * (1 - eta));
		l.set(1, 0, -0.25 * (1 - xi));

		l.set(0, 1, 0.25 * (1 - eta));
		l.set(1, 1, -0.25 * (1 + xi));

		l.set(0, 2, 0.25 * (1 + eta));
		l.set(1, 2, 0.25 * (1 + xi));
		
		l.set(0, 3, -0.25 * (1 + eta));
		l.set(1, 3, 0.25 * (1 - xi));
		
		Matrix J = new Matrix(dof, dof);
		for (int i = 0; i < dof; i++) {
			for (int j = 0; j < dof; j++) {
				double v = 0;
				for (int k = 0; k < nnode; k++) {
					v += l.get(i, k) * r.get(k, j);
				}
				J.set(i, j, v);
			}
		}
		return J;
	}

	public Matrix getStress() {
		return new Matrix();
	}
}
