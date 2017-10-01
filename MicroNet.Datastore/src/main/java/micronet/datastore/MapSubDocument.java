package micronet.datastore;

import com.couchbase.client.core.message.kv.subdoc.multi.Lookup;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.subdoc.DocumentFragment;

import micronet.serialization.Serialization;

public class MapSubDocument {
	private String documentId;
	private String subId;
	private Bucket bucket;
	
	public MapSubDocument(String documentId, String subId, Bucket bucket) {
		this.documentId = documentId;
		this.subId = subId;
		this.bucket = bucket;
	}

	public SubDocument get(String key) {
		String lookupString = String.format("%s.%s", subId, key);
		return new SubDocument(documentId, lookupString, bucket);
	}
	
	public <T> T get(String key, Class<T> c) {
		return Serialization.deserialize(getRaw(key), c);
	}
	
	public String getRaw(String key) {
		String lookupString = String.format("%s.%s", subId, key);
		DocumentFragment<Lookup> result = bucket
			    .lookupIn(documentId)
			    .get(lookupString)
			    .execute();
		if (result.content(lookupString) == null)
			return null;
		return result.content(lookupString).toString();
	}
	
	public void add(String key, Object value) {
		String lookupString = String.format("%s.%s", subId, key);
		
		bucket
			    .mutateIn(documentId)
			    .insert(lookupString, value)
			    .execute();
	}
	
	public void put(String key, Object value) {
		String lookupString = String.format("%s.%s", subId, key);
		
		bucket
			    .mutateIn(documentId)
			    .upsert(lookupString, value)
			    .execute();
	}
	
	public void remove(String key) {
		String lookupString = String.format("%s.%s", subId, key);
		bucket
			    .mutateIn(documentId)
			    .remove(lookupString)
			    .execute();
	}
}
