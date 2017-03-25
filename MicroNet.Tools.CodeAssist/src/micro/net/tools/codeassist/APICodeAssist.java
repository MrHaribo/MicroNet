package micro.net.tools.codeassist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.jdt.ui.text.java.IJavaCompletionProposalComputer;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;

public class APICodeAssist implements IJavaCompletionProposalComputer {

	private final static List<String> fullAPI = Arrays.asList(
		"mn://myService/someMethod",
		"mn://myService/someMethod/additionalStuff", 
		"mn://myService/plain/some",
		"mn://hisService/plain/some");
	
	
	@Override
	public void sessionStarted() {
		System.out.println("Session Start");
	}

	@Override
	public List<ICompletionProposal> computeCompletionProposals(ContentAssistInvocationContext context, IProgressMonitor monitor) {

		List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();

	  	try {

			String line =  getCurrentLine(context);
			
			
			if (!line.contains("mn://"))
				return proposals;
			
			int idx = line.indexOf("mn://");
			int lineOffset = getLineOffset(context);
			int replacementOffset = lineOffset + idx;
			int replacementLength = context.getInvocationOffset() - replacementOffset;
			
			String currentString = line.substring(idx, idx + replacementLength);
			List<String> apiCalls = filterAPI(fullAPI, currentString);
			
			for (String apiCall : apiCalls) {
				
				String s = "<b>tags:</b>" + apiCall;
				proposals.add(new CompletionProposal(s, replacementOffset, replacementLength, apiCall.length()));
			}
			
			System.out.println("Token: " + line);
		} catch (BadLocationException e) {
			System.out.println("Bad Matcher Location: " +  context.getInvocationOffset());
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
		
		System.out.println("Complete Proposals Error");
		return "You SUck";
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
	
	private static final Pattern LINE_DATA_PATTERN = Pattern.compile(".*?([^\\p{Alnum}]?)(\\p{Alnum}*)$");

	 /**
	  * Extract context relevant information from current line. The returned matcher locates the last alphanumeric word in the line and an optional non
	  * alphanumeric character right before that word. result.group(1) contains the last non-alphanumeric token (eg a dot, brackets, arithmetic operators, ...),
	  * result.group(2) contains the alphanumeric text. This text can be used to filter content assist proposals.
	  * 
	  * @param context
	  *            content assist context
	  * @return matcher containing content assist information
	  * @throws BadLocationException
	  */
	 protected Matcher matchLastToken(final ContentAssistInvocationContext context) throws BadLocationException {
		 String data = getCurrentLine(context);
		 return LINE_DATA_PATTERN.matcher(data);
	 }

	 /**
	  * Extract text from current line up to the cursor position
	  * 
	  * @param context
	  *            content assist context
	  * @return current line data
	  * @throws BadLocationException
	  */
	 protected String getCurrentLine(final ContentAssistInvocationContext context) throws BadLocationException {
		 IDocument document = context.getDocument();
		 int lineNumber = document.getLineOfOffset(context.getInvocationOffset());
		 IRegion lineInformation = document.getLineInformation(lineNumber);

		 return document.get(lineInformation.getOffset(), context.getInvocationOffset() - lineInformation.getOffset());
	 }
	 
	 protected int getLineOffset(final ContentAssistInvocationContext context) throws BadLocationException {
		 IDocument document = context.getDocument();
		 int lineNumber = document.getLineOfOffset(context.getInvocationOffset());
		 IRegion lineInformation = document.getLineInformation(lineNumber);
		 return lineInformation.getOffset();
	 }
}
