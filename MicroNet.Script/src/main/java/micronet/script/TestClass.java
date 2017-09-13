package micronet.script;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class TestClass {
	
	
	Invocable invocable = null;
	
	
	public int testInt = 42;
	public String testString = "SonGoku";
	
	public static void main(String[] args) {
		TestClass test = new TestClass();
		test.callJS();
	}
	
	public void callJS() {
		try {
			Object result = invocable.invokeFunction("fun1", "Peter Parker");
			System.out.println(result);
			System.out.println(result.getClass());
			
		} catch (NoSuchMethodException | ScriptException e) {
			e.printStackTrace();
		}
	}
	
	public TestClass() {
	
		try {
			ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
			engine.eval(new FileReader("script.js"));
			invocable = (Invocable) engine;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
