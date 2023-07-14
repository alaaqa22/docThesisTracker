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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.setup.TestSetup;

import java.time.Duration;
import java.util.*;

public class ChangeUserStateTest {
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
    public void changeUserState () {
        driver.get (TestSetup.getBaseUrl ());
        driver.manage ().window ().setSize (new Dimension (1269, 809));

        Duration maxWaitTime = Duration.ofSeconds (50);
        WebDriverWait wait = new WebDriverWait (driver, maxWaitTime);

        // Login
        driver.findElement (By.id ("login-form:email-itxt")).click ();
        driver.findElement (By.id ("login-form:email-itxt")).sendKeys ("test@test.com");
        driver.findElement (By.id ("login-form:password-iscrt")).click ();
        driver.findElement (By.id ("login-form:password-iscrt")).sendKeys ("password123");
        driver.findElement (By.id ("login-form:login-cbtn")).click ();

        // Navigate to user list
        driver.findElement (By.id ("navbarForm:user-list-link")).click ();

        // Update user state
        wait.until (ExpectedConditions.elementToBeClickable (By.id ("j_idt46:userState"))).click ();
        WebElement userStateDropdown = driver.findElement (By.id ("j_idt46:userState"));
        userStateDropdown.findElement (By.xpath ("//option[. = 'EXAMINCOMMITTEEMEMBERS']")).click ();

        driver.findElement (By.id ("data:user-dt:0:firstName-otxt")).click ();
        driver.findElement (By.cssSelector ("div:nth-child(9)")).click ();

        // Select faculty
        wait.until (ExpectedConditions.elementToBeClickable (By.id ("profile:faculty-slcom"))).click ();
        WebElement facultyDropdown = driver.findElement (By.id ("profile:faculty-slcom"));
        facultyDropdown.findElement (By.xpath ("//option[. = 'Faculty of Engineering']")).click ();

        // Assert the selected faculty option
        String selectedFacultyOption = facultyDropdown.getAttribute ("value");
        boolean isFacultyOptionSelected = facultyDropdown.findElement (By.xpath ("//option[. = 'Faculty of Engineering']")).isSelected ();
        assert isFacultyOptionSelected : "The selected faculty option is incorrect.";

        // Select user state
        wait.until (ExpectedConditions.visibilityOfElementLocated (By.id ("profile:user-states-slcom"))).click ();
        WebElement userStateDropdown2 = driver.findElement (By.id ("profile:user-states-slcom"));
        userStateDropdown2.findElement (By.xpath ("//option[. = 'EXAMINER']")).click ();

        // Assert the selected user state option
        boolean isUserStateOptionSelected = userStateDropdown2.findElement (By.xpath ("//option[. = 'EXAMINER']")).isSelected ();
        assert isUserStateOptionSelected : "The selected user state option is incorrect.";

        driver.findElement (By.id ("profile:updateAuth-cbtn")).click ();
        driver.findElement (By.id ("navbarForm:logout-cbtn")).click ();
    }
}


