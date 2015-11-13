package geometry.node;

public class ThermalNode extends Node {

	public ThermalNode(double x, double y) {
		super(x, y);

		this.dofs.add(Dof.T);

	}
}
