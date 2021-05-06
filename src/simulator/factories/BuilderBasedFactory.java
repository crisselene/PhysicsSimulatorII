package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	List<Builder<T>> builderList;
	List<JSONObject> estructsJson = new ArrayList<JSONObject>(); //lista de JSonObjects devueltos por el Builder 
	
	public BuilderBasedFactory(List<Builder<T>>builders) {
		this.builderList = builders;
		for (Builder<T> builder : builderList) {
			estructsJson.add(builder.getBuilderInfo());
		}
	}

	@Override
	public T createInstance(JSONObject info) {
		// a ejecuta los constructores uno a uno hasta que
		//encuentre el constructor capaz de crear el objeto correspondiente
		for (int i = 0; i < builderList.size(); i++) {
			//cuando pueda crear el objeto sera distinto de null
			if(builderList.get(i).createInstance(info) != null) {
				return builderList.get(i).createInstance(info);
			}
		}
		//si no se encuentra se devuelve una excepcion
		throw new IllegalArgumentException();
	}

	@Override
	public List<JSONObject> getInfo() {
		//devuelve en una lista las estructuras JSON devueltas por getBuilderInfo()
		return estructsJson;
	}

}
