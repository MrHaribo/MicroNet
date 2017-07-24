package micronet.serialization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for MicroNet Serialization.
 */
public class SerializationTest extends TestCase
{
    public SerializationTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( SerializationTest.class );
    }
    
    public void testValueObject()
    {
    	String testString = "TeSt";
    	
    	DummyObject o = new DummyObject();
    	o.data = testString;
    	
    	String data = Serialization.serialize(o);
    	
    	DummyObject deserializedObj = Serialization.deserialize(data, DummyObject.class);
    	
    	assertNotSame(o, deserializedObj);
        assertEquals(o.data, deserializedObj.data);
        
        assertEquals(o.data, testString);
        assertEquals(deserializedObj.data, testString);
    }
    
    public void testGenericMap()
    {
    	Map<String, DummyObject> m = new HashMap<>();
    	
    	for (int i = 0; i < 10; i++) {
        	DummyObject o = new DummyObject();
        	o.data = "Entry" + i;
        	m.put(o.data, o);
    	}
    	
    	String data = Serialization.serialize(m);
    	
    	TypeToken<Map<String, DummyObject>> token = new TypeToken<Map<String, DummyObject>>(){};

    	Map<String, DummyObject> deserializedMap = Serialization.deserialize(data, token);
    	
    	assertNotSame(m, deserializedMap);

    	for (int i = 0; i < 10; i++) {
    		String key = "Entry" + i;
    		assertNotSame(m.get(key), deserializedMap.get(key));
    		assertEquals(m.get(key).data, deserializedMap.get(key).data);
    	}
    }
    
    public void testGenericList()
    {
    	List<DummyObject> l = new ArrayList<>();
    	
    	for (int i = 0; i < 10; i++) {
        	DummyObject o = new DummyObject();
        	o.data = "Entry" + i;
        	l.add(o);
    	}
    	
    	String data = Serialization.serialize(l);
    	
    	TypeToken<List<DummyObject>> token = new TypeToken<List<DummyObject>>(){};
    	List<DummyObject> deserializedList = Serialization.deserialize(data, token);
    	
    	assertNotSame(l, deserializedList);

    	for (int i = 0; i < 10; i++) {

    		assertNotSame(l.get(i), deserializedList.get(i));
    		assertEquals(l.get(i).data, deserializedList.get(i).data);
    	}
    }
    
    public void testListAsArray()
    {
    	List<DummyObject> l = new ArrayList<>();
    	
    	for (int i = 0; i < 10; i++) {
        	DummyObject o = new DummyObject();
        	o.data = "Entry" + i;
        	l.add(o);
    	}
    	
    	String data = Serialization.serialize(l);
    	
    	DummyObject[] deserializedArray = Serialization.deserialize(data, DummyObject[].class);
    	List<DummyObject> deserializedList = Arrays.asList(deserializedArray);
    	
    	assertNotSame(l, deserializedList);

    	for (int i = 0; i < 10; i++) {

    		assertNotSame(l.get(i), deserializedList.get(i));
    		assertEquals(l.get(i).data, deserializedList.get(i).data);
    		
        	assertEquals(l.get(i).data, deserializedArray[i].data);
    	}
    }
    
    private class DummyObject {
    	public String data;
    }
}
