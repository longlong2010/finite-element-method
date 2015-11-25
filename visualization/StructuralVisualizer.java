package visualization;

import geometry.node.Node;
import geometry.node.Dof;
import geometry.node.StructuralNode;
import geometry.element.StructuralElement;

public class StructuralVisualizer extends Visualizer {

	public StructuralVisualizer(int w, int h) {
		super(w, h);
	}

	public boolean addElement(StructuralElement e) {
		return super.addElement(e);
	}

	@Override
	protected int getX(Node n) {
		double x = n instanceof StructuralNode ? n.getX() + n.getValue(Dof.X) : n.getX();
		return super.getX(x);
	}

	@Override
	protected int getY(Node n) {
		double y = n instanceof StructuralNode ? n.getY() + n.getValue(Dof.Y) : n.getY();
		return super.getY(y);
	}
}
