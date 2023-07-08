package selenium.basic;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import org.openqa.selenium.*;

import selenium.setup.TestSetup;

import java.util.*;

public class CreateCircTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    TestSetup.setup();
    driver =TestSetup.getDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    TestSetup.tearDown();
    driver.quit();
  }
  @Test
  public void createCircTest() {
    driver.get(TestSetup.getBaseUrl());
    driver.manage().window().setSize(new Dimension(1440, 823));
    driver.findElement(By.id("login-form:email-itxt")).sendKeys("test@test.com");
    driver.findElement(By.id("login-form:password-iscrt")).sendKeys("password123");
    driver.findElement(By.id("login-form:login-cbtn")).click();
    driver.findElement(By.id("navbarForm:create-circulation-link")).click();
    driver.findElement(By.id("createCirculation:title-itxt")).click();
    driver.findElement(By.id("createCirculation:title-itxt")).sendKeys("Doktor der Naturwissenschaften");
    driver.findElement(By.id("createCirculation:doctoral-candidate-name-itxt")).click();
    driver.findElement(By.id("createCirculation:doctoral-candidate-name-itxt")).sendKeys("Martin Haaland.");
    driver.findElement(By.id("createCirculation:doctoral-supervisor-itxt")).click();
    driver.findElement(By.id("createCirculation:doctoral-supervisor-itxt")).sendKeys("Prof. Dr Zimmermann");
    driver.findElement(By.id("createCirculation:description-itxt")).click();
    driver.findElement(By.id("createCirculation:description-itxt")).sendKeys("..");
    driver.findElement(By.id("createCirculation:start-date-itxt")).click();
    driver.findElement(By.id("createCirculation:start-date-itxt")).sendKeys("23 Jan 24");
    driver.findElement(By.id("createCirculation:end-date-itxt")).sendKeys("20 Feb 24");
    driver.findElement(By.id("createCirculation:faculty-som")).click();
    {
      WebElement dropdown = driver.findElement(By.id("createCirculation:faculty-som"));
      dropdown.findElement(By.xpath("//option[. = 'Faculty of Science']")).click();
    }
    driver.findElement(By.id("createCirculation:create")).click();
  }

}
