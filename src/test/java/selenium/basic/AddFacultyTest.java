package selenium.basic;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.hamcrest.CoreMatchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import selenium.setup.TestSetup;

import java.util.*;

public class AddFacultyTest {
    JavascriptExecutor js;
    private WebDriver driver;
    private Map<String, Object> vars;

    @Before
    public void setUp() {
        TestSetup.setup();
        driver = TestSetup.getDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }

    @After
    public void tearDown() {
        TestSetup.tearDown();
        driver.quit();
    }


    @Test
    public void addFaculty() throws InterruptedException {
        driver.get(TestSetup.getBaseUrl());
        driver.manage().window().setSize(new Dimension(1440, 820));
        driver.findElement(By.id("login-form:email-itxt")).sendKeys("test@test.com");
        driver.findElement(By.id("login-form:password-iscrt")).sendKeys("password123");
        driver.findElement(By.id("login-form:login-cbtn")).click();
        driver.findElement(By.id("navbarForm:faculty-id")).click();
        driver.findElement(By.id("facultyNew:newFaculty")).click();
        driver.findElement(By.id("facultyNew:newFaculty")).sendKeys("Faculty of Sport");
        driver.findElement(By.name("facultyNew:cbt-new")).click();
        driver.findElement(By.id("navbarForm:user-list-link")).click();

        driver.findElement(By.id("j_idt46:userFaculty")).click();
        Thread.sleep(500);

        WebElement dropdown = driver.findElement(By.id("j_idt46:userFaculty"));
        dropdown.findElement(By.xpath("//option[. = 'Faculty of Sport']")).click();

        driver.findElement(By.id("navbarForm:logout-cbtn")).click();
    }

}
