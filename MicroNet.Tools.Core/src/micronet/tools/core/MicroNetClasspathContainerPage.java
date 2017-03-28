package micronet.tools.core;

import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.ui.wizards.IClasspathContainerPage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class MicroNetClasspathContainerPage implements IClasspathContainerPage {

	IClasspathEntry entry;
	IWizard wizard;
	Composite control;
	
	@Override
	public boolean finish() {
		return true;
	}

	@Override
	public IClasspathEntry getSelection() {
		return entry;
	}

	@Override
	public void setSelection(IClasspathEntry arg0) {
		entry = arg0;
	}

	@Override
	public boolean canFlipToNextPage() {
		System.out.println("Here");
		return false;
	}

	@Override
	public String getName() {
		System.out.println("Here");
		return "MyName";
	}

	@Override
	public IWizardPage getNextPage() {
		System.out.println("Here");
		return null;
	}

	@Override
	public IWizardPage getPreviousPage() {
		System.out.println("Here");
		return null;
	}

	@Override
	public IWizard getWizard() {
		System.out.println("Here");
		return wizard;
	}

	@Override
	public boolean isPageComplete() {
		System.out.println("Here");
		return false;
	}

	@Override
	public void setPreviousPage(IWizardPage arg0) {
		System.out.println("Here");
		
	}

	@Override
	public void setWizard(IWizard arg0) {
		System.out.println("Here");
		wizard = arg0;
	}

	@Override
	public void createControl(Composite arg0) {
		System.out.println("Here");
		control = arg0;
	}

	@Override
	public void dispose() {
		System.out.println("Here");
		
	}

	@Override
	public Control getControl() {
		System.out.println("Here");
		return control;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getErrorMessage() {
		System.out.println("Here");
		return null;
	}

	@Override
	public Image getImage() {
		System.out.println("Here");
		return null;
	}

	@Override
	public String getMessage() {
		System.out.println("Here");
		return null;
	}

	@Override
	public String getTitle() {
		System.out.println("Here");
		return null;
	}

	@Override
	public void performHelp() {
		System.out.println("Here");
	}

	@Override
	public void setDescription(String arg0) {
		System.out.println("Here");
	}

	@Override
	public void setImageDescriptor(ImageDescriptor arg0) {
		System.out.println("Here");
	}

	@Override
	public void setTitle(String arg0) {
		System.out.println("Here");
	}

	@Override
	public void setVisible(boolean arg0) {
		System.out.println("Here");
	}

}
