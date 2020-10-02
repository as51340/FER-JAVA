package hr.fer.zemris.java.hw11.jnotepadpp.components;

import javax.swing.JMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Class that is specialization of {@link JMenu}. No action is set but class can handle localization changes
 * @author Andi Škrgat
 * @version 1.0
 */
public class NotepadMenu extends JMenu implements ILocalizationListener, ILocalizationProvider{

	private static final long serialVersionUID = 1L;

	/**
	 *  Reference to the {@linkplain ILocalizationProvider} for getting translation
	 */
	private ILocalizationProvider lp;
	
	/**
	 * Initializes parameters for {@linkplain NotepadMenu}
	 * @param key key for translation
	 * @param lp provided that will perform translation
	 */
	public NotepadMenu(String key, ILocalizationProvider lp) {
		this.lp = lp;
		lp.addLocalizationListener(this);
		setText(key);
	}
	
	@Override
	public void localizationChanged() {
		String trans = lp.getString(getText());
		setText(trans);
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
