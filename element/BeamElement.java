package element;

import node.BeamNode;
import matrix.Matrix;

public class BeamElement extends Element {
	
	protected double ei;

	public BeamElement(BeamNode n1, BeamNode n2, double ei) {
		super();
		
		this.addNode(n1);
		this.addNode(n2);
		this.ei = ei;
		this.ke = new Matrix(4, 4);
			
		double dx = n2.getX() - n1.getX();
		double dy = n2.getY() - n1.getY();
		double l = Math.sqrt(dx * dx + dy * dy);

		double theta = Math.atan(dy / dx);

		this.ke.set(0, 0, 12 * ei);
		this.ke.set(0, 1, 6 * l * ei);
		this.ke.set(0, 2, -12 * ei);
		this.ke.set(0, 3, 6 * l * ei);

		this.ke.set(1, 0, 6 * l * ei);
		this.ke.set(1, 1, 4 * l * l * ei);
		this.ke.set(1, 2, -6 * l * ei);
		this.ke.set(1, 3, 2 * l * l * ei);
		
		this.ke.set(2, 0, -12 * ei);
		this.ke.set(2, 1, -6 * l * ei);
		this.ke.set(2, 2, 12 * ei);
		this.ke.set(2, 3, -6 * l * ei);
		
		this.ke.set(3, 0, 6 * l * ei);
		this.ke.set(3, 1, 2 * l * l * ei);
		this.ke.set(3, 2, -6 * l * ei);
		this.ke.set(3, 3, 4 * l * l * ei);
	
	}
}
