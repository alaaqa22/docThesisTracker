package selenium.setup;

import dtt.business.utilities.SystemInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.mockito.Mock;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;

public class TestSetup {

    private static WebDriver driver;
    // Modify this based on your tomcat port.
    private static final int PORT = 9999;
    // Modify this based on your base url.
    private static final String URL = "http://localhost:" + PORT + "/docthesistracker_war_exploded/";
    public static void setup() {
        // Modify this based on your browser.
        driver = new ChromeDriver();
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static String getBaseUrl() {
        return URL;
    }
    public static void tearDown() {
        driver.quit();
    }
}