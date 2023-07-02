package dtt.business.validation;

import dtt.dataAccess.repository.interfaces.UserDAO;
import dtt.dataAccess.utilities.Transaction;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EmailAddressAvailabilityValidatorTest {
    @InjectMocks
    private EmailAddressAvailabilityValidator validator;
    @Mock
    private UserDAO userDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testValidateEmailInUse() {
        FacesContext context = mock(FacesContext.class,
                Mockito.RETURNS_DEEP_STUBS);
        UIComponent component = mock(UIComponent.class);
        String emailInUse = "test@test.com";

        // Mock DAO behavior to return true (existing email)
        when(userDAO.findUserByEmail(any(), any()))
                .thenReturn(true);
        when(context.getApplication().evaluateExpressionGet(any(), anyString(),
                any())).thenReturn(userDAO);
        try (MockedConstruction<Transaction> myobjectMockedConstruction =
                     Mockito.mockConstruction(Transaction.class)) {
            Assertions.assertThrows(ValidatorException.class, () -> {
                validator.validate(context, component, emailInUse);
            });
        }
    }

    @Test
    public void testValidateEmailNotInUse() {
        FacesContext context = mock(FacesContext.class,
                Mockito.RETURNS_DEEP_STUBS);
        UIComponent component = mock(UIComponent.class);
        String emailNotInUse = "test4@test.com";

        // Mock DAO behavior to return false (non-existing email)
        when(userDAO.findUserByEmail(any(), any()))
                .thenReturn(false);
        when(context.getApplication().evaluateExpressionGet(any(), anyString(),
                any())).thenReturn(userDAO);

        try (MockedConstruction<Transaction> myobjectMockedConstruction =
                     Mockito.mockConstruction(Transaction.class)) {
            validator.validate(context, component, emailNotInUse);

        }
    }


}