package dtt.business.backing;

import dtt.business.utilities.Hashing;
import dtt.business.utilities.TokenManager;
import dtt.dataAccess.exceptions.DataNotCompleteException;
import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.exceptions.KeyExistsException;
import dtt.dataAccess.repository.interfaces.UserDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.User;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Backing bean for the new password page.
 *
 * @author Alaa Qasem
 */
@ViewScoped
@Named
public class SetNewPasswordBacking implements Serializable {
    private static final long serialVersionUID = 3599044613046868659L;
    /** Initialize Logger. */
    private static final Logger LOGGER = LogManager
            .getLogger(SetNewPasswordBacking.class);
    /** The user being worked on. */
    private User user;
    /** The new password. */
    private String password;
    /** The Token provided. */
    private String token;
    /** Inject the DAO to access the User database. */
    @Inject
    private UserDAO userDAO;
    /** Inject the Token Manager with a map of all Tokens. */
    @Inject
    private TokenManager tokenManager;
    /** Inject faclet context. */
    @Inject
    private FacesContext fctx;

    /**
     * Initialize Objects.
     */
    @PostConstruct
    public void init() {
        token = "";
        user = new User();
    }

    /**
     * Read token from the URL and setup objects.
     *
     * @return page redirect
     */
    public String setup() {
        LOGGER.debug("setup called.");
        if (!tokenManager.lookupToken(token)) {
            return "/views/anonymous/login";
        }
        user = tokenManager.getUserForToken(token);
        return null;
    }

    /**
     * Save the new password/user.
     * @return navigation
     */
    public String save() {
        try (Transaction transaction = new Transaction()) {
            if (userDAO.findUserByEmail(user, transaction)) {
                User tempUser = new User();
                tempUser.setId(user.getId());
                tempUser.setPasswordSalt(Hashing.generateSalt());
                tempUser.setPasswordHashed(Hashing.hashPassword(password,
                        tempUser.getPasswordSalt()));
                userDAO.update(tempUser, transaction);
            } else {
                user.setPasswordSalt(Hashing.generateSalt());
                user.setPasswordHashed(
                        Hashing.hashPassword(password, user.getPasswordSalt()));
                userDAO.add(user, transaction);
            }
            transaction.commit();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException
                | InvalidInputException | DataNotFoundException
                | DataNotCompleteException e) {
            LOGGER.error("Failed to change password " + user.getId(), e);
            throw new IllegalStateException("Failed to change password", e);
        } catch (KeyExistsException e) {
            LOGGER.error(
                    "Failed to change password " + user.getId() + "key exists",
                    e);
            final FacesMessage fmsg = new FacesMessage(
                    "Error adding Faculty: Incomplete Data");
            fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fctx.addMessage("setNew:generalMessage", fmsg);
        }
        return "/view/anonymous/login";
    }

    /**
     * Getter for token.
     *
     * @return the token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Setter for token.
     *
     * @param newToken the new Token
     */
    public void setToken(final String newToken) {
        this.token = newToken;
    }

    /**
     * Getter for user.
     *
     * @return the user DTO
     */
    public User getUser() {
        return user;
    }

    /**
     * Getter for password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for password.
     *
     * @param newPassword the new password
     */
    public void setPassword(final String newPassword) {
        this.password = newPassword;
    }

}
