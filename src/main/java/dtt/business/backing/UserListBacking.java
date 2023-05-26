package dtt.business.backing;

import dtt.business.utilities.Pagination;
import dtt.global.tansport.User;

import java.util.List;

/**
 * Backing bean for the user list page.
 *
 * @author Alaa Aasem
 */
public class UserListBacking {

    private Pagination<User> userPagination;
    private List<User> users;
    /**
     *  Initialize the dto object.
     */
    public void init(){


    }

    /**
     *
     * @param user
     * @return
     */
    public Pagination<User> filterUsers(User user){

        return userPagination;

    }


    /**
     *
     * @return
     */

    public Pagination<User> getUserPagination() {
        return userPagination;
    }

    /**
     *
     * @param userPagination
     */
    public void setUserPagination(Pagination<User> userPagination) {
        this.userPagination = userPagination;
    }

    /**
     *
     * @return
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     *
     * @param users
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }
}
