package ftn.uns.ac.rs.NVTKTS20222023.test;

import ftn.uns.ac.rs.NVTKTS20222023.page.DriverPage;
import ftn.uns.ac.rs.NVTKTS20222023.page.ForgotPage;
import ftn.uns.ac.rs.NVTKTS20222023.page.GooglePage;
import ftn.uns.ac.rs.NVTKTS20222023.page.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DriverPageTest {

    private WebDriver driver;

    private LoginPage loginPage;

    private DriverPage driverPage;


    @BeforeEach
    public void setup() {
        // Set up Chrome driver
        System.setProperty("webdriver.gecko.driver" , "geckodriver.exe");

        System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox\\firefox.exe");

        driver = new FirefoxDriver();

        loginPage = new LoginPage(driver);

        driverPage = new DriverPage(driver);

    }

    @Test
    public void testRideButtonWrongPets() {

        driver.manage().window().maximize();

        loginPage.get();

        loginPage.enterCredentials("dr5","asd");

        loginPage.clickLoginButton();

        driverPage.notificationLink();

        driverPage.clickFinishButton();

        assertEquals(true,driverPage.isDataEqual());

    }

}
