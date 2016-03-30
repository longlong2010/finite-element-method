package demo;

import load.*;
import geometry.element.*;
import geometry.node.*;
import constraint.*;
import visualization.*;
import model.*;
import property.*;

//  Center displacement of Simply supported beam

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
		BeamNode n2 = new BeamNode(0.5, 0);
		BeamNode n3 = new BeamNode(1, 0);

		n1.addConstraint(Constraint.X);
		n1.addConstraint(Constraint.Y);
		
		n3.addConstraint(Constraint.X);
		n3.addConstraint(Constraint.Y);
		
		n2.addLoad(Load.Y, 1);

		BeamElement e1 = new BeamElement(n1, n2, material, dimension);
		BeamElement e2 = new BeamElement(n2, n3, material, dimension);
		s.addElement(e1);
		s.addElement(e2);

		s.solve();
		
		//	1 / (48 * P * L ^3)
		System.out.println(n2.getValue(Dof.Y));
	}
}
