package demo;

import node.*;
import load.*;
import element.*;
import constraint.*;
import visualization.*;
import structure.*;

import matrix.*;

public class Demo4 {

	public static void main(String[] args) {
		Structure s = new Structure();
		Visualizer v = new Visualizer(600, 400);
		double e = 200e6;
		double a = 1e-4;

		Node n1 = new Node(0, -1);
		Node n2 = new Node(1, 0);
		Node n3 = new Node(0, 1);

		Element e1 = new TrussElement(n1, n2, e, a); 
		Element e2 = new TrussElement(n2, n3, e, a);

		Load lx = Load.X;
		lx.setValue(1);
		Load ly = Load.Y;
		ly.setValue(1);

		n2.addLoad(lx);
		n2.addLoad(ly);

		n1.addConstraint(Constraint.X);
		n1.addConstraint(Constraint.Y);
		n3.addConstraint(Constraint.X);
		n3.addConstraint(Constraint.Y);

		s.addElement(e1);
		s.addElement(e2);

		v.addElement(e1);
		v.addElement(e2);

		s.solve();
		v.show(Visualizer.SIGMA_X);

	}
}
