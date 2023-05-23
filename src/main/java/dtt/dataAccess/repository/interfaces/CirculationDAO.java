package dtt.dataAccess.repository.interfaces;

import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Circulation;

public interface CirculationDAO {
	public void add(Circulation circulation, Transaction transaction);
	public void remove(Circulation circulation, Transaction transaction);
	public void update(Circulation circulation, Transaction transaction);
	public void getCirculation(Circulation circulation, Transaction transaction);
	public void getCirculations(Circulation circulation, Transaction transaction, int offset, int count);
}
