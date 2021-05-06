package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;


public class EpsilonEqualStates implements StateComparator {
	
	double eps;

	public EpsilonEqualStates(double eps) {
		super();
		this.eps = eps;
	}



	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {
		boolean cuerpos_iguales = true;
		int i = 0; // para controlar que no nos salgamos de tamanio del jsonArray
		// mismo time
		if (s1.getDouble("time") == s2.getDouble("time")) {
			// listas de cuerpos de los dos json
			JSONArray bodiesS1 = s1.getJSONArray("bodies");
			JSONArray bodiesS2 = s2.getJSONArray("bodies");
			// si tienen la misma longitud recorremos la lista
			if (bodiesS1.length() == bodiesS2.length()) {
				while (cuerpos_iguales && i < bodiesS1.length()) {
					JSONObject uno = bodiesS1.getJSONObject(i); //los dos cuerpos: 1
					JSONObject dos = bodiesS2.getJSONObject(i); //2
					// que el id sea igual
					if (dos.getString("id").equals(uno.getString("id"))
							&& Math.abs(dos.getDouble("m") - uno.getDouble("m")) <= eps) {
						// vectores para comparar la posicion
						double distanciaP = obtener_distancia(uno, dos, "p");
						
						// vector velocidad
						double distanciaV = obtener_distancia(uno, dos, "v");
						// vector fuerza
						double distanciaF = obtener_distancia(uno, dos, "f");
						
						if (!igual_modulo(distanciaP) || !igual_modulo(distanciaV) || !igual_modulo(distanciaF)) {
							cuerpos_iguales = false;
						}
					}else {
						cuerpos_iguales=false;}
					i++;
				}
			}else {
				cuerpos_iguales=false;
			}
		}else {
			cuerpos_iguales=false;
		}
		return cuerpos_iguales;

	}



	private double obtener_distancia(JSONObject uno, JSONObject dos , String elemento) {
		Vector2D p1 = generateVector2D(uno.getJSONArray(elemento));
		Vector2D p2 = generateVector2D(dos.getJSONArray(elemento));
		double distancia = p1.distanceTo(p2);
		return distancia;
	}
	
	//genera los vectores2D
	private Vector2D generateVector2D(JSONArray ja) {
		Vector2D vector = new Vector2D(ja.getDouble(0),ja.getDouble(1));
		return vector;
	}

	//comprueba si tienen el mismo modulo
	private boolean igual_modulo(double a) {
		return (a <= eps);
		
	}

}
