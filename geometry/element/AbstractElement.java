package geometry.element;
import org.la4j.Matrix;
import org.la4j.matrix.dense.Basic2DMatrix;

import java.util.ArrayList;

import geometry.node.Node;
import geometry.node.Dof;

public abstract class AbstractElement implements Element {
	protected Basic2DMatrix ke;
	protected ArrayList<Node> nodes;	

	public AbstractElement() {
		this.nodes = new ArrayList<Node>();
	}

	protected boolean addNode(Node n) {
		return this.nodes.contains(n) ? false : this.nodes.add(n);
	}

	public ArrayList<Node> getNodes() {
		return this.nodes;
	}

	public Matrix getKe() {
		return this.ke;
	}

	public double getValue(Dof d) {
		double v = 0;
		int k = 0;
		for (Node n:nodes) {
			v += n.getValue(d);
			k++;
		}
		return v / k;
	}

	public int getNodeNum() {
		return this.nodes.size();
	}

	public int getDofNum() {
		int ndof = 0;
		for (Node n:nodes) {
			ndof += n.getDofNum();
		}
		return ndof;
	}
}
