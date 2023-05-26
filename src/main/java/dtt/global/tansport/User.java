package dtt.global.tansport;

import java.util.Map;
/**
 *  Represents a User in the System.
 */
public class User {
    private int id; // The ID of the user
    private String firstName; // The first name of the user
    private String lastName; // The last name of the user
    private String email; // The email of the user
    private int facultyId; // The faculty ID of the user
    private AccountState accountState; // The account state of the user
    private Map<Faculty, UserState> userState; // The user state map

    private boolean loggedIn;

    /**
     * Default constructor for the User class.
     */
    public User() {
    }


    /**
     * Sets the ID of the user.
     * @param id The ID of the user
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the first name of the user.
     * @param firstName The first name of the user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the last name of the user.
     * @param lastName The last name of the user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the email of the user.
     * @param email The email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the faculty ID of the user.
     * @param facultyId The faculty ID of the user
     */
    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    /**
     * Sets the account state of the user.
     * @param accountState The account state of the user
     */
    public void setAccountState(AccountState accountState) {
        this.accountState = accountState;
    }

    /**
     * Sets the user state map.
     * @param userState The user state map
     */
    public void setUserState(Map<Faculty, UserState> userState) {
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
        return id;
    }


    /**
     * Returns the first name of the user.
     * @return The first name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of the user.
     * @return The last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the email of the user.
     * @return The email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the faculty ID of the user.
     * @return The faculty ID of the user
     */
    public int getFacultyId() {
        return facultyId;
    }

    /**
     * Returns the account state of the user.
     * @return The account state of the user
     */
    public AccountState getAccountState() {
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
}

