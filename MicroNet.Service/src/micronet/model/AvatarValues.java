package micronet.model;

import micronet.type.Vector2;

public class AvatarValues {
	private String name;
	private FactionValues faction;
	private String job;
	private ID regionID;
	private ID homeRegionID;
	private ID poiID;
	private boolean landed;
	private Vector2 position;
	private int credits;

	public AvatarValues(String name) {
		this.name = name;
	}

	public FactionValues getFaction() {
		return faction;
	}

	public void setFaction(FactionValues faction) {
		this.faction = faction;
	}

	public ID getRegionID() {
		return regionID;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public void setRegionID(ID regionID) {
		this.regionID = regionID;
	}

	public ID getHomeRegionID() {
		return homeRegionID;
	}

	public void setHomeRegionID(ID homeRegionID) {
		this.homeRegionID = homeRegionID;
	}

	public boolean isLanded() {
		return landed;
	}

	public void setLanded(boolean landed) {
		this.landed = landed;
	}

	public ID getPoiID() {
		return poiID;
	}

	public void setPoiID(ID poiID) {
		this.poiID = poiID;
	}

	public String getName() {
		return name;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}
}
