package geometry.element;

import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.vector.dense.BasicVector;

import geometry.node.StructuralNode;
import geometry.node.Node;
import geometry.node.Dof;
import property.Material;
import property.Dimension;
import property.MaterialProperty;
import property.DimensionProperty;
import result.structure.Stress;
import load.Load;

import java.util.TreeSet;

public class TriangleStructuralElement extends TriangleElement implements StructuralElement {

	protected Matrix me;
	protected Matrix be;
	protected Matrix de;

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

		double delta = this.getJacobi().determinant() * 0.5;

		int ndof = this.getDofNum();
		int nnode = this.getNodeNum();

		this.me = new Basic2DMatrix(ndof, ndof);
		this.be = new Basic2DMatrix(3, ndof);
		this.de = new Basic2DMatrix(3, 3);

		double[] b = new double[nnode];
		double[] c = new double[nnode];
		for (int i = 0; i < nnode; i++) {
			b[i] = -(this.nodes.get((i + 2) % nnode).getY() - this.nodes.get((i + 1) % nnode).getY());
		}

		for (int i = 0; i < nnode; i++) {
			c[i] = this.nodes.get((i + 2) % nnode).getX() - this.nodes.get((i + 1) % nnode).getX();
		}
		
		double d = e * t / (4 * (1 - nu * nu) * Math.abs(delta));
		
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

		for (int i = 0; i < 3; i++) {
			int j = i * 2;
			double v1 = b[i] / (2 * delta);
			double v2 = c[i] / (2 * delta);
			this.be.set(0, j, v1);
			this.be.set(2, j, v2);
			this.be.set(1, j + 1, v2);
			this.be.set(2, j + 1, v1);
		}

		double d0 = e / (1 - nu * nu);
		this.de.set(0, 0, d0);
		this.de.set(1, 1, d0);
		this.de.set(2, 2, d0 * (1 - nu) / 2);
		this.de.set(0, 1, d0 * nu);
		this.de.set(1, 0, d0 * nu);

		for (int i = 0; i < nnode; i++) {
			for (int j = 0; j < nnode; j++) {
				double v = i == j ? Math.abs(delta) / 6 : Math.abs(delta) / 12;
				this.me.set(i * 2, j * 2, v * rho * t);
				this.me.set(i * 2 + 1, j * 2 + 1, v * rho * t);
			}
		}
	}

	public void addThermalLoad(double T) {
		double T0 = this.material.getProperty(MaterialProperty.T0);
		double alpha = this.material.getProperty(MaterialProperty.alpha);
		double t = this.dimension.getProperty(DimensionProperty.t);
		
		double delta = this.getJacobi().determinant() * 0.5;
		
		Vector p = this.be.transpose().multiply(this.de).multiply(new BasicVector(new double[]{1, 1, 0}));
		double v = alpha * (T - T0) * Math.abs(delta) * t;
		
		int k = 0;
		for (Node n:nodes) {
			TreeSet<Dof> dofs = n.getDofs();
			for (Dof d:dofs) {
				switch (d) {
					case X:
						n.addLoad(Load.X, p.get(k++) * v);
						break;
					case Y:
						n.addLoad(Load.Y, p.get(k++) * v);
						break;
				}
			}
		}
	}

	public double getStress(Stress s) {
		int ndof = this.getDofNum();
		Vector vdof = new BasicVector(ndof);

		int k = 0;
		for (Node n:nodes) {
			TreeSet<Dof> dofs = n.getDofs();
			for (Dof d:dofs) {
				vdof.set(k++, n.getValue(d));
			}
		}
		Vector v = this.de.multiply(this.be).multiply(vdof);
		return v.get(s.ordinal());
	}

	public Matrix getMe() {
		return this.me;
	}
}
