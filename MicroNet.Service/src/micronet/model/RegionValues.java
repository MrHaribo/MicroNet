package micronet.model;

import java.util.Random;

import micronet.type.Vector2;

public class RegionValues {

	public class LevelType {
		public static final int Planet = 0;
		public static final int AsteroidField = 1;
		public static final int Cave = 2;
	}

	private ID id;

	private String name;
	private Vector2 worldPosition;
	private int threadLevel;
	private String biomName;
	private float seed;
	private String faction;
	private POIValues[] pois;

	public void setID(ID id) {
		this.id = id;
	}

	public ID getID() {
		return id;
	}

	public int getLevelType() {
		if (id.isMasterID())
			return LevelType.Planet;
		if (seed < 0.4f)
			return LevelType.Cave;
		return LevelType.AsteroidField;
	}

	public short getSubregionCount() {
		return (short) (new Random(id.GetHashCode()).nextInt(4) + 2);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector2 getWorldPosition() {
		if (id.isMasterID()) {
			return worldPosition;
		} else {
			Random regionRandom = new Random(id.GetHashCode());
			float posX = worldPosition.x + regionRandom.nextFloat() * 2 - 1;
			float posY = worldPosition.y + regionRandom.nextFloat() * 2 - 1;
			return new Vector2(posX, posY);
		}
	}

	public int getThreadLevel() {
		if (id.isMasterID())
			return threadLevel;
		else
			return (int) (threadLevel * 0.3f) + new Random(id.GetHashCode()).nextInt(30);
	}

	public void setThreadLevel(int threadLevel) {
		this.threadLevel = threadLevel;
	}

	public void setWorldPosition(Vector2 worldPosition) {
		this.worldPosition = worldPosition;
	}

	public String getBiomName() {
		return biomName;
	}

	public void setBiomName(String biomName) {
		this.biomName = biomName;
	}

	public float getSeed() {
		return seed;
	}

	public void setSeed(float seed) {
		this.seed = seed;
	}

	public String getFaction() {
		return faction;
	}

	public void setFaction(String faction) {
		this.faction = faction;
	}

	public POIValues[] getPois() {
		return pois;
	}

	public void setPois(POIValues[] pois) {
		this.pois = pois;
	}

}
