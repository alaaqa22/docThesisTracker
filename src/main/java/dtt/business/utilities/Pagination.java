package dtt.business.utilities;



import dtt.global.utilities.ConfigReader;

import java.util.List;

/**
 * Abstract class which implements a list pagination.
 *
 * @author Alaa Qasem
 */
public abstract class Pagination<T> {

    protected int maxItems = Integer.parseInt(ConfigReader.getProperty(ConfigReader.PAGINATION_MAX_ITEMS));
    protected int totalNumOfPages;
    private int currentPage;
    private List<T> entries;
    private String sortColumn;


    public Pagination() {
        setCurrentPage(1);
    }

    /**
     * Load data of next page, unless you are already on the last page.
     */
    public void nextPage() {
        if (currentPage < totalNumOfPages) {
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
        setCurrentPage(totalNumOfPages);
        loadData();

    }

    /**
     * Load data on page 1.
     */
    public void firstPage() {
        setCurrentPage(1);
        loadData();

    }

    public void previousPage() {
        if (currentPage > 1) {
            setCurrentPage(currentPage - 1);
            this.loadData();
        }
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * Get the total number of needed pages.
     *
     */
    public abstract int getTotalNumOfPages();

    public void setTotalNumOfPages() {
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

    public int getMaxItems() {
        return maxItems;
    }

    public boolean isFirstPage() {
        return currentPage == 1;
    }

    public boolean isLastPage() {
        return currentPage == totalNumOfPages;
    }

    public boolean isTotalNumberOfPagesOne() {
        return getTotalNumOfPages() == 1;
    }


}
