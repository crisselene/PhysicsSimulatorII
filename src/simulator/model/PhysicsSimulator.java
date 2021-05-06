package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {

	private double dt; //tiempo real de un paso.
	private ForceLaws ley;
	private List<Body> bs;
	private double tiempo = 0.0; //tiempo acumulado
	private List<SimulatorObserver> observers;
	
	public PhysicsSimulator(double dt, ForceLaws ley) throws IllegalArgumentException {
		if(dt > 0)
			this.dt = dt;
		else throw new IllegalArgumentException();
		
		if(ley != null)
			this.ley = ley;
		else throw new IllegalArgumentException();
		
		bs = new ArrayList<Body>();
		observers = new ArrayList<SimulatorObserver>();
	}
	
	public void advance() {
		for (Body body : bs) {
			body.resetForce();
		}
		ley.apply(bs);
		for(Body body : bs) {
			body.move(dt);
		}
		tiempo += dt;
		for (SimulatorObserver o: observers) {
			o.onAdvance(bs, tiempo);
		}
	}
	
	public void addBody(Body b) {
		if(bs.contains(b)) {
			throw new IllegalArgumentException();
		}
		bs.add(b);
		for (SimulatorObserver o: observers) {
			o.onBodyAdded(bs, b);
		}
	}
	
	public void reset() {
		bs.clear();
		tiempo = 0.0;
		for (SimulatorObserver o: observers) {
			o.onReset(bs, tiempo, dt, ley.toString());
		}
	}
	
	public void setDeltaTime(double dt) {
		if(dt > 0)
			this.dt = dt;
		else throw new IllegalArgumentException();
		for (SimulatorObserver o: observers) {
			o.onDeltaTimeChanged(dt);
		}
	}
	
	public void setForceLaws(ForceLaws ley) {
		if(ley != null)
			this.ley = ley;
		else throw new IllegalArgumentException();
		for (SimulatorObserver o: observers) {
			o.onForceLawsChanged(ley.toString());
		}
	}
	
	public void addObserver(SimulatorObserver o) {
		if(observers.contains(o)) {
			throw new IllegalArgumentException();
		}
		observers.add(o);
		//Solo al observador que se acaba de registrar
		o.onRegister(bs, tiempo, dt, ley.toString());
	}
	
	public JSONObject getState() {
		//Asegurarse de lo de la key (null) esta bien y si no como hacerlo.
		JSONObject jo1 = new JSONObject();
		JSONArray joAux = new JSONArray();
		
		jo1.put("time", tiempo);
		for(Body body: bs) {
			joAux.put(body.getState());
		}
		jo1.put("bodies", joAux);
		return jo1;
	}
	
	public String toString() {
		return getState().toString();
	}
}
