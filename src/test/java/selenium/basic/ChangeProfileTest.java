package selenium.basic;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import selenium.setup.TestSetup;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChangeProfileTest {

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
        driver.quit();
    }

    @Test
    public void changeProfile() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(TestSetup.getBaseUrl());
        driver.manage().window().setSize(new Dimension(1382, 692));
        driver.findElement(By.id("login-form:email-itxt")).click();
        driver.findElement(By.id("login-form:password-iscrt"))
                .sendKeys("Asht123&");
        driver.findElement(By.id("login-form:email-itxt"))
                .sendKeys("john@example.com");
        driver.findElement(By.id("login-form:login-cbtn")).click();
        driver.findElement(By.id("navbarForm:profil-link")).click();
        driver.findElement(By.id("profile:firstName-itxt")).click();
        driver.findElement(By.id("profile:firstName-itxt")).click();
        driver.findElement(By.id("profile:firstName-itxt"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), "Bob");
        driver.findElement(By.id("profile:lastName-itxt")).click();
        driver.findElement(By.id("profile:lastName-itxt"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), "Wild");
        driver.findElement(By.id("profile:birthDate-itxt")).click();
        driver.findElement(By.id("profile:birthDate-itxt"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), "20.04.1970");
        driver.findElement(By.id("profile:save-cbtn")).click();
        wait.until(ExpectedConditions
                .presenceOfElementLocated(By.cssSelector(".alert")));
        assertThat(driver.findElement(By.cssSelector(".alert")).getText(),
                is("Neue Änderungen wurden erfolgreich aktualisiert."));
        driver.findElement(By.id("navbarForm:homepage-link")).click();
        driver.findElement(By.id("navbarForm:profil-link")).click();
        driver.findElement(By.id("profile:firstName-itxt"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), "John");
        driver.findElement(By.id("profile:lastName-itxt")).click();
        driver.findElement(By.id("profile:lastName-itxt"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), "Doe");
        driver.findElement(By.id("profile:birthDate-itxt")).click();
        driver.findElement(By.id("profile:birthDate-itxt")).click();
        driver.findElement(By.id("profile:birthDate-itxt"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), "20.04.1969");
        driver.findElement(By.id("profile:save-cbtn")).click();
        wait.until(ExpectedConditions
                .presenceOfElementLocated(By.cssSelector(".alert")));
        driver.findElement(By.cssSelector(".alert")).click();
        driver.findElement(By.id("navbarForm:logout-cbtn")).click();
    }
}
