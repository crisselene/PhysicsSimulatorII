package simulator.control;

import org.json.JSONObject;

public class DifferentStructs_Exception extends Exception{

	private static final long serialVersionUID = -8943985273521923691L;

	public DifferentStructs_Exception(String message,JSONObject currState, JSONObject expState, int step) {
		super(message+ "estado actual: " + currState + "estado esperado: " + expState
				+ " en el paso " + step);
	}

	
}
