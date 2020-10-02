package hr.fer.zemris.java.gui.prim;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JList;

import org.junit.jupiter.api.Test;

public class PrimTest {

	@Test
	void testListModelWithNext() {
		PrimListModel listModel = new PrimListModel();
		assertEquals(2, listModel.next());
		assertEquals(3, listModel.next());
		assertEquals(5, listModel.next());
		assertEquals(7, listModel.next());
		assertEquals(11, listModel.next());
		assertEquals(13, listModel.next());
		assertEquals(17, listModel.next());
	}
	
	@Test
	void testListModelWithAdd() {
		PrimListModel listModel = new PrimListModel();
		for(int i = 0; i < 10; i++) {
			listModel.add();
		}
		assertEquals(2, listModel.getElementAt(1));
		assertEquals(3, listModel.getElementAt(2));
		assertEquals(5, listModel.getElementAt(3));
		assertEquals(7, listModel.getElementAt(4));
		assertEquals(11, listModel.getElementAt(5));
		assertEquals(13, listModel.getElementAt(6));
		assertEquals(17, listModel.getElementAt(7));
		assertEquals(19, listModel.getElementAt(8));	
	}
	
	@Test
	void testJListInitialState() {
		PrimListModel listModel = new PrimListModel();
		JList<Long> list1 = new JList<Long>(listModel);
		assertEquals(1, list1.getModel().getElementAt(0));
	}
	
	@Test
	void testJListRandomNumber() {
		PrimListModel listModel = new PrimListModel();
		JList<Long> list1 = new JList<Long>(listModel);
		for(int i = 0; i < 50; i++) {
			listModel.add();
		}
		assertEquals(59, list1.getModel().getElementAt(17));
		assertEquals(227, list1.getModel().getElementAt(49));
	}

}
