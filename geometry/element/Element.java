package geometry.element;

import org.la4j.Matrix;

import geometry.node.Node;
import geometry.node.Dof;

import java.util.ArrayList;

public interface Element {
	public ArrayList<Node> getNodes();
	public Matrix getKe();
	public double getValue(Dof d);
	public int getNodeNum();
	public int getDofNum();
}
