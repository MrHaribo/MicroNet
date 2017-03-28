package micronet.tools.core;

import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.wizards.IClasspathContainerPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class MicroNetClasspathContainerPage extends WizardPage implements IClasspathContainerPage {

	public MicroNetClasspathContainerPage() {
		super("MicroNet Page Name");
	}

	@Override
	public void createControl(Composite parent) {
		Label label = new Label(parent, SWT.CENTER);
		super.setControl(label);
	}
	@Override
	public boolean finish() {
		return true;
	}
	@Override
	public IClasspathEntry getSelection() {
		return JavaCore.newContainerEntry(new Path("micronet.id.MICRONET_CONTAINER"));
	}
	@Override
	public void setSelection(IClasspathEntry arg0) {
	}
	
	@Override
	public boolean canFlipToNextPage() {
		return false;
	}
	
	@Override
	public String getMessage() {
		return "Add the MicroNet library to the project.";
	}

	@Override
	public String getTitle() {
		return "MicroNet Library";
	}
}