import node.*;
import load.*;
import element.*;
import constraint.*;

import matrix.*;

public class Main {
	public static void main(String[] args) {
		Node n1 = new Node(0, 0);
		Node n2 = new Node(1, 0);
		Node n3 = new Node(2, 0);
		
		n1.addConstraint(Constraint.Y);
		n1.addConstraint(Constraint.THETA);

		Load l1 = Load.Q;
		Load l2 = Load.M;

		l1.setValue(-0.5);
		l2.setValue(1.125);

		n2.addLoad(l1);
		n2.addLoad(l2);

		n3.addConstraint(Constraint.Y);
		n3.addConstraint(Constraint.THETA);
		
		Element e1 = new BeamElement(n1, n2, 2);
		Element e2 = new BeamElement(n2, n3, 1);
		
		Structure s = new Structure();				
		s.addElement(e1);
		s.addElement(e2);
		
		s.solve();
		/*
		Node n1 = new Node(0, 0);
		Node n2 = new Node(1, 0);
		Node n3 = new Node(0, 1);

		Element e1 = new TriangleElement(n1, n2, n3, 1, 0); 
		
		Structure s = new Structure();
		s.addElement(e1);
		
		Load l1 = Load.N;
		l1.setValue(1);
		Load l2 = Load.N;
		l2.setValue(2);
		System.out.println(l2.getValue());*/
	}
}
