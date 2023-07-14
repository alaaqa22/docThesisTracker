package selenium.basic;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.setup.TestSetup;

import java.time.Duration;
import java.util.*;

public class VoteTest {
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
    public void voteTest () {
        driver.get (TestSetup.getBaseUrl ());
        driver.manage ().window ().setSize (new Dimension (1269, 809));
        driver.findElement (By.id ("login-form:email-itxt")).click ();
        driver.findElement (By.id ("login-form:email-itxt")).sendKeys ("Ram@test.com");
        driver.findElement (By.id ("login-form:password-iscrt")).click ();
        driver.findElement (By.id ("login-form:password-iscrt")).sendKeys ("Password@123");
        driver.findElement (By.id ("login-form:login-cbtn")).click ();
        driver.findElement (By.id ("dataForm:cir-dt:0:titel-otxt")).click ();
        driver.findElement (By.id ("circulationDetails:vote-options-slcom")).click ();

        // Click on the "Vote" button
        driver.findElement (By.id ("circulationDetails:vote-btn")).click ();

        // Wait for the logout button to be clickable
        WebDriverWait wait = new WebDriverWait (driver, Duration.ofSeconds (10));
        wait.until (ExpectedConditions.elementToBeClickable (By.id ("navbarForm:logout-cbtn")));

        // Logout
        driver.findElement (By.id ("navbarForm:logout-cbtn")).click ();
    }
}

