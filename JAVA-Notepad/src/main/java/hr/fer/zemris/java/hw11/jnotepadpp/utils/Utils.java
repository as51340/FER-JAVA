package hr.fer.zemris.java.hw11.jnotepadpp.utils;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

import javax.swing.ImageIcon;

/**
 * Class for obtaining icons from resources
 * @author Andi Škrgat
 * @version 1.0
 */
public class Utils {

	/**
	 * @param text icon's name 
	 * @throws IOException 
	 * @returns icon that is on that path obtained from resources
	 */
	public static ImageIcon getIcon(String text) throws IOException {
		InputStream is = Utils.class.getResourceAsStream("../icons/" + text);
		if(is == null) {
			throw new NoSuchElementException("No such png file in directory");
		} 
		byte[] bytes = is.readAllBytes();
		is.close();
		
		ImageIcon icon = new ImageIcon(bytes);
		Image img = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		return new ImageIcon(img);
	}
	
	/**
	 * @param text file path to resources
	 * @returns scaled icon that will be use to get modification icon
	 * @throws IOException
	 */
	public static ImageIcon getTabIcon(String text) throws IOException {
		Image img = getIcon(text).getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH);
		return new ImageIcon(img);
	}
}
