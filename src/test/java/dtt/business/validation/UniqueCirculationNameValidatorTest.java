package dtt.business.validation;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dtt.dataAccess.repository.interfaces.CirculationDAO;
import dtt.dataAccess.utilities.Transaction;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;

class UniqueCirculationNameValidatorTest {

    /** Mocks. */
    @InjectMocks
    private UniqueCirculationNameValidator validator;

    /** Mocks. */
    @Mock
    private CirculationDAO circulationDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidateValidName() {
        FacesContext context = mock(FacesContext.class,
                Mockito.RETURNS_DEEP_STUBS);
        UIComponent component = mock(UIComponent.class);
        String circulationName = "Unique Circulation Name";

        // Mock DAO behavior to return false (non-existing circulation)
        when(circulationDAO.findCirculationByTitle(any(), any()))
                .thenReturn(false);
        when(context.getApplication().evaluateExpressionGet(any(), anyString(),
                any())).thenReturn(circulationDAO);
        try (MockedConstruction<Transaction> myobjectMockedConstruction =
                Mockito.mockConstruction(Transaction.class)) {
            validator.validate(context, component, circulationName);
        } catch (ValidatorException e) {
            fail("Validation should not throw an exception for a valid name.");
        }
    }

    @Test
    public void testValidateDuplicateName() {
        FacesContext context = mock(FacesContext.class,
                Mockito.RETURNS_DEEP_STUBS);
        UIComponent component = mock(UIComponent.class);
        String circulationName = "Duplicate Circulation Name";

        // Mock DAO behavior to return true (existing circulation)
        when(circulationDAO.findCirculationByTitle(any(), any()))
                .thenReturn(true);
        when(context.getApplication().evaluateExpressionGet(any(), anyString(),
                any())).thenReturn(circulationDAO);

        try (MockedConstruction<Transaction> myobjectMockedConstruction =
                Mockito.mockConstruction(Transaction.class)) {
            Assertions.assertThrows(ValidatorException.class, () -> {
                validator.validate(context, component, circulationName);
            });
        }
    }
}
