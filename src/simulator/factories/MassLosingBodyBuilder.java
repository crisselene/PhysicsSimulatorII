package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MassLossingBody;

public class MassLosingBodyBuilder extends Builder<Body>{

	public MassLosingBodyBuilder() {
		super("mlb", "MassLosingBodyBuilder");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Body createTheInstance(JSONObject jo) {
		// TODO Auto-generated method stub
		double array[] = new double[2];
		String id = jo.getString("id");
		
		JSONArray ja = jo.getJSONArray("p"); 
		obtener_vector(array, ja); //metodo en el builder
		Vector2D p = new Vector2D(array[0], array[1]);
		
		ja = jo.getJSONArray("v");
		obtener_vector(array, ja);
		Vector2D v = new Vector2D(array[0], array[1]);
		
		//Tal y como se ve en el enunciado... la fuerza no se le deberia pasar al constructor
		//Por eso no hago aqui el vector F
		//Recordatiro: --> quitar fuerza del parametro del constructor
		double m = jo.getDouble("m");
		double freq = jo.getDouble("freq");
		double factor = jo.getDouble("factor");
		
		return new MassLossingBody(id, m, v, p, factor, freq);
	}

	
	@Override
	protected JSONObject createData() {
		JSONObject jo = new JSONObject();
		
		jo.put("id", "identifier");
		
		JSONArray p = new JSONArray();
		p.put(0, "positionX");
		p.put(1, "positionY");
		jo.put("p", p);
		
		JSONArray v = new JSONArray();
		v.put(0, "velocityX");
		v.put(1, "volocityY");
		jo.put("v", v);
		
		jo.put("m", "mass");
		jo.put("freq", "frequency");
		jo.put("factor", "factor");
		
		return jo;
	}
	
}
