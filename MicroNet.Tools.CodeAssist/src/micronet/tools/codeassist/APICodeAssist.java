package micronet.tools.codeassist;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.variables.IDynamicVariable;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.jdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.jdt.ui.text.java.IJavaCompletionProposalComputer;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;

import micronet.api.ListenerAPI;
import micronet.api.ServiceAPI;
import micronet.serialization.Serialization;


public class APICodeAssist implements IJavaCompletionProposalComputer {

	private List<ServiceAPI> fullAPI = new ArrayList<>();
	
	
	@Override
	public void sessionStarted() {
		System.out.println("Session Start");
		
		try {
			IDynamicVariable var = VariablesPlugin.getDefault().getStringVariableManager().getDynamicVariable("workspace_loc");
			String workspacePath = var.getValue(null);
			File dir = new File(workspacePath + "/shared_api");
			File[] directoryListing = dir.listFiles();

			fullAPI = new ArrayList<>();
			for (File apiFile : directoryListing) {
				String data = new String(Files.readAllBytes(apiFile.toPath()), StandardCharsets.UTF_8);
				ServiceAPI api = Serialization.deserialize(data, ServiceAPI.class);
				fullAPI.add(api);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ICompletionProposal> computeCompletionProposals(ContentAssistInvocationContext context, IProgressMonitor monitor) {

		List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
		
	  	try {

			String line =  getCurrentLine(context);
			
			if (!line.contains("mn://"))
				return proposals;
			
			int uriRelativeStartIdx = line.indexOf("mn://");
			int uriRelativeEndIdx = line.indexOf("\"", uriRelativeStartIdx);
			int lineOffset = getLineOffset(context);
			int uriStartIdx = lineOffset + uriRelativeStartIdx;
			int uriEndIdx = lineOffset + uriRelativeEndIdx;
			
			
			if (context.getInvocationOffset() < uriStartIdx || context.getInvocationOffset() > uriEndIdx)
				return proposals;
		
			String currentString = line.substring(uriRelativeStartIdx, uriRelativeEndIdx);
			String pathPlain = currentString.replace("mn://", "");
			String[] tokens = pathPlain.split("/");
			
			int pathStartIdx = uriStartIdx + tokens[0].length() + 5;
			boolean cursorInService = context.getInvocationOffset() < pathStartIdx;
			
			if (cursorInService) {
				return fullServiceProposal(uriStartIdx, currentString.length());
			} else {
				
				Optional<ServiceAPI> service = fullAPI.stream()
			            .filter(s -> s.getServiceUri().equals("mn://" + tokens[0]))
			            .findFirst();
				
				if (!service.isPresent())
					return fullServiceProposal(uriStartIdx, currentString.length());
				
				for (ListenerAPI listenerApi : service.get().getListeners()) {
					proposals.add(new CompletionProposal(
							listenerApi.getListenerUri(), pathStartIdx, uriEndIdx - pathStartIdx, listenerApi.getListenerUri().length()));
				}
			}
			
	  	} catch (BadLocationException e) {
			System.out.println("Bad Matcher Location: " +  context.getInvocationOffset());
		} 
	  	
	  	return proposals;
	}
	
	List<ICompletionProposal> fullServiceProposal(int replacementOffset, int replacementLength) {
		List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
		for (ServiceAPI serviceApi : fullAPI) {
			proposals.add(new CompletionProposal(
					serviceApi.getServiceUri(), replacementOffset, replacementLength, serviceApi.getServiceUri().length()));
		}
		return proposals;
	}

	@Override
	public List<IContextInformation> computeContextInformation(ContentAssistInvocationContext context, IProgressMonitor monitor) {
		
		ContextInformation info = new ContextInformation("Context", "Info");
		List<IContextInformation> proposals = new ArrayList<IContextInformation>();
		proposals.add(info);
		System.out.println("Complete Proposals Info");
		
		return proposals;
	}

	@Override
	public String getErrorMessage() {
		
		System.out.println("Compute Complete Proposals Error Msg");
		return null;
	}

	@Override
 	public void sessionEnded() {
		System.out.println("Session End");
	}
	
	private static List<String> filterAPI(List<String> apiList, String startChars) {
		List<String> copy = new ArrayList<>();
		for(String apiCall : apiList)
		{
		    if(apiCall.startsWith(startChars))
		    {
		    	copy.add(apiCall);
		    }
		}
		return copy;
	}
	
	protected String getCurrentLine(final ContentAssistInvocationContext context) throws BadLocationException {
		IDocument document = context.getDocument();
		int lineNumber = document.getLineOfOffset(context.getInvocationOffset());
		IRegion lineInformation = document.getLineInformation(lineNumber);
		return document.get(lineInformation.getOffset(), lineInformation.getLength());
	}
	 
	protected int getLineOffset(final ContentAssistInvocationContext context) throws BadLocationException {
		IDocument document = context.getDocument();
		int lineNumber = document.getLineOfOffset(context.getInvocationOffset());
		IRegion lineInformation = document.getLineInformation(lineNumber);
		return lineInformation.getOffset();
	}
}
