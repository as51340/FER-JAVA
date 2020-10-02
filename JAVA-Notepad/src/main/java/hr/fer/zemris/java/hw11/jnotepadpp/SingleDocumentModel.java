package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import javax.swing.JTextArea;

/**
 * Interface that represents one document, opened in {@linkplain JNotepadPP}. 
 * It stores infomation about file path from where document was loaded, document modification status and reference to Swing component 
 * which is used for editing-each document has its own editor component. When document is first opened, 
 * file path is set to <code> null</code>
 * @author Andi Škrgat
 * @version 1.0
 */
public interface SingleDocumentModel {

	/**
	 * @returns text that is within this document
	 */
	JTextArea getTextComponent();
	
	/**
	 * @returns path from where document is loaded or null if new document is opened
	 */
	Path getFilePath();
	
	/**
	 * Sets path to some file
	 * @param path new path that must not be <code> null </code>
	 */
	void setFilePath(Path path);
	
	/**
	 * @returns true if document has been modified, <code> false </code> otherwise
	 */
	boolean isModified();
	
	/**
	 * Sets modification status
	 * @param modified status that will be written over
	 */
	void setModified(boolean modified);
	
	/**
	 * Adds listeners that are interested in this document model
	 * @param l {@linkplain SingleDocumentListener} that will be added into intern list of listeners
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * Removes given listener from list i.e. listener is no longer interested in this document model
	 * @param l
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);

}
