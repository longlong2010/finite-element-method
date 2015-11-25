package demo;

import load.*;
import geometry.element.*;
import geometry.node.*;
import constraint.*;
import visualization.*;
import model.*;
import property.*;


public class Demo1 {
	
	public static void main(String[] args) {
		Structure s = new Structure();
		double nu = 0.2;
		double e = 200e6;
		double t = 0.01;
		double rho = 7.9e3;

		Material material = new Material();
		Dimension dimension = new Dimension();

		material.setProperty(MaterialProperty.E, e);
		material.setProperty(MaterialProperty.nu, nu);
		material.setProperty(MaterialProperty.rho, rho);

		dimension.setProperty(DimensionProperty.t, t);

		int m = 22;
		int n = 6;

		StructuralNode[][] nodes = new StructuralNode[m][n];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				nodes[i][j] = new StructuralNode(i, j);
			}
		}

		Load lx = Load.X;

		for (int j = 0; j < n; j++) {
			nodes[0][j].addLoad(lx, -100);
			nodes[m - 1][j].addConstraint(Constraint.X);
			nodes[m - 1][j].addConstraint(Constraint.Y);
		}

		for (int i = 0; i < m - 1; i++) {
			for (int j = 0; j < n - 1; j++) {
				if (i == m / 2  && j == n / 2 - 1) {
					continue;
				}

				StructuralElement e1 = new TriangleStructuralElement(nodes[i][j], nodes[i][j + 1], nodes[i + 1][j], material, dimension);
				StructuralElement e2 = new TriangleStructuralElement(nodes[i][j + 1], nodes[i + 1][j + 1], nodes[i + 1][j], material, dimension);
				s.addElement(e1);
				s.addElement(e2);

			}
		}

		s.solve();
	}
}
