package dttTest.business.validation;

import dtt.business.validation.PasswordValidator;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This class contains unit tests for the {@link PasswordValidator} class.
 */
public class PasswordValidatorTest {

    private PasswordValidator passwordValidator;
    private FacesContext facesContext;
    private UIComponent uiComponent;

    /**
     * Set up the test environment before each test case.
     */
    @BeforeEach
    public void setUp() {
        passwordValidator = new PasswordValidator();
        facesContext = null; // FacesContext is not used in validate()
        uiComponent = null;  // UIComponent is not used in validate()
    }

    /**
     * Test case to verify that a valid password is accepted by the validator.
     */
    @Test
    public void testValidPassword() {
        String validPassword = "Password1@";
        assertDoesNotThrow(() -> passwordValidator.validate(facesContext, uiComponent, validPassword));
    }

    /**
     * Test case to verify that an invalid password throws a ValidatorException.
     */
    @Test
    public void testInvalidPassword() {
        String invalidPassword = "password";
        assertThrows(ValidatorException.class, () -> passwordValidator.validate(facesContext, uiComponent, invalidPassword));
    }
}
