package micronet.model;

public class VehicleValues {
	private String name;
	private String[] primaryWeapons;
	private String[] heavyWeapons;

	public VehicleValues(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String[] getPrimaryWeapons() {
		return primaryWeapons;
	}

	public void setPrimaryWeapons(String[] primaryWeapons) {
		this.primaryWeapons = primaryWeapons;
	}

	public String[] getHeavyWeapons() {
		return heavyWeapons;
	}

	public void setHeavyWeapons(String[] heavyWeapons) {
		this.heavyWeapons = heavyWeapons;
	}
}
