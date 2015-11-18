package bg.proxiad.demo.championship.front;

import bg.proxiad.demo.championship.logic.GroupingService;
import bg.proxiad.demo.championship.logic.ParticipantService;
import bg.proxiad.demo.championship.jsonbeans.GroupReceiver;
import bg.proxiad.demo.championship.jsonbeans.StatusResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/ajax")
public class AjaxController {

    @Autowired
    ParticipantService participantService;

    @Autowired
    GroupingService groupingService;

    @Autowired
    ServletContext context;

    @RequestMapping(value = "/rearrangePlayers", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    StatusResponse rearrangePlayers(@RequestBody List<GroupReceiver> playerDistributions) {
        StatusResponse statusResponse = new StatusResponse();
        statusResponse.setStatus("OK");

        try {
            groupingService.reassignPlayers(playerDistributions);
        } catch (Exception e) {
            statusResponse.setStatus("FAIL");
            statusResponse.setData(e.getMessage());
        }

        return statusResponse;
    }

    // AJAX method
    @RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    StatusResponse uploadPhoto(@RequestParam("file") MultipartFile file, ServletRequest request) throws IOException {
        StatusResponse statusResponse = new StatusResponse();
        Random rn = new Random();
        String name = rn.nextInt(99999) + "_" + file.getOriginalFilename();

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(
                        context.getRealPath(context.getContextPath() + File.separator + ".." + File.separator + "img")
                        + File.separator + name)));
                stream.write(bytes);
                stream.close();

                statusResponse.setStatus("OK");
                statusResponse.setData(name);
            } catch (Exception e) {
                statusResponse.setStatus("FAIL");
                statusResponse.setData("You failed to upload " + name + " => " + e.getMessage());
            }
        } else {
            statusResponse.setStatus("FAIL");
            statusResponse.setData("A non-empty file is  required");
        }

        return statusResponse;
    }

    @RequestMapping(value = "/test")
    public @ResponseBody
    String selenium2Example() {
        StringBuilder response = new StringBuilder();

        WebDriver driver = new ChromeDriver();
        driver.get("http://www.google.com");

        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("Cheese!");
        element.submit();

        response.append("Page title is: " + driver.getTitle());

        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("cheese!");
            }
        });

        response.append("Page title is: " + driver.getTitle());

        driver.quit();

        return response.toString();
    }

    @RequestMapping(value = "/isGuest")
    public @ResponseBody
    String checkIfGuestOnLogin(HttpServletRequest request) {
        StringBuilder response = new StringBuilder();
        
        // In this case "localhost:8080/championship" !NB url scheme is missing
        String baseURL = request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        
        WebDriver driver = new ChromeDriver();
        driver.get(baseURL);

        WebElement element = driver.findElement(By.id("username"));

        if ("Guest".equals(element.getText())) {
            response.append("WORKS");
        } else {
            response.append("FAILS");
        }

        driver.quit();

        return response.toString();
    }

    @RequestMapping(value = "/isIntruderBlocked")
    public @ResponseBody
    String checkIfAuthFiltersWork(HttpServletRequest request) {
        StringBuilder response = new StringBuilder();
        
        // In this case "localhost:8080/championship" !NB url scheme is missing
        String baseURL = request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

        WebDriver driver = new ChromeDriver();
        driver.get(baseURL + "/app/groups");
        
        Boolean isFound = driver.findElements(By.className("form-signin")).size() > 0;
        
        if (isFound) {
            response.append("WORKS");
        } else {
            response.append("FAILS");
        }

        driver.quit();

        return response.toString();
    }
    
    @RequestMapping(value = "/isUserCreated")
    public @ResponseBody String checkIfUserIsShownInListAfterCreation(HttpServletRequest request) {
        StringBuilder response = new StringBuilder();
        
        // In this case "localhost:8080/championship" !NB url scheme is missing
        String baseURL = request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        
        WebDriver driver = new FirefoxDriver();
        
        driver.get(baseURL + "/app/users/create");
        
        
        
        return response.toString();
    }
    
    
}
