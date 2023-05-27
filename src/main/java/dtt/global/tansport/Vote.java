package dtt.global.tansport;
/**
 * Represents the vote with the selected option for each user.
 *  @author Hadi Abou Hassoun
 */
public class Vote {
    private Options selection; // The selected option for the vote
    private User user; // The user who cast the vote

    private Circulation circulation; // The circulation associated with the vote

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
    public void setUser(User user) {
        this.user = user;
    }

    public void setCirculation (Circulation circulation) {
        this.circulation = circulation;
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
    public User getUser() {
        return user;
    }

    public Circulation getCirculation () {
        return circulation;
    }
}
