package node;

public class BeamNode extends Node {

	public BeamNode(double x, double y) {
		super(x, y);
		this.dofs.add(Dof.THETA);
		//hack
		this.dofs.remove(Dof.X);
	}
}
