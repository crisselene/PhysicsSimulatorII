package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {

	public MovingTowardsFixedPointBuilder() {
		super("mtfp", "Moving towards a fixed point");
	}
	@Override
	protected ForceLaws createTheInstance(JSONObject jo) {
		int array[] = new int[2];

		Vector2D c = jo.has("centro") ? obtener_c(jo, array, jo.getJSONArray("centro")) : new Vector2D(0,0);
		double g = jo.has("g") ? jo.getDouble("g") : 9.81;
		
		return new MovingTowardsFixedPoint(c, g);
	}
	
	private Vector2D obtener_c(JSONObject jo, int[] array, JSONArray ja) {
		if(ja != null) {
			for(int i = 0; i < ja.length(); i++) {
				array[i] = ja.getInt(i);
			}	
		}
		return new Vector2D(array[0], array[1]);
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject jo = new JSONObject();
		
		JSONArray c = new JSONArray();
		c.put(0, "positionX");
		c.put(1, "positionY");
		//jo.put("c", c);
		jo.put("c", "the point towards which bodies move (a json list of 2 numbers, e.g., [100.0, 50.0]");
		
		jo.put("g", "the length of the acceleration vector (a number) ");
		
		return jo;
	}
}
