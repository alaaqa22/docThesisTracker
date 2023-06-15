package dtt.business.backing;

import dtt.business.utilities.Pagination;
import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.repository.Postgres.UserDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Circulation;
import dtt.global.tansport.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.jboss.logging.annotations.Pos;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * Backing bean for the user list page.
 *
 * @author Alaa Aasem
 */
@ViewScoped
@Named
public class UserListBacking implements Serializable {

    private Pagination<User> userPagination;
    private List<User> users;

    @Inject
    private SessionInfo sessionInfo;


    UserDAO userDAO;

    User filter;

    public UserListBacking(){
        userPagination = new Pagination<User>() {
            @Override
            public void loadData() {
                // Load User data using a transaction
                Transaction transaction = new Transaction();
                    int offset = (getCurrentPage() - 1) * getMaxItems ();
                    int count = getMaxItems ();

                List<User> userList = null;
                try {
                    userList = userDAO.getUsers(filter, transaction, offset, count);
                } catch (InvalidInputException e) {
                    throw new RuntimeException (e);
                }
                setEntries(userList);

                    // Commit the transaction
                    transaction.commit();

            }
        };
    }

    /**
     *  Initialize the dto object.
     */
    @PostConstruct
    public void init(){
        filter = new User ();
    }


    public Pagination<User> getUserPagination() {
        return userPagination;
    }


    public void setUserPagination(Pagination<User> userPagination) {
        this.userPagination = userPagination;
    }


}

