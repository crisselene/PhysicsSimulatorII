package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public class MassLossingBody extends Body{

	private double lossFactor; //numero entre 0 y 1. Factor de perdida de masa
	private double lossFrequency; //tiempo en segundos despues del cual el objecto pierde masa. Positivo
	private double contador;
	protected Vector2D f;//fuerza
	
	public MassLossingBody(String id, double m, Vector2D v, Vector2D p, double lossFactor, double lossFrequency) {
		super(id, m, v, p);
		this.lossFactor = lossFactor;
		this.lossFrequency = lossFrequency;
		//para que la fuerza no este en los parametros del constructor
		this.f = new Vector2D(0.0,0.0);
		contador = 0.0;
	}
	
	public double getLossFactor() {
		return lossFactor;
	}
	
	public double getLossFrequency() {
		return lossFrequency;
	}
	
	void move(double t) {
		super.move(t);
		
		//Losing mass if needed
		contador += t;
		if (contador >= lossFrequency) {
			contador = 0.0;
			m = m * (1 - lossFactor);
		}
	}
	
	@Override
	public JSONObject getState() {
		JSONObject jo1 = super.getState();
		jo1.put("lossFactor", getLossFactor());
		jo1.put("lossFrequency", getLossFrequency());
		return jo1;
	}

}
