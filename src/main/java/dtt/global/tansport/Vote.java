package dtt.global.tansport;
/**
 * Represents the vote with the selected option for each user.
 *  @author Hadi Abou Hassoun
 */
public class Vote {

    private int voteId;  // The ID of the Vote.
    private Options selection; // The selected option for the vote
    private int  userId; //
    private String votedByName;

    private int  circulationId; //

    private String  description; // The text field for the short explanation


    public Vote () {}

    /**
     * Sets the selected option for the vote.
     * @param selection The selected option for the vote
     */
    public void setSelection(Options selection) {
        this.selection = selection;
    }

    /**
     * Sets the user who cast the vote.
     * @param user The user who cast the vote
     */
    public void setUser(int user) {
        this.userId = user;
    }

    public void setCirculation (int circulation) {
        this.circulationId = circulation;
    }

    /**
     * Returns the selected option for the vote.
     * @return The selected option for the vote
     */
    public Options getSelection() {
        return selection;
    }

    /**
     * Returns the user who cast the vote.
     * @return The user who cast the vote
     */
    public int getUserId() {
        return userId;
    }

    public int getCirculationId () {
        return circulationId;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public String getDescription () {
        return description;
    }

    public int getVoteId () {
        return voteId;
    }

    public void setVoteId (int voteId) {
        this.voteId = voteId;
    }

    public String getVotedByName() {
        return votedByName;
    }

    public void setVotedByName(String votedByName) {
        this.votedByName = votedByName;
    }
}
