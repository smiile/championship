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

public class UpdateUserIT {

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
    public void updateUser() {
        // Create a temp user to work with
        driver.get("http://localhost:8080/championship/app/users/create");
        driver.findElement(By.id("emailInput")).sendKeys(email);
        driver.findElement(By.id("nameInput")).sendKeys(username);
        driver.findElement(By.id("passwordInput")).sendKeys(password);
        driver.findElement(By.id("userForm")).submit();
        
        WebElement updateBtn = (WebElement) ((JavascriptExecutor)driver).executeScript("return $('tr:last td:last a:nth-of-type(2)')[0]");
        driver.get(updateBtn.getAttribute("href"));
        
        // Update both fields
        String updatedEmailValue = "noonewouldusethis@ever.com";
        String updatedNameValue = "To Be Deleted";
        
        driver.findElement(By.id("emailInput")).clear();
        driver.findElement(By.id("emailInput")).sendKeys(updatedEmailValue);
        
        driver.findElement(By.id("nameInput")).clear();
        driver.findElement(By.id("nameInput")).sendKeys(updatedNameValue);
        
        driver.findElement(By.id("userForm")).submit();
        
        // Check if they're updated properly
        WebElement updatedName = (WebElement) ((JavascriptExecutor)driver).executeScript("return $('tr:last td:first')[0]");
        assertEquals(updatedNameValue, updatedName.getText());
        
        WebElement updatedEmail = (WebElement) ((JavascriptExecutor)driver).executeScript("return $('tr:last td:nth-of-type(2) a')[0]");
        assertEquals(updatedEmailValue, updatedEmail.getText());
        
        // Delete temp user
        WebElement deleteBtn = (WebElement) ((JavascriptExecutor)driver).executeScript("return $('tr:last td:last a:last')[0]");
        driver.get(deleteBtn.getAttribute("href"));
    }
    
    
}
