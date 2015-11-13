package geometry.element;

import org.la4j.Vector;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.vector.dense.BasicVector;

import geometry.node.StructuralNode;
import property.Material;
import property.Dimension;
import property.MaterialProperty;
import property.DimensionProperty;

public class TriangleStructuralElement extends TriangleElement {

	protected Basic2DMatrix me;
	protected Material material;
	protected Dimension dimension;
	
	public TriangleStructuralElement(StructuralNode n1, StructuralNode n2, StructuralNode n3, Material material, Dimension dimension) {
		super(n1, n2, n3);
		this.material = material;
		this.dimension = dimension;

		double rho = this.material.getProperty(MaterialProperty.rho);
		double e = this.material.getProperty(MaterialProperty.E);
		double nu = this.material.getProperty(MaterialProperty.nu);

		double t = this.dimension.getProperty(DimensionProperty.t);

		double delta = Math.abs(this.getJacobi().determinant()) * 0.5;

		int ndof = this.getDofNum();
		int nnode = this.getNodeNum();

		this.me = new Basic2DMatrix(ndof, ndof);
		
		double d = e * t / (4 * (1 - nu * nu) * delta);

		double[] b = new double[nnode];
		double[] c = new double[nnode];
		for (int i = 0; i < nnode; i++) {
			b[i] = -(this.nodes.get((i + 2) % nnode).getY() - this.nodes.get((i + 1) % nnode).getY());
		}

		for (int i = 0; i < nnode; i++) {
			c[i] = this.nodes.get((i + 2) % nnode).getX() - this.nodes.get((i + 1) % nnode).getX();
		}
		
		for (int i = 0; i < nnode; i++) {
			for (int j = 0; j < nnode; j++) {
				double k1 = d * (b[i] * b[j] + (1 - nu) * c[i] * c[j] / 2);
				double k2 = d * (nu * c[i] * b[j] + (1 - nu) * b[i] * c[j] / 2);
				double k3 = d * (nu * b[i] * c[j] + (1 - nu) * c[i] * b[j] / 2);
				double k4 = d * (c[i] * c[j] + (1 - nu) * b[i] * b[j] / 2);
				
				this.ke.set(i * 2, j * 2, k1);
				this.ke.set(i * 2, j * 2 + 1, k3);
				this.ke.set(i * 2 + 1, j * 2, k2);
				this.ke.set(i * 2 + 1, j * 2 + 1, k4);
			}
		}

		for (int i = 0; i < nnode; i++) {
			for (int j = 0; j < nnode; j++) {
				double v = i == j ? delta / 6 : delta / 12;
				this.me.set(i * 2, j * 2, v * rho * t);
				this.me.set(i * 2 + 1, j * 2 + 1, v * rho * t);
			}
		}
	}
}
