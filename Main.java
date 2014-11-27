import element.*;
import constraint.*;
import node.*;
import matrix.*;

public class Main {
	public static void main(String[] args) {
		Node n1 = new Node(0, 0);
		Node n2 = new Node(1, 0);
		Node n3 = new Node(2, 0);
		n1.addConstraint(Constraint.Y);
		n1.addConstraint(Constraint.THETA);

		n3.addConstraint(Constraint.Y);
		n3.addConstraint(Constraint.THETA);
		
		Element e1 = new BeamElement(n1, n2, 2);
		Element e2 = new BeamElement(n2, n3, 1);
		
		Structure s = new Structure();				
		s.addElement(e1);
		s.addElement(e2);
		
		Matrix b = new Matrix(6, 1);
		b.set(0, 0, 0);
		b.set(1, 0, 0);
		b.set(2, 0, -0.5);
		b.set(3, 0, 1.125);
		b.set(4, 0, 0);
		b.set(5, 0, 0);
		
		s.solve(b);
	}
}
