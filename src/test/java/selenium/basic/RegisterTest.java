package selenium.basic;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import java.util.HashMap;
import java.util.Map;


public class RegisterTest {
    
    private final static String localhost = "http://localhost:9999/docthesistracker_war_exploded/";
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
    public void register() {
        driver.get(localhost);
        driver.manage().window().setSize(new Dimension(1382, 692));
        driver.findElement(By.linkText("Registrieren")).click();
        driver.findElement(By.id("registration-form:email-itxt")).click();
        driver.findElement(By.id("registration-form:email-itxt")).sendKeys(
                "rwewycrwraupfdufcyruodwfy61441r41cb6car14ar56c4wcwba6@test.com");
        driver.findElement(By.id("registration-form:firstname-itxt")).click();
        driver.findElement(By.id("registration-form:firstname-itxt"))
                .sendKeys("Marcus");
        driver.findElement(By.id("registration-form:lastname-itxt")).click();
        driver.findElement(By.id("registration-form:lastname-itxt"))
                .sendKeys("Mustermann");
        driver.findElement(By.id("registration-form:birthdate-itxt")).click();
        driver.findElement(By.id("registration-form:birthdate-itxt"))
                .sendKeys("1990-05-05");
        driver.findElement(By.name("registration-form:j_idt59")).click();
        assertThat(driver.findElement(By.cssSelector(".mb-4")).getText(),
                is("Login"));
    }
}
