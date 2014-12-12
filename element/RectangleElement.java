package element;

import node.Node;

import matrix.Matrix;

public class RectangleElement extends Element {
	
	public RectangleElement(Node n1, Node n2, Node n3, Node n4, double et, double nu) {
		super();
		this.addNode(n1);
		this.addNode(n2);
		this.addNode(n3);
		this.addNode(n4);

		int nnode = this.nodes.size();

		int xi[] = new int[3];
		int eta[] = new int[3];
		double a, b;
		a = b = 0;

		for (int i = 0; i < nnode; i++) {
			for (int j = i + 1; j < nnode; j++) {
				
				double dx = this.nodes.get(i).getX() - this.nodes.get(j).getX();
				double signx = Math.signum(dx);
				if (signx != 0) {
					a = Math.abs(dx);
				}
				xi[i] = signx > 0 ? 1 : (signx < 0 ? -1 : 0);

				double dy = this.nodes.get(i).getY() - this.nodes.get(j).getY();
				double signy = Math.signum(dy);
				if (signy != 0) {
					b = Math.abs(dy);
				}
				eta[i] = signy > 0 ? 1 : (signy < 0 ? -1 : 0);

				if (xi[i] != 0 && eta[i] != 0) {
					break;
				}
			}
		}
		double delta = a * b;
		double s = et / (4 * (1 - nu * nu) * delta);

		this.ke = new Matrix(nnode * 2, nnode * 2);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				double k1 = 1;	
				double k2 = 1;	
				double k3 = 1;	
				double k4 = 1;

				this.ke.set(i * 2, j * 2, k1);
				this.ke.set(i * 2, j * 2 + 1, k3);
				this.ke.set(i * 2 + 1, j * 2, k2);
				this.ke.set(i * 2 + 1, j * 2 + 1, k4);
			}
		}
	}

	public Matrix getStress() {
		return new Matrix();
	}
}
