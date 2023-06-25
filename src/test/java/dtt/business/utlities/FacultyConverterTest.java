package dtt.business.utlities;

import dtt.business.utilities.FacultyConverter;
import dtt.global.tansport.Faculty;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verifyNoInteractions;

class FacultyConverterTest {

    @Mock
    private FacesContext facesContext;
    @Mock
    private UIComponent uiComponent;

    private FacultyConverter facultyConverter;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        facultyConverter = new FacultyConverter();
    }

    @Test
    void testGetAsString_withValidFaculty_shouldReturnName() {
        String facultyName = "Computer Science";
        Faculty faculty = new Faculty();
        faculty.setName(facultyName);

        String result = facultyConverter.getAsString(facesContext, uiComponent, faculty);

        assertEquals(facultyName, result);
        verifyNoInteractions(facesContext);
    }
}
