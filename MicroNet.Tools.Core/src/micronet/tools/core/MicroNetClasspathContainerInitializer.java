package micronet.tools.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

public class MicroNetClasspathContainerInitializer extends ClasspathContainerInitializer {

	@Override
	public void initialize(IPath path, IJavaProject javaProject) throws CoreException {
		System.out.println("Adding MicroNet to Project");
		MicroNetClasspathContainer classpathContainer = new MicroNetClasspathContainer();
        JavaCore.setClasspathContainer(path, new IJavaProject[] {javaProject}, new IClasspathContainer[] {classpathContainer}, null);
	}

}
