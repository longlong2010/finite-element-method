package visualization;

import geometry.node.Node;
import geometry.node.Dof;
import geometry.node.StructuralNode;
import geometry.element.Element;
import geometry.element.StructuralElement;
import result.structure.Stress;

public class StructuralVisualizer extends AbstractVisualizer {

	protected Stress stress;

	public StructuralVisualizer(int w, int h) {
		super(w, h);
	}

	public boolean addElement(StructuralElement e) {
		return super.addElement(e);
	}

	@Override
	protected double getValue(Element e) {
		if (this.stress != null && e instanceof StructuralElement) {
			StructuralElement se = (StructuralElement) e;
			return se.getStress(this.stress);
		}
		if (this.dof != null) {
			return super.getValue(e);
		}
		return 0;
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

	@Override
	public void show(Dof d) {
		this.dof = d;
		this.stress = null;
		this.frame.setVisible(true);
	}

	public void show(Stress s) {
		this.stress = s;
		this.dof = null;
		this.frame.setVisible(true);
	}
}
