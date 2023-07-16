package selenium.basic;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import org.openqa.selenium.*;

import selenium.setup.TestSetup;

import java.util.*;

public class SearchFilterTest {
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
    public void searchFilterTest(){
        driver.get(TestSetup.getBaseUrl());
        driver.manage().window().setSize(new Dimension(1440, 823));
        driver.findElement(By.id("login-form:email-itxt")).sendKeys("test@test.com");
        driver.findElement(By.id("login-form:password-iscrt")).sendKeys("password123");
        driver.findElement(By.id("login-form:login-cbtn")).click();
        //filter with doctoral supervisor
        driver.findElement(By.id("dataForm:cir-dt:doctoralSupervisor-itxt")).click();
        driver.findElement(By.id("dataForm:cir-dt:doctoralSupervisor-itxt")).sendKeys("Prof.y" + Keys.ENTER);
        driver.findElement(By.id("dataForm:cir-dt:0:titel-otxt")).click();

    }
}
