package simulator.factories;

import org.json.JSONObject;

import simulator.control.MassEqualStates;
import simulator.control.StateComparator;

public class MassEqualStatesBuilder extends Builder<StateComparator> {

	public MassEqualStatesBuilder() {
		super("masseq", "MassEqualStatesBuilder");
	}
	
	@Override
	protected StateComparator createTheInstance(JSONObject jo) {
		// TODO Auto-generated method stub
			return new MassEqualStates();
	}

	@Override 
	protected JSONObject createData() {
		JSONObject jo = new JSONObject();
		return jo;
	}
	
}
