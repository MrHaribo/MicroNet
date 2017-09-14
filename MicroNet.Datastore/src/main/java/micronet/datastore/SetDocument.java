package micronet.datastore;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonArrayDocument;
import com.couchbase.client.java.document.json.JsonArray;

public class SetDocument {
	private String id;
	private Bucket bucket;
	
	public SetDocument(String id, Bucket bucket) {
		this.id = id;
		this.bucket = bucket;
		
		if (!bucket.exists(id))
			bucket.insert(JsonArrayDocument.create(id, JsonArray.create()));
	}
	
	public boolean contains(Object obj) {
		return bucket.setContains(id, obj);
	}
	
	public void add(Object obj) {
		bucket.setAdd(id, obj);
	}
	
	public void remove(Object obj) {
		bucket.setRemove(id, obj);
	}
	
	public int size() {
		return bucket.setSize(id);
	}
}
