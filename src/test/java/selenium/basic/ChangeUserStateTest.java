package selenium.basic;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class ChangeUserStateTest {
    private WebDriver driver;
    private Map<String, Object> vars;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        vars = new HashMap<>();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void changeUserStateTest() {
        driver.get("http://localhost:8080/docthesistracker_war_exploded/views/anonymous/login.xhtml");
        driver.manage().window().setSize(new Dimension(1269, 875));
        driver.findElement(By.id("login-form:email-itxt")).click();
        driver.findElement(By.id("login-form:email-itxt")).sendKeys("jane@example.com");
        driver.findElement(By.id("login-form:password-iscrt")).click();
        driver.findElement(By.id("login-form:password-iscrt")).sendKeys("Password@123");
        driver.findElement(By.id("login-form:login-cbtn")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("navbarForm:user-list-link")));
        driver.findElement(By.id("navbarForm:user-list-link")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("j_idt46:userState")));
        driver.findElement(By.id("j_idt46:userState")).click();

        WebElement dropdown = driver.findElement(By.id("j_idt46:userState"));
        dropdown.findElement(By.xpath("//option[. = 'PENDING']")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("data:user-dt:0:firstName-otxt"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("j_idt46:userState")));
        driver.findElement(By.id("j_idt46:userState")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("j_idt48:user-states-slcom")));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("navbarForm:user-list-link")));


        dropdown = driver.findElement(By.id("j_idt48:user-states-slcom"));
        dropdown.findElement(By.xpath("//option[. = 'EXAMINER']")).click();

        driver.findElement(By.id("j_idt48:updateAuth-cbtn")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("navbarForm:user-list-link")));
        driver.findElement(By.id("navbarForm:user-list-link")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("j_idt46:userState")));
        driver.findElement(By.id("j_idt46:userState")).click();

        dropdown = driver.findElement(By.id("j_idt46:userState"));
        dropdown.findElement(By.xpath("//option[. = 'EXAMINER']")).click();


        driver.findElement(By.id("navbarForm:logout-cbtn")).click();
    }

}

