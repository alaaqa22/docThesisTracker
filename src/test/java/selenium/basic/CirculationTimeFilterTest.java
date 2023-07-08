package selenium.basic;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;
import java.util.*;
public class CirculationTimeFilterTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    @Before
    public void setUp() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }
    @After
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void circulationTimeFilter() {
        driver.get("http://localhost:8080/docthesistracker_war_exploded/views/anonymous/login.xhtml");
        driver.manage().window().setSize(new Dimension(1269, 813));
        driver.findElement(By.id("login-form:email-itxt")).click();
        driver.findElement(By.id("login-form:email-itxt")).sendKeys("test@test.com");
        driver.findElement(By.id("login-form:password-iscrt")).click();
        driver.findElement(By.id("login-form:password-iscrt")).sendKeys("password123");
        driver.findElement(By.id("login-form:login-cbtn")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(By.id("j_idt48:all-cir-btn"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("j_idt48:aktiv-cir-btn"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("dataForm:nextpage-link"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("dataForm:nextpage-link"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("j_idt48:old-cir-btn"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("dataForm:nextpage-link"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("dataForm:firstpage-link"))).click();
        driver.findElement(By.id("navbarForm:logout-cbtn")).click();
    }
}
