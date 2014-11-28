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
		
		double delta = 1;
		double s = et / (4 * (1 - nu * nu) * delta);

		this.ke = new Matrix(8, 8);
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
}
