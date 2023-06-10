package ftn.uns.ac.rs.NVTKTS20222023.test;

import ftn.uns.ac.rs.NVTKTS20222023.page.ForgotPage;
import ftn.uns.ac.rs.NVTKTS20222023.page.GooglePage;
import ftn.uns.ac.rs.NVTKTS20222023.page.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginPageTest {

    private WebDriver driver;

    private LoginPage loginPage;

    private ForgotPage forgotPage;

    private GooglePage googlePage;

    @BeforeEach
    public void setup() {
        // Set up Chrome driver
        System.setProperty("webdriver.gecko.driver" , "geckodriver.exe");

        System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox\\firefox.exe");

        driver = new FirefoxDriver();

        loginPage = new LoginPage(driver);

        forgotPage = new ForgotPage(driver);

        googlePage = new GooglePage(driver);
    }

    @Test
    public void testLoginButton() {

        loginPage.get();

        loginPage.enterCredentials("pera","asd");

        loginPage.clickLoginButton();

        //WRONG CREDENTIALS... TRY AGAIN
        if(loginPage.isWrongCredentials()){

            loginPage.enterCredentials("c2","asd");

            loginPage.clickLoginButton();

            assertEquals(loginPage.isHomeUrl() , true);
        }

    }

    @Test
    public void testForgotButton() {

        loginPage.get();

        loginPage.clickForgotButton();

        forgotPage.enterForm("c55","asr" , "asr" , "CITIZEN");

        forgotPage.clickResetButton();

        loginPage.enterCredentials("c55","asrr");

        loginPage.clickLoginButton();
        //WRONG CREDENTIALS... TRY AGAIN
        if(loginPage.isWrongCredentials()){

            loginPage.enterCredentials("c55","asr");

            loginPage.clickLoginButton();

            assertEquals(loginPage.isHomeUrl() , true);
        }

    }

    @Test
    public void testForgotButtonWrongUsername() {

        loginPage.get();

        loginPage.clickForgotButton();

        forgotPage.enterForm("c87","asr" , "asr" , "CITIZEN");

        forgotPage.clickResetButton();

        if(forgotPage.isResetPassworUrl()){

            forgotPage.enterForm("c55","asm" , "asm" , "CITIZEN");

            forgotPage.clickResetButton();

        }

        loginPage.enterCredentials("c55","asrr");

        loginPage.clickLoginButton();
        //WRONG CREDENTIALS... TRY AGAIN
        if(loginPage.isWrongCredentials()){

            loginPage.enterCredentials("c55","asm");

            loginPage.clickLoginButton();

            assertEquals(loginPage.isHomeUrl() , true);
        }

    }

//    @Test
//    public void testOAuthButton() {
//
//        loginPage.get();
//
//        loginPage.clickGoogleButton();
//
//        googlePage.clickVaskeUser();
//
//        assertEquals(loginPage.isHomeUrl() , true);
//
//    }

//    @AfterEach
//    public void cleanup() {
//        // Quit the driver
//        driver.quit();
//    }

}
