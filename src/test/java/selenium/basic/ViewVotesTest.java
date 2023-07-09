package selenium.basic;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import java.util.*;
public class ViewVotesTest{
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
    public void viewVoteTest() {
        driver.get("http://localhost:8080/docthesistracker_war_exploded/views/anonymous/login.xhtml");
        driver.manage().window().setSize(new Dimension(1269, 813));
        driver.findElement(By.id("login-form:email-itxt")).click();
        driver.findElement(By.id("login-form:email-itxt")).sendKeys("jane@example.com");
        driver.findElement(By.id("login-form:password-iscrt")).click();
        driver.findElement(By.id("login-form:password-iscrt")).sendKeys("Password@123");
        driver.findElement(By.id("login-form:login-cbtn")).click();
        driver.findElement(By.id("dataForm:cir-dt:0:titel-otxt")).click();
        driver.findElement(By.id("circulationDetails:voteList-link")).click();
    }
}


