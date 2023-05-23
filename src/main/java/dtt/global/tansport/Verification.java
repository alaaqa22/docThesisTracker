package dtt.global.tansport;

import java.time.LocalDateTime;

public class Verification {
    private User user;
    private String tocken;
    private LocalDateTime expiryDate;
    public Verification(){};
    public Verification (User user, String tocken, LocalDateTime expiryDate) {
        this.user = user;
        this.tocken = tocken;
        this.expiryDate = expiryDate;
    }

    public void setUser (User user) {
        this.user = user;
    }

    public void setTocken (String tocken) {
        this.tocken = tocken;
    }

    public void setExpiryDate (LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public User getUser () {
        return user;
    }

    public String getTocken () {
        return tocken;
    }

    public LocalDateTime getExpiryDate () {
        return expiryDate;
    }
}
