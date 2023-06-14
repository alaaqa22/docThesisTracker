package dttTest.business.validation;

import dtt.business.validation.EmailAddressSyntaxValidator;
import dtt.global.utilities.ConfigReader;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmailAddressSyntaxValidatorTest {

    private EmailAddressSyntaxValidator emailAddressSyntaxValidator;
    private FacesContext facesContext;
    private UIComponent uiComponent;
    private String[] validDomains;

    @BeforeEach
    public void setUp() {
        emailAddressSyntaxValidator = new EmailAddressSyntaxValidator();
        facesContext = null; // FacesContext is not used in validate().
        uiComponent = null;  // UIComponent is not used in validate().

        // Load valid domains from config file
        if (!ConfigReader.arePropertiesLoaded()) {
            ConfigReader.loadProperties();
        }
        validDomains = ConfigReader.getProperty("EMAIL_PATTERN").split(",");
    }

    @Test
    public void testValidEmailAddresses() {
        for (String domain : validDomains) {
            String validEmail = "test@" + domain;
            assertDoesNotThrow(() -> emailAddressSyntaxValidator.validate(facesContext, uiComponent, validEmail));
        }
    }

    @Test
    public void testInvalidEmailAddress() {
        String invalidEmail = "test@otherdomain.com";
        assertThrows(ValidatorException.class, () -> emailAddressSyntaxValidator.validate(facesContext, uiComponent, invalidEmail));
    }
}
