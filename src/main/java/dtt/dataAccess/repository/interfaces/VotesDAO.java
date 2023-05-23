package dtt.dataAccess.repository.interfaces;

import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Circulation;
import dtt.global.tansport.Vote;

public interface VotesDAO {
	public void add(Vote vote, Circulation circulation, Transaction transaction);
	public void remove(Vote vote, Circulation circulation, Transaction transaction);
	public void update(Vote vote, Circulation circulation, Transaction transaction);
	public void getVotes(Vote vote, Circulation circulation, Transaction transaction);
}
