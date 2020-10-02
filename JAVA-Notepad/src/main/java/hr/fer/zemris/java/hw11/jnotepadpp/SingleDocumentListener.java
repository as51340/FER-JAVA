package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Listener for {@linkplain SingleDocumentModel} that is interested in changes of some opened document
 * @author Andi Škrgat
 * @version 1.0
 */
public interface SingleDocumentListener {
	
	/**
	 * Notification that modification status was recently updated. 
	 * @param model
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * Specifies that file path of some document is opened(for example when some document is saved on new file path).
	 * @param model
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
	