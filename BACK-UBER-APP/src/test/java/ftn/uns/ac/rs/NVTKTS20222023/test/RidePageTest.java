package ftn.uns.ac.rs.NVTKTS20222023.test;

import ftn.uns.ac.rs.NVTKTS20222023.page.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RidePageTest {

    private WebDriver driver;

    private LoginPage loginPage;

    private RidePage ridePage;

    private NotificationPage notificationPage;

    @BeforeEach
    public void setup() {
        // Set up Chrome driver
        System.setProperty("webdriver.gecko.driver" , "geckodriver.exe");

        System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox\\firefox.exe");

        driver = new FirefoxDriver();

        loginPage = new LoginPage(driver);

        ridePage = new RidePage(driver);

        notificationPage = new NotificationPage(driver);
    }

    @Test
    public void testRideButton() {

        driver.manage().window().maximize();

        loginPage.get();

        loginPage.enterCredentials("c2","asd");

        loginPage.clickLoginButton();

        ridePage.enterForm("Posao voznja" , "True" , "True", "VAN" , "0" , Arrays.asList("c33"));

        ridePage.addRoutes();

        ridePage.clickCalculateButton();

        ridePage.clickRideButton();

        notificationPage.clickNotificationButton();

        ridePage.logout();

        loginPage.enterCredentials("c33" , "asd");

        loginPage.clickLoginButton();

        ridePage.notificationLink();

        ridePage.logout();

        assertEquals(true,loginPage.isLoginUrl());

    }

    @Test
    public void testRideButtonWrongPets() {

        driver.manage().window().maximize();

        loginPage.get();

        loginPage.enterCredentials("c2","asd");

        loginPage.clickLoginButton();

        ridePage.enterForm("Posao voznja" , "False" , "True", "MINI" , "0" , Arrays.asList());

        ridePage.clickRideButton();

        assertEquals(true,ridePage.isWrongdata());

    }

    @Test
    public void testRideButtonWrongBaby() {

        driver.manage().window().maximize();

        loginPage.get();

        loginPage.enterCredentials("c2","asd");

        loginPage.clickLoginButton();

        ridePage.enterForm("Posao voznja" , "True" , "False", "CAR" , "0" , Arrays.asList());

        ridePage.clickRideButton();

        assertEquals(true,ridePage.isWrongdata());

    }

    @Test
    public void testRideButtonWrongCarType() {

        driver.manage().window().maximize();

        loginPage.get();

        loginPage.enterCredentials("c2","asd");

        loginPage.clickLoginButton();

        ridePage.enterForm("Posao voznja" , "True" , "True", "MINI" , "0" , Arrays.asList());

        ridePage.clickRideButton();

        assertEquals(true,ridePage.isWrongdata());

    }

    @Test
    public void testRideButtonWrongUser() {

        driver.manage().window().maximize();

        loginPage.get();

        loginPage.enterCredentials("c2","asd");

        loginPage.clickLoginButton();

        ridePage.enterForm("Posao voznja" , "True" , "True", "VAN" , "0" , Arrays.asList("c543"));

        ridePage.clickRideButton();

        assertEquals(true,ridePage.isWrongdata());

    }

    @Test
    public void testRideButtonWrongRoute() {

        driver.manage().window().maximize();

        loginPage.get();

        loginPage.enterCredentials("c2","asd");

        loginPage.clickLoginButton();

        ridePage.enterForm("Posao voznja" , "True" , "True", "VAN" , "0" , Arrays.asList("c33"));

        ridePage.clickRideButton();

        assertEquals(true,ridePage.isWrongdata());

    }

    @Test
    public void testRideButtonWrongData() {

        driver.manage().window().maximize();

        loginPage.get();

        loginPage.enterCredentials("c2","asd");

        loginPage.clickLoginButton();

        ridePage.enterForm("Posao voznja" , "False" , "False", "MINI" , "0" , Arrays.asList("m65"));

        ridePage.clickRideButton();

        assertEquals(true,ridePage.isWrongdata());

    }

}
