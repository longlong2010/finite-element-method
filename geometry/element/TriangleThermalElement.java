package geometry.element;

import org.la4j.Matrix;
import org.la4j.inversion.GaussJordanInverter;
import org.la4j.vector.dense.BasicVector;

import geometry.node.ThermalNode;
import property.Material;
import property.Dimension;
import property.MaterialProperty;
import property.DimensionProperty;

public class TriangleThermalElement extends TriangleElement {
	
	protected Material material;
	protected Dimension dimension;
	
	public TriangleThermalElement(ThermalNode n1, ThermalNode n2, ThermalNode n3, Material material, Dimension dimension) {
		super(n1, n2, n3);
		this.material = material;
		this.dimension = dimension;

		double kappa = this.material.getProperty(MaterialProperty.k);
		double t = this.dimension.getProperty(DimensionProperty.t);

		Matrix jacobi = this.getJacobi();
		Matrix ninvjacobi = new GaussJordanInverter(jacobi).inverse().transpose().multiplyByItsTranspose();

		double delta = Math.abs(jacobi.determinant()) * 0.5;

		int ndof = this.getDofNum();
		int nnode = this.getNodeNum();
		int dim = jacobi.rows();
		BasicVector[] vectors = new BasicVector[nnode];
		vectors[0] = new BasicVector(new double[]{1, 0});
		vectors[1] = new BasicVector(new double[]{0, 1});
		vectors[2] = new BasicVector(new double[]{-1, -1});

		for (int i = 0; i < ndof; i++) {
			for (int j = 0; j <= i; j++) {
				double v = 0;
				for (int k = 0; k < dim; k++) {
					for (int l = 0; l < dim; l++) {
						v += vectors[i].get(k) * vectors[j].get(l) * ninvjacobi.get(k, l);
					}
				}
				this.ke.set(i, j, v * delta * kappa * t);
				this.ke.set(j, i, v * delta * kappa * t);
			}
		}
	}
}
