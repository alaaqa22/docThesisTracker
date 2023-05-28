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
    private String searchField;

    private String searchItem;
    private String filterItem;
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

    public void setSearchField (String searchField) {
        this.searchField = searchField;
    }

    public void setSearchItem (String searchItem) {
        this.searchItem = searchItem;
    }

    public void setFilterItem (String filterItem) {
        this.filterItem = filterItem;
    }

    public String getSearchField () {
        return searchField;
    }

    public String getSearchItem () {
        return searchItem;
    }

    public String getFilterItem () {
        return filterItem;
    }
}
