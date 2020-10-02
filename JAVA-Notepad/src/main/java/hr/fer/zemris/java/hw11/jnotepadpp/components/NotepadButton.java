package hr.fer.zemris.java.hw11.jnotepadpp.components;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Button used in {@linkplain JNotepadPP} implementation for setting action and for listenening localization changes
 * @author Andi Škrgat
 * @version 1.0
 */
public class NotepadButton extends JButton implements ILocalizationListener, ILocalizationProvider{

	private static final long serialVersionUID = 1L;

	/**
	 * Reference to the {@linkplain ILocalizationProvider} for getting translation
	 */
	private ILocalizationProvider lp;
	
	/**
	 * Initializes parameters for {@linkplain NotepadButton}
	 * @param key key for translation
	 * @param lp provided that will perform translation
	 * @param act action for this button
	 */
	public NotepadButton(String key, ILocalizationProvider lp, AbstractAction act) {
		this.lp = lp;
		lp.addLocalizationListener(this);
		setAction(act);
		setToolTipText(key);
	}
	
	@Override
	public void localizationChanged() {
		String trans = lp.getString(getToolTipText());
		setToolTipText(trans);
	}

	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		lp.addLocalizationListener(listener);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		lp.removeLocalizationListener(listener);
	}

	@Override
	public String getString(String key) {
		return lp.getString(key);
	}


	@Override
	public String getCurrentLanguage() {
		return lp.getCurrentLanguage();
	}

}
