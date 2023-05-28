package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.exceptions.DataIntegrityException;
import dtt.dataAccess.repository.Postgres.CirculationDAO;
import dtt.global.tansport.Circulation;
import dtt.global.tansport.Options;
import dtt.global.tansport.Vote;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;

/**
 * Backing bean for the circulation details.
 *
 * @author Alaa Qasem
 */
@ViewScoped
@Named
public class CirculationDetailsBacking {
    @Inject
    private CirculationDAO circulationDAO;
    @Inject
    private SessionInfo sessionInfo;
    private Circulation circulation;

    private Vote vote;

    /**
     * Initialized circulation und Vote dto object.
     *
     *
     */
    @PostConstruct
    public void init(){

    }

    /**
     * Get the correct circulation id from the view param (will be called in a view action) and check if the
     * user allowed to view the circulation, then load all data that should be displayed from the datasource.
     *
     * @throws DataIntegrityException If the user has no permission to see the circulation.
     */

    public void loadCirculation(){
    }


    /**
     * Download the pdf belonging to a specific circulation.
     *
     * @param circulation The circulation to download.
     * @throws IOException If the download fails.
     */
   public void download(Circulation circulation){

    }

    /**
     * Delete a circulation.
     *
     * @param circulation The Circulation to remove.
     */
    public void remove(Circulation circulation){

    }

    /**
     * Modify the circulation details.
     *
     * @param circulation The Circulation to modify.
     */
    public void modify(Circulation circulation){

    }

    /**
     * Casts or change a vote for a specific choice.
     *
     * @param choice The choice to vote for.
     *
     */
    public  void vote(Options choice){

    }

    /**
     * Load all the votes of a specified circulation.
     *
     * @param circulation The circulation that the votes should be loaded from.
     */
    public void loadVotes(Circulation circulation){


    }

    public CirculationDAO getCirculationDAO() {
        return circulationDAO;
    }

    public void setCirculationDAO(CirculationDAO circulationDAO) {
        this.circulationDAO = circulationDAO;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public Circulation getCirculation() {
        return circulation;
    }

    public void setCirculation(Circulation circulation) {
        this.circulation = circulation;
    }
}
