package dtt.global.tansport;

public class Vote {
    private Options selection;
    private User user;

    public Vote (Options selection, User user) {
        this.selection = selection;
        this.user = user;
    }

    public void setSelection (Options selection) {
        this.selection = selection;
    }

    public void setUser (User user) {
        this.user = user;
    }

    public Options getSelection () {
        return selection;
    }

    public User getUser () {
        return user;
    }
}
