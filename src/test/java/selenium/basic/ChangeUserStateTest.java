package selenium.basic;

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
        driver.manage ().window ().setSize (new Dimension (1724, 991));
        driver.findElement (By.id ("login-form:email-itxt")).click ();
        driver.findElement (By.id ("login-form:email-itxt")).sendKeys ("test@test.com");
        driver.findElement (By.id ("login-form:password-iscrt")).click ();
        driver.findElement (By.id ("login-form:password-iscrt")).sendKeys ("password123");
        driver.findElement (By.id ("login-form:login-cbtn")).click ();
        driver.findElement (By.id ("navbarForm:user-list-link")).click ();
        driver.findElement (By.id ("data:user-dt:0:firstName-otxt")).click ();
        driver.findElement (By.id ("profile:user-states-slcom")).click ();
        {
            WebElement dropdown = driver.findElement (By.id ("profile:user-states-slcom"));
            dropdown.findElement (By.xpath ("//option[. = 'EXAMINER']")).click ();
        }
        driver.findElement (By.id ("profile:faculty-slcom")).click ();
        {
            WebElement dropdown = driver.findElement (By.id ("profile:faculty-slcom"));
            dropdown.findElement (By.xpath ("//option[. = 'Faculty of Darts']")).click ();
        }
        driver.findElement (By.id ("profile:updateAuth-cbtn")).click ();
        driver.findElement (By.id ("navbarForm:logout-cbtn")).click ();
        driver.findElement (By.id ("login-form:email-itxt")).click ();
        driver.findElement (By.id ("login-form:email-itxt")).sendKeys ("jane@example.com");

        driver.findElement (By.id ("login-form:password-iscrt")).click ();

        driver.findElement (By.id ("login-form:password-iscrt")).sendKeys ("Password@123");
        driver.findElement (By.id ("login-form:login-cbtn")).click ();
        driver.findElement (By.id ("navbarForm:selected-faculty-slom")).click ();
        {
            WebElement dropdown = driver.findElement (By.id ("navbarForm:selected-faculty-slom"));
            dropdown.findElement (By.xpath ("//option[. = 'Faculty of Darts']")).click ();
        }
        driver.findElement (By.id ("navbarForm:logout-cbtn")).click ();
    }
}

