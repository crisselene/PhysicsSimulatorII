package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public class Body {
	protected String id;
	protected double m;//masa
	protected Vector2D v;//velocidad
	protected Vector2D f = new Vector2D();//fuerza
	protected Vector2D p;//posicion
	
	public Body(String id, double m, Vector2D v, Vector2D p) {
		super();
		this.id = id;
		this.m = m;
		this.v = v;
		this.p = p;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Body other = (Body) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getId() {
		return id;
	}

	public double getMass() {
		return m;
	}

	public Vector2D getVelocity() {
		return v;
	}

	public Vector2D getForce() {
		return f;
	}

	public Vector2D getPosition() {
		return p;
	}
	

	public void setId(String id) {
		this.id = id;
	}

	public void setMass(double m) {
		this.m = m;
	}

	public void setVelocity(Vector2D v) {
		this.v = v;
	}

	public void setForce(Vector2D f) {
		this.f = f;
	}

	public void setPosition(Vector2D p) {
		this.p = p;
	}
	

	void addForce(Vector2D f) {
		this.f = this.f.plus(f);
	}
	
	void resetForce() {
		this.f = new Vector2D();
	}

	void move(double t) {
		Vector2D a;
		if(m!=0.0) {
		a= f.scale(1.0/m);
		}
		else {
			a = new Vector2D(0,0);
		}
				
		this.p = p.plus(v.scale(t).plus(a.scale(t * t * 0.5))); //OJO LOS PARENTESIS
		this.v= v.plus(a.scale(t)); 
		
	}
	
	
	public JSONObject getState() {
		JSONObject jo1 = new JSONObject();
		jo1.put("id", getId());
		jo1.put("m", getMass());
		jo1.put("v", getVelocity().asJSONArray());
		jo1.put("f", getForce().asJSONArray());
		jo1.put("p", getPosition().asJSONArray());
		return jo1;
	}
	
	@Override 
	public String toString() {
		return getState().toString();
	}
}
