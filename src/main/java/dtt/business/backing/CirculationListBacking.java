package dtt.business.backing;


import dtt.business.utilities.Pagination;
import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.repository.Postgres.CirculationDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Circulation;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.JsonUtils;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;



/**
 * Backing bean for the circulation list page.
 *
 * @author Alaa Qasem
 */
@ViewScoped
@Named
public class CirculationListBacking implements Serializable {
    private Circulation filter;
    private Pagination<Circulation> circPagination;
    private List<Circulation> circulations;
    @Inject
    private CirculationDAO circDAO;
    @Inject
    private SessionInfo sessionInfo;
    private final Logger logger = LogManager.getLogger();


    /**
     * Constructor for the CirculationListBacking class.
     * Initializes the circPagination object with a custom implementation of the Pagination interface.
     * The loadData() method is overridden to load the circulations data using a transaction and commit the transaction.
     * Any SQLException that occurs during the transaction commit is caught and an error message is printed.
     */
    public CirculationListBacking() {
        this.circPagination = createPagination();
    }

    private Pagination<Circulation> createPagination() {
        return new Pagination<Circulation>() {
            @Override
            public void loadData() {
                int currentPage = getCurrentPage();
                int maxItems = getMaxItems ();
                if (currentPage <= 0 || maxItems <= 0) {
                    logger.error("Invalid currentPage or maxItems value.");
                }

                int offset = (currentPage - 1) * maxItems;
                int count = maxItems;

                Transaction transaction ;

                    transaction = new Transaction();
                    List<Circulation> cir = circDAO.getCirculations(filter, transaction, offset, count);
                    setEntries(cir);
                    circulations = cir;
                    transaction.commit();

            }
        };
    }



    /**
     * Initialize dto object.
     */
    @PostConstruct
    public void init(){

        filter = new Circulation ();
        loadCirculations();


    }


    /**
     * Loads the circulations data and updates the circulations list.
     */
    public void loadCirculations() {
        // Load data using the pagination object
        circPagination.loadData();

        // Update the circulations list with the loaded entries
        circulations = circPagination.getEntries();
    }



    public Pagination<Circulation> getCircPagination() {
        return circPagination;
    }

    public void setCircPagination(Pagination<Circulation> circPagination) {
        this.circPagination = circPagination;
    }

    public List<Circulation> getCirculations() {
        return circulations;
    }

    public void setCirculations(List<Circulation> circulations) {
        this.circulations = circulations;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public void setFilter (Circulation filter) {
        this.filter = filter;
    }

    public void setCircDAO (CirculationDAO circDAO) {
        this.circDAO = circDAO;
    }

    public Circulation getFilter () {
        return filter;
    }

    public CirculationDAO getCircDAO () {
        return circDAO;
    }
}
