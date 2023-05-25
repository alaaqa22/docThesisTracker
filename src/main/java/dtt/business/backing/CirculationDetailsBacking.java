package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.repository.Postgres.CirculationDAO;
import dtt.global.tansport.Circulation;
import dtt.global.tansport.Options;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

/**
 * Backing bean for the circulation details.
 *
 * @author Alaa Qasem
 */
public class CirculationDetailsBacking {
    @Inject
    private CirculationDAO circulationDAO;
    @Inject
    private SessionInfo sessionInfo;
    private Circulation circulation;

    /**
     * Initialized dto object.
     */
    @PostConstruct
    public void init(){

    }

    /**
     * Load details of a specified circulation.
     */
    public void loadCirculation(){
    }

    /**
     * Download the circulation.
     */
   public void download(){

    }

    /**
     * Remove the circulation.
     */
    public void remove(){


    }

    /**
     * Modify the circulation details.
     */
    public void modify(){

    }

    /**
     * Casts a vote for a specific choice.
     *
     * @param choice The choice to vote for.
     *
     */
    public  void vote(Options choice){

    }

    /**
     * Changes the vote to a new choice.
     *
     * @param newChoice The new choice to vote for.
     */
    public void changeVote(Options newChoice){

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
