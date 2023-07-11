package dtt.global.tansport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *  Represents a User in the System.
 *   @author Hadi Abou Hassoun
 */
public class User {
    private static final Logger LOGGER = LogManager.getLogger(User.class);
    private int id; // The ID of the user
    private String password;
    private String passwordSalt;
    private String passwordHashed;
    private String firstName; // The first name of the user
    private String lastName; // The last name of the user
    private String email; // The email of the user
    private AccountState accountState; // The account state of the user
    private Map<Faculty, UserState> userState; // The user state map

    private boolean loggedIn; // Indicates whether the user is currently logged in.

    private int roleId; // The role ID of the user

    private LocalDate birthDate;

    private UserState currentUserState;



    /**
     * Default constructor for the User class.
     */


    public User() {
        LOGGER.debug("User() called.");
        userState = new HashMap<> ();
    }


    /**
     * Sets the ID of the user.
     * @param id The ID of the user
     */
    public void setId(int id) {
        LOGGER.debug("setId called: " + id);
        this.id = id;
    }

    /**
     * Sets the first name of the user.
     * @param firstName The first name of the user
     */
    public void setFirstName(String firstName) {
        LOGGER.debug("setFirstName() called: " + firstName);
        this.firstName = firstName;
    }

    /**
     * Sets the last name of the user.
     * @param lastName The last name of the user
     */
    public void setLastName(String lastName) {
        LOGGER.debug("setLastName() called: " + lastName);
        this.lastName = lastName;
    }

    /**
     * Sets the role ID of the user.
     * @param roleId The role ID of the user
     */
    public void setRoleId (int roleId) {
        this.roleId = roleId;
    }

    /**
     * Sets the email of the user.
     * @param email The email of the user
     */
    public void setEmail(String email) {
        LOGGER.debug("setEmail() called: " + email);
        this.email = email;
    }


    /**
     * Sets the account state of the user.
     * @param accountState The account state of the user
     */
    public void setAccountState(AccountState accountState) {
        LOGGER.debug("setAccountState() called: " + accountState.toString());
        this.accountState = accountState;
    }

    /**
     * Sets the user state map.
     * @param userState The user state map
     */
    public void setUserState(Map<Faculty, UserState> userState) {
        LOGGER.debug("setUserState() called.");
        this.userState = userState;
    }

    /**
     * Sets the login status of the user.
     *
     * @param loggedIn the login status of the user (true if logged in; false if logged out)
     */
    public void setLoggedIn (boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * Returns the ID of the user.
     * @return The ID of the user
     */
    public int getId() {
        LOGGER.debug("getId() called: " + id);
        return id;

    }

    /**
     * Returns the role ID of the user.
     * @return The role ID of the user
     */
    public int getRoleId () {
        return roleId;
    }

    /**
     * Returns the first name of the user.
     * @return The first name of the user
     */
    public String getFirstName() {
        LOGGER.debug("getFirstName() called: " + firstName);
        return firstName;
    }

    /**
     * Returns the last name of the user.
     * @return The last name of the user
     */
    public String getLastName() {
        LOGGER.debug("getLastName() called:" + lastName);
        return lastName;
    }

    /**
     * Returns the email of the user.
     * @return The email of the user
     */
    public String getEmail() {
        LOGGER.debug("getEmail() called: " + email);
        return email;
    }

    /**
     * Returns the faculty ID of the user.
     * @return The faculty ID of the user
     */

    /**
     * Returns the account state of the user.
     * @return The account state of the user
     */
    public AccountState getAccountState() {
        LOGGER.debug("getAccountState() called.");
        return accountState;
    }

    /**
     * Returns the user state map.
     * @return The user state map
     */
    public Map<Faculty, UserState> getUserState() {
        return userState;
    }

    /**
     * Returns the current login status of the user.
     *
     * @return true if the user is logged in, false if the user is logged out
     */
    public boolean isLoggedIn () {
        return loggedIn;
    }

    public String getPasswordHashed () {
        return passwordHashed;
    }

    public String getPasswordSalt () {
        return passwordSalt;
    }

    public void setPasswordHashed (String passwordHashed) {
        this.passwordHashed = passwordHashed;
    }

    public void setPasswordSalt (String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }
    public LocalDate getBirthDate () {
        LOGGER.debug("getBirthDate() called: ");
        if (birthDate != null) {
            LOGGER.debug(birthDate.toString());
        }
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        LOGGER.debug("setBirthDate called: " + birthDate.toString());
        this.birthDate = birthDate;
    }
    public List<UserState> getUserStates () {

        return new ArrayList<> (getUserState ().values ());

    }
    public List<String> getUserFaculties () {

            List<Faculty> faculties = new ArrayList<> (getUserState ().keySet ());
            List<String> facultiesName = new ArrayList<> ();

            for (Faculty faculty : faculties) {
                facultiesName.add (faculty.getName ());

            }

            return facultiesName;

    }
    public void setCurrentUserState (UserState currentUserState) {
        this.currentUserState = currentUserState;
    }

    public UserState getCurrentUserState () {
        return currentUserState;
    }


}


