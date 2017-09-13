package micronet.script;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import jdk.nashorn.api.scripting.ClassFilter;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

public enum ScriptExecutor {
	INSTANCE;

	ScriptEngine engine;
	Invocable invocable;

	private ScriptExecutor() {
		
		NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
	    engine = factory.getScriptEngine(new StrictCF());
	    System.out.println("Using Strict Script Engine");
		//engine = new ScriptEngineManager().getEngineByName("nashorn");
	    //System.out.println("Using Default Script Engine");
		
		invocable = (Invocable) engine;

		String scriptLocation = System.getenv("script_location") != null ? System.getenv("script_location")
				: "../shared/scripts";
		File dir = new File(scriptLocation);
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File scriptFile : directoryListing) {
				registerScript(scriptFile);
			}
		}
	}

	public void registerScript(File scriptFile) {
		try {
			engine.eval(new FileReader(scriptFile));
		} catch (ScriptException | FileNotFoundException e) {
			System.err.println("Script File Not Found: " + scriptFile);
			e.printStackTrace();
		}
	}

	public Object invokeFunction(String name, Object... args) {
		try {
			return invocable.invokeFunction(name, args);
		} catch (NoSuchMethodException | ScriptException e) {
			System.err.println("Invoke Script FUnction failed: " + name);
			e.printStackTrace();
		}
		return null;
	}

	class StrictCF implements ClassFilter {
		@Override
		public boolean exposeToScripts(String s) {
			return false;
		}
	}
}
