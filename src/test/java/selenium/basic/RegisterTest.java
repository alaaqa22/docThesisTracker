package selenium.basic;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import selenium.setup.TestSetup;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import java.util.HashMap;
import java.util.Map;

public class RegisterTest {

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
    public void register() {
        driver.get(TestSetup.getBaseUrl());
        driver.manage().window().setSize(new Dimension(1382, 692));
        driver.findElement(By.linkText("Registrieren")).click();
        driver.findElement(By.id("registration-form:email-itxt")).click();
        driver.findElement(By.id("registration-form:email-itxt")).sendKeys(
                Keys.chord(Keys.CONTROL, "a"),
                "rwewycrwraupfdufcyruodwfy61441r41cb6car14ar56c4wcwba6@test.com");
        driver.findElement(By.id("registration-form:firstname-itxt")).click();
        driver.findElement(By.id("registration-form:firstname-itxt"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), "Marcus");
        driver.findElement(By.id("registration-form:lastname-itxt")).click();
        driver.findElement(By.id("registration-form:lastname-itxt"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), "Mustermann");
        driver.findElement(By.id("registration-form:birthdate-itxt")).click();
        driver.findElement(By.id("registration-form:birthdate-itxt"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), "1990-05-05");
        driver.findElement(By.name("registration-form:j_idt57")).click();
        assertThat(driver.getCurrentUrl(),
                is(TestSetup.getBaseUrl() + "views/anonymous/token.xhtml"));
    }
}
