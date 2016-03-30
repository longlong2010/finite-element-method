package demo;

import geometry.node.*;
import geometry.element.*;
import load.*;
import constraint.*;
import visualization.*;
import model.*;
import property.*;

import java.io.*;
import java.util.Scanner;
import java.util.TreeMap;

public class Demo3 {

	public static void main(String[] args) {
		double nu = 0.2;
		double e = 200e9;
		double t = 0.01;
		double eps = 1e-5;

		double kappa = 100;

		Material material = new Material();
		Dimension dimension = new Dimension();
		material.setProperty(MaterialProperty.E, e);
		material.setProperty(MaterialProperty.nu, nu);
		material.setProperty(MaterialProperty.k, kappa);

		dimension.setProperty(DimensionProperty.t, t);

		Constraint c1 = Constraint.T;
		Constraint c2 = Constraint.T;
		Load l = Load.T;

		try {
			BufferedReader reader = new BufferedReader(new FileReader("demo/demo2.msh"));
			TreeMap<Integer, ThermalNode> map = new TreeMap<Integer, ThermalNode>();
			HeatTransfer ht = new HeatTransfer();
			Visualizer v = new Visualizer(600, 400);

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
							Scanner in = new Scanner(line);
							int num = in.nextInt();
							double x = in.nextDouble();
							double y = in.nextDouble();
							ThermalNode node = new ThermalNode(x, y);
							if (Math.abs(x) < eps) {
								node.addConstraint(c1, -20);
							}
							if (Math.abs(x - 10.0) < eps) {
								node.addConstraint(c2, 20);
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

									ThermalNode n1 = map.get(nn1);
									ThermalNode n2 = map.get(nn2);
									ThermalNode n3 = map.get(nn3);
									ThermalElement element = new TriangleThermalElement(n1, n2, n3, material, dimension); 
									ht.addElement(element);
									v.addElement(element);
									break;
							}
						}
						n++;
						break;
				}
			}
			ht.solve();
			v.show(Dof.T);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
