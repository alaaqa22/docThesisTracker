package selenium.basic;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import selenium.setup.TestSetup;

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
    public void voteTeset () {
        driver.get (TestSetup.getBaseUrl ());
        driver.manage ().window ().setSize (new Dimension (1269, 813));
        driver.findElement (By.id ("login-form:email-itxt")).click ();
        driver.findElement (By.id ("login-form:email-itxt")).sendKeys ("ThomasMuller@gamil.com");
        driver.findElement (By.id ("login-form:password-iscrt")).click ();
        driver.findElement (By.id ("login-form:password-iscrt")).sendKeys ("Password@1234");
        driver.findElement (By.id ("login-form:login-cbtn")).click ();
        driver.findElement (By.id ("dataForm:cir-dt:0:titel-otxt")).click ();
        driver.findElement (By.id ("circulationDetails:vote-options-slcom")).click ();
        driver.findElement (By.id ("circulationDetails:reason-itxt")).click ();
        driver.findElement (By.id ("circulationDetails:reason-itxt")).click ();
        {
            WebElement element = driver.findElement (By.id ("circulationDetails:reason-itxt"));
            Actions builder = new Actions (driver);
            builder.doubleClick (element).perform ();
        }
        driver.findElement (By.id ("circulationDetails:vote-btn")).click ();
        driver.findElement (By.id ("navbarForm:homepage-link")).click ();
        driver.findElement (By.id ("navbarForm:logout-cbtn")).click ();
    }
}

