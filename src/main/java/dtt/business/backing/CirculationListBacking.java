package dtt.business.backing;


import dtt.business.utilities.Pagination;
import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.repository.Postgres.CirculationDAO;
import dtt.global.tansport.Circulation;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

import java.util.List;

/**
 * Backing bean for the circulation list page.
 *
 * @author Alaa Qasem
 */
public class CirculationListBacking {
    Circulation circulation;
    private Pagination<Circulation> circPagination;
    private List<Circulation> circulations;
    private CirculationDAO circDAO;
    @Inject
    private SessionInfo sessionInfo;
    /**
     * Initialize dto object.
     */
    @PostConstruct
    public void init(){

    }

    /**
     * Get the pagination of the circulations.
     *
     * @param circulation
     * @return  The pagination of the circulations.
     */
    public Pagination<Circulation> filterCirculation(Circulation circulation){

        return circPagination;

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

    public CirculationDAO getCircDAO() {
        return circDAO;
    }

    public void setCircDAO(CirculationDAO circDAO) {
        this.circDAO = circDAO;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }


}
