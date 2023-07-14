package selenium.basic;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.setup.TestSetup;

import java.time.Duration;
import java.util.*;

public class CirculationTimeFilterTest {
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
    public void circulationTimeFilter () {
        driver.get (TestSetup.getBaseUrl ());
        driver.manage ().window ().setSize (new Dimension (1269, 813));
        driver.findElement (By.id ("login-form:email-itxt")).click ();
        driver.findElement (By.id ("login-form:email-itxt")).sendKeys ("test@test.com");
        driver.findElement (By.id ("login-form:password-iscrt")).click ();
        driver.findElement (By.id ("login-form:password-iscrt")).sendKeys ("password123");
        driver.findElement (By.id ("login-form:login-cbtn")).click ();

        WebDriverWait wait = new WebDriverWait (driver, Duration.ofSeconds (10));

        WebElement allCirBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("j_idt48:all-cir-btn")));
        Assert.assertTrue("All Circulation button is still clickable after click.", allCirBtn.isEnabled());



        WebElement aktiv = wait.until(ExpectedConditions.elementToBeClickable(By.id("j_idt48:aktiv-cir-btn")));
        wait.until (ExpectedConditions.elementToBeClickable (By.id ("j_idt48:aktiv-cir-btn"))).click ();
        Assert.assertTrue("aktiv Circulation button is still clickable after click.", aktiv.isEnabled());


        wait.until (ExpectedConditions.elementToBeClickable (By.id ("dataForm:nextpage-link"))).click ();
        wait.until (ExpectedConditions.elementToBeClickable (By.id ("dataForm:nextpage-link"))).click ();

        WebElement oldCir = wait.until(ExpectedConditions.elementToBeClickable(By.id("j_idt48:old-cir-btn")));
        wait.until (ExpectedConditions.elementToBeClickable (By.id ("j_idt48:old-cir-btn"))).click ();
        Assert.assertTrue("old Circulation button is still clickable after click.", oldCir.isEnabled());


        driver.findElement (By.id ("navbarForm:logout-cbtn")).click ();
    }
}
