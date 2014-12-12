import node.*;
import load.*;
import element.*;
import constraint.*;
import visualization.*;

import matrix.*;

public class Main {
	public static void main(String[] args) {
		/*
		BeamNode n1 = new BeamNode(0, 0);
		BeamNode n2 = new BeamNode(1, 0);
		BeamNode n3 = new BeamNode(2, 0);
		
		n1.addConstraint(Constraint.Y);
		n1.addConstraint(Constraint.THETA);

		Load l1 = Load.Y;
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
		
		s.solve();*/
		
		Structure s = new Structure();
	
		Node nn1 = new Node(0, 1);
		Node nn2 = new Node(1, 1);
		Node nn3 = new Node(0, 0);
		Node nn4 = new Node(1, 0);
		Node nn5 = new Node(2, 0);
		
		Load l = Load.X;
		l.setValue(1);
		nn1.addLoad(l);

		nn3.addConstraint(Constraint.X);
		nn3.addConstraint(Constraint.Y);

		nn4.addConstraint(Constraint.X);
		nn4.addConstraint(Constraint.Y);
		
		nn5.addConstraint(Constraint.X);
		nn5.addConstraint(Constraint.Y);
		
		Element e1 = new TriangleElement(nn1, nn3, nn4, 1, 0.2); 
		Element e2 = new TriangleElement(nn2, nn4, nn5, 1, 0.2);
		Element e3 = new TriangleElement(nn4, nn2, nn1, 1, 0.2);

		s.addElement(e1);
		s.addElement(e2);
		s.addElement(e3);

		s.solve();
		
		Visualizer v = new Visualizer(400, 400);
		v.addElement(e1);
		v.addElement(e2);
		v.addElement(e3);
		v.show();
	}
}
