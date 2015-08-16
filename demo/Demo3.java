package demo;

import node.*;
import load.*;
import element.*;
import constraint.*;
import visualization.*;
import structure.*;

import matrix.*;

import java.io.*;
import java.util.Scanner;
import java.util.TreeMap;

public class Demo3 {

	public static void main(String[] args) {
		double nu = 0.2;
		double e = 200e6;
		double t = 0.01;
		double eps = 1e-5;
		Load lx = Load.X;
		lx.setValue(-100);

		try {
			BufferedReader reader = new BufferedReader(new FileReader("demo/demo3.msh"));
			TreeMap<Integer, Node> map = new TreeMap<Integer, Node>();
			Structure s = new Structure();
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
							Node node = new Node(x, y);
							if (Math.abs(x - 10.0) < eps) {
								node.addConstraint(Constraint.X);
								node.addConstraint(Constraint.Y);
							}
							if (Math.abs(x) < 1e-5) {
								node.addLoad(lx);
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

									Node n1 = map.get(nn1);
									Node n2 = map.get(nn2);
									Node n3 = map.get(nn3);
									Element element = new TriangleElement(n1, n2, n3, e, t, nu); 
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
			v.show(Visualizer.SIGMA_X);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
