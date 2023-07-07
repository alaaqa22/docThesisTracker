package selenium.basic;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import selenium.setup.TestSetup;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;


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

    @SuppressWarnings("deprecation")
    @Test
    public void changeEmailPw() {
        driver.get(TestSetup.getBaseUrl());
        driver.manage().window().setSize(new Dimension(1299, 692));
        driver.findElement(By.id("login-form:email-itxt")).click();
        driver.findElement(By.id("login-form:password-iscrt"))
                .sendKeys("Asdf&123");
        driver.findElement(By.id("login-form:email-itxt"))
                .sendKeys("tom.test@test.com");
        driver.findElement(By.id("login-form:login-cbtn")).click();
        driver.findElement(
                By.cssSelector("#navbarForm\\3Aprofil-link > .nav-link"))
                .click();
        driver.findElement(By.id("j_idt50:email-itxt")).click();
        driver.findElement(By.id("j_idt50:email-itxt"))
                .sendKeys("bob.test@test.com");
        driver.findElement(By.id("j_idt50:password-iscrt")).click();
        driver.findElement(By.id("j_idt50:password-iscrt"))
                .sendKeys("Asdf123&");
        driver.findElement(By.id("j_idt50:save-cbtn")).click();
        assertThat(driver.findElement(By.cssSelector("li > span")).getText(),
                is("Ihre persönlichen Informationen wurden erfolgreich aktualisiert."));
        driver.findElement(By.id("navbarForm:logout-cbtn")).click();
        driver.findElement(By.id("login-form:email-itxt")).click();
        driver.findElement(By.id("login-form:password-iscrt"))
                .sendKeys("Asdf123&");
        driver.findElement(By.id("login-form:email-itxt"))
                .sendKeys("bob.test@test.com");
        driver.findElement(By.id("login-form:login-cbtn")).click();
        assertThat(driver.findElement(By.cssSelector("th > .text-center"))
                .getText(), is("Alle Umläufe"));
        driver.findElement(
                By.cssSelector("#navbarForm\\3Aprofil-link > .nav-link"))
                .click();
        driver.findElement(By.id("j_idt50:email-itxt")).click();
        driver.findElement(By.id("j_idt50:password-iscrt"))
                .sendKeys("Asdf&123");
        driver.findElement(By.id("j_idt50:email-itxt"))
                .sendKeys("tom.test@test.com");
        driver.findElement(By.id("j_idt50:save-cbtn")).click();
        driver.findElement(By.id("navbarForm:logout-cbtn")).click();
        driver.findElement(By.id("login-form:email-itxt")).click();
        driver.findElement(By.id("login-form:password-iscrt"))
                .sendKeys("Asdf&123");
        driver.findElement(By.id("login-form:email-itxt"))
                .sendKeys("tom.test@test.com");
        driver.findElement(By.id("login-form:login-cbtn")).click();
        driver.findElement(By.id("navbarForm:logout-cbtn")).click();
    }
}
