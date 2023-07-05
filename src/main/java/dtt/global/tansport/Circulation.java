package dtt.global.tansport;

import java.sql.Timestamp;

/**
 *  Represents a circulation in the System.
 * @author Hadi Abou Hassoun
 */
public class Circulation {
    private int id; // The ID of the circulation
    private String title; // The title of the circulation
    private String doctoralCandidateName; // The name of the doctoral candidate
    private String doctoralSupervisor; // The name of the doctoral supervisor
    private String description; // The description of the circulation
    private Timestamp startDate; // The start deadline of the circulation
    private Timestamp endDate; // The end deadline of the circulation
    private boolean isObligatory; // Indicates if the circulation is obligatory


    private int createdBy; // The userId of the creator.
    private int facultyId;
    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    private boolean isValid; // Represents the vote status.


    public Circulation() {}

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDoctoralCandidateName(String doctoralCandidateName) {
        this.doctoralCandidateName = doctoralCandidateName;
    }

    public void setDoctoralSupervisor(String doctoralSupervisor) {
        this.doctoralSupervisor = doctoralSupervisor;
    }

    public void setObligatory(boolean obligatory) {
        isObligatory = obligatory;
    }


    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }



    public void setFacultyId (int facultyId) {
        this.facultyId = facultyId;
    }

    public int getFacultyId () {
        return facultyId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDoctoralCandidateName() {
        return doctoralCandidateName;
    }

    public String getDoctoralSupervisor() {
        return doctoralSupervisor;
    }

    public boolean isObligatory() {
        return isObligatory;
    }


    public Timestamp getStartDate() {
        return startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }



}
