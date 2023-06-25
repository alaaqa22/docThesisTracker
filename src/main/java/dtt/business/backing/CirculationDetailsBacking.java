package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.exceptions.*;
import dtt.dataAccess.repository.interfaces.CirculationDAO;
import dtt.dataAccess.repository.interfaces.VoteDAO;
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
import java.time.LocalDate;
import java.util.List;

/**
 * Backing bean for the circulation details.
 *
 * @author Alaa Qasem
 */
@ViewScoped
@Named("circulationDetailsBacking")
public class CirculationDetailsBacking implements Serializable {
    private static final Logger LOGGER = LogManager.getLogger(CirculationDetailsBacking.class);
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
    private List<Vote> totalVotes;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;


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
        try (Transaction transaction = new Transaction()) {
            circulationDAO.getCirculationById(circulation, transaction);
            transaction.commit();

        } catch (DataNotFoundException e) {
            LOGGER.error("Failed to load the circulation " + circulation.getId());
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
    public String remove() {
        try (Transaction transaction = new Transaction()) {
            circulationDAO.remove(circulation, transaction);
            transaction.commit();
            LOGGER.info("Circulation with id = " + circulation.getId() + " was removed");
            return "/views/authenticated/circulationslist.xhtml?faces-redirect=true";

        } catch (DataNotFoundException e) {
            LOGGER.error("Circulation" + circulation.getId() + " could not be removed");
            throw new IllegalStateException();
        }
    }


    /**
     * Modify the circulation details.
     */
    public void modify() {
        try (Transaction transaction = new Transaction()) {
            circulationDAO.update(circulation, transaction);
            transaction.commit();
            LOGGER.info("circulation with id: " + circulation.getId() + " was modified");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Umlauf mit der ID: " + circulation.getId() + " wurde geändert.", null);
            FacesContext.getCurrentInstance().addMessage(null, message);

        } catch (DataNotFoundException | InvalidInputException | KeyExistsException e) {
            LOGGER.error("circulation" + circulation.getId() + " could not be modified");
            throw new IllegalStateException(e);
        }
    }

    /**
     * Casts or change a vote for a specific choice.
     */
    public void submitVote() {
        try (Transaction transaction = new Transaction()) {
            if (!voteDAO.findVote(vote, transaction)) {
                vote.setSelection(choice);
                vote.setDescription(reason);
                voteDAO.add(vote, transaction);
                LOGGER.info("New vote was added to circulation id" + circulation.getId() + " by " + getSessionInfo().getUser().getId());
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Ihre Stimme " + "(" + vote.getSelection().getLabel() + ")" + " wurde erfolgreich gespeichert.", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else {
                vote.setSelection(choice);
                vote.setDescription(reason);
                voteDAO.update(vote, transaction);
                LOGGER.info("Updated vote to circulation id " + circulation.getId() + " by user id: " + getSessionInfo().getUser().getId());
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Ihre neue Stimme " + "(" + vote.getSelection().getLabel() + ")" + " wurde erfolgreich aktualisiert.", null);
                FacesContext.getCurrentInstance().addMessage(null, message);

            }
            transaction.commit();

        } catch (DataNotCompleteException | InvalidInputException | DataNotFoundException e) {
            LOGGER.error("Failed to cast the vote.");
            throw new IllegalStateException(e);
        }


    }

    /**
     * Load all the votes of the circulation.
     */
    public void loadVotes() {
        try (Transaction transaction = new Transaction()) {
            vote.setCirculation(circulation.getId());
            vote.setUser(sessionInfo.getUser().getId());
            voteDAO.findVote(vote, transaction);
            transaction.commit();
        }
    }

    /**
     * Get all the votes for a circulation.
     *
     * @return List of votes.
     */
    public List<Vote> getTotalVotes() {
        try (Transaction transaction = new Transaction()) {
            totalVotes = voteDAO.getVotes(vote, transaction);
        }
        return totalVotes;
    }

    public void setTotalVotes(List<Vote> totalVotes) {
        this.totalVotes = totalVotes;
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

    public String getReason() {
        reason = vote.getDescription();
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public Options getChoice() {
        choice = vote.getSelection();
        return choice;
    }

    public void setChoice(Options choice) {
        this.choice = choice;
    }

    public Options[] getOptions() {
        return Options.values();
    }


    public LocalDate getEndDate() {
        java.sql.Timestamp timestamp = circulation.getEndDate();
        endDate = timestamp.toLocalDateTime().toLocalDate();
        return endDate;
    }

    public void setEndDate(LocalDate endDate1) {
        circulation.setEndDate(java.sql.Timestamp.valueOf(endDate1.atStartOfDay()));
        endDate = endDate1;
    }

    public LocalDate getStartDate() {
        java.sql.Timestamp timestamp = circulation.getStartDate();
        startDate = timestamp.toLocalDateTime().toLocalDate();
        return startDate;
    }

    public void setStartDate(LocalDate startDate1) {
        circulation.setStartDate(java.sql.Timestamp.valueOf(startDate1.atStartOfDay()));
        startDate = startDate1;
    }
}