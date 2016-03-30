package geometry.element;

import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.vector.dense.BasicVector;

import geometry.node.BeamNode;
import geometry.node.Node;
import geometry.node.Dof;
import property.Material;
import property.Dimension;
import property.MaterialProperty;
import property.DimensionProperty;
import result.structure.Stress;
import load.Load;

public class BeamElement extends AbstractElement implements StructuralElement {
	
	protected Matrix me;
	
	protected Material material;
	protected Dimension dimension;

	public BeamElement(BeamNode n1, BeamNode n2, Material material, Dimension dimension) {
		
		this.addNode(n1);
		this.addNode(n2);

		this.material = material;
		this.dimension = dimension;
		
		double rho = this.material.getProperty(MaterialProperty.rho);
		double e = this.material.getProperty(MaterialProperty.E);

		double a = this.dimension.getProperty(DimensionProperty.A);
		double I = this.dimension.getProperty(DimensionProperty.I);
	
		int ndof = this.getDofNum();
		int nnode = this.getNodeNum();

		this.ke = new Basic2DMatrix(ndof, ndof);
		this.me = new Basic2DMatrix(ndof, ndof);

		double dx = n2.getX() - n1.getX();
		double dy = n2.getY() - n1.getY();

		double l = Math.sqrt(dx * dx + dy * dy);
		double c = dx / l;
		double s = dy / l;

		double l2 = l * l;
		double l3 = l * l * l;
		
		this.ke.set(0, 0, e * a / l);
		this.ke.set(0, 3, -e * a / l);
		this.ke.set(3, 0, -e * a / l);
		this.ke.set(3, 3, e * a / l);

		this.ke.set(1, 1, 12 * e * I / l3);
		this.ke.set(1, 2, 6 * e * I / l2);
		this.ke.set(1, 4, -12 * e * I / l3);
		this.ke.set(1, 5, 6 * e * I / l2);

		this.ke.set(2, 1, 6 * e * I / l2);
		this.ke.set(2, 2, 4 * e * I / l);
		this.ke.set(2, 4, -6 * e * I / l2);
		this.ke.set(2, 5, 2 * e * I / l);
		
		this.ke.set(4, 1, -12 * e * I / l3);
		this.ke.set(4, 2, -6 * e * I / l2);
		this.ke.set(4, 4, 12 * e * I / l3);
		this.ke.set(4, 5, -6 * e * I / l2);
		
		this.ke.set(5, 1, 6 * e * I / l2);
		this.ke.set(5, 2, 2 * e * I / l);
		this.ke.set(5, 4, -6 * e * I / l2);
		this.ke.set(5, 5, 4 * e * I / l);

		Basic2DMatrix Q = new Basic2DMatrix(ndof, ndof);
		for (int i = 0; i < nnode; i++) {
			int j = i * 3;
			Q.set(j, j, c);
			Q.set(j, j + 1, s);
			Q.set(j + 1, j, -s);
			Q.set(j + 1, j + 1, c);
			Q.set(j + 2, j + 2, 1);
		}
		this.ke = Q.transpose().multiply(this.ke).multiply(Q);
	}

	@Override
	public void addThermalLoad(double T) {

	}
	
	@Override
	public double getStress(Stress s) {
		return 0;
	}
	
	public Matrix getMe() {
		return this.me;
	}
}
