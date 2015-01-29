package element;

import matrix.Matrix;

public class TrussElement extends Element {
	
	protected double ea;
	protected double l;

	public TrussElement(Node n1, Node n2, double ea) {
		super();
		
		this.addNode(n1);
		this.addNode(n2);
		this.ea = ea;
		this.ke = new Matrix(2, 2);


		double dx = n2.getX() - n1.getX();
		double dy = n2.getY() - n1.getY();
		double l = Math.sqrt(dx * dx + dy * dy);
		this.l = l;
		double theta = Math.atan(dy / dx);

		this.ke.set(0, 0, ea / l);
		this.ke.set(0, 1, -ea / l);
		this.ke.set(1, 0, ea / l);
		this.ke.set(1, 1, ea / l);
	}
	
	public Matrix getStress() {
		return new Matrix();
	}
}
