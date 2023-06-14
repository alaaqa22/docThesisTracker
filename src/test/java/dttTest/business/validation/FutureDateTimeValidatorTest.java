package dttTest.business.validation;

import dtt.business.validation.FutureDateTimeValidator;
import jakarta.faces.validator.ValidatorException;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;


/**
 * This class contains unit tests for the {@link FutureDateTimeValidator} class.
 */
public class FutureDateTimeValidatorTest {

    private FutureDateTimeValidator futureDateTimeValidator;

    /**
     * Set up the test environment before each test case.
     */
    @Before
    public void setup() {
        futureDateTimeValidator = new FutureDateTimeValidator();
    }

    /**
     * Test case to verify that a future date is considered valid by the validator.
     */
    @Test
    public void testValidateFutureDate() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);
        futureDateTimeValidator.validate(null, null, futureDate);
    }

    /**
     * Test case to verify that a past date throws a ValidatorException.
     */
    @Test(expected = ValidatorException.class)
    public void testValidatePastDate() {
        LocalDateTime pastDate = LocalDateTime.now().minusDays(1);
        futureDateTimeValidator.validate(null, null, pastDate);
    }

    /**
     * Test case to verify that providing a value of the wrong type throws an IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testValidateWrongType() {
        futureDateTimeValidator.validate(null, null, "wrong type");
    }
}
