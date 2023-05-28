package dtt.business.utilities;

import dtt.global.utilities.ConfigReader;

/**
 * Abstract class which implements a list pagination.
 *
 * @author Alaa Aasem
 */
public abstract class Pagination <T>{

    protected int maxItems = Integer.parseInt(ConfigReader.getProperty("PAGINATION_MAX_ITEMS"));
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
