package bg.proxiad.demo.championship.front;

import java.util.Objects;
import java.util.Random;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChangePasswordIT {

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
    public void changePassword() {
        // Create a temp user to work with
        driver.get("http://localhost:8080/championship/app/users/create");
        driver.findElement(By.id("emailInput")).sendKeys(email);
        driver.findElement(By.id("nameInput")).sendKeys(username);
        driver.findElement(By.id("passwordInput")).sendKeys(password);
        driver.findElement(By.id("userForm")).submit();
        
        WebElement changePasswordBtn = (WebElement) ((JavascriptExecutor)driver).executeScript("return $('tr:last td:last a:first')[0]");
        driver.get(changePasswordBtn.getAttribute("href"));
        
        // Assert that all 3 fields have errors
        driver.findElement(By.id("userForm")).submit();
        assertEquals("errors everywhere", 3, driver.findElements(By.className("has-error")).size());
        
        // Test old password field : with incorrect password
        driver.findElement(By.id("oldPasswordInput")).sendKeys("123456");
        driver.findElement(By.id("userForm")).submit();
        assertEquals("incorrect oldPassword", 1, driver.findElements(By.id("oldPassword.errors")).size());
        
        // Test old password field: no errors
        driver.findElement(By.id("oldPasswordInput")).clear();
        driver.findElement(By.id("oldPasswordInput")).sendKeys(password);
        driver.findElement(By.id("userForm")).submit();
        assertEquals("correct oldPassword", 0, driver.findElements(By.id("oldPassword.errors")).size());
        
        // Test different new passwords
        driver.findElement(By.id("newPasswordInput")).sendKeys("something");
        driver.findElement(By.id("repeatPasswordInput")).sendKeys("somethingelse");
        driver.findElement(By.id("userForm")).submit();
        assertEquals("different new passwords", 1, driver.findElements(By.id("repeatPassword.errors")).size());
        
        // Success scenario
        driver.findElement(By.id("oldPasswordInput")).clear();
        driver.findElement(By.id("newPasswordInput")).clear();
        driver.findElement(By.id("repeatPasswordInput")).clear();
        driver.findElement(By.id("oldPasswordInput")).sendKeys(password);
        driver.findElement(By.id("newPasswordInput")).sendKeys("newpassword");
        driver.findElement(By.id("repeatPasswordInput")).sendKeys("newpassword");
        driver.findElement(By.id("userForm")).submit();
        
        WebElement myDynamicElement = (new WebDriverWait(driver, 2))
            .until(ExpectedConditions.presenceOfElementLocated(By.className("alert-success")));
        
        assertTrue(!Objects.equals(myDynamicElement, null));
        
        // Delete temp user
        WebElement deleteBtn = (WebElement) ((JavascriptExecutor)driver).executeScript("return $('tr:last td:last a:last')[0]");
        driver.get(deleteBtn.getAttribute("href"));
    }
    
}
