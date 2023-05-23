package dtt.dataAccess.repository.interfaces;

import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Faculty;

public interface FacultyDAO {
	public void add(Faculty faculty, Transaction transaction);
	public void remove(Faculty faculty, Transaction transaction);
	public void update(Faculty faculty, Transaction transaction);
	public void getFaculties(Faculty faculty, Transaction transaction, int offset, int count);
}
