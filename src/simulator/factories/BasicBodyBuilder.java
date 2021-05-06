package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Body;
import simulator.misc.Vector2D;

public class BasicBodyBuilder extends Builder<Body> {

	public BasicBodyBuilder() {
		super("basic", "BasicBodyBuilder");
	}

	@Override
	protected Body createTheInstance(JSONObject data) {
		double array[] = new double[2]; //recorrer los array de posicion, velocidad
		String id = data.getString("id");
		JSONArray ja = data.getJSONArray("p");
		obtener_vector(array, ja);
		
		Vector2D p = new Vector2D(array[0], array[1]);
		ja = data.getJSONArray("v");
		obtener_vector(array, ja);
		
		Vector2D v = new Vector2D(array[0], array[1]);
		double m = data.getDouble("m");	
		return new Body(id, m, v, p);
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
		
		return jo;
	}
}
