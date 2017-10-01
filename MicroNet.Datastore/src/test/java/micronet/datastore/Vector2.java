package micronet.datastore;

/**
 * Vector2 class used for storing and transmitting 2D positions
 * 
 * @author Jonas Biedermann
 *
 */
public class Vector2 {
	public float x;
	public float y;

	/**
	 * Creates a Zero Vector
	 */
	public Vector2() {

	}

	/**
	 * Creates a Vector using the provided coordinates
	 * @param x X coordinate of the Vector
	 * @param y Y coordinate of the Vector
	 */
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Serializes the Vector to a String Representation in CVS format
	 */
	@Override
	public String toString() {
		return "{" + x + "," + y + "," + "}";
	}

	/**
	 * Helper method to deserialize a Vector from a String in CVS format
	 * @param value String representation of the Vector in CVS format
	 * @return Returns the parsed Vector
	 */
	public static Vector2 parseVector2(String value) {
		String[] temp = value.substring(1, value.length() - 2).split(",");
		return new Vector2(Float.parseFloat(temp[0]), Float.parseFloat(temp[1]));
	}
}
