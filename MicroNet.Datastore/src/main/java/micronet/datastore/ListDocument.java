package micronet.datastore;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonArrayDocument;
import com.couchbase.client.java.document.json.JsonArray;

public class ListDocument {
	private String id;
	private Bucket bucket;
	
	public ListDocument(String id, Bucket bucket) {
		this.id = id;
		this.bucket = bucket;
		
		if (!bucket.exists(id))
			bucket.insert(JsonArrayDocument.create(id, JsonArray.create()));
	}
	
	public <T> T get(int index, Class<T> c) {
		return bucket.listGet(id, index, c);
	}
	
	public void set(int index, Object obj) {
		bucket.listSet(id, index, obj);
	}
	
	public void append(Object obj) {
		bucket.listAppend(id, obj);
	}

	public void prepend(Object obj) {
		bucket.listPrepend(id, obj);
	}
	
	public void remove(int index) {
		bucket.listRemove(id, index);
	}
	
	public int size() {
		return bucket.listSize(id);
	}
}
