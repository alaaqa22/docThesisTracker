package selenium.basic;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import java.util.*;
public class UserFacultyTest {
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
    public void userFacultyFilterTest() {
        driver.get("http://localhost:8080/docthesistracker_war_exploded/views/anonymous/login.xhtml");
        driver.manage().window().setSize(new Dimension(1724, 991));
        driver.findElement(By.id("login-form:email-itxt")).click();
        driver.findElement(By.id("login-form:email-itxt")).sendKeys("test@test.com");
        driver.findElement(By.id("login-form:password-iscrt")).click();
        driver.findElement(By.id("login-form:password-iscrt")).sendKeys("password123");
        driver.findElement(By.id("login-form:login-cbtn")).click();
        driver.findElement(By.id("navbarForm:user-list-link")).click();
        driver.findElement(By.id("j_idt46:userFaculty")).click();
        {
            WebElement dropdown = driver.findElement(By.id("j_idt46:userFaculty"));
            dropdown.findElement(By.xpath("//option[. = 'Faculty of Arts']")).click();
        }
        driver.findElement(By.id("j_idt46:userState")).click();
        {
            WebElement dropdown = driver.findElement(By.id("j_idt46:userState"));
            dropdown.findElement(By.xpath("//option[. = 'EXAMINER']")).click();
        }
        driver.findElement(By.id("j_idt46:userState")).click();
        {
            WebElement dropdown = driver.findElement(By.id("j_idt46:userState"));
            dropdown.findElement(By.xpath("//option[. = 'EXAMINCOMMITTEEMEMBERS']")).click();
        }
        driver.findElement(By.id("j_idt46:userState")).click();
        {
            WebElement dropdown = driver.findElement(By.id("j_idt46:userState"));
            dropdown.findElement(By.xpath("//option[. = 'DEANERY']")).click();
        }
        driver.findElement(By.id("j_idt46:userState")).click();
        {
            WebElement dropdown = driver.findElement(By.id("j_idt46:userState"));
            dropdown.findElement(By.xpath("//option[. = 'PENDING']")).click();
        }
        driver.findElement(By.id("j_idt46:userFaculty")).click();
        {
            WebElement dropdown = driver.findElement(By.id("j_idt46:userFaculty"));
            dropdown.findElement(By.xpath("//option[. = 'Alle Fakultäten']")).click();
        }
        driver.findElement(By.id("j_idt46:userState")).click();
        {
            WebElement dropdown = driver.findElement(By.id("j_idt46:userState"));
            dropdown.findElement(By.xpath("//option[. = 'Alle Benutzer']")).click();
        }
        driver.findElement(By.id("data:lastpage-link")).click();
        driver.findElement(By.id("j_idt46:userState")).click();
        {
            WebElement dropdown = driver.findElement(By.id("j_idt46:userState"));
            dropdown.findElement(By.xpath("//option[. = 'EXAMINCOMMITTEEMEMBERS']")).click();
        }
        driver.findElement(By.id("j_idt46:userState")).click();
        {
            WebElement dropdown = driver.findElement(By.id("j_idt46:userState"));
            dropdown.findElement(By.xpath("//option[. = 'EXAMINER']")).click();
        }
        driver.findElement(By.id("j_idt46:userState")).click();
        {
            WebElement dropdown = driver.findElement(By.id("j_idt46:userState"));
            dropdown.findElement(By.xpath("//option[. = 'DEANERY']")).click();
        }
        driver.findElement(By.id("j_idt46:userFaculty")).click();
        {
            WebElement dropdown = driver.findElement(By.id("j_idt46:userFaculty"));
            dropdown.findElement(By.xpath("//option[. = 'Faculty of Science']")).click();
        }
        driver.findElement(By.id("navbarForm:logout-cbtn")).click();
    }
}
