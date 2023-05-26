package dtt.business.validation;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator("UniqueFacultyNameValidator")
public class UniqueFacultyNameValidator implements Validator {
    /**
     *
     * @param context FacesContext for the request we are processing
     * @param component UIComponent we are checking for correctness
     * @param value the value to validate
     * @throws ValidatorException
     */
    @Override
    public void validate (FacesContext context, UIComponent component, Object value) throws ValidatorException {

    }
    /**
     * check if the Faculty name is unique
     * @param FacultyName : The Name of the Faculty.
     * @return  {@code true} if the faculty name is unique, {@code false} otherwise.
     */
    private boolean isValueUnique(String FacultyName) {
        return true;
    }
}
