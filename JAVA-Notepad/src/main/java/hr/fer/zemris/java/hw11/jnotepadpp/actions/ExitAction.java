package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;

/**
 * Implementation of action that will be done when user wants to exit from {@linkplain JNotepadPP}
 * @author Andi Škrgat
 * @version 1.0
 */
public class ExitAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to the existing documents of documents
	 */
	private MultipleDocumentModel documents;
	
	/**
	 * Reference to the frame that user could possibly close
	 */
	private JFrame frame;
	
	/**
	 * Initializes all required parameters for some {@linkplain DefaultMultipleDocumentModel}
	 * @param documents reference to the existing documents of documents
	 * @param frame that user could possibly close
	 */
	public ExitAction(MultipleDocumentModel documents, JFrame frame) {
		this.documents = documents;
		this.frame = frame;
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F4);
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt F4"));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(((DefaultMultipleDocumentModel)documents).checkEnding() == false) {
			frame.dispose();
		}
		
	}

}
