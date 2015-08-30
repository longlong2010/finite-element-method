package element;

import java.util.ArrayList;

import node.Node;
import matrix.Matrix;

public class TrussElement extends Element {
	
	protected double e;
	protected double a;
	protected double l;
	protected double theta;

	public TrussElement(Node n1, Node n2, double e, double a) {
		super();
		
		this.addNode(n1);
		this.addNode(n2);
		this.e = e;
		this.a = a;
		this.ke = new Matrix(4, 4);

		double ea = e * a;

		double dx = n2.getX() - n1.getX();
		double dy = n2.getY() - n1.getY();
		double l = Math.sqrt(dx * dx + dy * dy);
		this.l = l;
		this.theta = Math.atan(dy / dx);
		double s = Math.sin(theta);
		double c = Math.cos(theta);
		
		this.ke.set(0, 0, ea / l * (c * c));
		this.ke.set(0, 1, ea / l * (c * s));
		this.ke.set(0, 2, -ea / l * (c * c));
		this.ke.set(0, 3, -ea / l * (c * s));

		this.ke.set(1, 0, ea / l * (c * s));
		this.ke.set(1, 1, ea / l * (s * s));
		this.ke.set(1, 2, -ea / l * (c * s));
		this.ke.set(1, 3, -ea / l * (s * s));
		
		this.ke.set(2, 0, -ea / l * (c * c));
		this.ke.set(2, 1, -ea / l * (c * s));
		this.ke.set(2, 2, ea / l * (c * c));
		this.ke.set(2, 3, ea / l * (c * s));

		this.ke.set(3, 0, -ea / l * (c * s));
		this.ke.set(3, 1, -ea / l * (s * s));
		this.ke.set(3, 2, ea / l * (c * s));
		this.ke.set(3, 3, ea / l * (s * s));

	}
	
	public Matrix getStress() {
		Matrix sigma = new Matrix(1, 1);
		double s = Math.sin(theta);	
		double c = Math.cos(theta);	
		ArrayList<Node> nodes = getNodes();
		Node n1 = nodes.get(0);
		Node n2 = nodes.get(1);
		double v = (-c * n1.getU() - s * n1.getV() + c * n2.getU() + s * n2.getV()) * e / l;
		sigma.set(0, 0, v);
		return sigma;
	}
}
