package element;

import node.Node;

import matrix.Matrix;

public class TriangleElement extends Element {
	
	public TriangleElement(Node n1, Node n2, Node n3, double et, double nu) {
		super();
		this.addNode(n1);
		this.addNode(n2);
		this.addNode(n3);
	
		double delta = n2.getX() * n3.getY() - n2.getY() * n3.getX() 
					 + n3.getX() * n1.getY() - n3.getY() * n1.getX()
					 + n1.getX() * n2.getY() - n1.getY() * n2.getX();
		
		double s = et / (4 * (1 - nu * nu) * delta);

		Node[] ns = new Node[3];
		ns[0] = n1;
		ns[1] = n2;
		ns[2] = n3;
		
		double[] b = new double[3];
		double[] c = new double[3];
		for (int i = 0; i < 3; i++) {
			b[i] = -(ns[(i + 2) % 3].getY() - ns[(i + 1) % 3].getY());
		}

		for (int i = 0; i < 3; i++) {
			c[i] = ns[(i + 2) % 3].getX() - ns[(i + 1) % 3].getX();
		}
		
		this.ke = new Matrix(6, 6);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				double k1 = b[i] * b[j] + (1 - nu) * c[i] * b[j] / 2;
				double k2 = nu * c[i] * b[j] + (1 - nu) * b[i] * c[j] / 2;
				double k3 = k2;
				double k4 = c[i] * c[j] + (1 - nu) * b[i] * b[j] / 2;
				this.ke.set(i * 2, j * 2, k1);
				this.ke.set(i * 2, j * 2 + 1, k2);
				this.ke.set(i * 2 + 1, j * 2, k3);
				this.ke.set(i * 2 + 1, j * 2 + 1, k4);
			}
		}
	}
}
