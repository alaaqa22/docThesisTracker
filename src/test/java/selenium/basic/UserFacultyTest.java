package selenium.basic;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import selenium.setup.TestSetup;

import java.util.*;

public class UserFacultyTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @Before
    public void setUp () {
        TestSetup.setup ();
        driver = TestSetup.getDriver ();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object> ();
    }

    @After
    public void tearDown () {
        driver.quit ();
    }

    @Test
    public void userFacultyFilterTest() {
        driver.get(TestSetup.getBaseUrl());
        driver.manage().window().setSize(new Dimension(1724, 991));
        driver.findElement(By.id("login-form:email-itxt")).click();
        driver.findElement(By.id("login-form:email-itxt")).sendKeys("test@test.com");
        driver.findElement(By.id("login-form:password-iscrt")).click();
        driver.findElement(By.id("login-form:password-iscrt")).sendKeys("password123");
        driver.findElement(By.id("login-form:login-cbtn")).click();
        driver.findElement(By.id("navbarForm:user-list-link")).click();

        // Select Faculty of Arts from dropdown and assert selection
        selectDropdownOption(By.id("j_idt46:userFaculty"), "Faculty of Arts");
        String selectedFaculty = getSelectedDropdownOptionText(By.id("j_idt46:userFaculty"));
        Assert.assertEquals("Faculty of Arts", selectedFaculty);

        // Select EXAMINER from dropdown and assert selection
        selectDropdownOption(By.id("j_idt46:userState"), "EXAMINER");
        String selectedState = getSelectedDropdownOptionText(By.id("j_idt46:userState"));
        Assert.assertEquals("EXAMINER", selectedState);

        // Select EXAMINCOMMITTEEMEMBERS from dropdown and assert selection
        selectDropdownOption(By.id("j_idt46:userState"), "EXAMINCOMMITTEEMEMBERS");
        selectedState = getSelectedDropdownOptionText(By.id("j_idt46:userState"));
        Assert.assertEquals("EXAMINCOMMITTEEMEMBERS", selectedState);

        // Select DEANERY from dropdown and assert selection
        selectDropdownOption(By.id("j_idt46:userState"), "DEANERY");
        selectedState = getSelectedDropdownOptionText(By.id("j_idt46:userState"));
        Assert.assertEquals("DEANERY", selectedState);

        // Select PENDING from dropdown and assert selection
        selectDropdownOption(By.id("j_idt46:userState"), "PENDING");
        selectedState = getSelectedDropdownOptionText(By.id("j_idt46:userState"));
        Assert.assertEquals("PENDING", selectedState);

        // Select All Faculties from dropdown and assert selection
        selectDropdownOption(By.id("j_idt46:userFaculty"), "Alle Fakultäten");
        selectedFaculty = getSelectedDropdownOptionText(By.id("j_idt46:userFaculty"));
        Assert.assertEquals("", "");

        // Select All Users from dropdown and assert selection
        selectDropdownOption(By.id("j_idt46:userState"), "Alle Benutzer");
        selectedState = getSelectedDropdownOptionText(By.id("j_idt46:userState"));
        Assert.assertEquals("", selectedState);

        // Select Faculty of Science from dropdown and assert selection
        selectDropdownOption(By.id("j_idt46:userFaculty"), "Faculty of Science");
        selectedFaculty = getSelectedDropdownOptionText(By.id("j_idt46:userFaculty"));
        Assert.assertEquals("Faculty of Science", selectedFaculty);

        driver.findElement(By.id("navbarForm:logout-cbtn")).click();
    }

// Helper methods to select dropdown options and get the selected option text

    private void selectDropdownOption(By dropdownLocator, String optionText) {
        WebElement dropdown = driver.findElement(dropdownLocator);
        dropdown.click();
        WebElement option = dropdown.findElement(By.xpath("//option[. = '" + optionText + "']"));
        option.click();
    }

    private String getSelectedDropdownOptionText(By dropdownLocator) {
        WebElement dropdown = driver.findElement(dropdownLocator);
        return dropdown.getAttribute("value");
    }
}
