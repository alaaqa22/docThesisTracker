package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.exceptions.DataIntegrityException;
import dtt.dataAccess.exceptions.DataNotCompleteException;
import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.repository.Postgres.CirculationDAO;
import dtt.dataAccess.repository.Postgres.VoteDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Circulation;
import dtt.global.tansport.Options;
import dtt.global.tansport.Vote;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;

/**
 * Backing bean for the circulation details.
 *
 * @author Alaa Qasem
 */
@ViewScoped
@Named("circulationDetailsBacking")
public class CirculationDetailsBacking implements Serializable {
    private Options choice;
    @Inject
    private SessionInfo sessionInfo;
    private Circulation circulation;
    private Vote vote;

    private Options[] options;

    /**
     * Initialize circulation und vote dto objects.
     */
    @PostConstruct
    public void init() {
        circulation = new Circulation();
        circulation.setDoctoralSupervisor("Pas");
        circulation.setDoctoralCandidateName("Pr.wew");
        circulation.setTitle("geo");
        vote = new Vote();
        choice = Options.STIMME_ZU;

    }

    /**
     * Get the correct circulation id from the view param (will be called in a view action) and check if the
     * user allowed to view the circulation, then load all data that should be displayed from the datasource.
     *
     * @throws DataIntegrityException If the user has no permission to see the circulation.
     */

    public void loadCirculation() {
        Transaction tr = new Transaction();
        CirculationDAO circ = new CirculationDAO();
        Circulation circDB = new Circulation();
        circDB.setId(circulation.getId());
        try {
            circ.getCirculationById(circDB,tr);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * Download the pdf belonging to a specific circulation.
     *
     * @param circulation The circulation to download.
     * @throws IOException If the download fails.
     */
    public void download(Circulation circulation) {

    }

    /**
     * Delete a circulation.
     */
    public void remove() {
        Transaction transaction = new Transaction();
        CirculationDAO cirDAO = new CirculationDAO();

        try {
            cirDAO.remove(circulation,transaction);
            try {
                transaction.commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }


    }


    /**
     * Modify the circulation details.
     */
    public void modify() {

    }

    /**
     * Casts or change a vote for a specific choice.
     */
    public void submitVote() {
        Transaction transaction = new Transaction();
        vote.setSelection(choice);
        VoteDAO voteDAO= new VoteDAO();
        try {
            voteDAO.add(vote,transaction);
        } catch (DataNotCompleteException e) {
            e.printStackTrace();
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }


    }

    /**
     * Load all the votes of the circulation.
     */
    public void loadVotes() {
        Transaction tr = new Transaction();
        VoteDAO voteDao = new VoteDAO();
        voteDao.getVotes(vote,tr);


    }


    public Circulation getCirculation() {
        return circulation;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public Options getChoice() {
        return choice;
    }

    public void setChoice(Options choice) {
        this.choice = choice;
    }

    public void setCirculation(Circulation circulation) {
        this.circulation = circulation;
    }

    public Options[] getOptions() {
        return Options.values();
    }
}