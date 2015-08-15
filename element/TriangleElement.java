package element;

import node.Node;

import matrix.Matrix;

public class TriangleElement extends Element {
	
	public TriangleElement(Node n1, Node n2, Node n3, double e, double t, double nu) {
		super();
		this.addNode(n1);
		this.addNode(n2);
		this.addNode(n3);
		
		int nnode = this.nodes.size();
		
		this.s = new Matrix(nnode, nnode * 2);
		
		double delta = (n2.getX() * n3.getY() - n2.getY() * n3.getX() 
					 + n3.getX() * n1.getY() - n3.getY() * n1.getX()
					 + n1.getX() * n2.getY() - n1.getY() * n2.getX()) / 2;
	
		double d = e * t / (4 * (1 - nu * nu) * Math.abs(delta));
		
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
				double k1 = d * (b[i] * b[j] + (1 - nu) * c[i] * c[j] / 2);
				double k2 = d * (nu * c[i] * b[j] + (1 - nu) * b[i] * c[j] / 2);
				double k3 = d * (nu * b[i] * c[j] + (1 - nu) * c[i] * b[j] / 2);
				double k4 = d * (c[i] * c[j] + (1 - nu) * b[i] * b[j] / 2);
				
				this.ke.set(i * 2, j * 2, k1);
				this.ke.set(i * 2, j * 2 + 1, k3);
				this.ke.set(i * 2 + 1, j * 2, k2);
				this.ke.set(i * 2 + 1, j * 2 + 1, k4);
			}
		}
		
		d = e /  (2 * (1 - nu * nu) * delta);
		
		for (int i = 0; i < nnode; i++) {
			int j = 2 * i;
			this.s.set(0, j, d * b[i]);
			this.s.set(0, j + 1, d * nu * c[i]);
			this.s.set(1, j, d * nu * b[i]);
			this.s.set(1, j + 1, d * c[i]);
			this.s.set(2, j, d * (1 - nu) * c[i] / 2);
			this.s.set(2, j + 1, d * (1 - nu) * b[i] / 2);
		}
	}

	public Matrix getStress() {
		Matrix sigma = new Matrix(3, 1);
		
		int nnode = this.nodes.size();
		double[] d = new double[nnode * 2];

		int k = 0;
		for (Node n:this.nodes) {
			d[k++] = n.getU();
			d[k++] = n.getV();
		}
		
		int row = this.s.getRowSize();
		int col = this.s.getColumnSize();
		for (int i = 0; i < row; i++) {
			double stress = 0;
			for (int j = 0; j < col; j ++) {
				stress += s.get(i, j) * d[j];
			}
			sigma.set(i, 0, stress);
		}
		return sigma;
	}
}
