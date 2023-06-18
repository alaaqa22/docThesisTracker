package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.exceptions.*;
import dtt.dataAccess.repository.postgres.CirculationDAO;
import dtt.dataAccess.repository.postgres.VoteDAO;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    Logger logger = LogManager.getLogger();
    private Options choice;
    @Inject
    private SessionInfo sessionInfo;
    @Inject
    private CirculationDAO circulationDAO;
    @Inject
    private VoteDAO voteDAO;
    private Circulation circulation;
    private Vote vote;
    private Options[] options;

    /**
     * Initialize circulation und vote dto objects.
     */
    @PostConstruct
    public void init() {
        circulation = new Circulation();
        vote = new Vote();
    }

    /**
     * Get the correct circulation id from the view param (will be called in a view action) and check if the
     * user allowed to view the circulation, then load all data that should be displayed from the datasource.
     *
     * @throws DataIntegrityException If the user has no permission to see the circulation.
     */

    public void loadCirculation() {
        Transaction transaction = new Transaction();
        try {
            circulationDAO.getCirculationById(circulation, transaction);
            transaction.commit();

        } catch (DataNotFoundException e) {
            throw new IllegalStateException(e);
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
        transaction.commit();
        try {
            circulationDAO.remove(circulation, transaction);

        } catch (DataNotFoundException e) {
            throw new IllegalStateException();
        }
    }


    /**
     * Modify the circulation details.
     */
    public void modify() {
        Transaction transaction = new Transaction();
        try {
            circulationDAO.update(circulation, transaction);
            transaction.commit();

        } catch (DataNotFoundException | InvalidInputException | KeyExistsException e) {
        }
    }

    /**
     * Casts or change a vote for a specific choice.
     */
    public void submitVote() {
        Transaction transaction = new Transaction();
        try {
            if (!voteDAO.findVote(vote, transaction)) {
                vote.setSelection(choice);
                voteDAO.add(vote, transaction);
            } else {
                vote.setSelection(choice);
                voteDAO.update(vote, transaction);
            }
            transaction.commit();

        } catch (DataNotCompleteException | InvalidInputException | DataNotFoundException e) {
        }


    }

    /**
     * Load all the votes of the circulation.
     */
    public void loadVotes() {
        Transaction transaction = new Transaction();
        vote.setCirculation(circulation.getId());
        vote.setUser(sessionInfo.getUser().getId());
        voteDAO.findVote(vote, transaction);
        transaction.commit();

    }


    public Circulation getCirculation() {
        return circulation;
    }

    public void setCirculation(Circulation circulation) {
        this.circulation = circulation;
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

    public Options[] getOptions() {
        return Options.values();
    }
}