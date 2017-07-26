package micronet.type;

/**
 * Vector3 class used for storing and transmitting 3D positions
 * 
 * @author Jonas Biedermann
 *
 */
public class Vector3 {
	public float x;
	public float y;
	public float z;

	/**
	 * Creates a Zero Vector
	 */
	public Vector3() {

	}

	/**
	 * Creates a Vector using the provided coordinates
	 * @param x X coordinate of the Vector
	 * @param y Y coordinate of the Vector
	 * @param z Z coordinate of the Vector
	 */
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Serializes the Vector to a String Representation in CVS format
	 */
	@Override
	public String toString() {
		return "{" + x + "," + y + "," + z + "}";
	}

	/**
	 * Helper method to deserialize a Vector from a String in CVS format
	 * @param value String representation of the Vector in CVS format
	 * @return Returns the parsed Vector
	 */
	public static Vector3 parseVector3(String value) {
		String[] temp = value.substring(1, value.length() - 2).split(",");
		return new Vector3(Float.parseFloat(temp[0]), Float.parseFloat(temp[1]), Float.parseFloat(temp[2]));
	}
}
