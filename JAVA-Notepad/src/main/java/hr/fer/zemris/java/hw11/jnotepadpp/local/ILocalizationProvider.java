package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Interface used for notifying listeners that the selected language changed
 * @author Andi Škrgat
 * @version 1.0
 */
public interface ILocalizationProvider {

	/**
	 * Method for registering listener
	 * @param list created listener that wants to be "subscribed" for changes
	 */
	void addLocalizationListener(ILocalizationListener listener);
	
	/**
	 * Deregisters listener
	 * @param listener listener that doesn't want anymore listen changes
	 */
	void removeLocalizationListener(ILocalizationListener listener);
	
	/**
	 * @param key text that will be translated
	 * @returns corresponding translation from file 
	 */
	String getString(String key);
	
	/**
	 * @returns user's selected language
	 */
	String getCurrentLanguage();
}
