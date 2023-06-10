package ftn.uns.ac.rs.NVTKTS20222023.test;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.MethodName.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GooglePageTest {

    WebDriver driver;

    WebDriver privateDriver;

    @BeforeAll
    public void initialize(){
        System.setProperty("webdriver.gecko.driver" , "geckodriver.exe");
        System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox\\firefox.exe");

        driver = new FirefoxDriver();

        // Create the FirefoxOptions with private mode
        FirefoxOptions privateOptions = new FirefoxOptions();
        privateOptions.addArguments("-private");

        // Create the FirefoxDriver with private mode options
        privateDriver = new FirefoxDriver(privateOptions);


    }


    @Test
    public void driverTest(){
//        driver.get("https://www.google.com");

        // Use the private browser instance for testing
//        privateDriver.get("https://www.google.com");
//        privateDriver.get("http://localhost:4200/login");

    }

}
