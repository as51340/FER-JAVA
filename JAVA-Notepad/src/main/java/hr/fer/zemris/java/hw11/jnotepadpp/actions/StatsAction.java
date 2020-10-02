package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Implementation of action that will be done when user requests statistical info for current document. A number of all characters
 * is returned, together with a number of non-blank characters and number of lines 
 * @author Andi Škrgat
 * @version 1.0
 */
public class StatsAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to the existing documents
	 */
	private MultipleDocumentModel documents;
	
	/**
	 * Initializes all required parameters for some {@linkplain DefaultMultipleDocumentModel}
	 * @param documents reference to the existing documents
	 * @param frame that user could possibly close
	 */
	public StatsAction(MultipleDocumentModel documents) {
		this.documents = documents;
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_M);
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl M"));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		SingleDocumentModel curr = documents.getCurrentDocument();
		int allChars = curr.getTextComponent().getText().length(); //dal se tu ubraja new line i to
		int nonBlankChars = curr.getTextComponent().getText().replaceAll("\\s","").length();
		int lines = curr.getTextComponent().getLineCount();
		String your = "Your";
		String doc = "document";
		String has = "has";
		String blanks ="non blank";
		String chars = "characters";
		String and = "and";
		String line = (lines == 1 ? "line": "lines");
		String stats = "Infos";
		if(LocalizationProvider.getInstance().getCurrentLanguage().equals("en") == false) {
			your = LocalizationProvider.getInstance().getString(your);	
			doc = LocalizationProvider.getInstance().getString(doc);
			has = LocalizationProvider.getInstance().getString(has);
			blanks = LocalizationProvider.getInstance().getString(blanks);
			chars = LocalizationProvider.getInstance().getString(chars);
			and = LocalizationProvider.getInstance().getString(and);
			line = LocalizationProvider.getInstance().getString(line);
			stats = LocalizationProvider.getInstance().getString(stats);
		}
		String sentence = your + " " + doc + " "  + has + " " + Integer.toString(allChars) + " " + chars + ", " + blanks + " " + Integer.toString(nonBlankChars)
		+ " " + chars + " " + and
				+ " " + Integer.toString(lines) + " " + line + ".";
		
		JOptionPane.showOptionDialog(null, sentence , stats , JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, null, null);
	}

}
