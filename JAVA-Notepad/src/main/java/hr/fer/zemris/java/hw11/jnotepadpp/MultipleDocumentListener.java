package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Listener for group of documents that are currently opened in {@linkplain JNotepadPP}
 * @author Andi Škrgat
 * @version 1.0
 */
public interface MultipleDocumentListener {
	
	/**
	 * Listener is notified that current document on which user worked changed. It's now possible that model on which 
	 * user worked was <code> null </code> and that new document is also <code> null </code>  
	 * @param previousModel document on which user worked before. It's possible that is <code> null </code> - that would
	 * mean that user didn't work on any document before he opened or created the new one. 
	 * @param currentModel new document on which user works. It's allowed that is <code> null </code>, i.e there are 
	 * no currently active documents. 
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	
	/**
	 * New document is opened 
	 * @param model opened document
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * User closed some document
	 * @param model removed document
	 */
	void documentRemoved(SingleDocumentModel model);

}
