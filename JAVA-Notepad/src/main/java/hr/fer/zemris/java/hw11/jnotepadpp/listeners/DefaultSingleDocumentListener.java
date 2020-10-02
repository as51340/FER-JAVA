package hr.fer.zemris.java.hw11.jnotepadpp.listeners;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.utils.Utils;

/**
 * Implementation of {@linkplain SingleDocumentListener} that is used for changing icon when some document is modifed 
 * and for updating tab's title.
 * @author Andi Škrgat
 * @version 1.0
 */
public class DefaultSingleDocumentListener implements SingleDocumentListener {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((documents == null) ? 0 : documents.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof DefaultSingleDocumentListener))
			return false;
		DefaultSingleDocumentListener other = (DefaultSingleDocumentListener) obj;
		if (documents == null) {
			if (other.documents != null)
				return false;
		} else if (!documents.equals(other.documents))
			return false;
		return true;
	}

	/**
	 * Reference to the group of documents where change will be seen
	 */
	private DefaultMultipleDocumentModel documents;
	
	/**
	 * Initializing constructor
	 * @param documents existing documents
	 */
	public DefaultSingleDocumentListener(DefaultMultipleDocumentModel documents) {
		this.documents = documents;
	}
	
	@Override
	public void documentModifyStatusUpdated(SingleDocumentModel model) {
		int index = documents.getDocuments().indexOf(model);
		JTabbedPane pane = (JTabbedPane) documents;
		try {
			pane.setIconAt(index, Utils.getTabIcon("mod" + (model.isModified() == true ? "Red.png" : ".png")));
		} catch (IOException e) {
			JOptionPane.showConfirmDialog(null, "Implementation error, please continue...", "Error",
					JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null);
		}
	}

	@Override
	public void documentFilePathUpdated(SingleDocumentModel model) {
		int index = documents.getDocuments().indexOf(model);
		JTabbedPane pane = (JTabbedPane) documents;
		if(model.getFilePath() != null) {
			pane.setTitleAt(index, model.getFilePath().toFile().getName());
			pane.setToolTipTextAt(index, model.getFilePath().toFile().toString());
		}

	}
}
