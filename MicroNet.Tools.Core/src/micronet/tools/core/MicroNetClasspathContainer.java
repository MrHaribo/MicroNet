package micronet.tools.core;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;

public class MicroNetClasspathContainer implements IClasspathContainer {

	@Override
	public IClasspathEntry[] getClasspathEntries() {
		return new IClasspathEntry[] { JavaCore.newContainerEntry(new Path("lib.name")) };
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
		return new Path(".");
	}
}
