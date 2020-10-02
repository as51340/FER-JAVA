package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;

/**
 * Implementation of action that will be done when user wants to create new file
 * @author Andi Škrgat
 * @version 1.0
 */
public class NewFileAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to the existing documents of documents
	 */
	private MultipleDocumentModel documents;
	
	/**
	 * Initializes all needed parameters for performing this action on some {@linkplain DefaultMultipleDocumentModel}
	 * @param documents reference to the existing documents of documents
	 */
	public NewFileAction(MultipleDocumentModel documents) {
		this.documents = documents;
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		documents.createNewDocument();
	}

}
