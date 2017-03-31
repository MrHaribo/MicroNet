package micronet.model;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class ID {
	private static final String versionSplitString = "-";
	private static final String subVersionSplitString = "n";

	private byte[] types;
	private short[] values;

	private ID() {

	}

	public ID(byte type, short id) {
		types = new byte[1];
		values = new short[1];
		types[0] = type;
		values[0] = id;
	}

	public ID(byte type, short id, ID parentID) {
		types = new byte[parentID.types.length + 1];
		values = new short[parentID.values.length + 1];
		System.arraycopy(parentID.values, 0, values, 0, parentID.values.length);
		System.arraycopy(parentID.types, 0, types, 0, parentID.types.length);

		types[parentID.types.length] = type;
		values[parentID.values.length] = id;
	}

	public ID(String data) {
		String[] tokens = data.split(versionSplitString);
		types = new byte[tokens.length];
		values = new short[tokens.length];
		for (int i = 0; i < tokens.length; i++) {
			String[] idString = tokens[i].split(subVersionSplitString);
			types[i] = Byte.parseByte(idString[0]);
			values[i] = Short.parseShort(idString[1]);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < types.length; i++) {
			builder.append(types[i]);
			builder.append(subVersionSplitString);
			builder.append(values[i]);
			if (i != types.length - 1)
				builder.append(versionSplitString);
		}
		return builder.toString();
	}

	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof ID))
			return false;
		ID otherID = (ID) other;

		boolean typesEqual = Arrays.equals(this.types, otherID.types);
		boolean valuesEqual = Arrays.equals(this.values, otherID.values);
		return typesEqual && valuesEqual;
	}

	public ID getParentID() {
		if (isMasterID())
			return null;

		byte[] tmpTypes = new byte[types.length - 1];
		short[] tmpValues = new short[values.length - 1];
		System.arraycopy(types, 0, tmpTypes, 0, types.length - 1);
		System.arraycopy(values, 0, tmpValues, 0, values.length - 1);

		ID parentID = new ID();
		parentID.types = tmpTypes;
		parentID.values = tmpValues;
		return parentID;
	}

	public int GetHashCode() {

		final int p = 16777619;
		int hash = 2147483647;

		for (int i = 0; i < types.length; i++) {

			ByteBuffer buffer = ByteBuffer.allocate(4);
			buffer.put(types[i]);
			buffer.putShort(values[i]);
			buffer.put(types[i]);
			int tmp = buffer.getInt(0);
			hash = (hash ^ tmp) * p;
		}

		hash += hash << 13;
		hash ^= hash >> 7;
		hash += hash << 3;
		hash ^= hash >> 17;
		hash += hash << 5;
		return hash;
	}

	public ID getMasterID() {
		return new ID(types[0], values[0]);
	}

	public boolean isMasterID() {
		return types.length == 1;
	}

	public byte getMasterType() {
		return types[0];
	}

	public byte getParentType() {
		if (isMasterID())
			return IDType.Unknown;
		return types[types.length - 2];
	}

	public byte getType() {
		return types[types.length - 1];
	}

	public short getValue() {
		return values[values.length - 1];
	}

	public URI getURI() {
		return URI.create(getAddress());
	}

	public String getAddress() {
		return "mn://" + toString();
	}

	public URI getQueueAddress(String handlerName) {
		return URI.create(getAddress() + handlerName);
	}

}
