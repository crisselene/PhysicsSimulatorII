package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws {

	Vector2D centro; //direccion
	double g;//acerelacion fija, i.e gravedad
	
	
	public MovingTowardsFixedPoint(Vector2D centro, double acceleration) {
		super();
		this.centro = centro;
		this.g = acceleration;
	}

	@Override
	public void apply(List<Body> bs) {
		//dada una aceleracion fija(g) y una direccion la aceleracion es (-g* direccion)
		//la fuerza del cuerpo entonces es igual a masa por la aceleracion obtenida
		
		for (Body body : bs) {
			body.addForce(centro.minus(body.getPosition()).direction().scale(g*body.getMass()));
			/*Vector2D acelera_direction = centro.scale(-g);
			body.f = acelera_direction.scale(body.m);
			System.out.println(body);*/
		}
	}

	@Override
	public String toString() {
		return "Moving towards - " + centro + " with constant acceleration - " + g;
	}
	
	
	
}
