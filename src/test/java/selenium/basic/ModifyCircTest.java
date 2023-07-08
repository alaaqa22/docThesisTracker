
package selenium.basic;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.JavascriptExecutor;

import selenium.setup.TestSetup;

import java.util.*;

public class ModifyCircTest {
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
  public void modifyCircTest() {
    driver.get(TestSetup.getBaseUrl());
      driver.manage().window().setSize(new Dimension(1440, 823));
      driver.findElement(By.id("login-form:email-itxt")).sendKeys("test@test.com");
      driver.findElement(By.id("login-form:password-iscrt")).sendKeys("password123");
      driver.findElement(By.id("login-form:login-cbtn")).click();
      driver.findElement(By.id("dataForm:cir-dt:13:titel-otxt")).click();
      driver.findElement(By.id("circulationDetails:end-date-itxt")).click();
      driver.findElement(By.id("circulationDetails:end-date-itxt")).click();
      {
          WebElement element = driver.findElement(By.id("circulationDetails:end-date-itxt"));
          Actions builder = new Actions(driver);
          builder.doubleClick(element).perform();
      }
      driver.findElement(By.id("circulationDetails:end-date-itxt")).sendKeys("10 Nov 2023");
      driver.findElement(By.id("circulationDetails:modify-cbtn")).click();

  }
}

