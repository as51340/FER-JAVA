package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * Implementation of status bar that shows us informations about number of lines, columns, length of selected or all text
 * @author Andi Škrgat
 * @version 1.0
 */
public class StatusBar extends JToolBar{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Label for document's length
	 */
	private JLabel length;
	
	/**
	 * Length value
	 */
	private int lengthVal;
	
	/**
	 * @param lengthVal the lengthVal to set
	 */
	public void setLengthVal(int lengthVal) {
		this.lengthVal = lengthVal;
	}

	/**
	 * @param lnVal the lnVal to set
	 */
	public void setLnVal(int lnVal) {
		this.lnVal = lnVal;
	}

	/**
	 * @param colVal the colVal to set
	 */
	public void setColVal(int colVal) {
		this.colVal = colVal;
	}

	/**
	 * @param selVal the selVal to set
	 */
	public void setSelVal(int selVal) {
		this.selVal = selVal;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(JLabel date) {
		this.date = date;
	}

	/**
	 * Label for number of lines in document
	 */
	private JLabel ln;
	
	/**
	 * Line value
	 */
	private int lnVal;
	
	/**
	 * Label for number of columns in document
	 */
	private JLabel col;
	
	/**
	 * Column value
	 */
	private int colVal;
	
	/**
	 * Length of selected text
	 */
	private JLabel sel;
	
	/**
	 * Selection value
	 */
	private int selVal;
	
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the lengthVal
	 */
	public int getLengthVal() {
		return lengthVal;
	}

	/**
	 * @return the lnVal
	 */
	public int getLnVal() {
		return lnVal;
	}

	/**
	 * @return the colVal
	 */
	public int getColVal() {
		return colVal;
	}

	/**
	 * @return the selVal
	 */
	public int getSelVal() {
		return selVal;
	}

	/**
	 * Label for showing time date
	 */
	private JLabel date;
	
	
	/**
	 * @return the date
	 */
	public JLabel getDate() {
		return date;
	}

	/**
	 * Constructor that calls method for initializing GUI components of status bar
	 */
	public StatusBar() {
		initGUI();
	}
	
	/**
	 * Initializes status bar
	 */
	private void initGUI() {
		length = new JLabel("Length:0");
		ln = new JLabel("ln:1");
		col = new JLabel("col:0");
		sel = new JLabel("sel:0");
		date = new JLabel();
		length.setFont(new Font("Courier", Font.BOLD, 14));
		ln.setFont(new Font("Courier", Font.BOLD, 14));
		col.setFont(new Font("Courier", Font.BOLD, 14));
		sel.setFont(new Font("Courier", Font.BOLD, 14));
		date.setFont(new Font("Courier", Font.BOLD, 14));
		
		Timer t = new Timer(1000, (e) ->  {
			date.setText(getTime());
		});
		t.start();
		this.setLayout(new GridLayout(1, 2));
		this.setBackground(new Color(212, 255, 255));
		this.setBorder(BorderFactory.createMatteBorder(1,0,0,0, Color.BLACK));
		JPanel infos = new JPanel();
		infos.setBackground(new Color(212, 255, 255));
		infos.setLayout(new GridLayout(1, 2));		
		infos.add(length);
		
		JPanel rightInfos = new JPanel();
		rightInfos.setBackground(new Color(212, 255, 255));
		ln.setVerticalAlignment(SwingConstants.BOTTOM);
		col.setVerticalAlignment(SwingConstants.BOTTOM);
		sel.setVerticalAlignment(SwingConstants.BOTTOM);
		rightInfos.add(ln);
		rightInfos.add(col);
		rightInfos.add(sel);
		infos.add(rightInfos);
		this.add(infos);
		this.add(date);
		date.setHorizontalAlignment(SwingConstants.RIGHT);
	}
	
	/**
	 * @param length the length to set
	 */
	public void setLength(JLabel length) {
		this.length = length;
	}

	/**
	 * @param ln the ln to set
	 */
	public void setLn(JLabel ln) {
		this.ln = ln;
	}

	/**
	 * @param col the col to set
	 */
	public void setCol(JLabel col) {
		this.col = col;
	}

	/**
	 * @param sel the sel to set
	 */
	public void setSel(JLabel sel) {
		this.sel = sel;
	}

	/**
	 * @returns current time in format "yyyy/MM/dd HH:mm:ss"
	 */
	private String getTime() {
		LocalDateTime time = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		String currTime = time.format(dtf);
		return currTime;
	}

	/**
	 * @return the length
	 */
	public JLabel getLength() {
		return length;
	}

	/**
	 * @return the ln
	 */
	public JLabel getLn() {
		return ln;
	}

	/**
	 * @return the col
	 */
	public JLabel getCol() {
		return col;
	}

	/**
	 * @return the sel
	 */
	public JLabel getSel() {
		return sel;
	}
	

}
