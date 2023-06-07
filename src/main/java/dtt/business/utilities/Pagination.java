package dtt.business.utilities;

import dtt.business.backing.UserListBacking;
import dtt.global.utilities.ConfigReader;

import java.util.List;

/**
 * Abstract class which implements a list pagination.
 *
 * @author Alaa Aasem
 */
public abstract class Pagination <T>{

    protected int maxItems = Integer.parseInt(ConfigReader.getProperty(ConfigReader.PAGINATION_MAX_ITEMS));
    private int currentPage;
    private int maxPerPage;
    private List<T> entries;
    private String sortColumn;
    /**
     *  Load data of next page, unless you are already on the last page.
     */
    public void nextPage(){

    }

    /**
     *  Load data of previous page, unless you are already on the first page.
     */

    public void previousPage(){

    }

    public void setMaxItems (int maxItems) {
        this.maxItems = maxItems;
    }

    public void setCurrentPage (int currentPage) {
        this.currentPage = currentPage;
    }

    public void setMaxPerPage (int maxPerPage) {
        this.maxPerPage = maxPerPage;
    }

    public void setEntries (List<T> entries) {
        this.entries = entries;
    }

    public void setSortColumn (String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public int getMaxItems () {
        return maxItems;
    }

    public int getCurrentPage () {
        return currentPage;
    }

    public int getMaxPerPage () {
        return maxPerPage;
    }

    public List<T> getEntries () {
        return entries;
    }

    public String getSortColumn () {
        return sortColumn;
    }

    /**
     * Needs to be overwritten to load the correct data items for the page.
     */
    public abstract void loadData();
    /**
     * Load data on last page.
     */
    public void lastPage(){

    }
    /**
     * Load data on page 1.
     */
    public void firstPage(){

    }

    /**
     * Sort the data in the list by a certain column.
     */
    public void sortBy(){

    }

    /**
     * Calculate the number of pages.
     *
     * @return The number of pages.
     */
    public Integer calculateNumberOfPages(){
        return 0;
    }
}
