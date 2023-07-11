package selenium.basic;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;


import org.openqa.selenium.*;

import selenium.setup.TestSetup;

import java.util.*;


/**
 * This test requires the user johannes.test@email.com to be a reigstered authenticated user. With access to a faculty
 * that has a circulation with the title: 'Mad Title'. Not case-sensitive.
 * @author Johannes Silvennoinen
 */
public class SearchCirculationListTest {
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

  /**
   * Checks if the user can search for the title with all lowercase and all uppercase with the same result.
   * @throws InterruptedException if the sleeping thread gets interrupted.
   */
  @Test
  public void searchCirculationList() throws InterruptedException {
    // Setup
    driver.get(TestSetup.getBaseUrl());
    driver.manage().window().setSize(new Dimension(1134, 636));

    // Get the loginUrl
    String previousUrl = driver.getCurrentUrl();

    // Login
    driver.findElement(By.id("login-form:email-itxt")).sendKeys("johannes.test@email.com");
    driver.findElement(By.id("login-form:password-iscrt")).sendKeys("gdx7hMAeqD5AKtH*");
    driver.findElement(By.id("login-form:login-cbtn")).click();

    // Get the circulationURL
    String currentUrl = driver.getCurrentUrl();

    // Assert that we are no longer on the login page
    if (!currentUrl.equals(previousUrl)) {
      System.out.println("URl has changed");
    } else {
      Assert.fail("Url has not changed. Test failed.");
    }

    // Search for "MAD TITLE"
    driver.findElement(By.id("dataForm:cir-dt:titel-itxt")).click();
    driver.findElement(By.id("dataForm:cir-dt:titel-itxt")).sendKeys("MAD TITLE" + Keys.ENTER);

    // Wait for rows to update
    Thread.sleep(500);

    // Get the rows from the data table
    By rowLocator = By.xpath("//*[@id=\"dataForm:cir-dt\"]/tbody/tr/td[1]");
    List<WebElement> rows = driver.findElements(rowLocator);
    String firstSearch = rows.get(0).getText();

    // Go back to the homepage
    driver.findElement(By.cssSelector("td:nth-child(2)")).click();
    driver.findElement(By.id("navbarForm:homepage-link")).click();

    // Search for "mad title"
    driver.findElement(By.id("dataForm:cir-dt:titel-itxt")).click();
    driver.findElement(By.id("dataForm:cir-dt:titel-itxt")).sendKeys("mad title" + Keys.ENTER);

    // Wait for the rows to update
    Thread.sleep(500);

    // Get the rows from the data table
    rows = driver.findElements(rowLocator);
    String secondSearch = rows.get(0).getText();

    // Assert that the search yields the same result regardless.
    Assert.assertEquals("Search did not yield the same result.", firstSearch, secondSearch);
  }
}
