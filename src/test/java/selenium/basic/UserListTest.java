package selenium.basic;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import selenium.setup.TestSetup;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import java.util.Map;
import java.time.Duration;
import java.util.HashMap;

public class UserListTest {

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
    public void userList() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(TestSetup.getBaseUrl());
        driver.manage().window().setSize(new Dimension(1382, 692));
        driver.findElement(By.id("login-form:email-itxt")).click();
        driver.findElement(By.id("login-form:password-iscrt"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), "Asht123&");
        driver.findElement(By.id("login-form:email-itxt"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), "john@example.com");
        driver.findElement(By.id("login-form:login-cbtn")).click();
        driver.findElement(By.id("navbarForm:user-list-link")).click();
        wait.until(ExpectedConditions
                .presenceOfElementLocated(By.id("data:nextpage-link")));
        driver.findElement(By.id("data:nextpage-link")).click();
        wait.until(ExpectedConditions
                .presenceOfElementLocated(By.id("data:previouspage-link")));
        driver.findElement(By.id("data:previouspage-link")).click();
        wait.until(ExpectedConditions
                .presenceOfElementLocated(By.id("data:lastpage-link")));
        driver.findElement(By.id("data:lastpage-link")).click();
        wait.until(ExpectedConditions
                .presenceOfElementLocated(By.id("data:firstpage-link")));
        driver.findElement(By.id("data:firstpage-link")).click();
        driver.findElement(By.id("j_idt46:userFaculty")).click();
        {
            WebElement dropdown = driver
                    .findElement(By.id("j_idt46:userFaculty"));
            dropdown.findElement(By.xpath("//option[. = 'Faculty of Arts']"))
                    .click();
        }
        driver.findElement(
                By.cssSelector("#j_idt46\\3AuserFaculty > option:nth-child(2)"))
                .click();
        driver.findElement(By.id("j_idt46:userFaculty")).click();
        {
            WebElement dropdown = driver
                    .findElement(By.id("j_idt46:userFaculty"));
            dropdown.findElement(By.xpath("//option[. = 'Faculty of Darts']"))
                    .click();
        }
        driver.findElement(
                By.cssSelector("#j_idt46\\3AuserFaculty > option:nth-child(5)"))
                .click();
        driver.findElement(By.id("j_idt46:userFaculty")).click();
        {
            WebElement dropdown = driver
                    .findElement(By.id("j_idt46:userFaculty"));
            dropdown.findElement(By.xpath("//option[. = 'Faculty of Science']"))
                    .click();
        }
        driver.findElement(
                By.cssSelector("#j_idt46\\3AuserFaculty > option:nth-child(4)"))
                .click();
        driver.findElement(By.id("j_idt46:userFaculty")).click();
        {
            WebElement dropdown = driver
                    .findElement(By.id("j_idt46:userFaculty"));
            dropdown.findElement(By.xpath("//option[. = 'Faculty of Darts']"))
                    .click();
        }
        driver.findElement(
                By.cssSelector("#j_idt46\\3AuserFaculty > option:nth-child(5)"))
                .click();
        driver.findElement(By.id("data:user-dt:firstName-itxt")).click();
        driver.findElement(By.id("data:user-dt:firstName-itxt"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), "α");
        driver.findElement(By.id("data:user-dt:firstName-itxt"))
                .sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions
                .presenceOfElementLocated(By.linkText("αleph")));
        driver.findElement(By.linkText("αleph")).click();
        driver.findElement(By.id("profile:faculty-slcom")).click();
        {
            WebElement dropdown = driver
                    .findElement(By.id("profile:faculty-slcom"));
            dropdown.findElement(By.xpath("//option[. = 'Faculty of Darts']"))
                    .click();
        }
        driver.findElement(By.cssSelector(
                "#profile\\3A faculty-slcom > option:nth-child(4)")).click();
        driver.findElement(By.id("profile:user-states-slcom")).click();
        {
            WebElement dropdown = driver
                    .findElement(By.id("profile:user-states-slcom"));
            dropdown.findElement(
                    By.xpath("//option[. = 'EXAMINCOMMITTEEMEMBERS']")).click();
        }
        driver.findElement(By.cssSelector(
                "#profile\\3Auser-states-slcom > option:nth-child(2)")).click();
        driver.findElement(By.id("profile:updateAuth-cbtn")).click();
        assertThat(driver.findElement(By.cssSelector(".alert")).getText(),
                is("Authentifizierung wurde aktualisiert."));
        driver.findElement(By.id("profile:user-states-slcom")).click();
        {
            WebElement dropdown = driver
                    .findElement(By.id("profile:user-states-slcom"));
            dropdown.findElement(By.xpath("//option[. = 'EXAMINER']")).click();
        }
        driver.findElement(By.cssSelector(
                "#profile\\3Auser-states-slcom > option:nth-child(3)")).click();
        driver.findElement(By.id("profile:updateAuth-cbtn")).click();
        driver.findElement(By.id("navbarForm:user-list-link")).click();
        driver.findElement(By.id("navbarForm:logout-cbtn")).click();
    }
}
