package demo;

import node.*;
import load.*;
import element.*;
import constraint.*;
import visualization.*;
import structure.*;

import matrix.*;

public class Demo2 {

	public static void main(String[] args) {
		
		Structure s = new Structure();
		double nu = 0.2;
		double rho = 7800;
		double e = 200e6;
		double t = 0.01;
		double g = -9.8;
		double q = 1000 * t;

		Node n1, n2, n3, n4, n5;
		
		n1 = new Node(0, 1);
		n2 = new Node(1, 1);
		n3 = new Node(0, 0);
		n4 = new Node(1, 0);
		n5 = new Node(2, 0);

		n3.addConstraint(Constraint.X);
		n3.addConstraint(Constraint.Y);
		
		n4.addConstraint(Constraint.X);
		n4.addConstraint(Constraint.Y);
		
		n5.addConstraint(Constraint.X);
		n5.addConstraint(Constraint.Y);
		
		Load l1 = Load.X;
		l1.setValue(q);
		n1.addLoad(l1);
		
		Load l2 = Load.Y;
		l2.setValue(rho * 0.5 * t * g / 3);
		n3.addLoad(l2);
		n5.addLoad(l2);

		Load l3 = Load.Y;
		l3.setValue(rho * 0.5 * t * g * 2 / 3);
		n1.addLoad(l3);
		n2.addLoad(l3);
		
		Load l4 = Load.Y;
		l4.setValue(rho * 0.5 * t * g);
		n4.addLoad(l4);
		
		Element e1, e2, e3;
		e1 = new TriangleElement(n1, n3, n4, e, t, nu);
		e2 = new TriangleElement(n2, n4, n5, e, t, nu);
		e3 = new TriangleElement(n1, n2, n4, e, t, nu);

		s.addElement(e1);
		s.addElement(e2);
		s.addElement(e3);

		s.solve();

		Visualizer v = new Visualizer(400, 400);
		v.addElement(e1);
		v.addElement(e2);
		v.addElement(e3);
		v.show(Visualizer.SIGMA_X);
	}
}
