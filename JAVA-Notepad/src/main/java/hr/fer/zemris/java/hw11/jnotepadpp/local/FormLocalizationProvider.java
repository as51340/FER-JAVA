package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

/**
 * Registers itself as a {@linkplain WindowListener}. When frame is opened it calls {@link connect} and when frame is closed it calls {@link disconnect} 
 * @author Andi Škrgat
 * @version 1.0
 */
public class FormLocalizationProvider extends LocalizationProviderBridge{

	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				disconnect();
 			}
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
		});
	}
	
	@Override
	public void localizationChanged() {
		fire();
	}


}
