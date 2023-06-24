package dtt.business.utilities;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

import java.time.LocalDate;

@FacesConverter(value = "LocalDateConverter")
public class LocalDateConverter implements Converter<LocalDate> {

    @Override
    public LocalDate getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return LocalDate.parse(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, LocalDate localDate) {
        if (localDate == null) {
            return "";
        }
        return localDate.toString();
    }
}
