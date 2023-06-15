package dtt.business.utilities;

import dtt.dataAccess.repository.Postgres.CirculationDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Circulation;
import dtt.global.utilities.ConfigReader;
import jakarta.inject.Inject;

import java.util.List;

/**
 * Abstract class which implements a list pagination.
 *
 * @author Alaa Aasem
 */
public abstract class Pagination<T> {

    protected int maxItems = Integer.parseInt(ConfigReader.getProperty(ConfigReader.PAGINATION_MAX_ITEMS));
    private int currentPage;
    private int totalNumOfPages;
    private List<T> entries;
    private String sortColumn;

    public Pagination(){
        setCurrentPage(1);
    }

    /**
     * Load data of next page, unless you are already on the last page.
     */
    public void nextPage() {
        if (currentPage < getTotalNumOfPages()) {
            setCurrentPage(currentPage + 1);
            loadData();
        }
    }


    /**
     * Needs to be overwritten to load the correct data items for the page.
     */
    public abstract void loadData();

    /**
     * Load data on last page.
     */
    public void lastPage() {
        setCurrentPage(getTotalNumOfPages());
        loadData();

    }

    /**
     * Load data on page 1.
     */
    public void firstPage() {
        setCurrentPage(1);
        loadData();

    }

    /**
     * Sort the data in the list by a certain column.
     *
     * @param column column identifier to sort by.
     */
    public void sortBy(String column) {
        sortColumn = column;
        loadData();

    }

    public void previousPage() {
        if (currentPage > 1) {
            setCurrentPage(currentPage - 1);
            loadData();
        }

    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * Get the total pages that will be needed to arrange the pages.
     *
     * @return Total number of Pages.
     */
    public int getTotalNumOfPages() {
     /*   totalNumOfPages = circulationDAO.getTotalCirculationNumber(new Circulation(), new Transaction())
                / maxItems;*/
        return totalNumOfPages;
    }

    public void setTotalNumOfPages(int totalNumOfPages) {
        this.totalNumOfPages = totalNumOfPages;
    }

    public List<T> getEntries() {
        return entries;
    }

    public void setEntries(List<T> entries) {
        this.entries = entries;
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public int getMaxItems () {
        return maxItems;
    }
    public boolean isFirstPage(){
        return currentPage ==1 ;
    }
    public boolean isLastPage(){
        return currentPage ==totalNumOfPages ;
    }
}
