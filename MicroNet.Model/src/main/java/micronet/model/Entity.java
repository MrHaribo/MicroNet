package micronet.model;

public class Entity {
	private String id;
	private String type;
	
	public String getID() {
		return id;
	}
	public void setID(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getParentID() {
		if (id == null)
			return null;
		int lastIndexOf = id.lastIndexOf('.');
		if (lastIndexOf < 0)
			return null;
		return id.substring(0, lastIndexOf);
	}
}
