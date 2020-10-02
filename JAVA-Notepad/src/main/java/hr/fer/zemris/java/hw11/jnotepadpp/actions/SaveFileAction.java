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
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;

/**
 * Implementation of action that will be done when user wants to create new file
 * @author Andi Škrgat
 * @version 1.0
 */
public class SaveFileAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to the existing documents of documents
	 */
	private MultipleDocumentModel documents;
	
	/**
	 * Flag that is used so we can make difference between save and save as command 
	 */
	private boolean saveAs;
	
	/**
	 * Initializes all needed parameters for performing action on some {@linkplain DefaultMultipleDocumentModel}
	 * @param documents reference to the existing documents of documents
	 */
	public SaveFileAction(MultipleDocumentModel documents, boolean saveAs) {
		this.documents = documents;
		this.saveAs = saveAs;
		if(this.saveAs == false) {
			this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
			this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));	
		} else {
			this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_SHIFT);
			this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift S"));
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		SingleDocumentModel currentDoc = documents.getCurrentDocument();
		if((currentDoc != null && currentDoc.getFilePath() == null) || saveAs == true) {
			JFileChooser chs = new JFileChooser();
			int result = chs.showOpenDialog(null);
			if(result != JFileChooser.APPROVE_OPTION) {
				return;
			}
			Path selectedFile = chs.getSelectedFile().toPath();
			documents.saveDocument(currentDoc, selectedFile);
		} else {
			documents.saveDocument(currentDoc, null);
		}
	}

}
