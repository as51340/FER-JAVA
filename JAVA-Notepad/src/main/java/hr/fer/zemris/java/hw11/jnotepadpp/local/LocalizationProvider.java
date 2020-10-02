package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class that uses design pattern <code> Singleton </code>. It manages private variables {@link Bundle} and {@link language}. Default language
 * is set to english. It loades the resource bundle for this language and stores reference to it. 
 * @author Andi Škrgat
 * @version 1.0
 */
public class LocalizationProvider extends AbstractLocalizationProvider{

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bundle == null) ? 0 : bundle.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof LocalizationProvider))
			return false;
		LocalizationProvider other = (LocalizationProvider) obj;
		if (bundle == null) {
			if (other.bundle != null)
				return false;
		} else if (!bundle.equals(other.bundle))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		return true;
	}

	/**
	 * Private instance so we don't create for each frame new {@linkplain ResourceBundle}
	 */
	private static final LocalizationProvider instance = new LocalizationProvider();
	
	/**
	 * Variable that remembers current language
	 */
	private String language;
	
	/**
	 * Class that opens appropriate file on the disk(when method {@link getBundle} is called and gets the translation for key. Which language
	 * should be used is information stored in {@linkplain Locale}
	 */
	private ResourceBundle bundle;
	
	/**
	 * Constructot that does nothing
	 */
	private LocalizationProvider() {
		language = "en";
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.prijevodi", Locale.forLanguageTag(language));
	}
	
	/**
	 * Always returns same instance of this class
	 * @returns same instance of this class(we are more efficient with this way of implementation)
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
		localizationChanged();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getString(String key) {
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.prijevodi", Locale.forLanguageTag(this.language));
		return bundle.getString(key.toLowerCase());
	}

	@Override
	public void localizationChanged() {
		fire();
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}

}
