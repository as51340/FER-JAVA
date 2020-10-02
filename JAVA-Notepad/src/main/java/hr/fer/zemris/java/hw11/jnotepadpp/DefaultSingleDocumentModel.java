package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.LookAndFeel;

public class DefaultSingleDocumentModel implements SingleDocumentModel{

	/**
	 * Path for opened document or <code>null</code> if new document is created
	 */
	private Path path;
	
	/**
	 * Text that is in document
	 */
	private JTextArea area;
	
	/**
	 * @return the listeners
	 */
	public List<SingleDocumentListener> getListeners() {
		return listeners;
	}

	/**
	 * Flag for checking if document has been modified
	 */
	private boolean modified = false;
	
	/**
	 * Listeners interested in changes of single document
	 */
	private List<SingleDocumentListener> listeners;
	
	/**
	 * Initializes parameters for this class 
	 * @param path path for opened document or <code>null</code> if new document is created 
	 * @param text text that will be assigned to {@linkplain JTextArea}
	 */
	public DefaultSingleDocumentModel(Path path, String text) {
		this.path = path;
		area = new JTextArea(text);
		LookAndFeel.installBorder(area, "BorderFactory.createEmptyBorder()");
		area.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				modified = true;
				for(SingleDocumentListener list: listeners) {
					list.documentModifyStatusUpdated(DefaultSingleDocumentModel.this);
				}
			}
		});
		listeners = new ArrayList<SingleDocumentListener>();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public JTextArea getTextComponent() {
		return this.area;
	}

	/**
	 * {@inheritDoc}
	 */
	public Path getFilePath() {
		return this.path;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setFilePath(Path path) {
		if(path == null) {
			JOptionPane.showConfirmDialog(null, "Implementation error, please continue...", "Error", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE, null);
			return;
		}
		this.path = path;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isModified() {
		return modified;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setModified(boolean modified) {
		this.modified = modified;
	}

	/**
	 * {@inheritDoc}
	 */
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		getListeners().remove(l);
	}

}
