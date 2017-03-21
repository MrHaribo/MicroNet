package micronet.core.type;

public class Vector3 {
	public float x;
	public float y;
	public float z;

	public Vector3() {

	}

	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString() {
		return "{" + x + "," + y + "," + z + "}";
	}

	public static Vector3 parseVector3(String value) {
		String[] temp = value.substring(1, value.length() - 2).split(",");
		return new Vector3(Float.parseFloat(temp[0]), Float.parseFloat(temp[1]), Float.parseFloat(temp[2]));
	}
}
