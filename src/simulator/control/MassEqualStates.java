package simulator.control;

import org.json.JSONArray; 
import org.json.JSONObject;

public class MassEqualStates implements StateComparator{
	
	 @Override
	 public boolean equal(JSONObject s1, JSONObject s2) {
		 boolean cuerpos_iguales = false; 
		 //mismo time
		 if(s1.getDouble("time") == s2.getDouble("time")){
			//listas de cuerpos de los dos json
			JSONArray bodiesS1 = s1.getJSONArray("bodies");
			JSONArray bodiesS2 = s2.getJSONArray("bodies");
			//si tienen la misma longitud recorremos la lista
			if(bodiesS1.length() == bodiesS2.length()) {
				for (int i = 0; i <bodiesS1.length(); i++) {
					JSONObject uno = bodiesS1.getJSONObject(i);
					JSONObject dos = bodiesS2.getJSONObject(i);
					//que el id sea igual
					if(dos.getString("id").equals(uno.getString("id")) &&
						dos.getString("m").equals(uno.getString("m"))) {
							cuerpos_iguales= true;
					}else {
						cuerpos_iguales = false;
						return cuerpos_iguales;
					}		
				}	
			}
			return cuerpos_iguales; 
		 }
		return cuerpos_iguales;
	 }
		
}
