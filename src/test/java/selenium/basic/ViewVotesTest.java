package selenium.basic;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.*;
import selenium.setup.TestSetup;

import java.util.*;

public class ViewVotesTest {
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
    public void viewVoteTest () {
        driver.get (TestSetup.getBaseUrl ());
        driver.manage().window().setSize(new Dimension(1269, 809));
        driver.findElement(By.id("login-form:email-itxt")).click();
        driver.findElement(By.id("login-form:email-itxt")).sendKeys("test@test.com");
        driver.findElement(By.id("login-form:password-iscrt")).click();
        driver.findElement(By.id("login-form:password-iscrt")).sendKeys("password123");
        driver.findElement(By.id("login-form:login-cbtn")).click();
        driver.findElement(By.id("dataForm:cir-dt:0:titel-otxt")).click();
        driver.findElement(By.id("circulationDetails:voteList-link")).click();
        // Assertion to check if the "Vote List" page is loaded
        List<WebElement> voteListPageElements = driver.findElements(By.id("voteListPageElementId"));
        boolean voteListPageLoaded = !voteListPageElements.isEmpty();
        Assert.assertFalse ("Vote List page is not loaded", voteListPageLoaded);
        driver.findElement(By.id("navbarForm:logout-cbtn")).click();
    }
}


