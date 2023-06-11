package dtt.business.validation;

import dtt.business.validation.FutureDateTimeValidator;
import jakarta.faces.validator.ValidatorException;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;


public class FutureDateTimeValidatorTest {

    private FutureDateTimeValidator futureDateTimeValidator;

    @Before
    public void setup() {
        futureDateTimeValidator = new FutureDateTimeValidator();
    }

    @Test
    public void testValidateFutureDate() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);
        futureDateTimeValidator.validate(null, null, futureDate);
    }

    @Test(expected = ValidatorException.class)
    public void testValidatePastDate() {
        LocalDateTime pastDate = LocalDateTime.now().minusDays(1);
        futureDateTimeValidator.validate(null, null, pastDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateWrongType() {
        futureDateTimeValidator.validate(null, null, "wrong type");
    }
}
