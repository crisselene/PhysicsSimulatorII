package simulator.model;

import java.util.List;

public class NoForce implements ForceLaws {
	

	@Override
	public void apply(List<Body> bs) {
		// vacio, aceleracion fija

	}

	@Override
	public String toString() {
		return "No Force ";
	}
	
	

}
