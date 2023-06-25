package dtt.business.backing;


import dtt.business.utilities.Pagination;
import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.repository.interfaces.CirculationDAO;
import dtt.dataAccess.repository.postgres.FacultyDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Circulation;
import dtt.global.tansport.Faculty;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
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
    private final Logger logger = LogManager.getLogger ();
    @Inject
    private FacultyDAO facultyDAO;
    private static final Logger LOGGER = LogManager.getLogger (CirculationListBacking.class);
    boolean timeButton = false;
    boolean currentCirculation = false;


    /**
     * Constructor for the CirculationListBacking class.
     * Initializes the circPagination object with a custom implementation of the Pagination interface.
     * The loadData() method is overridden to load the circulations data using a transaction and commit the transaction.
     * Any SQLException that occurs during the transaction commit is caught and an error message is printed.
     */
    public CirculationListBacking () {
        this.circPagination = createPagination ();

    }

    private Pagination<Circulation> createPagination () {
        return new Pagination<Circulation> () {
            @Override
            public void loadData () {
                int currentPage = getCurrentPage ();
                int maxItems = getMaxItems ();
                if (currentPage <= 0 || maxItems <= 0) {
                    logger.error ("Invalid currentPage or maxItems value.");
                }


                int offset = (currentPage - 1) * maxItems;
                int count = maxItems;
                if (!timeButton) {

                    try (Transaction transaction = new Transaction ()) {
                        List<Circulation> cir = circDAO.getCirculations (filter, transaction, offset, count);
                        setEntries (cir);
                        circulations = cir;
                        transaction.commit ();
                    }
                } else {
                    if (currentCirculation) {
                        try (Transaction transaction = new Transaction ()) {
                            List<Circulation> cir = circDAO.getAllCurrentCirculations (filter, transaction, offset, count);
                            setEntries (cir);
                            circulations = cir;
                            transaction.commit ();
                        }
                    } else {
                        try (Transaction transaction = new Transaction ()) {
                            List<Circulation> cir = circDAO.getAllCompletedCirculations (filter, transaction, offset, count);
                            setEntries (cir);
                            circulations = cir;
                            transaction.commit ();
                        }
                    }


                }

            }

            @Override
            public int getTotalNumOfPages () {
                try (Transaction t = new Transaction ()) {
                    int totalNumOfPages;

                    if (!timeButton) {
                        totalNumOfPages = (int) Math.ceil ((double) (circDAO.getTotalCirculationNumber (filter, t)) / maxItems);
                    } else {
                        if (currentCirculation) {
                            totalNumOfPages = (int) Math.ceil ((double) (circDAO.getTotalCurrentCirculationNumber (filter, t)) / maxItems);
                        } else {
                            totalNumOfPages = (int) Math.ceil ((double) (circDAO.getTotalCompletedCirculationNumber (filter, t)) / maxItems);
                        }
                    }

                    t.commit ();
                    this.totalNumOfPages = totalNumOfPages;
                    return totalNumOfPages;
                }
            }
        };
    }


    /**
     * Initialize dto object.
     */
    @PostConstruct
    public void init () {
        logger.fatal ("start init");
        filter = new Circulation ();
        loadCirculations ();

    }


    /**
     * Loads the circulations data and updates the circulations list.
     */
    public void loadCirculations () {
        logger.fatal ("start loadCirculations");
        // Load data using the pagination object
        circPagination.loadData ();
    }


    public Pagination<Circulation> getCircPagination () {
        return circPagination;
    }

    public void setCircPagination (Pagination<Circulation> circPagination) {
        this.circPagination = circPagination;
    }

    public List<Circulation> getCirculations () {
        return circulations;
    }

    public void setCirculations (List<Circulation> circulations) {
        this.circulations = circulations;
    }

    public SessionInfo getSessionInfo () {
        return sessionInfo;
    }

    public void setSessionInfo (SessionInfo sessionInfo) {
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

    public String getFacultyName (int facultyId) {

        try (Transaction transaction = new Transaction ()) {

            Faculty faculty = facultyDAO.getFacultyById (facultyId, transaction);

            return faculty != null ? faculty.getName () : "N/A";
        }

    }


    public void showCompletedCirculations () {
        timeButton = true;
        currentCirculation = false;
        circPagination.setCurrentPage (1);
        loadCirculations ();


    }

    public void showCurrentCirculations () {
        timeButton = true;
        currentCirculation = true;
        circPagination.setCurrentPage (1);
        loadCirculations ();

    }

    public String getHeaderText () {

        if (timeButton) {
            if (currentCirculation) {
                return "Aktuelle Umläufe ";
            }
            return "Abgeschlossene Umläufe";
        }
        return "Alle Umläufe ";
    }

}




