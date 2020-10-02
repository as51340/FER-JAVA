package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.CloseFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CopyAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CutAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ExitAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.NewFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.PasteAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.StatsAction;
import hr.fer.zemris.java.hw11.jnotepadpp.components.NotepadButton;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.utils.Utils;

/**
 * Notepad toolbar with actions for creating, opening, saving, closing document etc. Same functionality as {@linkplain NotepadMenuBar}
 * @author Andi Škrgat
 * @version 1.0
 */
public class NotepadToolbar extends JToolBar{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Reference to the documents
	 */
	private DefaultMultipleDocumentModel documents;
	
	/**
	 * Currently opened frame
	 */
	private JFrame frame;
	
	/**
	 * Reference to {@linkplain FormLocalizationProvider} for connecting to the {@linkplain LocalizationProvider}
	 */
	private FormLocalizationProvider flp;
	
	/**
	 * 
	 * @param documents existing documents
	 * @param frame opened frame
	 * @throws IOException if error occurrs during obtaining icons
	 */
	public NotepadToolbar(DefaultMultipleDocumentModel documents, JFrame frame, ILocalizationProvider flp) throws IOException {
		this.documents = documents;
		this.frame = frame;
		this.flp = (FormLocalizationProvider) flp;
		setFloatable(false);
		initializeToolbarIcons();
	}

	/**
	 * Initializes toolbar with all available actions encapsulated in appropriate button
	 * @param toolbar created toolbar
	 * @throws IOException 
	 */
	private void initializeToolbarIcons() throws IOException {
		JButton newButton = new NotepadButton("New", flp, new NewFileAction(documents));
		newButton.setIcon(Utils.getIcon("newFile.png"));
		JButton openButton = new NotepadButton("Open", flp, new OpenFileAction(documents));
		openButton.setIcon(Utils.getIcon("openFile.png"));;
		JButton saveButton = new NotepadButton("Save", flp, new SaveFileAction(documents, false));
		saveButton.setIcon(Utils.getIcon("saveFile.png"));
		JButton saveAsButton = new NotepadButton("Save As", flp, new SaveFileAction(documents, true));
		saveAsButton.setIcon(Utils.getIcon("saveAsIcon.png"));
		JButton closeButton = new NotepadButton("Close", flp, new CloseFileAction(documents));
		closeButton.setIcon(Utils.getIcon("closeFile.png"));
		JButton cutButton = new NotepadButton("Cut", flp, new CutAction(documents));
		cutButton.setIcon(Utils.getIcon("cutIcon.png"));
		JButton copyButton = new NotepadButton("Copy", flp, new CopyAction(documents));
		copyButton.setIcon(Utils.getIcon("copyIcon.png"));
		JButton pasteButton = new NotepadButton("Paste", flp, new PasteAction(documents));
		pasteButton.setIcon(Utils.getIcon("pasteIcon.png"));
		JButton exitButton = new NotepadButton("Exit", flp, new ExitAction(documents, frame));
		exitButton.setIcon(Utils.getIcon("exitIcon.png"));
		JButton statButton = new NotepadButton("Stats", flp, new StatsAction(documents));
		statButton.setIcon(Utils.getIcon("statisticsIcon.png"));
		add(newButton);
		add(openButton);
		add(saveButton);
		add(saveAsButton);
		add(closeButton);
		add(cutButton);
		add(copyButton);
		add(pasteButton);
		add(exitButton);
		add(statButton);
	}
}
