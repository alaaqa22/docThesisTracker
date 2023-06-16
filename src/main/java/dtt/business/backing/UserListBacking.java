package dtt.business.backing;

import dtt.business.utilities.Pagination;
import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.repository.Postgres.UserDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Circulation;
import dtt.global.tansport.Faculty;
import dtt.global.tansport.User;
import dtt.global.tansport.UserState;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private User filter;
    private Pagination<User> userPagination;
    private List<User> users;

    @Inject
    private UserDAO userDAO;

    @Inject
    private SessionInfo sessionInfo;
    private final Logger logger = LogManager.getLogger();

    public UserListBacking(){
        userPagination = createPagination();
    }

    private Pagination<User> createPagination() {
        return new Pagination<User>() {
            @Override
            public void loadData() {
                int currentPage = getCurrentPage();
                int maxItems = getMaxItems();
                if (currentPage <= 0 || maxItems <= 0) {
                    logger.error("Invalid currentPage or maxItems value.");
                }

                int offset = (currentPage - 1) * maxItems;
                int count = maxItems;

                Transaction transaction;


                    transaction = new Transaction();
                    Faculty faculty = null;
                    UserState auth = null;
                    users = userDAO.getUsers(filter, faculty, auth, transaction, offset, count);
                    setEntries(users);

                    transaction.commit();

            }
        };
    }



    @PostConstruct
    public void init(){
        filter = new User();
        loadUsers();
    }

    public void loadUsers() {
        userPagination.loadData();
        users = userPagination.getEntries();
    }

    public Pagination<User> getUserPagination() {
        return userPagination;
    }


    public void setUserPagination(Pagination<User> userPagination) {
        this.userPagination = userPagination;
    }

    public void setUsers (List<User> users) {
        this.users = users;
    }

    public List<User> getUsers () {
        return users;
    }
}

