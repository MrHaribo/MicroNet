package micronet.tools.core;

import org.eclipse.jdt.core.IClasspathEntry;
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
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public IClasspathEntry getSelection() {
		// TODO Auto-generated method stub
		return null;
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