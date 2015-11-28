/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.proxiad.demo.championship.selenium;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author DSD
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({bg.proxiad.demo.championship.selenium.UpdateUserIT.class, bg.proxiad.demo.championship.selenium.AuthenticationControllerIT.class, bg.proxiad.demo.championship.selenium.ChangePasswordIT.class, bg.proxiad.demo.championship.selenium.CreateUserIT.class})
public class TestSuite {
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        // Windows-only
        System.setProperty("webdriver.chrome.driver", "C:\\tools\\chromedriver.exe");
        
    }
    
}
