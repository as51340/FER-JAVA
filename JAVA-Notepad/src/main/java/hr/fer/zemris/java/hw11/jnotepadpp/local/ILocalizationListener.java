package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Interface for listeners that are going to be notified when the selected language changes.
 * @author Andi Škrgat
 * @version 1.0
 */
public interface ILocalizationListener {

	/**
	 * Method that will perform GUI update
	 */
	void localizationChanged();
}
