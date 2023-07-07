package selenium.basic;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

public class ChangeProfileTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void changeProfile() {
        driver.get("http://localhost:8080/DocThesisTracker/");
        driver.manage().window().setSize(new Dimension(884, 692));
        driver.findElement(By.id("login-form:email-itxt")).click();
        driver.findElement(By.id("login-form:password-iscrt"))
                .sendKeys("password123");
        driver.findElement(By.id("login-form:email-itxt"))
                .sendKeys("test@test.com");
        driver.findElement(By.id("login-form:login-cbtn")).click();
        driver.findElement(
                By.cssSelector("#navbarForm\\3Aprofil-link > .nav-link"))
                .click();
        driver.findElement(By.id("j_idt50:firstName-itxt")).click();
        driver.findElement(By.id("j_idt50:firstName-itxt")).sendKeys("Bob");
        driver.findElement(By.id("j_idt50:lastName-itxt")).click();
        driver.findElement(By.id("j_idt50:lastName-itxt")).sendKeys("Bob");
        driver.findElement(By.id("j_idt50:birthDate-itxt")).click();
        driver.findElement(By.id("j_idt50:birthDate-itxt"))
                .sendKeys("20.04.1970");
        driver.findElement(By.id("j_idt50:save-cbtn")).click();
        driver.findElement(By.cssSelector("li > span")).click();
        assertThat(driver.findElement(By.cssSelector("li > span")).getText(),
                is("Ihre persönlichen Informationen wurden erfolgreich aktualisiert."));
        driver.findElement(By.id("navbarForm:homepage-link")).click();
        driver.findElement(
                By.cssSelector("#navbarForm\\3Aprofil-link > .nav-link"))
                .click();
        driver.findElement(By.id("j_idt50:firstName-itxt")).sendKeys("Test");
        driver.findElement(By.id("j_idt50:lastName-itxt")).click();
        driver.findElement(By.id("j_idt50:lastName-itxt")).sendKeys("Test");
        driver.findElement(By.id("j_idt50:birthDate-itxt")).click();
        driver.findElement(By.id("j_idt50:birthDate-itxt")).click();
        driver.findElement(By.id("j_idt50:birthDate-itxt"))
                .sendKeys("20.04.1969");
        driver.findElement(By.id("j_idt50:save-cbtn")).click();
        driver.findElement(By.cssSelector("li > span")).click();
        driver.findElement(By.id("navbarForm:logout-cbtn")).click();
    }
}
