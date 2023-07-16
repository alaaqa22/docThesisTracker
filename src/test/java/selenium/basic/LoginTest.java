package selenium.basic;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import selenium.setup.TestSetup;

import java.util.*;

public class LoginTest {

    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    @Before
    public void setUp() {
        TestSetup.setup();
        driver = TestSetup.getDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }
    @After
    public void tearDown() {
        TestSetup.tearDown();
        driver.quit();
    }
    @Test
    public void loginTest() {
        // Setup
        driver.get(TestSetup.getBaseUrl());
        driver.manage().window().setSize(new Dimension(1708, 960));

        // Login
        driver.findElement(By.id("login-form:email-itxt")).click();
        driver.findElement(By.id("login-form:email-itxt")).sendKeys("johannes.test@email.com");
        driver.findElement(By.id("login-form:password-iscrt")).click();
        driver.findElement(By.id("login-form:password-iscrt")).sendKeys("gdx7hMAeqD5AKtH*");
        driver.findElement(By.id("login-form:login-cbtn")).click();

        // Get the circulationURL
        String currentUrl = driver.getCurrentUrl();

        // Assert that we are on the main page
        Assert.assertEquals(TestSetup.getBaseUrl() + "views/authenticated/circulationslist.xhtml", currentUrl);
        // Logout
        driver.findElement(By.id("navbarForm:logout-cbtn")).click();
        currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(TestSetup.getBaseUrl()+ "views/anonymous/login.xhtml", currentUrl);
    }
}