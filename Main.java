import geometry.element.TriangleThermalElement;
import geometry.node.ThermalNode;
import model.HeatTransfer;
import constraint.Constraint;
import property.*;

public class Main {
	public static void main(String[] args) {
		ThermalNode n1, n2, n3, n4;
		n1 = new ThermalNode(0, 0);
		n2 = new ThermalNode(1, 0);
		n3 = new ThermalNode(0, 1);
		n4 = new ThermalNode(1, 1);

		Constraint c = Constraint.T;

		n1.addConstraint(c, 10);
		n3.addConstraint(c, 10);

		Material material = new Material();
		material.setProperty(MaterialProperty.k, 1);
		Dimension dimension = new Dimension();
		dimension.setProperty(DimensionProperty.t, 1);

		TriangleThermalElement e1 = new TriangleThermalElement(n1, n2, n3, material, dimension);
		TriangleThermalElement e2 = new TriangleThermalElement(n2, n3, n4, material, dimension);

		HeatTransfer ht = new HeatTransfer();
		ht.addElement(e1);
		ht.addElement(e2);
		ht.solve();
	}
}
