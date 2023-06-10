package ftn.uns.ac.rs.NVTKTS20222023.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private static final String PAGE_URL = "http://localhost:4200/login";

    private WebDriver driver;

    @FindBy(how = How.ID, using = "login-button")
    private WebElement loginButton;

    @FindBy(how = How.ID, using = "exampleInputUsername")
    private WebElement usernameField;

    @FindBy(how = How.ID, using = "exampleInputPassword")
    private WebElement passwordField;

    @FindBy(how = How.ID, using = "forgot-button")
    private WebElement forgotButton;

    @FindBy(how = How.ID, using = "google-button")
    private WebElement googleButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    public void get() {
        driver.get(PAGE_URL);
    }

    public void enterCredentials(String username, String password) {

        usernameField.clear();

        usernameField.sendKeys(username);

        passwordField.clear();

        passwordField.sendKeys(password);

    }

    public void clickLoginButton() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();

//        loginButton.click();

    }

    public boolean isWrongCredentials() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("wrong")));

        return element.isDisplayed();
    }

    public void clickForgotButton() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(forgotButton)).click();

//        forgotButton.click();

    }

    public void clickGoogleButton() {

        googleButton.click();

    }

    public boolean isHomeUrl(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Wait for the URL to be updated to the expected value
        wait.until(ExpectedConditions.urlToBe("http://localhost:4200/citizen-home"));

        return driver.getCurrentUrl().contains("home");
    }

    public boolean isLoginUrl(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Wait for the URL to be updated to the expected value
        wait.until(ExpectedConditions.urlToBe("http://localhost:4200/login"));

        return driver.getCurrentUrl().contains("login");
    }

}