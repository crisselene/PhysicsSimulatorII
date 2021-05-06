package simulator.factories;

import org.json.JSONObject;

import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;

public class EpsilonEqualStatesBuilder extends Builder<StateComparator> {

	public EpsilonEqualStatesBuilder() {
		super("epseq", "EpsilonEqualStatesBuilder");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected StateComparator createTheInstance(JSONObject jo) {
		// TODO Auto-generated method stub
			double eps = jo.has("eps") ? jo.getDouble("eps") : 0.0;
			return new EpsilonEqualStates(eps);
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject jo = new JSONObject();
		jo.put("eps", "epsilon");
		return jo;
	}
}
