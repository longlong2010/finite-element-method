package geometry.element;

import org.la4j.Matrix;

import result.structure.Stress;

public interface StructuralElement extends Element {

	public Matrix getMe();
	public double getStress(Stress s);
}
