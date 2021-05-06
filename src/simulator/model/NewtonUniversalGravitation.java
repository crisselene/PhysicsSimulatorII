package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {
	protected double G;
	
	public NewtonUniversalGravitation(double g) {
		super();
		this.G = g;
	}


	@Override
	public void apply(List<Body> bs) {
		//coger el jesimo y el iesimo y compararlos y i son iguales les anadimos la fuerza
		for (Body iesimo : bs) {
				for (Body jesimo : bs) {
					if (!jesimo.equals(iesimo)) {
						iesimo.addForce(forceNewton(iesimo, jesimo));
					}
			}
		}
	}

	private Vector2D forceNewton(Body iesimo, Body jesimo) {
		Vector2D delta = jesimo.getPosition().minus(iesimo.getPosition());
	    double dist = delta.magnitude();
	    double magnitude = (dist > 0) ? (G * iesimo.getMass() * jesimo.getMass()) / (dist * dist) : 0.0;
	    return delta.direction().scale(magnitude);
	}
	

	@Override
	public String toString() {
		return "Newton´s Universal Gravitation with G= " + G;
	}
	
	
}
