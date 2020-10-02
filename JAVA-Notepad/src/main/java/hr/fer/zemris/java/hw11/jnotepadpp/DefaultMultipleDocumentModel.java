package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import hr.fer.zemris.java.hw11.jnotepadpp.listeners.DefaultSingleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Implementation of {@linkplain MultipleDocumentModel} that handles collection of {@linkplain SingleDocumentModel}, reference
 * to the current document model and a support for listeners management.
 * @author Andi Škrgat
 * @version 1.0
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Collection of opened documents
	 */
	private List<SingleDocumentModel> documents;
	
	/**
	 * @return the documents
	 */
	public List<SingleDocumentModel> getDocuments() {
		return documents;
	}

	/**
	 * Reference to the current document that user is currently modifying
	 */
	private SingleDocumentModel currentDocument;
	
	/**
	 * Collections of objects interested in changes of this model 
	 */
	private List<MultipleDocumentListener> listeners;

	/**
	 * Allocates memory for collection of opened documents and sets variable numberDocuments to zero.
	 */
	public DefaultMultipleDocumentModel() {
		documents = new ArrayList<SingleDocumentModel>();
		listeners = new ArrayList<MultipleDocumentListener>();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Iterator<SingleDocumentModel> iterator() {
		return new DocumentIterator();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel newDocument = new DefaultSingleDocumentModel(null,"");
		newDocument.addSingleDocumentListener(new DefaultSingleDocumentListener(this));
		for(MultipleDocumentListener list: listeners) {
			list.documentAdded(newDocument);	
		}
		return newDocument;
	}

	/**
	 * {@inheritDoc}
	 */
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	/**
	 * @param currentDocument the currentDocument to set
	 */
	public void setCurrentDocument(SingleDocumentModel currentDocument) {
		this.currentDocument = currentDocument;
	}

	/**
	 * {@inheritDoc}
	 */
	public SingleDocumentModel loadDocument(Path path) {
		if(path == null) {
			JOptionPane.showMessageDialog(null, "This file cannot be opened!", 
					"Error occurred", JOptionPane.DEFAULT_OPTION, null);
			return null;
		}
		SingleDocumentModel founded = null;
		for(SingleDocumentModel doc: this) {
			if(doc.getFilePath() != null && doc.getFilePath().toString().equals(path.toString()) == true) {
				founded = doc;
				for(MultipleDocumentListener list: listeners) {
					list.currentDocumentChanged(currentDocument, doc);
				}
				break;
			}
		}
		if(founded != null) {
			return founded;
		}
		if(getNumberOfDocuments() == 1 && currentDocument.isModified() == false) {
			for(MultipleDocumentListener list: listeners) {
				list.documentRemoved(currentDocument);
			}
			
		}
		String text = "";
		try {
			List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			for(String s:lines) {
				text = text.concat(s + "\n");
			}
			SingleDocumentModel loaded = new DefaultSingleDocumentModel(path, text);
			loaded.addSingleDocumentListener(new DefaultSingleDocumentListener(this));
			for(MultipleDocumentListener list: listeners) {
				list.documentAdded(loaded);
			}
			return loaded;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "This file cannot be opened!", 
					"Error occurred", JOptionPane.DEFAULT_OPTION, null);
			return null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		JTextArea text = model.getTextComponent();
		for(SingleDocumentModel doc: this) {
			if(doc.getFilePath() == null) {
				continue;
			}
			if(model.equals(doc) == false) {
				Path path = newPath == null ? model.getFilePath() : newPath;
				if(path.toString().equals(doc.getFilePath().toString()) == true) {
					JOptionPane.showConfirmDialog(this, "Specified file is already opened!", "Error", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE, null);
					for(MultipleDocumentListener listener: this.getListeners()) {
						listener.currentDocumentChanged(null, doc);
					}
					return;
				}
			}
		}
		if(newPath != null) {
			model.setFilePath(newPath);
		}
		try {
			StringReader sr = new StringReader(text.getText());
			BufferedReader br = new BufferedReader(sr);
			BufferedWriter bw = Files.newBufferedWriter(newPath == null ? model.getFilePath() : newPath, StandardCharsets.UTF_8);
			int cntLines = text.getLineCount();
			for(int i = 0; i < cntLines; i++) {
				String line = br.readLine();
				if(line != null) {
					bw.write(line);
				}
				if(i != cntLines -1) {
					bw.newLine();	
				}
			}
			bw.close();
			br.close();
			sr.close();
			((DefaultSingleDocumentModel) model).setModified(false);
			for(SingleDocumentListener listener: ((DefaultSingleDocumentModel) model).getListeners()) {
				listener.documentModifyStatusUpdated(model);
			}
			for(SingleDocumentListener listener: ((DefaultSingleDocumentModel) model).getListeners()) {
				listener.documentFilePathUpdated(model);
			}
			fireStateChanged();
		} catch(IOException ex) {
			JOptionPane.showConfirmDialog(this, "Could not save this file!", "Error", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE, null);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void closeDocument(SingleDocumentModel model) {
		for(MultipleDocumentListener list: listeners) {
			list.documentRemoved(model);
		}
		if(documents.size() == 0) {
			createNewDocument();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	/**
	 * @return the listeners
	 */
	public List<MultipleDocumentListener> getListeners() {
		return listeners;
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}
	
	/**
	 * {@inheritDoc}-
	 */
	public int getNumberOfDocuments() {
		return documents.size();
	}

	/**
	 * {@inheritDoc}
	 */
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}
	
	/**
	 * Iterates through all documents and checks if user wants to save modified and unsaved documents
	 */
	public boolean checkEnding() {
		boolean cancel = false;
		for(int i = getNumberOfDocuments() -1; i >= 0; i--) {
			SingleDocumentModel doc = getDocument(i);
			if(doc.isModified() == false) {
				continue;
			}
			String[] options = {"YES", "NO", "CANCEL"};
			String save = "Save";
			String file ="file";
			if(LocalizationProvider.getInstance().getCurrentLanguage().equals("en") == false) {
				options = new String[]{LocalizationProvider.getInstance().getString("YES"), LocalizationProvider.getInstance().getString("NO"), 
						LocalizationProvider.getInstance().getString("CANCEL")};
				save = LocalizationProvider.getInstance().getString("Save");
				file = LocalizationProvider.getInstance().getString("file");
			}
			String title = save + " " + file;
			int choice = JOptionPane.showOptionDialog(this, title + (doc.getFilePath() == null ? "(unnamed)" :
				doc.getFilePath().toFile().getName()),save  , JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options , null);
			if(choice == 0) {
				if(doc == null || doc.getFilePath() == null) {
					JFileChooser chs = new JFileChooser();
					int result = chs.showOpenDialog(null);
					if(result != JFileChooser.APPROVE_OPTION) {
						return false;
					}
					Path selectedFile = chs.getSelectedFile().toPath();
					saveDocument(doc, selectedFile);
				} else {
					saveDocument(doc, null);
				}
			} else if(choice == 1) {
				closeDocument(doc);
			} else {
				cancel = true;
				break;
			}
		}
		return cancel;
		
	}
	
	/**
	 * Intern iterator that will be used for iterating over collection of active documents.
	 * @author Andi Škrgat
	 * @version 1.0
	 */
	private class DocumentIterator implements Iterator<SingleDocumentModel> {
		
		/**
		 * Saves current position in the collection of documents
		 */
		private int index = 0;
		
		/**
		 * {@inheritDoc}
		 */
		public boolean hasNext() {
			return index < getNumberOfDocuments();
		}

		/**
		 * {@inheritDoc}
		 */
		public SingleDocumentModel next() {
			if(hasNext() == false) {
				throw new NoSuchElementException("There are no more elements in the list of documents");
			}
			return documents.get(index++);
		}
		
	}

}
