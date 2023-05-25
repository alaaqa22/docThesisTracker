package dtt.business.utilities;

import java.util.Properties;

/**
 * A utility class for sending emails.
 * @author Johannes Silvennoinen
 */
public class EmailSender {

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
    public void sendEmail(String recipientEmail, String subject, String body) {

    }
}
