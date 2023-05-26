package dtt.global.tansport;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 *  Represents a circulation in the System.
 */
public class Circulation {
    private int id; // The ID of the circulation
    private String title; // The title of the circulation
    private String description; // The description of the circulation
    private String doctoralCandidateName; // The name of the doctoral candidate
    private String doctoralSupervisor; // The name of the doctoral supervisor
    private boolean isObligatory; // Indicates if the circulation is obligatory
    private int acceptedCount; // The count of accepted votes
    private int rejectedCount; // The count of rejected votes
    LocalDateTime startDeedline; // The start deadline of the circulation
    LocalDateTime endDeedline; // The end deadline of the circulation
    private List<Attachments> attachmentslist; // The list of attachments

    public Circulation() {}


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

    public void setAcceptedCount(int acceptedCount) {
        this.acceptedCount = acceptedCount;
    }

    public void setRejectedCount(int rejectedCount) {
        this.rejectedCount = rejectedCount;
    }

    public void setStartDeedline(LocalDateTime startDeedline) {
        this.startDeedline = startDeedline;
    }

    public void setEndDeedline(LocalDateTime endDeedline) {
        this.endDeedline = endDeedline;
    }


    public void setAttachmentslist(List<Attachments> attachmentslist) {
        this.attachmentslist = attachmentslist;
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

    public int getAcceptedCount() {
        return acceptedCount;
    }

    public int getRejectedCount() {
        return rejectedCount;
    }

    public LocalDateTime getStartDeedline() {
        return startDeedline;
    }

    public LocalDateTime getEndDeedline() {
        return endDeedline;
    }

    public List<Attachments> getAttachmentslist() {
        return attachmentslist;
    }
}
