package selenium.basic;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import org.openqa.selenium.*;


import selenium.setup.TestSetup;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModifyCircTest {
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
    public void searchModifyCircTest() throws InterruptedException {
        driver.get(TestSetup.getBaseUrl());
        driver.manage().window().setSize(new Dimension(1440, 823));
        driver.findElement(By.id("login-form:email-itxt")).sendKeys("test@test.com");
        driver.findElement(By.id("login-form:password-iscrt")).sendKeys("password123");
        driver.findElement(By.id("login-form:login-cbtn")).click();

        //filter with title
        driver.findElement(By.id("dataForm:cir-dt:titel-itxt")).click();
        driver.findElement(By.id("dataForm:cir-dt:titel-itxt")).sendKeys("SelCirc"+ Keys.ENTER);
        Thread.sleep(1000);
        driver.findElement(By.id("dataForm:cir-dt:0:titel-otxt")).click();

        //Change the end date
        WebElement inputField = driver.findElement(By.id("circulationDetails:end-date-itxt"));
        inputField.clear();
        driver.findElement(By.id("circulationDetails:end-date-itxt")).sendKeys("18.09.2023");
        driver.findElement(By.id("circulationDetails:modify-cbtn")).click();
        String expectedMessage = "Umlauf wurde geändert.";

        //get the message from server after clicking on modify button
        String actualMessage = driver.findElement(By.cssSelector(".alert")).getText();
        assertEquals(expectedMessage, actualMessage);

    }
}

