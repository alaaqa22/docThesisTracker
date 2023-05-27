package dtt.business.backing;

import dtt.business.utilities.Pagination;
import dtt.global.tansport.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.util.List;

/**
 * Backing bean for the user list page.
 *
 * @author Alaa Aasem
 */
@ViewScoped
@Named
public class UserListBacking {

    private Pagination<User> userPagination;
    private List<User> users;
    private String search;
    /**
     *  Initialize the dto object.
     */
    public void init(){


    }


    public Pagination<User> getUserPagination() {
        return userPagination;
    }


    public void setUserPagination(Pagination<User> userPagination) {
        this.userPagination = userPagination;
    }


}
