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
		Visualizer v = new Visualizer(400, 400);

		Node[][] ns = new Node[11][];
		for (int i = 0; i < 11; i++) {
			ns[i] = new Node[21 - i];
			for (int j = 0; j < 21 - i; j++) {
				Node n = new Node(j * 0.1, i * 0.1);
				ns[i][j] = n;
			}
		}

		for (int i = 0; i < 10; i++) {
			int j = 0;
			for (j = 0; j < 19 - i; j++) {
				Node n1 = ns[i][j];
				Node n2 = ns[i][j + 1];
				Node n3 = ns[i + 1][j];
				Node n4 = ns[i + 1][j + 1];
				
				Element e1 = new TriangleElement(n1, n2, n3, 100, 0.2);
				Element e2 = new TriangleElement(n2, n3, n4, 100, 0.2);
				s.addElement(e1);
				s.addElement(e2);
			
				v.addElement(e1);
				v.addElement(e2);
			}

			Node n1 = ns[i][j];
			Node n2 = ns[i + 1][j];
			Node n3 = ns[i][j + 1];
		
			Element e3 = new TriangleElement(n1, n2, n3, 100, 0.2);
			
			s.addElement(e3);
			v.addElement(e3);
		
		}

		Load l = Load.X;
		l.setValue(1);
		ns[10][0].addLoad(l);

		ns[0][0].addConstraint(Constraint.X);
		ns[0][0].addConstraint(Constraint.Y);

		ns[0][10].addConstraint(Constraint.X);
		ns[0][10].addConstraint(Constraint.Y);

		ns[0][20].addConstraint(Constraint.X);
		ns[0][20].addConstraint(Constraint.Y);

		s.solve();

		v.show();
		/*
		Node nn1 = new Node(0, 1);
		Node nn2 = new Node(1, 1);
		Node nn3 = new Node(0, 0);
		Node nn4 = new Node(1, 0);
		Node nn5 = new Node(2, 0);
		Node nn6 = new Node(0.5, 0.5);
		Node nn7 = new Node(1.5, 0.5);

		Load l = Load.X;
		l.setValue(1);
		nn1.addLoad(l);

		nn3.addConstraint(Constraint.X);
		nn3.addConstraint(Constraint.Y);

		nn4.addConstraint(Constraint.X);
		nn4.addConstraint(Constraint.Y);
		
		nn5.addConstraint(Constraint.X);
		nn5.addConstraint(Constraint.Y);
		
		Element e1 = new TriangleElement(nn1, nn2, nn6, 1, 0.2); 
		Element e2 = new TriangleElement(nn2, nn4, nn6, 1, 0.2);
		Element e3 = new TriangleElement(nn1, nn3, nn6, 1, 0.2);
		Element e4 = new TriangleElement(nn4, nn3, nn6, 1, 0.2);

		Element e5 = new TriangleElement(nn2, nn4, nn7, 1, 0.2);
		Element e6 = new TriangleElement(nn5, nn7, nn4, 1, 0.2);

		s.addElement(e1);
		s.addElement(e2);
		s.addElement(e3);
		s.addElement(e4);
		s.addElement(e5);
		s.addElement(e6);

		s.solve();
		
		Visualizer v = new Visualizer(400, 400);
		v.addElement(e1);
		v.addElement(e2);
		v.addElement(e3);
		v.addElement(e4);
		v.addElement(e5);
		v.addElement(e6);
		v.show();
		*/
	}
}
