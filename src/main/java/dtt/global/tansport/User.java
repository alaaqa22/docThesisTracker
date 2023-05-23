package dtt.global.tansport;

import java.util.Map;

public class User {
    private int id ;
    private String firstName;
    private String lastName;
    private String email;
    private int faculityId;
    private AccountState accountState;
    private Map<Faculity, UserState> userState;

    public User(){}

    public User (String firstName, String lastName, String email, int faculityId, AccountState accountState, Map<Faculity, UserState> userState) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.faculityId = faculityId;
        this.accountState = accountState;
        this.userState = userState;
    }

    public void setId (int id) {
        this.id = id;
    }

    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }

    public void setLastName (String lastName) {
        this.lastName = lastName;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public void setFaculityId (int faculityId) {
        this.faculityId = faculityId;
    }

    public void setAccountState (AccountState accountState) {
        this.accountState = accountState;
    }

    public void setUserState (Map<Faculity, UserState> userState) {
        this.userState = userState;
    }

    public int getId () {
        return id;
    }

    public String getFirstName () {
        return firstName;
    }

    public String getLastName () {
        return lastName;
    }

    public String getEmail () {
        return email;
    }

    public int getFaculityId () {
        return faculityId;
    }

    public AccountState getAccountState () {
        return accountState;
    }

    public Map<Faculity, UserState> getUserState () {
        return userState;
    }

}
