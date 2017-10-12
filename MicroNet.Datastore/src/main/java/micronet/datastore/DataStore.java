package micronet.datastore;

import java.util.ArrayList;
import java.util.List;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;

import micronet.serialization.Serialization;

public class DataStore {
	
	private Cluster cluster;
	private Bucket bucket;

	public DataStore() {
		String connectionString = System.getenv("couchbase_address") != null ? System.getenv("couchbase_address") : "localhost";
		System.out.println("Connecting to Couchbase: " + connectionString);
		cluster = CouchbaseCluster.create(connectionString);
		bucket = cluster.openBucket("entities");
        bucket.bucketManager().createN1qlPrimaryIndex(true, false);
	}
	
	public Cluster getCluster() {
		return cluster;
	}
	
	public Bucket getBucket() {
		return bucket;
	}
	
	public SubDocument getSub(String id) {
		return new SubDocument(id, bucket);
	}

	public String getRaw(String id) {
		JsonDocument doc = bucket.get(id);
		if (doc == null)
			return null;
		return doc.content().toString();
	}
	
	public <T> T get(String id, Class<T> c) {
		JsonDocument doc = bucket.get(id);
		if (doc == null)
			return null;
		return Serialization.deserialize(doc.content().toString(), c);
	}
	
	public <T> T getAndTouch(String id, int expiry, Class<T> c) {
		JsonDocument doc = bucket.getAndTouch(id, expiry);
		if (doc == null)
			return null;
		return Serialization.deserialize(doc.content().toString(), c);
	}
	
	public <T> T query(String queryString, Class<T> c, Object ... positionalArgs) {
        N1qlQueryResult result = bucket.query(
            N1qlQuery.parameterized(queryString, 
            JsonArray.from(positionalArgs))
        );
        for (N1qlQueryRow row : result) {
        	return Serialization.deserialize(row.value().toString(), c);
        }
        return null;
	}
	
	public <T> List<T> queryAll(String queryString, Class<T> c, Object ... positionalArgs) {
        N1qlQueryResult result = bucket.query(
            N1qlQuery.parameterized(queryString, 
            JsonArray.from(positionalArgs))
        );

        List<T> resultObjects = new ArrayList<>();
        for (N1qlQueryRow row : result) {
        	T resultObject = Serialization.deserialize(row.value().toString(), c);
        	resultObjects.add(resultObject);
        }
        return resultObjects;
	}
	
	public void insert(String id, Object obj) {
		String data = Serialization.serialize(obj);
		JsonObject jsonObject = JsonObject.fromJson(data);
		JsonDocument doc = JsonDocument.create(id, jsonObject);
		bucket.insert(doc);
	}
	
	public void insert(String id, int expiry, Object obj) {
		String data = Serialization.serialize(obj);
		JsonObject jsonObject = JsonObject.fromJson(data);
		JsonDocument doc = JsonDocument.create(id, expiry, jsonObject);
		bucket.insert(doc);
	}
	
	public void upsertRaw(String id, String jsonString) {
		JsonObject jsonObject = JsonObject.fromJson(jsonString);
		JsonDocument doc = JsonDocument.create(id, jsonObject);
		bucket.upsert(doc);
	}
	
	public void upsert(String id, Object obj) {
		String data = Serialization.serialize(obj);
		JsonObject jsonObject = JsonObject.fromJson(data);
		JsonDocument doc = JsonDocument.create(id, jsonObject);
		bucket.upsert(doc);
	}
	
	public void upsert(String id, int expiry, Object obj) {
		String data = Serialization.serialize(obj);
		JsonObject jsonObject = JsonObject.fromJson(data);
		JsonDocument doc = JsonDocument.create(id, expiry, jsonObject);
		bucket.upsert(doc);
	}
	
	public void remove(String id) {
		bucket.remove(id);
	}
	
	public boolean exists(String id) {
		return bucket.exists(id);
	}
}
