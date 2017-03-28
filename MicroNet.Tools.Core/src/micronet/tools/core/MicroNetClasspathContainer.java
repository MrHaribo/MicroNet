package micronet.tools.core;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;

public class MicroNetClasspathContainer implements IClasspathContainer {

	@Override
	public IClasspathEntry[] getClasspathEntries() {
		System.out.println("getClasspathEntries");
		return new IClasspathEntry[] { JavaCore.newLibraryEntry(new Path("/Lib/gson-2.4.jar"), null, null) };
	}

	@Override
	public String getDescription() {
		return "MicroNet Library";
	}

	@Override
	public int getKind() {
		return K_APPLICATION;
	}

	@Override
	public IPath getPath() {
		return new Path("micronet.id.MICRONET_CONTAINER");
	}
}
