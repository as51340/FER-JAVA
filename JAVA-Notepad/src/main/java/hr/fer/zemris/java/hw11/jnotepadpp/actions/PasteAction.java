package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;

/**
 * Action that will be executed when user wants to paste text he recently copied and stored it into buffer
 * @author Andi Škrgat
 * @version 1.0
 */
public class PasteAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to the existing documents of documents
	 */
	private MultipleDocumentModel documents;
	
	/**
	 * Initializes all needed parameters for performing acion on some {@linkplain DefaultMultipleDocumentModel}
	 * @param documents reference to the existing documents of documents
	 */
	public PasteAction(MultipleDocumentModel documents) {
		this.documents = documents;
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl V"));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTextArea area = documents.getCurrentDocument() == null ? null : documents.getCurrentDocument().getTextComponent();
		area.paste();
	}
}
