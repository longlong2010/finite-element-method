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

		int nnode = this.nodes.size();

		double[] b = new double[nnode];
		double[] c = new double[nnode];
		for (int i = 0; i < nnode; i++) {
			b[i] = -(this.nodes.get((i + 2) % nnode).getY() - this.nodes.get((i + 1) % nnode).getY());
		}

		for (int i = 0; i < nnode; i++) {
			c[i] = this.nodes.get((i + 2) % nnode).getX() - this.nodes.get((i + 1) % nnode).getX();
		}
		
		this.ke = new Matrix(nnode * 2, nnode * 2);
		for (int i = 0; i < nnode; i++) {
			for (int j = 0; j < nnode; j++) {
				double k1 = b[i] * b[j] + (1 - nu) * c[i] * c[j] / 2;
				double k2 = nu * c[i] * b[j] + (1 - nu) * b[i] * c[j] / 2;
				double k3 = nu * b[i] * c[j] + (1 - nu) * c[i] * b[j] / 2;
				double k4 = c[i] * c[j] + (1 - nu) * b[i] * b[j] / 2;
				
				this.ke.set(i * 2, j * 2, k1);
				this.ke.set(i * 2, j * 2 + 1, k3);
				this.ke.set(i * 2 + 1, j * 2, k2);
				this.ke.set(i * 2 + 1, j * 2 + 1, k4);
			}
		}
	}
}
