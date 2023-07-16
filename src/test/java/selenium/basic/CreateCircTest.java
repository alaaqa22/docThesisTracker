package selenium.basic;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import org.openqa.selenium.*;

import selenium.setup.TestSetup;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class CreateCircTest {
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
    public void createCircTest() throws InterruptedException {
        driver.get(TestSetup.getBaseUrl());
        driver.manage().window().setSize(new Dimension(1440, 823));
        driver.findElement(By.id("login-form:email-itxt")).click();
        driver.findElement(By.id("login-form:email-itxt")).sendKeys("test@test.com");
        driver.findElement(By.id("login-form:email-itxt")).click();
        driver.findElement(By.id("login-form:password-iscrt")).sendKeys("password123");
        driver.findElement(By.id("login-form:login-cbtn")).click();
        driver.findElement(By.id("navbarForm:create-circulation-link")).click();
        driver.findElement(By.id("createCirculation:title-itxt")).click();
        driver.findElement(By.id("createCirculation:title-itxt")).sendKeys("Doktor der Naturwissenschaften");
        driver.findElement(By.id("createCirculation:doctoral-candidate-name-itxt")).click();
        driver.findElement(By.id("createCirculation:doctoral-candidate-name-itxt")).sendKeys("Martin Haaland");
        driver.findElement(By.id("createCirculation:doctoral-supervisor-itxt")).click();
        driver.findElement(By.id("createCirculation:doctoral-supervisor-itxt")).sendKeys("Prof. Dr Zimmermann");
        driver.findElement(By.id("createCirculation:description-itxt")).click();
        driver.findElement(By.id("createCirculation:description-itxt")).sendKeys("..");
        driver.findElement(By.id("createCirculation:start-date-itxt")).click();
        driver.findElement(By.id("createCirculation:start-date-itxt")).sendKeys("23 Jan 2024");
        driver.findElement(By.id("createCirculation:end-date-itxt")).click();
        driver.findElement(By.id("createCirculation:end-date-itxt")).sendKeys("20 Feb 2024");
        driver.findElement(By.id("createCirculation:faculty-som")).click();
        {
            WebElement dropdown = driver.findElement(By.id("createCirculation:faculty-som"));
            dropdown.findElement(By.xpath("//option[. = 'Faculty of Science']")).click();
        }
        driver.findElement(By.id("createCirculation:create")).click();

        String actualMessage = driver.findElement(By.cssSelector(".message")).getText();
        String expectedMessage = "Circulation created successfully";
        assertEquals(expectedMessage, actualMessage);


    }

    @Test
    public void removeCircTest() throws InterruptedException {
        driver.get(TestSetup.getBaseUrl());
        driver.manage().window().setSize(new Dimension(1440, 823));
        driver.findElement(By.id("login-form:email-itxt")).sendKeys("test@test.com");
        driver.findElement(By.id("login-form:password-iscrt")).sendKeys("password123");
        driver.findElement(By.id("login-form:login-cbtn")).click();
        driver.findElement(By.id("dataForm:cir-dt:doctoralSupervisor-itxt")).click();
        driver.findElement(By.id("dataForm:cir-dt:titel-itxt")).sendKeys("Doktor der Naturwissenschaften" + Keys.ENTER);

        Thread.sleep(500);
        driver.findElement(By.id("dataForm:cir-dt:0:titel-otxt")).click();
        driver.findElement(By.id("circulationDetails:remove-cbtn")).click();
        assertThat(driver.switchTo().alert().getText(), is("Sind sie sicher, dass sie dieses Umlauf löschen wollen?"));
        driver.switchTo().alert().accept();
        assertThat(driver.getCurrentUrl(),
                is(TestSetup.getBaseUrl() + "views/authenticated/circulationslist.xhtml"));
    }


}
