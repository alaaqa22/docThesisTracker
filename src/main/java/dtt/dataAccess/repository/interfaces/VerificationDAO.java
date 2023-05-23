package dtt.dataAccess.repository.interfaces;

import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Verification;

public interface VerificationDAO {
	public void add(Verification verification, Transaction transaction);
	public void remove(Verification verification, Transaction transaction);
	public void update(Verification verification, Transaction transaction);
	public void getVerification(Verification verification, Transaction transaction);
}
