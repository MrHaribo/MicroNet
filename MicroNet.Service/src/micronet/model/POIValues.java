package micronet.model;

import micronet.type.Vector2;

public class POIValues {
	private int id;
	private String type;
	private Vector2 position;
	private float seed;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public float getSeed() {
		return seed;
	}

	public void setSeed(float seed) {
		this.seed = seed;
	}
}
