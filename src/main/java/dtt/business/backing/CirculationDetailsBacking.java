package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.exceptions.*;
import dtt.dataAccess.repository.interfaces.CirculationDAO;
import dtt.dataAccess.repository.interfaces.FacultyDAO;
import dtt.dataAccess.repository.interfaces.UserDAO;
import dtt.dataAccess.repository.interfaces.VoteDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.*;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    private UserDAO userDAO;
    @Inject
    private SessionInfo sessionInfo;
    @Inject
    private CirculationDAO circulationDAO;
    @Inject
    private VoteDAO voteDAO;
    @Inject
    private FacultyDAO facultyDAO;
    private Circulation circulation;
    private Vote vote;
    private Options[] options;
    private List<Vote> totalVotes;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String createdBy;
    private String facultyName;
    private int numberOfAcceptVotes;
    private int numberOfDeclineVotes;
    private int numberOfAbstainVotes;
    private BarChartModel barModel;


    /**
     * Initialize circulation und vote dto objects.
     */
    @PostConstruct
    public void init() {
        circulation = new Circulation();
        vote = new Vote();
        barModel = new BarChartModel();

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
     * Removes a circulation from the system.
     */
    public String remove() {
        try (Transaction transaction = new Transaction()) {
            circulationDAO.remove(circulation, transaction);
            transaction.commit();
            LOGGER.info("Circulation with id = " + circulation.getId() + " was removed");
            return "/views/authenticated/circulationslist.xhtml?faces-redirect=true";

        } catch (DataNotFoundException e) {
            LOGGER.error("Circulation" + circulation.getId() + " could not be removed");
            throw new IllegalStateException(e);
        }
    }


    /**
     * Modify the circulation details.
     */
    public void modify() {
        if (endDate.isBefore(startDate)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Umlauf konnte nicht geändert werden", null);
            FacesContext.getCurrentInstance().addMessage("form:vote-btn", message);
            return;
        }


        try (Transaction transaction = new Transaction()) {
            circulationDAO.update(circulation, transaction);
            transaction.commit();
            LOGGER.info("circulation with id: " + circulation.getId() + " was modified");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Umlauf wurde geändert.", null);
            FacesContext.getCurrentInstance().addMessage(null, message);

        } catch (DataNotFoundException e) {
            LOGGER.error("circulation" + circulation.getId() + " could not be modified");
            throw new IllegalStateException(e);
        } catch (InvalidInputException | KeyExistsException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Umlauf konnte nicht geändert werden", null);
            FacesContext.getCurrentInstance().addMessage(null, message);

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
                        "Stimme " + "(" + vote.getSelection().getLabel() + ")" + " wurde erfolgreich gespeichert.", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else {
                vote.setSelection(choice);
                vote.setDescription(reason);
                voteDAO.update(vote, transaction);
                LOGGER.info("Updated vote to circulation id " + circulation.getId() + " by user id: " + getSessionInfo().getUser().getId());
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Neue Stimme " + "(" + vote.getSelection().getLabel() + ")" + " wurde erfolgreich aktualisiert.", null);
                FacesContext.getCurrentInstance().addMessage(null, message);

            }
            transaction.commit();
        } catch (DataNotCompleteException e) {
            LOGGER.error("Failed to cast the vote.");
            throw new IllegalStateException(e);
        } catch (InvalidInputException | DataNotFoundException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Stimme konnte nicht gespeichert.", null);
            FacesContext.getCurrentInstance().addMessage(null, message);

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
            getTotalVotes();
            createBarChart();
            transaction.commit();
        }
    }

    /**
     * Get all the votes for a circulation and count the number of each vote to represent them on bar chart.
     *
     * @return List of votes.
     */
    public List<Vote> getTotalVotes() {
        try (Transaction transaction = new Transaction()) {
            totalVotes = voteDAO.getVotes(vote, transaction);
            for (Vote voteFromList : totalVotes) {
                User user = new User();
                if (voteFromList.getSelection() == Options.STIMME_ZU) {
                    numberOfAcceptVotes++;
                } else if (voteFromList.getSelection() == Options.LEHNE_AB) {
                    numberOfDeclineVotes++;
                } else if (voteFromList.getSelection() == Options.ENTHALTE_MICH) {
                    numberOfAbstainVotes++;
                }
                if (voteFromList.getUserId() != 0) {
                    user.setId(voteFromList.getUserId());
                    userDAO.getUserById(user, transaction);
                    voteFromList.setVotedByName(user.getFirstName().concat(" " + user.getLastName()));
                }
                transaction.commit();
            }
        } catch (DataNotFoundException e) {
            throw new IllegalStateException(e);
        }

        return totalVotes;
    }

    public void setTotalVotes(List<Vote> totalVotes) {
        this.totalVotes = totalVotes;
    }

    private void createBarChart() {
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Ergebnis");

        List<Number> values = new ArrayList<>();
        values.add(numberOfAcceptVotes);
        values.add(numberOfDeclineVotes);
        values.add(numberOfAbstainVotes);
        barDataSet.setData(values);

        List<Options> labels = new ArrayList<>(Arrays.asList(Options.values()));
        List<String> stringList = labels.stream()
                .map(Options::getLabel)
                .collect(Collectors.toList());

        data.setLabels(stringList);
        //Data
        barModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setMirror(true);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(28);
        legend.setLabels(legendLabels);
        options.setLegend(legend);
        barModel.setOptions(options);


        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(0, 128, 0, 0.2)"); //Red
        bgColor.add("rgba(255, 0, 0, 0.2)"); //Green
        bgColor.add("rgba(255, 205, 86, 0.2)"); //Yellow

        barDataSet.setBackgroundColor(bgColor);
        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(0, 128, 0)");
        borderColor.add("rgb(255, 0, 0)");
        borderColor.add("rgb(255, 205, 86)");

        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);
        data.addChartDataSet(barDataSet);

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


    /**
     * Returns the creator's name of the circulation.The name will be retrieved from the database using the userID.
     *
     * @return The creator's name of the circulation
     */
    public String getCreatedBy() {
        User user = new User();
        user.setId(circulation.getCreatedBy());
        try (Transaction transaction = new Transaction()) {
            userDAO.getUserById(user, transaction);
            createdBy = user.getFirstName().concat(" " + user.getLastName());
            transaction.commit();
        } catch (DataNotFoundException e) {
            throw new IllegalStateException(e);
        }
        return createdBy;
    }

    public String getFacultyName() {
        try (Transaction transaction = new Transaction()) {
            Faculty faculty = facultyDAO.getFacultyById(circulation.getFacultyId(), transaction);
            facultyName = faculty.getName();
        }
        return facultyName;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }


}