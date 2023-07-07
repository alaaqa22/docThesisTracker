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

public class UserListTest {
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
    public void userList() {
        driver.get("http://localhost:8080/DocThesisTracker/");
        driver.manage().window().setSize(new Dimension(884, 692));
        driver.findElement(By.id("login-form:email-itxt")).click();
        driver.findElement(By.id("login-form:password-iscrt"))
                .sendKeys("password123");
        driver.findElement(By.id("login-form:email-itxt"))
                .sendKeys("test@test.com");
        driver.findElement(By.id("login-form:login-cbtn")).click();
        driver.findElement(By.id("navbarForm:user-list-link")).click();
        driver.findElement(By.id("data:nextpage-link")).click();
        driver.findElement(By.id("data:previouspage-link")).click();
        driver.findElement(By.id("data:lastpage-link")).click();
        driver.findElement(By.id("data:firstpage-link")).click();
        driver.findElement(By.id("j_idt48:userFaculty")).click();
        {
            WebElement dropdown = driver
                    .findElement(By.id("j_idt48:userFaculty"));
            dropdown.findElement(By.xpath("//option[. = 'Faculty of Arts']"))
                    .click();
        }
        driver.findElement(
                By.cssSelector("#j_idt48\\3AuserFaculty > option:nth-child(2)"))
                .click();
        driver.findElement(By.id("j_idt48:userFaculty")).click();
        {
            WebElement dropdown = driver
                    .findElement(By.id("j_idt48:userFaculty"));
            dropdown.findElement(By.xpath("//option[. = 'Faculty of Darts']"))
                    .click();
        }
        driver.findElement(
                By.cssSelector("#j_idt48\\3AuserFaculty > option:nth-child(5)"))
                .click();
        driver.findElement(By.id("j_idt48:userFaculty")).click();
        {
            WebElement dropdown = driver
                    .findElement(By.id("j_idt48:userFaculty"));
            dropdown.findElement(By.xpath("//option[. = 'Faculty of Science']"))
                    .click();
        }
        driver.findElement(
                By.cssSelector("#j_idt48\\3AuserFaculty > option:nth-child(4)"))
                .click();
        driver.findElement(By.id("j_idt48:userFaculty")).click();
        {
            WebElement dropdown = driver
                    .findElement(By.id("j_idt48:userFaculty"));
            dropdown.findElement(By.xpath("//option[. = 'Faculty of Darts']"))
                    .click();
        }
        driver.findElement(
                By.cssSelector("#j_idt48\\3AuserFaculty > option:nth-child(5)"))
                .click();
        driver.findElement(By.id("data:user-dt:j_idt61")).click();
        driver.findElement(By.id("data:user-dt:j_idt61")).sendKeys("α");
        driver.findElement(By.id("data:user-dt:j_idt61")).sendKeys(Keys.ENTER);
        driver.findElement(By.linkText("αleph")).click();
        driver.findElement(By.id("j_idt50:user-states-slcom")).click();
        {
            WebElement dropdown = driver
                    .findElement(By.id("j_idt50:user-states-slcom"));
            dropdown.findElement(
                    By.xpath("//option[. = 'EXAMINCOMMITTEEMEMBERS']")).click();
        }
        driver.findElement(By.cssSelector("option:nth-child(2)")).click();
        driver.findElement(By.id("j_idt50:save-cbtn")).click();
        driver.findElement(By.id("j_idt50:user-states-slcom")).click();
        {
            WebElement dropdown = driver
                    .findElement(By.id("j_idt50:user-states-slcom"));
            dropdown.findElement(By.xpath("//option[. = 'EXAMINER']")).click();
        }
        driver.findElement(By.cssSelector("option:nth-child(3)")).click();
        driver.findElement(By.id("j_idt50:save-cbtn")).click();
        driver.findElement(By.id("navbarForm:user-list-link")).click();
        driver.findElement(By.id("navbarForm:logout-cbtn")).click();
    }
}
