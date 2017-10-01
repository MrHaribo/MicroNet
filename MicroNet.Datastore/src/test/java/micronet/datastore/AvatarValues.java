package micronet.datastore;

import java.lang.String;

public class AvatarValues {
  private String name;

  private String faction;

  private String job;

  private String regionID;

  private String homeRegionID;

  private String poiID;

  private boolean landed;

  private Vector2 position;

  private int credits;

  public AvatarValues(String name) {
    this.name = name;
  }

  public void setName(String name) {
    this.name=name;
  }

  public String getName() {
    return name;
  }

  public void setFaction(String faction) {
    this.faction=faction;
  }

  public String getFaction() {
    return faction;
  }

  public void setJob(String job) {
    this.job=job;
  }

  public String getJob() {
    return job;
  }

  public void setRegionID(String regionID) {
    this.regionID=regionID;
  }

  public String getRegionID() {
    return regionID;
  }

  public void setHomeRegionID(String homeRegionID) {
    this.homeRegionID=homeRegionID;
  }

  public String getHomeRegionID() {
    return homeRegionID;
  }

  public void setPoiID(String poiID) {
    this.poiID=poiID;
  }

  public String getPoiID() {
    return poiID;
  }

  public void setLanded(boolean landed) {
    this.landed=landed;
  }

  public boolean getLanded() {
    return landed;
  }

  public void setPosition(Vector2 position) {
    this.position=position;
  }

  public Vector2 getPosition() {
    return position;
  }

  public void setCredits(int credits) {
    this.credits=credits;
  }

  public int getCredits() {
    return credits;
  }
}
