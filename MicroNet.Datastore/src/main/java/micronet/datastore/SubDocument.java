package micronet.datastore;

import com.couchbase.client.core.message.kv.subdoc.multi.Lookup;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.subdoc.DocumentFragment;

public class SubDocument {
	private String id;
	private Bucket bucket;
	
	public SubDocument(String id, Bucket bucket) {
		this.id = id;
		this.bucket = bucket;
	}
	
	public ListSubDocument getList(String id) {
		return new ListSubDocument(this.id, id, bucket);
	}
	
	public MapSubDocument getMap(String id) {
		return new MapSubDocument(this.id, id, bucket);
	}
	
	public <T> T get(String id, Class<T> c ) {
		DocumentFragment<Lookup> result = bucket
			    .lookupIn(this.id)
			    .get(id)
			    .execute();
		return result.content(id, c);
	}
	
	public void set(String id, Object value) {
		bucket
		    .mutateIn(this.id)
		    .replace(id, value)
		    .execute();
	}
}
