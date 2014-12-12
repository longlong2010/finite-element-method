package element;

import matrix.Matrix;
import java.util.ArrayList;

import node.Node;

public abstract class Element {
	protected Matrix ke;
	protected ArrayList<Node> nodes;	

	public Element() {
		this.nodes = new ArrayList<Node>();
	}

	public boolean addNode(Node n) {
		return this.nodes.contains(n) ? false : this.nodes.add(n);
	}

	public ArrayList<Node> getNodes() {
		return this.nodes;
	}

	public Matrix getKe() {
		return this.ke;
	}

	public abstract Matrix getStress();
}
