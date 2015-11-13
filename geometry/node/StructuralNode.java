package geometry.node;

public class StructuralNode extends Node {

	public StructuralNode(double x, double y) {
		super(x, y);

		this.dofs.add(Dof.X);
		this.dofs.add(Dof.Y);
	}
}
