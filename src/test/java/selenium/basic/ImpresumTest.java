package selenium.basic;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import selenium.setup.TestSetup;

import java.util.*;

public class ImpresumTest {
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
    public void impresum () {
        driver.get (TestSetup.getBaseUrl ());
        driver.manage().window().setSize(new Dimension(1269, 815));
        driver.findElement(By.id("login-form:email-itxt")).click();
        driver.findElement(By.id("login-form")).click();
        driver.findElement(By.id("j_idt65:imprint-otxt")).click();
        driver.findElement(By.id("navbarForm:homepageLog-lin")).click();
        driver.findElement(By.id("login-form:email-itxt")).click();
        driver.findElement(By.id("login-form:email-itxt")).sendKeys("test@test.com");
        driver.findElement(By.id("login-form:password-iscrt")).click();
        driver.findElement(By.id("login-form:password-iscrt")).sendKeys("password123");
        driver.findElement(By.id("login-form:login-cbtn")).click();
        driver.findElement(By.id("j_idt82:imprint-otxt")).click();
        driver.findElement(By.cssSelector("#navbarForm\\3Aprofil-link > .nav-link")).click();
        driver.findElement(By.id("j_idt78:imprint-otxt")).click();
        driver.findElement(By.id("navbarForm:create-circulation-link")).click();
        driver.findElement(By.id("j_idt71:imprint-otxt")).click();
        driver.findElement(By.id("navbarForm:user-list-link")).click();
        driver.findElement(By.id("j_idt87:imprint-otxt")).click();
        driver.findElement(By.id("navbarForm:faculty-id")).click();
        driver.findElement(By.id("j_idt64:imprint-otxt")).click();
        driver.findElement(By.id("navbarForm:logout-cbtn")).click();
    }
}
