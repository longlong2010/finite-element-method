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
import java.util.Scanner;
import java.util.TreeMap;
import java.util.ArrayList;

public class Demo4 {

	public static void main(String[] args) {
		double nu = 0.2;
		double e = 200e6;
		double t = 0.01;
		double eps = 1e-5;
		double rho = 7.9e3;
		double T0 = 15;

		double kappa = 100;
		double alpha = 1.2e-5;

		Material material = new Material();
		Dimension dimension = new Dimension();
		material.setProperty(MaterialProperty.E, e);
		material.setProperty(MaterialProperty.nu, nu);
		material.setProperty(MaterialProperty.k, kappa);
		material.setProperty(MaterialProperty.rho, rho);
		material.setProperty(MaterialProperty.alpha, alpha);
		material.setProperty(MaterialProperty.T0, T0);

		dimension.setProperty(DimensionProperty.t, t);

		Constraint c1 = Constraint.X;
		Constraint c2 = Constraint.Y;
		Constraint c3 = Constraint.T;
		Load l1 = Load.X;
		Load l2 = Load.T;

		try {
			BufferedReader reader = new BufferedReader(new FileReader("demo/demo2.msh"));
			TreeMap<Integer, StructuralNode> smap = new TreeMap<Integer, StructuralNode>();
			TreeMap<Integer, ThermalNode> hmap = new TreeMap<Integer, ThermalNode>();
			Structure s = new Structure();
			HeatTransfer h = new HeatTransfer();
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
							Scanner in = new Scanner(line);
							int num = in.nextInt();
							double x = in.nextDouble();
							double y = in.nextDouble();
							StructuralNode snode = new StructuralNode(x, y);
							ThermalNode hnode = new ThermalNode(x, y);
							if (Math.abs(x) < eps) {
								snode.addLoad(l1, -10);
								hnode.addConstraint(c3, 10);
							}
							if (Math.abs(x - 10.0) < eps) {
								snode.addConstraint(c1, 0);
								snode.addConstraint(c2, 0);
								hnode.addConstraint(c3, 20);
							}
							smap.put(num, snode);
							hmap.put(num, hnode);
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

									StructuralNode sn1 = smap.get(nn1);
									StructuralNode sn2 = smap.get(nn2);
									StructuralNode sn3 = smap.get(nn3);
									
									ThermalNode hn1 = hmap.get(nn1);
									ThermalNode hn2 = hmap.get(nn2);
									ThermalNode hn3 = hmap.get(nn3);

									StructuralElement se = new TriangleStructuralElement(sn1, sn2, sn3, material, dimension); 
									ThermalElement he = new TriangleThermalElement(hn1, hn2, hn3, material, dimension); 
									s.addElement(se);
									h.addElement(he);
									v.addElement(se);
									break;
							}
						}
						n++;
						break;
				}
			}
			h.solve();
			ArrayList<Element> helements = h.getElements();
			ArrayList<Element> selements = s.getElements();

			for (int i = 0; i < helements.size(); i++) {
				double T = helements.get(i).getValue(Dof.T);
				StructuralElement se = (StructuralElement) selements.get(i);
				se.addThermalLoad(T);
			}
			s.solve();
			v.show(Stress.X);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
