package micronet.datastore;

import com.couchbase.client.core.message.kv.subdoc.multi.Lookup;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.subdoc.DocumentFragment;

import micronet.serialization.Serialization;

public class SubDocument {
	private String id;
	private String subID;
	private Bucket bucket;
	
	public SubDocument(String id, Bucket bucket) {
		this(id, null, bucket);
	}
	
	public SubDocument(String id, String subID, Bucket bucket) {
		this.id = id;
		this.subID = subID;
		this.bucket = bucket;
	}

	public SubDocument getSub(String id) {
		subID = getSubID(id);
		return this;
	}
	
	public ListSubDocument asList() {
		return subID == null ? null : new ListSubDocument(this.id, subID, bucket);
	}
	
	public ListSubDocument getList(String id) {
		return new ListSubDocument(this.id, getSubID(id), bucket);
	}
	
	public MapSubDocument asMap() {
		return subID == null ? null : new MapSubDocument(this.id, subID, bucket);
	}
	
	public MapSubDocument getMap(String id) {
		return new MapSubDocument(this.id, getSubID(id), bucket);
	}
	
	public <T> T get(String id, Class<T> c ) {
		DocumentFragment<Lookup> result = bucket
			    .lookupIn(this.id)
			    .get(getSubID(id))
			    .execute();
		return Serialization.deserialize(result.content(id).toString(), c);
	}
	
	public void set(String id, Object value) {
		bucket
		    .mutateIn(this.id)
		    .replace(getSubID(id), value)
		    .execute();
	}
	
	private String getSubID(String id) {
		if (subID == null)
			return id;
		return String.format("%s.%s", subID, id);
	}
	
	//TODO: As List, Map, Set
}
