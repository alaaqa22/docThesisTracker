package dtt.business.backing;

import dtt.business.utilities.Pagination;
import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.repository.interfaces.UserDAO;
import dtt.dataAccess.repository.postgres.FacultyDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Faculty;
import dtt.global.tansport.User;
import dtt.global.tansport.UserState;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
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
    private FacultyDAO facultyDAO;

    @Inject
    private SessionInfo sessionInfo;
    private final Logger logger = LogManager.getLogger ();
    Faculty faculty;
    String facultyName;
    UserState userState;


    /**
     * Constructs a new instance of UserListBacking.
     * Initializes the userPagination object.
     */
    public UserListBacking () {
        userPagination = createPagination ();
    }

    /**
     * Creates a Pagination object for managing user list pagination.
     * Defines how data is loaded and how the total number of pages is calculated.
     *
     * @return A new Pagination object
     */
    private Pagination<User> createPagination () {
        return new Pagination<User> () {
            @Override
            public void loadData () {

                int maxItems = getMaxItems ();
                if (getCurrentPage () <= 0 || maxItems <= 0) {
                    logger.error ("Invalid currentPage or maxItems value.");
                }
                faculty = getFacultyByName (facultyName);

                isValidCurrent ();
                int currentPage = this.getCurrentPage ();


                int offset = (currentPage - 1) * maxItems;
                int count = maxItems;

                if (!sessionInfo.adminInCurrentFaculty ()) {
                    faculty = sessionInfo.getCurrentFaculty ();
                }


                try (Transaction transaction = new Transaction ()) {

                    users = userDAO.getUsers (filter, faculty, userState, transaction, offset, count);
                    transaction.commit ();
                }

            }

            @Override
            public int getTotalNumOfPages () {

                try (Transaction t = new Transaction ()) {
                    int totalNumOfPages = (int) Math.ceil ((double) (userDAO.getTotalUserNumber (filter, faculty, userState, t))
                            / maxItems);
                    this.totalOfPages = totalNumOfPages;


                    return totalNumOfPages;
                }
            }
        };
    }


    // Creates a new User object to be used as a filter and loads the first page of users.
    @PostConstruct
    public void init () {


        filter = new User ();

        loadUsers ();
    }

    public void loadUsers () {
        userPagination.loadData ();
    }

    public Pagination<User> getUserPagination () {
        return userPagination;
    }


    public void setUserPagination (Pagination<User> userPagination) {
        this.userPagination = userPagination;
    }

    public void setUsers (List<User> users) {
        this.users = users;
    }

    public List<User> getUsers () {
        return users;
    }


    public void setFilter (User filter) {
        this.filter = filter;
    }

    public User getFilter () {
        return filter;
    }

    public void setFaculty (Faculty faculty) {
        this.faculty = faculty;
    }

    public void setUserState (UserState userState) {
        this.userState = userState;
    }

    public Faculty getFaculty () {
        return faculty;
    }

    public UserState getUserState () {
        return userState;
    }

    public UserState[] getAllUserStates () {
        UserState[] allUserStates = UserState.values ();
        List<UserState> allowedUserStates = new ArrayList<> ();

        for (UserState userState : allUserStates) {
            if (sessionInfo.isAdmin ()) {

                if (!userState.equals (UserState.ADMIN)) {
                    allowedUserStates.add (userState);
                }


            } else {
                if (!userState.equals (UserState.DEANERY) && !userState.equals (UserState.ADMIN)) {
                    allowedUserStates.add (userState);
                }
            }
        }

        return allowedUserStates.toArray (new UserState[0]);
    }

    public String[] getAllUserFaculties () {
        List<Faculty> faculties;
        try (Transaction transaction = new Transaction ()) {
            faculties = facultyDAO.getFaculties (transaction);
        }
        String[] facultyNames = new String[faculties.size ()];

        for (int i = 0; i < faculties.size (); i++) {
            facultyNames[i] = faculties.get (i).getName ();
        }

        return facultyNames;
    }

    public void setFacultyName (String facultyName) {
        this.facultyName = facultyName;
    }

    public String getFacultyName () {
        return facultyName;
    }

    private Faculty getFacultyByName (String name) {
        Faculty faculty = new Faculty ();
        faculty.setName (name);

        try (Transaction transaction = new Transaction ()) {
            faculty = facultyDAO.getFacultyByName (faculty, transaction);
        }

        return faculty;
    }

}

