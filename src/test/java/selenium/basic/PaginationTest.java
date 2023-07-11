package selenium.basic;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import selenium.setup.TestSetup;

import java.time.Duration;
import java.util.*;

public class PaginationTest {
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
    public void testPaginationScenario () {
        driver.get(TestSetup.getBaseUrl());
        driver.manage().window().setSize(new Dimension(1269, 813));

        driver.manage().window().setSize(new Dimension(1269, 815));
        WebElement emailInput = driver.findElement(By.id("login-form:email-itxt"));
        emailInput.click();
        emailInput.sendKeys("test@test.com");

        WebElement passwordInput = driver.findElement(By.id("login-form:password-iscrt"));
        passwordInput.click();
        passwordInput.sendKeys("password123");

        driver.findElement(By.id("login-form:login-cbtn")).click();

        Duration maxWaitTime = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("dataForm:nextpage-link"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("dataForm:previouspage-link"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("dataForm:lastpage-link"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("dataForm:firstpage-link"))).click();

        driver.findElement(By.id("j_idt48:aktiv-cir-btn")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("dataForm:nextpage-link"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("dataForm:firstpage-link"))).click();

        driver.findElement(By.id("navbarForm:logout-cbtn")).click();
    }
}
