package micronet.core.type;

public class Vector2 {
	public float x;
	public float y;

	public Vector2() {

	}

	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "{" + x + "," + y + "," + "}";
	}

	public static Vector2 parseVector2(String value) {
		String[] temp = value.substring(1, value.length() - 2).split(",");
		return new Vector2(Float.parseFloat(temp[0]), Float.parseFloat(temp[1]));
	}
}
