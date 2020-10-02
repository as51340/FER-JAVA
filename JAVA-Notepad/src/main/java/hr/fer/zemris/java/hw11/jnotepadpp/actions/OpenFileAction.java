package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Path;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;

/**
 * Implementation of action that will be done when user wants to open existing file
 * @author Andi Škrgat
 * @version 1.0
 */
public class OpenFileAction extends AbstractAction{

	private static final long serialVersionUID = 1L;

	/**
	 * Reference to the existing documents of documents
	 */
	private MultipleDocumentModel documents;
	
	/**
	 * Initializes all needed parameters for performing action on some {@linkplain DefaultMultipleDocumentModel}
	 * @param documents reference to the existing documents of documents
	 */
	public OpenFileAction(MultipleDocumentModel documents) {
		this.documents = documents;
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl O"));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chs = new JFileChooser();
		int result = chs.showOpenDialog(null);
		if(result != JFileChooser.APPROVE_OPTION) {
			return;
		}
		Path selectedFile = chs.getSelectedFile().toPath();
		documents.loadDocument(selectedFile);
	}

}
