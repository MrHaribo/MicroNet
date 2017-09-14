package micronet.datastore;

import com.couchbase.client.core.message.kv.subdoc.multi.Lookup;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.subdoc.DocumentFragment;

public class ListSubDocument {
	private String documentId;
	private String subId;
	private Bucket bucket;

	public ListSubDocument(String documentId, String subId, Bucket bucket) {
		this.documentId = documentId;
		this.subId = subId;
		this.bucket = bucket;
	}

	public <T> T get(int index, Class<T> c) {
		String lookupString = String.format("%s[%d]", subId, index);
		DocumentFragment<Lookup> result = bucket
				.lookupIn(documentId)
				.get(lookupString)
				.execute();
		return result.content(lookupString, c);
	}

	public void set(int index, Object obj) {
		String lookupString = String.format("%s[%d]", subId, index);
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

	public void prepend(Object obj) {
		bucket
		    .mutateIn(documentId)
		    .arrayPrepend(subId, obj)
		    .execute();
	}

	public void remove(int index) {
		String lookupString = String.format("%s[%d]", subId, index);
		bucket
		    .mutateIn(documentId)
		    .remove(lookupString)
		    .execute();
	}
}
