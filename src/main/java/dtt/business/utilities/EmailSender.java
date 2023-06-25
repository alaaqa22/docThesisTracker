package dtt.business.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

/**
 * A utility class for sending emails.
 * @author Johannes Silvennoinen
 */
public class EmailSender {
    private static final Logger LOGGER = LogManager.getLogger(EmailSender.class);
    private String host;
    private int port;
    private String username;
    private String password;
    private Properties properties;

    /**
     * Sends an email to the specified recipient with the given subject and body.
     * @param recipientEmail the email address of the recipient.
     * @param subject the subject of the email.
     * @param body the body content of the email.
     */
    public static void sendEmail(String recipientEmail, String subject, String body) {
        LOGGER.info("Token sent to: " + recipientEmail);
        LOGGER.info("Token is: " + body);
    }
}
