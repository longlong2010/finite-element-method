package geometry.element;

import org.la4j.matrix.dense.Basic2DMatrix;

import geometry.node.Node;

public abstract class TriangleElement extends Element {
	
	public TriangleElement(Node n1, Node n2, Node n3) {
		super();
		this.addNode(n1);
		this.addNode(n2);
		this.addNode(n3);
		
		int nnode = this.nodes.size();
		int ndof = this.getDofNum();

		this.ke = new Basic2DMatrix(ndof, ndof);

	}

	protected Basic2DMatrix getJacobi() {
		Basic2DMatrix jacobi = new Basic2DMatrix(2, 2);
		Node n1 = this.nodes.get(0);
		Node n2 = this.nodes.get(1);
		Node n3 = this.nodes.get(2);

		jacobi.set(0, 0, n1.getX() - n3.getX());
		jacobi.set(0, 1, n1.getY() - n3.getY());
		jacobi.set(1, 0, n2.getX() - n3.getX());
		jacobi.set(1, 1, n2.getY() - n3.getY());

		return jacobi;
	}
}
