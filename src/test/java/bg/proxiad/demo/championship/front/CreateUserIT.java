package bg.proxiad.demo.championship.front;

import java.util.Random;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CreateUserIT {

    private static ChromeDriver driver;
    private static String username;
    private static String email;
    private static String password;

    @BeforeClass
    public static void prepareTestEnvironment() {
        driver = new ChromeDriver();
        
        Random rnd = new Random();
        username = common.Utils.randomString("abcdefghijklmnopqrstuvwxyz", 1 + rnd.nextInt(19));
        email = common.Utils.randomString("abcdefghijklmnopqrstuvwxyz", 1 + rnd.nextInt(19)) + "@"
                    + common.Utils.randomString("abcdefghijklmnopqrstuvwxyz", 1 + rnd.nextInt(9)) + ".com";
        
        password = common.Utils.randomString("abcdefghijklmnopqrstuvwxyz", 1 + rnd.nextInt(19));
    }

    @AfterClass
    public static void closeBrowser() {
        driver.quit();
    }
        
    @Test
    public void createUser() {
        driver.get("http://localhost:8080/championship/app/users/create");
        
        // Submit form without data
        driver.findElement(By.id("userForm")).submit();
        
        // Assert that all 3 fields have errors
        assertEquals("errors everywhere", 3, driver.findElements(By.className("has-error")).size());
        
        // Test email : invalid email
        driver.findElement(By.id("emailInput")).sendKeys("asdqwe");
        driver.findElement(By.id("userForm")).submit();
        assertEquals("invalid email",1, driver.findElements(By.id("email.errors")).size());
        driver.findElement(By.id("emailInput")).clear();
        
        // Test email : no errors 
        driver.findElement(By.id("emailInput")).sendKeys(email);
        driver.findElement(By.id("userForm")).submit();
        assertEquals("no errors on email", 0, driver.findElements(By.id("email.errors")).size());
        driver.findElement(By.id("emailInput")).clear();
        
        // Test name : no errors 
        driver.findElement(By.id("nameInput")).sendKeys(username);
        driver.findElement(By.id("userForm")).submit();
        assertEquals("no errors on name", 0, driver.findElements(By.id("name.errors")).size());
        driver.findElement(By.id("nameInput")).clear();
        
        // Test password : no errors
        driver.findElement(By.id("passwordInput")).sendKeys(password);
        driver.findElement(By.id("userForm")).submit();
        assertEquals("no errors on password", 0, driver.findElements(By.id("password.errors")).size());
        driver.findElement(By.id("passwordInput")).clear();
        
        // Success scenario: Fill in and submit the form
        driver.findElement(By.id("emailInput")).sendKeys(email);
        driver.findElement(By.id("nameInput")).sendKeys(username);
        driver.findElement(By.id("passwordInput")).sendKeys(password);
        driver.findElement(By.id("userForm")).submit();
        
        // Check if email is created
        assertTrue(driver.findElements(By.className("alert-success")).size() > 0);
        assertTrue(driver.findElementsByLinkText(email).size() > 0);
        
        // Find the newly added user and delete him
        WebElement deleteBtn = (WebElement) ((JavascriptExecutor)driver).executeScript("return $('tr:last td:last a:last')[0]");
        driver.get(deleteBtn.getAttribute("href"));
    }
}
