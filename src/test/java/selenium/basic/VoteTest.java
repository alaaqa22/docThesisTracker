package selenium.basic;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;

import java.util.*;

public class VoteTest {
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
    public void voteTest() {
        driver.get("http://localhost:8080/docthesistracker_war_exploded/views/anonymous/login.xhtml");
        driver.manage().window().setSize(new Dimension(1269, 809));
        driver.findElement(By.id("login-form:email-itxt")).sendKeys("ThomasMuller@gamil.com");
        driver.findElement(By.id("login-form:password-iscrt")).sendKeys("Password@123");
        driver.findElement(By.id("login-form:login-cbtn")).click();
        driver.findElement(By.id("dataForm:cir-dt:1:titel-otxt")).click();
        driver.findElement(By.id("j_idt49:reason-itxt")).click();
        driver.findElement(By.id("j_idt49:reason-itxt")).sendKeys("test Vote ");
        driver.findElement(By.id("j_idt49:vote-btn")).click();
        driver.findElement(By.id("navbarForm:homepage-link")).click();
        driver.findElement(By.id("navbarForm:logout-cbtn")).click();
        driver.findElement(By.id("login-form:email-itxt")).sendKeys("ThomasMuller@gamil.com");
        driver.findElement(By.id("login-form:password-iscrt")).sendKeys("Password@123");
    }
}
