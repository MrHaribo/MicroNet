package micronet.datastore;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;

public class MapDocument {
	private String id;
	private Bucket bucket;
	
	public MapDocument(String id, Bucket bucket) {
		this.id = id;
		this.bucket = bucket;
		
		if (!bucket.exists(id))
			bucket.insert(JsonDocument.create(id, JsonObject.empty()));
	}
	
	public <T> T get(String key, Class<T> c) {
		return bucket.mapGet(id, key, c);
	}
	
	public void add(String key, Object value) {
		bucket.mapAdd(id, key, value);
	}
	
	public void remove(String key) {
		bucket.mapRemove(id, key);
	}
	
	public int size() {
		return bucket.mapSize(id);
	}
}
