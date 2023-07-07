package selenium.basic;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;

import java.util.*;

public class ImpresumTest {
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
    public void impresu() {
        driver.get("http://localhost:8080/docthesistracker_war_exploded/views/anonymous/login.xhtml");
        driver.manage().window().setSize(new Dimension(1269, 809));
        driver.findElement(By.id("login-form:email-itxt")).sendKeys("test@test.com");
        driver.findElement(By.id("login-form:password-iscrt")).sendKeys("password123");
        driver.findElement(By.id("j_idt65:imprint-otxt")).click();
        driver.findElement(By.id("navbarForm:homepageLog-lin")).click();
        driver.findElement(By.id("login-form:email-itxt")).sendKeys("test@test.com");
        driver.findElement(By.id("login-form:password-iscrt")).sendKeys("password123");
        driver.findElement(By.id("login-form:login-cbtn")).click();
        driver.findElement(By.id("j_idt82:imprint-otxt")).click();
        driver.findElement(By.cssSelector("#navbarForm\\3Aprofil-link > .nav-link")).click();
        driver.findElement(By.id("j_idt48:email-itxt")).sendKeys("test@test.com");
        driver.findElement(By.id("j_idt48:password-iscrt")).sendKeys("password123");
        driver.findElement(By.id("j_idt75:imprint-otxt")).click();
        driver.findElement(By.id("navbarForm:create-circulation-link")).click();
        driver.findElement(By.id("j_idt71:imprint-otxt")).click();
        driver.findElement(By.id("navbarForm:faculty-id")).click();
        driver.findElement(By.id("j_idt64:imprint-otxt")).click();
        driver.findElement(By.id("navbarForm:help-authenticated-link")).click();
        driver.findElement(By.id("navbarForm:homepage-link")).click();
        driver.findElement(By.id("navbarForm:logout-cbtn")).click();
        driver.findElement(By.id("login-form:email-itxt")).sendKeys("test@test.com");
        driver.findElement(By.id("login-form:password-iscrt")).sendKeys("password123");
    }
}
