package element;

import node.Node;
public class TriangleElement extends Element {
	
	public TriangleElement(Node n1, Node n2, Node n3) {
		super();
		this.addNode(n1);
		this.addNode(n2);
		this.addNode(n3);
	}
}
