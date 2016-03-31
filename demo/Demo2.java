package demo;

import geometry.node.*;
import geometry.element.*;
import load.*;
import constraint.*;
import visualization.*;
import model.*;
import property.*;
import result.structure.*;

import java.io.*;
import java.util.Locale;
import java.util.Scanner;
import java.util.TreeMap;

public class Demo2 {

	public static void main(String[] args) {
		double nu = 0.2;
		double e = 200e9;
		double t = 0.01;
		double eps = 1e-5;
		double rho = 7.9e3;

		double kappa = 100;

		Material material = new Material();
		Dimension dimension = new Dimension();
		material.setProperty(MaterialProperty.E, e);
		material.setProperty(MaterialProperty.nu, nu);
		material.setProperty(MaterialProperty.k, kappa);
		material.setProperty(MaterialProperty.rho, rho);

		dimension.setProperty(DimensionProperty.t, t);

		Constraint c1 = Constraint.X;
		Constraint c2 = Constraint.Y;
		Load l = Load.X;

		try {
			BufferedReader reader = new BufferedReader(new FileReader("demo/demo2.msh"));
			TreeMap<Integer, StructuralNode> map = new TreeMap<Integer, StructuralNode>();
			Structure s = new Structure();
			StructuralVisualizer v = new StructuralVisualizer(600, 400);

			String line;
			int n = 0;
			String tag = "";
			while ((line = reader.readLine()) != null) {
				switch (line) {
					case "$Nodes":
					case "$EndNodes":
					case "$Elements":
					case "$EndElements":
						tag = line;
						n = 0;
						break;
					default:
				}
				switch (tag) {
					case "$Nodes":
						if (n > 1) {
							Scanner in = new Scanner(line).useLocale(Locale.US);
							int num = in.nextInt();
							double x = in.nextDouble();
							double y = in.nextDouble();
							StructuralNode node = new StructuralNode(x, y);
							if (Math.abs(x) < eps) {
								node.addLoad(l, -10);

							}
							if (Math.abs(x - 10.0) < eps) {
								node.addConstraint(c1, 0);
								node.addConstraint(c2, 0);
							}
							map.put(num, node);
						}
						n++;
						break;
					case "$Elements":
						if (n > 1) {
							Scanner in = new Scanner(line);
							int num = in.nextInt();
							int type = in.nextInt();

							int ntags = in.nextInt();
							for (int i = 0; i < ntags; i++) {
								in.nextInt();
							}
							switch (type) {
								case 2:
									int nn1 = in.nextInt();
									int nn2 = in.nextInt();
									int nn3 = in.nextInt();

									StructuralNode n1 = map.get(nn1);
									StructuralNode n2 = map.get(nn2);
									StructuralNode n3 = map.get(nn3);
									StructuralElement element = new TriangleStructuralElement(n1, n2, n3, material, dimension); 
									s.addElement(element);
									v.addElement(element);
									break;
							}
						}
						n++;
						break;
				}
			}
			s.solve();
			v.show(Stress.X);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
