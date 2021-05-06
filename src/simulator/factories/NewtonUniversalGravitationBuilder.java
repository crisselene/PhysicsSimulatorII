package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws>{

	public NewtonUniversalGravitationBuilder() {
		super("nlug", "Newton´s law of universal gravitation");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject jo) {
		// TODO Auto-generated method stub
		double G = jo.has("G") ? jo.getDouble("G") : 6.67E-11;
		return new NewtonUniversalGravitation(G);
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject jo = new JSONObject();
		jo.put("G", "the gravitational constant (a number) ");
		return jo;
	}
}
