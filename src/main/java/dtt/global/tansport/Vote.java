package dtt.global.tansport;
/**
 * Represents the vote with the selected option for each user.
 */
public class Vote {
    private Options selection; // The selected option for the vote
    private User user; // The user who cast the vote

    /**
     *  Constructs a Vote object with the specified selection and user.
     */
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
}
