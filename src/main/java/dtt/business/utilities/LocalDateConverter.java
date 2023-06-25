package dtt.business.utilities;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

import java.time.LocalDate;
/**
 * A JSF converter for converting between {@link LocalDate} and {@link String}.
 * This converter is registered with the identifier "LocalDateConverter".
 * @author Johannes Silvennoinen
 */
@FacesConverter(value = "LocalDateConverter")
public class LocalDateConverter implements Converter<LocalDate> {

    /**
     * Converts the provided string value into a {@link LocalDate} object.
     *
     * @param facesContext the current {@link FacesContext}
     * @param uiComponent  the {@link UIComponent} from which the value is being converted
     * @param s            the string value to convert
     * @return the converted {@link LocalDate} object, or {@code null} if the input is null or empty
     */
    @Override
    public LocalDate getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return LocalDate.parse(s);
    }
    /**
     * Converts the provided {@link LocalDate} object into its string representation.
     *
     * @param facesContext the current {@link FacesContext}
     * @param uiComponent  the {@link UIComponent} from which the value is being converted
     * @param localDate    the {@link LocalDate} object to convert
     * @return the string representation of the {@link LocalDate} object,
     *         or an empty string if the input is null
     */
    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, LocalDate localDate) {
        if (localDate == null) {
            return "";
        }
        return localDate.toString();
    }
}
