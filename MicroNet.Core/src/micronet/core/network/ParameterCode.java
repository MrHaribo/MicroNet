package micronet.core.network;

import java.util.HashMap;
import java.util.Map;

public enum ParameterCode {
	USER_ID(101),
	USER_REQUEST(102),
	TOKEN(2003),
	PORT(2004),
	ID(2005),
	NAME(2006),
	INDEX(2007),
	EVENT(2008),
	FACTION(2009),
	REGION_ID(2010),
	POSITION(2011),
	LANDED(2012),
	VEHICLE_IDX(2013),
	POI_ID(2014),
	HOST(2015);

	private final int value;

	private ParameterCode(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	private static final Map<Integer, ParameterCode> intToTypeMap = new HashMap<Integer, ParameterCode>();

	static {
		for (ParameterCode type : ParameterCode.values()) {
			intToTypeMap.put(type.value, type);
		}
	}

	public static ParameterCode fromInt(int i) {
		return intToTypeMap.get(Integer.valueOf(i));
	}
}
