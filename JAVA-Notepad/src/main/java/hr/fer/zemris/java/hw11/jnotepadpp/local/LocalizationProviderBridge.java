package hr.fer.zemris.java.hw11.jnotepadpp.local;

public class LocalizationProviderBridge extends AbstractLocalizationProvider implements ILocalizationListener, ILocalizationProvider{

	/**
	 * Private variable that saves information about connection between some object and {@linkplain LocalizationProvider}
	 */
	private boolean connected = false;
	
	/**
	 * Reference to the provided that will obtain translation of some key
	 */
	private ILocalizationProvider provider;
	
	/**
	 * 
	 * @param provider that will get translation of some key
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
	}
	
	@Override
	public String getString(String key) {
		return provider.getString(key);
	}

	/**
	 * Adds itself as listener if it hasn't been already added
	 */
	public void connect() {
		if(connected == true) {
			return;
		}
		connected = true;
		provider.addLocalizationListener(this);
	}
	
	/**
	 * Removes itself as listener
	 */
	public void disconnect() {
		connected = false;
		provider.removeLocalizationListener(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCurrentLanguage() {
		return provider.getCurrentLanguage();
	}

	@Override
	public void localizationChanged() {
		
	}
	

}
