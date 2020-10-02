package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import hr.fer.zemris.java.hw11.jnotepadpp.utils.Utils;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Text file editor capable of handling multiple documents opened in the same time. Operation supported for working with 
 * documents are: creating new document, opening existing document, saving document, saving-as document, close active 
 * document, remove active document, cut/copy/paste text, statistical info and exiting from application. 
 * @author Andi Škrgat
 * @version 1.0
 */
public class JNotepadPP extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Casted reference so we can use all methods
	 */
	private DefaultMultipleDocumentModel documents;
	
	/**
	 * GUI view of notepad
	 */
	private JTabbedPane notepad;
	
	/**
	 * Reference to the status bar
	 */
	private StatusBar bar;
	
	/**
	 * Reference to the menu bar
	 */
	private JMenuBar menuBar;
	
	/**
	 * Reference to the toolbar
	 */
	private JToolBar toolbar;
	
	/**
	 * Reference to the {@linkplain FormLocalizationProvider} so localization can be performed
	 */
	private FormLocalizationProvider flp;
	
	/**
	 * Initializes notepad
	 * @throws IOException 
	 */
	public JNotepadPP() throws IOException {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
//		setSize(1920, 1080);
		setSize(700 ,700);
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				if(documents.checkEnding() == false) {
					documents.getListeners().clear();
					dispose();
				}
 			}
		});
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		notepad = new DefaultMultipleDocumentModel();
		documents = (DefaultMultipleDocumentModel) notepad;
		initGUI();
		menuBar = new NotepadMenuBar(flp,documents);
		setJMenuBar(menuBar);
		toolbar = new NotepadToolbar(documents, this, flp);
		this.add(toolbar, BorderLayout.NORTH);
//		pack();
	}
	
	/**
	 * Inits GUI of this program
	 * @throws IOException 
	 */
	private void initGUI() throws IOException {
		this.add(notepad);
		bar = new StatusBar();
		documents.addMultipleDocumentListener(new DefaultMultipleDocumentListener());
		notepad.addChangeListener((e) -> {
			for(MultipleDocumentListener list: documents.getListeners()) {
				int index = notepad.getSelectedIndex();
				if(index < 0) {
					return;
				}
				SingleDocumentModel newDoc = documents.getDocument(index);
				list.currentDocumentChanged(documents.getCurrentDocument(), newDoc);
			}
			this.setTitle(documents.getCurrentDocument() == null || documents.getCurrentDocument().getFilePath() == null ? "(unnamed) - JNotepad++": documents.getCurrentDocument().
					getFilePath().toString() + " - JNotepad++");
		});
		documents.createNewDocument();
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(bar, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
	}
	
	/**
	 * Main method from where program starts
	 * @param args no arguments should be provided from user
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()-> {
			try {
				new JNotepadPP().setVisible(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Implementation of {@linkplain MultipleDocumentListener}
	 * @author Andi Škrgat
	 * @version 1.0
	 */
	private class DefaultMultipleDocumentListener implements MultipleDocumentListener {
		@Override
		public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
			if(previousModel == null && currentModel == null) {
				JOptionPane.showConfirmDialog(null, "Implementation error, please continue...", "Error", JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE, null);
				return;
			}
			if(currentModel != null) {
				int index = documents.getDocuments().indexOf(currentModel);
				documents.setSelectedIndex(index);
				documents.setCurrentDocument(currentModel);	
				updateStatusBar(currentModel);
			}
		}
		
		/**
		 * Private method that is called for updating status of status bar.
		 * @param model model that somehow changed and caused changes in status bar
		 */
		private void updateStatusBar(SingleDocumentModel model) {
			JTextComponent comp = model.getTextComponent();
			Document doc = comp.getDocument();
			Element root = doc.getDefaultRootElement();
			bar.getLength().setText("Length:" + comp.getText().length());
			int caretpos = comp.getCaretPosition();
			int linenum = root.getElementIndex(caretpos);
			bar.setLnVal(linenum +1);
			bar.getLn().setText("Ln:" + bar.getLnVal());
			int columnnum = caretpos - root.getElement(linenum)	.getStartOffset();
			bar.setColVal(columnnum +1);
			bar.getCol().setText("Col:" + bar.getColVal());
			bar.setSelVal(comp.getSelectedText() == null ? 0 : comp.getSelectedText().length());
			bar.getSel().setText("Sel:" + bar.getSelVal());
			if(menuBar == null) {
				return;
			}
			if(((NotepadMenuBar) menuBar).getChangeCase() != null) {
				for(Component c: ((NotepadMenuBar) menuBar).getChangeCase().getMenuComponents()) {
					if(bar.getSelVal() == 0) {
						c.setEnabled(false);	
					} else {
						c.setEnabled(true);
					}
				}
			}	
		}

		@Override
		public void documentAdded(SingleDocumentModel model) {
			model.getTextComponent().addCaretListener((e) -> {
				updateStatusBar(model);
			});
			model.getTextComponent().addMouseMotionListener(new MouseAdapter() {
				
				@Override
				public void mouseDragged(MouseEvent e) {
					updateStatusBar(model);
				}
			});
			String text = "";
			if(model.getFilePath() == null) {
				text +="(unnamed)";
			} else {
				text += model.getFilePath().toFile().getName();
			}
			Icon icon = null;
			try {
				icon = Utils.getTabIcon("mod.png"); //file hasn't been currently modified
			} catch (IOException e) {
				throw new IllegalArgumentException("Tab icon opening error");
			}
			documents.getDocuments().add(model);
			documents.addTab(text, icon, new JScrollPane(model.getTextComponent()));
			currentDocumentChanged(documents.getCurrentDocument(),model);
			String tooltip = documents.getCurrentDocument().getFilePath() == null ? "(unnamed)": documents.getCurrentDocument().getFilePath().toString();
			documents.setToolTipTextAt(documents.getNumberOfDocuments() -1, tooltip);
		}

		@Override
		public void documentRemoved(SingleDocumentModel model) {
			int index = documents.getDocuments().indexOf(model);
			if(index < 0) {
				return;
			}
			documents.removeTabAt(index);
			if(index > 0 ) {
				currentDocumentChanged(model, documents.getDocuments().get(index-1));
			} else if(index == 0) {
				currentDocumentChanged(model, null);
			}
			documents.getDocuments().remove(index);
			((DefaultSingleDocumentModel) model).getListeners().clear();
		}
	}
}
