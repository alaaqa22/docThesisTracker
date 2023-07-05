package dtt.business.utlities;

import dtt.business.utilities.LocalDateConverter;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LocalDateConverterTest {

    private final LocalDateConverter converter = new LocalDateConverter();

    @Test
    void testGetAsObject_withValidString() {
        FacesContext facesContext = null;
        UIComponent uiComponent = null;
        String dateString = "2023-06-25";
        LocalDate expectedDate = LocalDate.parse(dateString);

        LocalDate result = converter.getAsObject(facesContext, uiComponent, dateString);

        assertEquals(expectedDate, result);
    }

    @Test
    void testGetAsObject_withNullString() {
        FacesContext facesContext = null;
        UIComponent uiComponent = null;
        String dateString = null;

        LocalDate result = converter.getAsObject(facesContext, uiComponent, dateString);

        assertNull(result);
    }

    @Test
    void testGetAsString_withNonNullLocalDate() {
        FacesContext facesContext = null;
        UIComponent uiComponent = null;
        LocalDate localDate = LocalDate.of(2023, 6, 25);
        String expectedString = "2023-06-25";

        String result = converter.getAsString(facesContext, uiComponent, localDate);

        assertEquals(expectedString, result);
    }

    @Test
    void testGetAsString_withNullLocalDate() {
        FacesContext facesContext = null;
        UIComponent uiComponent = null;
        LocalDate localDate = null;

        String result = converter.getAsString(facesContext, uiComponent, localDate);

        assertEquals("", result);
    }
}
