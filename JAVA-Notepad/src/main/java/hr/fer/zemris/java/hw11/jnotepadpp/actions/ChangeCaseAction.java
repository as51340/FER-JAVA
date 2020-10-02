package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

/**
 * Implementation of action that will be done when user wants to change case. This class will have variable so program could know whether
 * change to capital letters, lower case letter or to invert letters.
 * @author Andi Škrgat
 * @version 1.0
 */
public class ChangeCaseAction extends AbstractAction{

	private static final long serialVersionUID = 2L;
	
	/**
	 * If op equals 0 change to capital letters, 1 change to lower case letters and 2 inverting will be performed
	 */
	private int op;
	
	/**
	 * Component where user enters text
	 */
	private JTextComponent comp;
		
	/**
	 * Initializes all required parameters for performing this action
	 * @param op flag for operation
	 * @param bar reference to the status bar
	 * @param comp reference to the current text component where changes will be seen
	 */
	public ChangeCaseAction(int op, JTextComponent comp) {
		this.op = op;
		this.comp = comp;
		if(this.op == 0) {
			this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
			this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));	
		} else if(this.op == 1) {
			this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
			this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl R"));	
		} else {
			this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
			this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl T"));	
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String selText = comp.getSelectedText();
		if(selText == null) {
			return;
		}
		int startPos = comp.getSelectionStart();
		int endPos = comp.getSelectionEnd();
		Document doc = comp.getDocument();
		try {
			doc.remove(startPos, endPos- startPos);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		if(op == 0) {
			selText = selText.toUpperCase();
		} else if(op == 1) {
			selText = selText.toLowerCase();
		} else {
			String old = selText;
			selText = "";
			for(Character c: old.toCharArray()) {
				selText += (Character.isLowerCase(c) == true ? Character.toUpperCase(c): Character.toLowerCase(c)); 
			}
		}
		try {
			doc.insertString(startPos, selText,null );
			comp.setSelectionStart(startPos);
			comp.setSelectionEnd(endPos);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

}
