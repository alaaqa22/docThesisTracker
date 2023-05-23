package dtt.dataAccess.repository.interfaces;

import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.User;

public interface UserDAO {
	public void add(User user, Transaction transaction);
	public void remove(User user, Transaction transaction);
	public void update(User user, Transaction transaction);
	public void getUser(User user, Transaction transaction);
	public void getUsers(User user, Transaction transaction, int offset, int count);
}
