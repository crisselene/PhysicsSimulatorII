package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws>{

	public NoForceBuilder() {
		super("ng", "No force");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject jo) {
		// TODO Auto-generated method stub
			return new NoForce();
	}

	@Override
	protected JSONObject createData() {
		return new JSONObject();
	}
}
