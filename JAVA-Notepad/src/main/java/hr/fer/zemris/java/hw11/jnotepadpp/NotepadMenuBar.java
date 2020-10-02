package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.ChangeCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CloseFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CopyAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CutAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.NewFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.PasteAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SortAction;
import hr.fer.zemris.java.hw11.jnotepadpp.components.NotepadMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.components.NotepadMenuItem;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Notepad's menu bar with options for editing and modifying documents 
 * @author Andi Škrgat
 * @version 1.0
 */
public class NotepadMenuBar extends JMenuBar{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Reference to the existing documents
	 */
	private DefaultMultipleDocumentModel documents;
	
	/**
	 * @return the languages
	 */
	public JMenu getLanguages() {
		return languages;
	}

	/**
	 * @param languages the languages to set
	 */
	public void setLanguages(JMenu languages) {
		this.languages = languages;
	}

	/**
	 * @return the file
	 */
	public JMenu getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(JMenu file) {
		this.file = file;
	}

	/**
	 * @return the edit
	 */
	public JMenu getEdit() {
		return edit;
	}

	/**
	 * @param edit the edit to set
	 */
	public void setEdit(JMenu edit) {
		this.edit = edit;
	}

	/**
	 * @return the tools
	 */
	public JMenu getTools() {
		return tools;
	}

	/**
	 * @param tools the tools to set
	 */
	public void setTools(JMenu tools) {
		this.tools = tools;
	}

	/**
	 * Menu for case changing
	 */
	private JMenu changeCase;
	
	/**
	 * Menu for languages
	 */
	private JMenu languages;
	
	/**
	 * File menu
	 */
	private JMenu file;
	
	/**
	 * Edit menu
	 */
	private JMenu edit;
	
	/**
	 * Tools menu
	 */
	private JMenu tools;
	
	/**
	 * @return the changeCase
	 */
	public JMenu getChangeCase() {
		return changeCase;
	}

	/**
	 * Reference to the {@linkplain FormLocalizationProvider} so localization can be performed
	 */
	private FormLocalizationProvider flp;
	
	/**
	 * Initializes all required parameters, flp for localization and documents 
	 * @param flp {@linkplain FormLocalizationProvider} for localization
	 * @param documents reference to the existing documents
	 */
	public NotepadMenuBar(FormLocalizationProvider flp, DefaultMultipleDocumentModel documents) {
		this.documents = documents;
		this.flp = flp;
		createMenuBar();
		initializeLanguages(languages);
	}
		
	/**
	 * Menu in which user will have option to choose language
	 * @param languages existing menu
	 */
	private void initializeLanguages(JMenu languages) {
		languages = new NotepadMenu("Languages", flp);
		JMenuItem en = new JMenuItem("en");
		JMenuItem de = new JMenuItem("de");
		JMenuItem hr = new JMenuItem("hr");
		en.addActionListener((e) -> {
			if(flp.getCurrentLanguage().equals("en") == false) {
				LocalizationProvider.getInstance().setLanguage("en");	
			}
			
		});
		de.addActionListener((e) -> {
			if(flp.getCurrentLanguage().equals("de") == false) {
				LocalizationProvider.getInstance().setLanguage("de");	
			}
		});
		hr.addActionListener((e) -> {
			if(flp.getCurrentLanguage().equals("hr") == false) {
				LocalizationProvider.getInstance().setLanguage("hr");	
			}
		});
		languages.add(en);
		languages.add(de);
		languages.add(hr);
		add(languages);
	}

	/**
	 * Creates menu bar
	 */
	private void createMenuBar() {
		file = new NotepadMenu("File", flp);
		edit = new NotepadMenu("Edit", flp);
		tools = new NotepadMenu("Tools", flp);
		createFileItem(file);
		createEditItem(edit);
		createToolsItem(tools);
		add(file);
		add(edit);
		add(tools);
	}
	
	/**
	 * Creates tools menu with possible actions of sorting and changing properties of letters
	 * @param tools created {@linkplain JMenu}
	 */
	private void createToolsItem(JMenu tools) {
		changeCase= new NotepadMenu("Letters", flp);
		JMenuItem upper = new NotepadMenuItem("Capital", flp, new ChangeCaseAction(0, documents.getCurrentDocument().getTextComponent()));
		upper.setEnabled(false);
		JMenuItem lower = new NotepadMenuItem("Lower", flp, new ChangeCaseAction(1, documents.getCurrentDocument().getTextComponent()));
		lower.setEnabled(false);
		JMenuItem invertCase = new NotepadMenuItem("Invert", flp, new ChangeCaseAction(2, documents.getCurrentDocument().getTextComponent()));
		invertCase.setEnabled(false);
		changeCase.add(upper);
		changeCase.add(lower);
		changeCase.add(invertCase);
		tools.add(changeCase);
		JMenu sort = new NotepadMenu("Sort", flp);
		JMenuItem ascend = new NotepadMenuItem("Ascending", flp, new SortAction(documents.getCurrentDocument().getTextComponent(),0));
		JMenuItem descend = new NotepadMenuItem("Descending",flp, new SortAction(documents.getCurrentDocument().getTextComponent(),1));
		JMenuItem unique = new NotepadMenuItem("Unique", flp, new SortAction(documents.getCurrentDocument().getTextComponent(),2));
		sort.add(ascend);
		sort.add(descend);
		sort.add(unique);
		tools.add(sort);
	}
	
	/**
	 * Creates items for cutting, copying and pasting text into
	 * @param edit reference to the existing {@linkplain JMenu} edit
	 */
	private void createEditItem(JMenu edit) {
		JMenuItem cut = new NotepadMenuItem("Cut", flp, new CutAction(documents));
		JMenuItem copy = new NotepadMenuItem("Copy", flp, new CopyAction(documents));
		JMenuItem paste =new NotepadMenuItem("Paste", flp, new PasteAction(documents));
		edit.add(cut);
		edit.add(copy);
		edit.add(paste);
	}
	
	/**
	 * Adds items for opening, saving, closing and creating new file
	 * @param file reference to the existing {@linkplain JMenu}
	 */
	private void createFileItem(JMenu file) {
		JMenuItem createNewDocument = new NotepadMenuItem("New", flp, new NewFileAction(documents));
		JMenuItem loadDocument = new NotepadMenuItem("Open", flp, new OpenFileAction(documents));
		JMenuItem saveDocument = new NotepadMenuItem("Save", flp, new SaveFileAction(documents, false));
		JMenuItem saveAsDocument = new NotepadMenuItem("Save as", flp, new SaveFileAction(documents, true));
		JMenuItem closeDocument = new NotepadMenuItem("Close", flp, new CloseFileAction(documents));
		file.add(createNewDocument);
		file.add(loadDocument);
		file.add(saveDocument);
		file.add(saveAsDocument);
		file.add(closeDocument);
	}
}
