package geometry.element;
import org.la4j.Matrix;
import org.la4j.matrix.dense.Basic2DMatrix;

import java.util.ArrayList;

import geometry.node.Node;
import geometry.node.Dof;

public abstract class Element implements ElementInterface {
	protected Basic2DMatrix ke;
	protected ArrayList<Node> nodes;	

	public Element() {
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
		int nnode = this.getNodeNum();
		int ndof = 0;
		for (int i = 0; i < nnode; i++) {
			ndof += this.nodes.get(i).getDofNum();
		}
		return ndof;
	}
}
