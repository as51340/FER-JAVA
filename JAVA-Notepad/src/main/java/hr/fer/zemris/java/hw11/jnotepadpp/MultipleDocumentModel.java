package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * Model capable of holding zero, one or more documents. It stores current document on which user works and one that 
 * is shown to the user.
 * @author Andi Škrgat
 * @version 1.0
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel>{
	
	/**
	 * Creates new {@linkplain SingleDocumentModel} for user
	 * @return creates document
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * @returns current document on which user works
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * Loads document from given path
	 * @param path path from where document will be loaded, it must not be <code> null</code>, if it is null user gets 
	 * appropriate message explaining why error occurred
	 * @returns document from given path
	 */
	SingleDocumentModel loadDocument(Path path);
	
	/**
	 * Saves document to the given path, if it is not <code> null </code>, and if it is null the path associated to the 
	 * document is used. In the first case, path associated to the document is updated. If path is already mapped to some
	 * document, method fails and tells user that some file on that path is already opened.
	 * @param model reference to the {@linkplain SingleDocumentModel}-document that will be saved
	 * @param newPath new path on which document will be saved. It can be null
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * Closes specified document without any questions before. 
	 * @param model document that is about to be closed
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * Adds listeners that are interested in changes of this model
	 * @param l reference to the interested listener
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Removes specific listener from intern list of listeners
	 * @param l
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * @returns number of currently opened documents
	 */
	int getNumberOfDocuments();
	
	/**
	 * @param index index from which document will be taken
	 * @returns document at the specific index
	 */
	SingleDocumentModel getDocument(int index);

}
