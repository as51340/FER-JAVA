package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Implementation of action that will be done when user wants to sort(in both directions) or remove duplicate lines
 * @author Andi Škrgat
 * @version 1.0
 */
public class SortAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Text component on which will perform transformations
	 */
	private JTextComponent comp;
	
	/**
	 * if op equals 0 then ascening sorting will be performed, if op equals 1 then descending sort, if 2 then all duplicate lines will be removed
	 */
	private int op;
	
	/**
	 * Initializes required parameters
	 * @param comp text component on which we will perform operations
	 * @param op parameters so we can distinguish required text transformations
	 */
	public SortAction(JTextComponent comp, int op) {
		this.comp = comp;
		this.op = op;
		if(op == 0) {
			this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
			this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl U"));	
		} else if(op == 1) {
			this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
			this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl I"));
		} else {
			this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
			this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Document doc = comp.getDocument();
		Element root = doc.getDefaultRootElement();
		int startPos = comp.getSelectionStart();
		int endPos = comp.getSelectionEnd();
		int startLine = root.getElementIndex(startPos);
		int startColumn = root.getElement(startLine).getStartOffset();
		int endLine = root.getElementIndex(endPos);
		int endColumn = root.getElement(endLine).getEndOffset();
		List<String> lines = new ArrayList<>();
		try {
			StringReader sr = new StringReader(comp.getText(startColumn, endColumn -startColumn));
			doc.remove(startColumn, endColumn-startColumn-1);
			BufferedReader br = new BufferedReader(sr);
			String line;
			while((line = br.readLine()) != null) {
				lines.add(line);
			}
			sr.close();
			br.close();
		} catch (BadLocationException | IOException e1) {
			e1.printStackTrace();
		}
		if(op == 0) {
			lines.sort(new MyComparator());
		} else if(op == 1) {
			lines.sort(Collections.reverseOrder(new MyComparator()));
		} else {
			Set<String> set = new LinkedHashSet<>(lines);
			lines.clear();
			lines.addAll(set);
		}
		try {
			int i = 1;
			for(String line: lines) {
				if(i != lines.size()) {
					line = line.concat("\n");
				} 
				doc.insertString(startColumn,line, null); 
				startColumn += line.length();
				i++;
			}
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Comparator for comparing lines in {@linkplain JNotepadPP} using installed language 
	 * @author Andi Škrgat
	 * @version 1.0
	 */
	private class MyComparator implements Comparator<String> {

		@Override
		public int compare(String o1, String o2) {
			Locale locale = new Locale(LocalizationProvider.getInstance().getCurrentLanguage());
			Collator collator = Collator.getInstance(locale);
			return collator.compare(o1, o2);
		}
		
	}

}
