package geometry.node;

public class BeamNode extends StructuralNode {

	public BeamNode(double x, double y) {
		super(x, y);

		this.dofs.add(Dof.THETA);
	}
}
