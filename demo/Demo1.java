package demo;

import node.*;
import load.*;
import element.*;
import constraint.*;
import visualization.*;
import structure.*;

import matrix.*;

public class Demo1 {
	
	public static void main(String[] args) {
		Structure s = new Structure();
		Visualizer v = new Visualizer(600, 400);
		double nu = 0.2;
		double e = 200e6;
		double t = 0.01;
		
		int m = 22;
		int n = 6;

		Node[][] nodes = new Node[m][n];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				nodes[i][j] = new Node(i, j);
			}
		}

		Load lx = Load.X;
		lx.setValue(-100);

		for (int j = 0; j < n; j++) {
			nodes[0][j].addLoad(lx);
			nodes[m - 1][j].addConstraint(Constraint.X);
			nodes[m - 1][j].addConstraint(Constraint.Y);
		}

		for (int i = 0; i < m - 1; i++) {
			for (int j = 0; j < n - 1; j++) {
				if (i == m / 2  && j == n / 2 - 1) {
					continue;
				}

				Element e1 = new TriangleElement(nodes[i][j], nodes[i][j + 1], nodes[i + 1][j], e, t, nu);
				Element e2 = new TriangleElement(nodes[i][j + 1], nodes[i + 1][j + 1], nodes[i + 1][j], e, t, nu);
				s.addElement(e1);
				s.addElement(e2);

				v.addElement(e1);
				v.addElement(e2);
			}
		}

		s.solve();
		v.show(Visualizer.SIGMA_X);
	}
}
