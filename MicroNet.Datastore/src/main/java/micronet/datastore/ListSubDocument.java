package micronet.datastore;

import com.couchbase.client.core.message.kv.subdoc.multi.Lookup;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.subdoc.DocumentFragment;

import micronet.serialization.Serialization;

public class ListSubDocument {
	private String documentId;
	private String subId;
	private Bucket bucket;

	public ListSubDocument(String documentId, String subId, Bucket bucket) {
		this.documentId = documentId;
		this.subId = subId;
		this.bucket = bucket;
	}
	
	public SubDocument get(int index) {
		String lookupString = getElementID(index);
		return new SubDocument(documentId, lookupString, bucket);
	}
	
	public <T> T get(int index, Class<T> c) {
		return Serialization.deserialize(getRaw(index), c);
	}
	
	public String getRaw(int index) {
		String lookupString = getElementID(index);
		DocumentFragment<Lookup> result = bucket
				.lookupIn(documentId)
				.get(lookupString)
				.execute();
		if (result.content(lookupString) == null)
			return null;
		return result.content(lookupString).toString();
	}

	public void set(int index, Object obj) {
		String lookupString = getElementID(index);
		bucket
		    .mutateIn(documentId)
		    .replace(lookupString, obj)
		    .execute();
	}

	public void append(Object obj) {
		bucket
		    .mutateIn(documentId)
		    .arrayAppend(subId, obj)
		    .execute();
	}
	
	public void appendUnique(Object obj) {
		bucket
		    .mutateIn(documentId)
		    .arrayAddUnique(subId, obj)
		    .execute();
	}

	public void prepend(Object obj) {
		bucket
		    .mutateIn(documentId)
		    .arrayPrepend(subId, obj)
		    .execute();
	}

	public void remove(int index) {
		String lookupString = getElementID(index);
		bucket
		    .mutateIn(documentId)
		    .remove(lookupString)
		    .execute();
	}
	
	private String getElementID(int index) {
		return String.format("%s[%d]", subId, index);
	}
}
