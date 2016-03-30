package demo;

import load.*;
import geometry.element.*;
import geometry.node.*;
import constraint.*;
import visualization.*;
import model.*;
import property.*;

public class Demo5 {

	public static void main(String[] args) {
		Structure s = new Structure();
		double e = 1;
		double a = 1;
		double I = 1;
		double rho = 1;

		Material material = new Material();
		Dimension dimension = new Dimension();

		material.setProperty(MaterialProperty.E, e);
		material.setProperty(MaterialProperty.rho, rho);

		dimension.setProperty(DimensionProperty.A, a);
		dimension.setProperty(DimensionProperty.I, I);

		BeamNode n1 = new BeamNode(0, 0);
		BeamNode n2 = new BeamNode(1, 1);
		n1.addConstraint(Constraint.X);
		n1.addConstraint(Constraint.Y);
		n1.addConstraint(Constraint.THETA);
		
		n2.addLoad(Load.Y, 1);
		n2.addLoad(Load.X, 1);

		BeamElement e1 = new BeamElement(n1, n2, material, dimension);
		s.addElement(e1);
		s.solve();
		System.out.println(n2.getValue(Dof.THETA));
	}
}
