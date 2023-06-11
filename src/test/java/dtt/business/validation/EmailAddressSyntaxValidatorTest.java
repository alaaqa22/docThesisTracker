package dtt.business.validation;

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

    @BeforeEach
    public void setUp() {
        emailAddressSyntaxValidator = new EmailAddressSyntaxValidator();
        facesContext = null; // FacesContext is not used in validate().
        uiComponent = null;  // UIComponent is not used in validate().
    }

    @Test
    public void testValidEmailAddress() {
        String validEmail = "test@uni-passau.de";
        assertDoesNotThrow(() -> emailAddressSyntaxValidator.validate(facesContext, uiComponent, validEmail));
    }

    @Test
    public void testInvalidEmailAddress() {
        String invalidEmail = "test@otherdomain.com";
        assertThrows(ValidatorException.class, () -> emailAddressSyntaxValidator.validate(facesContext, uiComponent, invalidEmail));
    }
}
