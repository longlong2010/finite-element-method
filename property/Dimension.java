package property;

import java.util.TreeMap;

public class Dimension {

	protected TreeMap<DimensionProperty, Double> property;

	public Dimension() {
		this.property = new TreeMap<DimensionProperty, Double>();
	}

	public double getProperty(DimensionProperty p) {
		return this.property.get(p);
	}

	public void setProperty(DimensionProperty p, double v) {
		this.property.put(p, v);
	}
}
