package visualization;

import geometry.node.Node;
import geometry.node.Dof;
import geometry.element.Element;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

import java.util.ArrayList;

public class Visualizer {
	
	protected JFrame frame;
	protected JPanel canvas;

	protected ArrayList<Element> elements;

	protected int width;
	protected int height;

	protected double xn;
	protected double xm;

	protected double yn;
	protected double ym;

	protected Dof dof;

	public Visualizer(int w, int h) {
		this.width = w;
		this.height = h;

		this.frame = new JFrame();
		this.frame.setSize(w, h);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.canvas = new JPanel() {
			@Override
			public void paint(Graphics g) {
				paintElements(g);
			}
		};

		this.frame.add(this.canvas);
		
		this.elements = new ArrayList<Element>();
	}

	protected void paintElements(Graphics g) {
		double smax = 0;

		for (Element e:this.elements) {
			double v = Math.abs(this.getValue(e));
			if (v > smax) {
				smax = v;
			}
		}

		for (Element e:this.elements) {
			int nnode = e.getNodes().size();
			int[] x = new int[nnode];
			int[] y = new int[nnode];
			
			int k = 0;
			for (Node n:e.getNodes()) {
				x[k] = this.getX(n);
				y[k] = this.getY(n);
				k++;
			}

			Color c = this.getColor(this.getValue(e) / smax);

			g.setColor(c);
			g.fillPolygon(x, y, nnode);
			g.setColor(Color.black);
			g.drawPolygon(x, y, nnode);
		}
	}

	protected double getValue(Element e) {
		return e.getValue(this.dof);
	}

	public boolean addElement(Element e) {
		boolean rc = this.elements.contains(e) ? false : this.elements.add(e);
		if (rc) {
			for (Node n:e.getNodes()) {
				double x = n.getX();
				if (x > this.xm) {
					this.xm = x;
				}
				if (x < this.xn) {
					this.xn = x;
				}

				double y = n.getY();
				if (y > this.ym) {
					this.ym = y;
				}
				if (y < this.yn) {
					this.yn = y;
				}
			}
		}
		return rc;
	}

	protected int getX(Node n) {
		return this.getX(n.getX());
	}

	protected int getX(double x) {
		double wx = this.xm - this.xn;
		double hy = this.ym - this.yn;
		double ratio = Math.min(this.getWidth() / wx, this.getHeight() / hy) * 0.8;
		
		return (int) (ratio * (x - xn) + 0.1 * this.getWidth());
	}

	protected int getY(Node n) {
		return this.getY(n.getY());
	}

	protected int getY(double y) {
		double wx = this.xm - this.xn;
		double hy = this.ym - this.yn;
		double ratio = Math.min(this.getWidth() / wx, this.getHeight() / hy) * 0.8;
	
		return (int) (this.getHeight() * 0.9 - ratio * (y - yn));
	}

	protected Color getColor(double ratio) {
		if (Math.abs(ratio) > 1) {
			ratio = ratio / Math.abs(ratio);
		}
		double r, g, b;
		if (ratio >= -1 && ratio < -0.5) {
			b = 1;
			r = 0;
			g = (1 + ratio) * 2;
		} else if (ratio >= -0.5 && ratio < 0) {
			g = 1;
			r = 0;
			b = -ratio * 2;
		} else if (ratio >= 0 && ratio < 0.5) {
			g = 1;
			b = 0;
			r = ratio * 2;
		} else {
			r = 1;
			b = 0;
			g = (1 - ratio) * 2;
		}
		return new Color((float) r, (float) g, (float) b);
	}

	public void show(Dof d) {
		this.dof = d;
		this.frame.setVisible(true);		
	}

	protected int getHeight() {
		return this.frame.getHeight();
	}

	protected int getWidth() {
		return this.frame.getWidth();
	}
}
