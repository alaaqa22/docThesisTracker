package dtt.business.backing;


import dtt.business.utilities.Pagination;
import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.repository.Postgres.CirculationDAO;
import dtt.global.tansport.Circulation;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

/**
 * Backing bean for the circulation list page.
 *
 * @author Alaa Qasem
 */
@ViewScoped
@Named
public class CirculationListBacking {
    Circulation circulation;
    private Pagination<Circulation> circPagination;
    private List<Circulation> circulations;
    private CirculationDAO circDAO;
    @Inject
    private SessionInfo sessionInfo;
    private String searchField;

    private String filterItem;
    private String searchItem;

    /**
     * Initialize dto object.
     */
    @PostConstruct
    public void init(){

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


}
