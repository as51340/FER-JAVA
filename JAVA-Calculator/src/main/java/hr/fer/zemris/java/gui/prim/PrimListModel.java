package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Implementation of {@linkplain PrimList}. Every time method <code>next</code> is called, another prim number is generated.
 * @author Andi Škrgat
 * @version 1.0
 */
public class PrimListModel implements ListModel<Long>{

	/**
	 * Identifies current prim number
	 */
	private long currentPrim = 1;
	
	/**
	 * List of all generated prim number
	 */
	private List<Long> listPrim;
	
	/**
	 * List of all listeners interested in our implementation of this model
	 */
	private List<ListDataListener> listeners;
	
	/**
	 * Sets initial state for this list model
	 */
	public PrimListModel() {
		listeners = new ArrayList<ListDataListener>();
		listPrim = new ArrayList<Long>();
		add();
	}
	
	/**
	 * Number of generated prim numbers
	 */
	private int cnt = 0;
	
	/**
	 * Method used for obtaining next prim number
	 * @returns prim number
	 */
	protected long next() {
		cnt++;
		if(currentPrim == 1 || currentPrim == 2) {
			long rem = currentPrim;
			currentPrim++;
			return rem;
		}
		long rem = currentPrim;
		long i = currentPrim+1;
		boolean founded = true;
		while(true) {
			founded = true;
			for(long j = 2; j *j <= i; j++) {
				if(i % j == 0) {
					founded = false;
					break;
				}
			}
			if(founded == true) {
				currentPrim = i;
				break;
			}
			i++;
		}
		return rem;
	}
	
	/**
	 * Method that adds new prim number into list
	 */
	public void add() {
		listPrim.add(next());
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, cnt, cnt);
		for(ListDataListener l: listeners) {
			l.intervalAdded(event);
		}
	}

	@Override
	public int getSize() {
		return cnt; 
	}
	
	
	@Override
	public Long getElementAt(int index) {
		return listPrim.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners = new ArrayList<ListDataListener>(listeners);
		listeners.add(l);
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners = new ArrayList<ListDataListener>(listeners);
		listeners.remove(l);
	}
	

}
