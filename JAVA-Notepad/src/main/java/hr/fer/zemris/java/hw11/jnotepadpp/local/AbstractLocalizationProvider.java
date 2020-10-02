package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that implements {@linkplain ILocalizationProvider} with additional method {@link fire} for notifying listeners
 * @author Andi Škrgat
 * @version 1.0
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider, ILocalizationListener{
	
	/**
	 * Collection of listeners
	 */
	private List<ILocalizationListener> listeners = new ArrayList<ILocalizationListener>();
	
	/**
	 * Adds listener to the collection of listeners
	 * @param listener listener that will be subscribed for changes
	 */
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Deregisters listener
	 * @param listener listener that doesn't want anymore listen changes
	 */
	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}

	/**
	 * @return the listeners
	 */
	public List<ILocalizationListener> getListeners() {
		return listeners;
	}
	
	/**
	 * Informs listeners of some change
	 */
	public void fire() {
		for(ILocalizationListener listener: getListeners()) {
			listener.localizationChanged();
		}
	}
	
	
	
	
	
}
