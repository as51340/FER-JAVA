package hr.fer.zemris.java.hw11.jnotepadpp.components;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Class that is specialization of {@link JMenuItem}. It sets action for this menu item and also handles localization changes
 * @author Andi Škrgat
 * @version 1.0
 */
public class NotepadMenuItem extends JMenuItem implements ILocalizationListener, ILocalizationProvider{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to the {@linkplain ILocalizationProvider} for getting translation
	 */
	private ILocalizationProvider lp;
	
	/**
	 * Initializes parameters for {@linkplain NotepadMenuItem}
	 * @param key key for translation
	 * @param lp provided that will perform translation
	 * @param sets action for this item
	 */
	public NotepadMenuItem(String key, ILocalizationProvider lp, AbstractAction act) {
		this.lp = lp;
		lp.addLocalizationListener(this);
		setAction(act);
		act.putValue(Action.NAME, key);
	}
	
	@Override
	public void localizationChanged() {
		String translation = lp.getString(getText());
		getAction().putValue(Action.NAME, translation);
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
