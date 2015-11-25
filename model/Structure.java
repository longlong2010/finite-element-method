package model;

import org.la4j.Matrix;
import org.la4j.matrix.sparse.CCSMatrix;

import geometry.element.Element;
import geometry.element.StructuralElement;

public class Structure extends Model {

	protected CCSMatrix M;

	public boolean addElement(StructuralElement e) {
		return super.addElement(e);
	}

	@Override
	protected void init() {
		super.init();
		int ndof = this.getDofNum();

		this.M = new CCSMatrix(ndof, ndof);
	}

	@Override
	protected void integrate(int[] map, Element e) {
		super.integrate(map, e);
		StructuralElement se = (StructuralElement) e;

		Matrix me = se.getMe();
		int size = se.getDofNum();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int mi = map[i];
				int mj = map[j];
				this.M.set(mi, mj, this.M.get(mi, mj) + me.get(i, j));
			}
		}
	}
}
