package visualization;

import node.*;
import element.*;

import matrix.Matrix;

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

	double xn;
	double xm;

	double yn;
	double ym;

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
		int m = 0;

		for (Element e:this.elements) {
			Matrix sigma = e.getStress();
			double v = Math.abs(sigma.get(m, 0));
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
				x[k] = this.getX(n.getX() + n.getU());
				y[k] = this.getY(n.getY() + n.getV());
				k++;
			}

			Matrix sigma = e.getStress();
			Color c = this.getColor(sigma.get(m, 0) / smax);

			g.setColor(c);
			g.fillPolygon(x, y, nnode);
			g.setColor(Color.black);
			g.drawPolygon(x, y, nnode);
		}
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

	protected int getX(double x) {
		double wx = this.xm - this.xn;
		double hy = this.ym - this.yn;
		double ratio = Math.min(this.width / wx, this.height / hy) * 0.8;
		
		return (int) (ratio * (x - xn) + 0.1 * this.width);
	}

	protected int getY(double y) {
		double wx = this.xm - this.xn;
		double hy = this.ym - this.yn;
		double ratio = Math.min(this.width / wx, this.height / hy) * 0.8;
	
		return (int) (this.height * 0.9 - ratio * (y - yn));
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

	public void show() {
		this.frame.setVisible(true);		
	}
}
