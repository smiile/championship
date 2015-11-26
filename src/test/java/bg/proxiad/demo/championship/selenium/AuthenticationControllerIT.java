package bg.proxiad.demo.championship.selenium;

import java.util.Objects;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class AuthenticationControllerIT {

    private static ChromeDriver driver;

    @BeforeClass
    public static void openBrowser() {
        driver = new ChromeDriver();
    }

    @AfterClass
    public static void closeBrowser() {
        driver.quit();
    }

    @Test
    public void isIntruderBlocked() {
        driver.get("http://localhost:8080/championship/app/groups");

        Boolean loginFormFound = driver.findElements(By.className("form-signin")).size() > 0;

        assertTrue(loginFormFound);
    }

    @Test
    public void userLifeCycle() {
        driver.get("http://localhost:8080/championship/app/auth/login");
        
        String initialSessionID = driver.manage().getCookieNamed("JSESSIONID").getValue();
        
        // Login
        driver.findElement(By.id("inputEmail")).sendKeys("test@test.it");
        driver.findElement(By.id("inputPassword")).sendKeys("test");
        driver.findElement(By.tagName("button")).submit();
        
        assertTrue("logged in", driver.findElementsByPartialLinkText("test").size() > 0);
        
        // Logout
        driver.get("http://localhost:8080/championship/app/auth/logout");
        String afterLogoutSessionID = driver.manage().getCookieNamed("JSESSIONID").getValue();
        
        assertTrue("logged out", !Objects.equals(initialSessionID, afterLogoutSessionID));
        assertEquals("logged out", "Guest", driver.findElement(By.id("username")).getText());
    }

}
