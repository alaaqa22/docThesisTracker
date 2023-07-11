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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.setup.TestSetup;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

public class ChangeEmailPwTest {

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
    public void changeEmailPw() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(TestSetup.getBaseUrl());
        driver.manage().window().setSize(new Dimension(1382, 692));
        driver.findElement(By.id("login-form:email-itxt")).click();
        driver.findElement(By.id("login-form:password-iscrt"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), "Asdf&123");
        driver.findElement(By.id("login-form:email-itxt"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), "tom.test@test.com");
        driver.findElement(By.id("login-form:login-cbtn")).click();
        driver.findElement(By.id("navbarForm:profil-link")).click();
        driver.findElement(By.id("profile:email-itxt")).click();
        driver.findElement(By.id("profile:email-itxt"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), "bob.test@test.com");
        driver.findElement(By.id("profile:password-iscrt")).click();
        driver.findElement(By.id("profile:password-iscrt"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), "Asdf123&");
        driver.findElement(By.id("profile:save-cbtn")).click();
        wait.until(ExpectedConditions
                .presenceOfElementLocated(By.cssSelector(".alert")));
        assertThat(driver.findElement(By.cssSelector(".alert")).getText(),
                is("Neue Änderungen wurden erfolgreich aktualisiert."));
        driver.findElement(By.id("navbarForm:logout-cbtn")).click();
        driver.findElement(By.id("login-form:email-itxt")).click();
        driver.findElement(By.id("login-form:password-iscrt"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), "Asdf123&");
        driver.findElement(By.id("login-form:email-itxt"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), "bob.test@test.com");
        driver.findElement(By.id("login-form:login-cbtn")).click();
        assertThat(driver.getCurrentUrl(), is(TestSetup.getBaseUrl()
                + "views/authenticated/circulationslist.xhtml"));
        driver.findElement(By.id("navbarForm:profil-link")).click();
        driver.findElement(By.id("profile:email-itxt")).click();
        driver.findElement(By.id("profile:password-iscrt"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), "Asdf&123");
        driver.findElement(By.id("profile:email-itxt"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), "tom.test@test.com");
        driver.findElement(By.id("profile:save-cbtn")).click();
        driver.findElement(By.id("navbarForm:logout-cbtn")).click();
        driver.findElement(By.id("login-form:email-itxt")).click();
        driver.findElement(By.id("login-form:password-iscrt"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), "Asdf&123");
        driver.findElement(By.id("login-form:email-itxt"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), "tom.test@test.com");
        driver.findElement(By.id("login-form:login-cbtn")).click();
        driver.findElement(By.id("navbarForm:logout-cbtn")).click();
    }
}
