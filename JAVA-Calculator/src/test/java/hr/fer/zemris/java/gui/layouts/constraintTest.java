package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

public class constraintTest {
	
	@Test
	public void test1() { //okay
		JPanel p = new JPanel(new CalcLayout(2));
		JComponent comp1 = new JLabel("");
		JComponent comp2 = new JLabel("");
		p.add(comp1, "2,3");
		p.add(comp2, "3,4");
	}

	@Test
	public void test2() {
		JPanel p = new JPanel(new CalcLayout(2)); //r to small
		JComponent comp1 = new JLabel("");
		JComponent comp2 = new JLabel("");
		assertThrows(CalcLayoutException.class, ()-> {
				p.add(comp1, "0,3");
		});
	}
	@Test
	public void test3() {
		JPanel p = new JPanel(new CalcLayout(2)); //r to large
		JComponent comp1 = new JLabel("");
		JComponent comp2 = new JLabel("");
		assertThrows(CalcLayoutException.class, ()-> {
				p.add(comp1, "6,3");
		});
	}
	
	@Test
	public void test4() {
		JPanel p = new JPanel(new CalcLayout(2)); //s to small
		JComponent comp1 = new JLabel("");
		JComponent comp2 = new JLabel("");
		assertThrows(CalcLayoutException.class, ()-> {
				p.add(comp1, "2,0");
		});
	}
	
	@Test
	public void test5() { //s to large
		JPanel p = new JPanel(new CalcLayout(2));
		JComponent comp1 = new JLabel("");
		JComponent comp2 = new JLabel("");
		assertThrows(CalcLayoutException.class, ()-> {
				p.add(comp1, "2, 8");
		});
	}
	
	@Test
	public void test6() {
		JPanel p = new JPanel(new CalcLayout(2)); //first row not okay
		JComponent comp1 = new JLabel("");
		JComponent comp2 = new JLabel("");
		assertThrows(CalcLayoutException.class, ()-> {
				p.add(comp1, "1,2");
		});
	}
	
	@Test
	public void test7() {
		JPanel p = new JPanel(new CalcLayout(2)); //okay
		JComponent comp1 = new JLabel("");
		JComponent comp2 = new JLabel("");
		p.add(comp1, "1,7");
	}
	
	@Test
	public void test8() {
		JPanel p = new JPanel(new CalcLayout(2)); //everything fine
		JComponent comp1 = new JLabel("");
		JComponent comp2 = new JLabel("");
		p.add(comp1, "1,1");
	}
	
	@Test
	public void test9() { //same component
		JPanel p = new JPanel(new CalcLayout(2));
		JComponent comp1 = new JLabel("");
		assertThrows(CalcLayoutException.class, ()-> {
				p.add(comp1, "1,2");
				p.add(comp1, "2, 5");
		});
	}

	@Test
	public void test10() { //null component
		JPanel p = new JPanel(new CalcLayout(2));
		assertThrows(NullPointerException.class, ()-> {
				p.add(null, "1,2");
		});
	}
	

	@Test
	public void test11() { //constraint wrong
		JPanel p = new JPanel(new CalcLayout(2));
		JComponent comp1 = new JLabel("");
		assertThrows(IllegalArgumentException.class, ()-> {
				p.add(comp1, 2);
		});
	}
	

	@Test
	public void test12() { //okay
		JPanel p = new JPanel(new CalcLayout(2));
		JComponent comp1 = new JLabel("");
		String input = "2,2";
		RCPosition pos = Util.parse(input);
		p.add(comp1,  pos);
	}
	
	@Test
	public void test13() { //null constraint
		JPanel p = new JPanel(new CalcLayout(2));
		JComponent comp1 = new JLabel("");
		assertThrows(NullPointerException.class, ()-> {
				p.add(comp1, null);
		});
	}
	
	@Test
	public void test14() { //constraint not parsable
		JPanel p = new JPanel(new CalcLayout(2));
		JComponent comp1 = new JLabel("");
		assertThrows(IllegalArgumentException.class, ()-> {
				p.add(comp1, "2 2");
		});
	}
	
	@Test
	public void test15() { 
		JPanel p = new JPanel(new CalcLayout(2));
		JComponent comp1 = new JLabel("");
		String input ="21, 5";
		assertThrows(CalcLayoutException.class, ()-> {
				p.add(comp1,input);
		});
	}
	
	
	
}
