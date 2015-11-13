package property;

import java.util.TreeMap;

public class Material {

	protected TreeMap<MaterialProperty, Double> property;

	public Material() {
		this.property = new TreeMap<MaterialProperty, Double>();
	}

	public double getProperty(MaterialProperty p) {
		return this.property.get(p);
	}

	public void setProperty(MaterialProperty p, double v) {
		this.property.put(p, v);
	}
}
