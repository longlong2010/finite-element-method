package element;

import node.Node;
import node.BeamNode;
import matrix.Matrix;

public class BeamElement extends Element {
	
	protected double ei;
	protected double l; 

	public BeamElement(BeamNode n1, BeamNode n2, double ei) {
		super();
		
		this.addNode(n1);
		this.addNode(n2);
		this.ei = ei;
		this.ke = new Matrix(4, 4);
			
		double dx = n2.getX() - n1.getX();
		double dy = n2.getY() - n1.getY();
		double l = Math.sqrt(dx * dx + dy * dy);
		this.l = l;
		double theta = Math.atan(dy / dx);
		
		double l2 = l * l;
		double l3 = l * l * l;

		this.ke.set(0, 0, 12 * ei / l3);
		this.ke.set(0, 1, 6 * ei/ l2);
		this.ke.set(0, 2, -12 * ei / l3);
		this.ke.set(0, 3, 6 * ei / l2);

		this.ke.set(1, 0, 6 * ei / l2);
		this.ke.set(1, 1, 4 * ei / l);
		this.ke.set(1, 2, -6 * ei / l2);
		this.ke.set(1, 3, 2 * ei / l);
		
		this.ke.set(2, 0, -12 * ei / l3);
		this.ke.set(2, 1, -6 * ei / l2);
		this.ke.set(2, 2, 12 * ei / l3);
		this.ke.set(2, 3, -6 * ei / l2);
		
		this.ke.set(3, 0, 6 * ei / l2);
		this.ke.set(3, 1, 2 * ei / l);
		this.ke.set(3, 2, -6 * ei / l2);
		this.ke.set(3, 3, 4 * ei / l);
		
	}

	public Matrix getStress() {
		Matrix sigma = new Matrix(1, 1);
			
		int nnode = this.nodes.size();
		double[] d = new double[nnode * 2];
		
		int k = 0;
		for (Node n:this.nodes) {
			BeamNode bn = (BeamNode) n;
			d[k++] = bn.getV();
			d[k++] = bn.getTheta();
		}
		
		return sigma;
	}
}
