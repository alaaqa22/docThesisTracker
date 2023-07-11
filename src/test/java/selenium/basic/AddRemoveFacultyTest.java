package selenium.basic;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import selenium.setup.TestSetup;

import java.util.*;

public class AddRemoveFacultyTest {
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
    TestSetup.tearDown();
    driver.quit();
  }


  @Test
  public void addAndRemoveFaculty() {
    driver.get(TestSetup.getBaseUrl());
    driver.manage().window().setSize(new Dimension(1440, 820));
    driver.findElement(By.id("login-form:email-itxt")).sendKeys("test@test.com");
    driver.findElement(By.id("login-form:password-iscrt")).sendKeys("password123");
    driver.findElement(By.id("login-form:login-cbtn")).click();
    driver.findElement(By.id("navbarForm:faculty-id")).click();
    driver.findElement(By.id("facultyList:newFaculty")).click();
    driver.findElement(By.id("facultyList:newFaculty")).sendKeys("Faculty of Sport");
    driver.findElement(By.name("facultyList:j_idt48")).click();
    driver.findElement(By.id("navbarForm:user-list-link")).click();
    driver.findElement(By.id("j_idt46:userFaculty")).click();
    {
      WebElement dropdown = driver.findElement(By.id("j_idt46:userFaculty"));
      dropdown.findElement(By.xpath("//option[. = 'Faculty of Sport']")).click();
    }
    driver.findElement(By.id("navbarForm:faculty-id")).click();
    driver.findElement(By.name("facultyTable:4:j_idt57:j_idt58")).click();
    assertThat(driver.switchTo().alert().getText(), is("Sind sie sicher, dass sie diese Fakultät löschen wollen?"));
    driver.switchTo().alert().accept();
   // driver.findElement(By.cssSelector("li > span")).click();
    driver.findElement(By.id("navbarForm:logout-cbtn")).click();
  }

}
