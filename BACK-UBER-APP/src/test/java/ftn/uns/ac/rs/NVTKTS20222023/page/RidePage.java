package ftn.uns.ac.rs.NVTKTS20222023.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.time.Duration;
import java.util.List;

public class RidePage {

    private WebDriver driver;

    @FindBy(how = How.ID, using = "exampleInputName")
    private WebElement nameField;

    @FindBy(how = How.ID, using = "exampleFormControlSelect1")
    private WebElement petsSelectField;

    @FindBy(how = How.ID, using = "exampleFormControlSelect2")
    private WebElement babySelectField;

    @FindBy(how = How.ID, using = "exampleFormControlSelect3")
    private WebElement carTypeSelectField;

    @FindBy(how = How.ID, using = "exampleFormControlSelect4")
    private WebElement minutesSelectField;

    @FindBy(how = How.ID, using = "exampleInputUser")
    private WebElement usernameField;

    @FindBy(how = How.ID, using = "add-user-button")
    private WebElement addUserButton;

    @FindBy(how = How.ID, using = "calculate-button")
    private WebElement calculateButton;

    @FindBy(how = How.ID, using = "ride-button")
    private WebElement rideButton;

    @FindBy(how = How.ID, using = "logout-button")
    private WebElement logoutButton;

    @FindBy(how = How.ID, using = "notification-link")
    private WebElement notificationLink;

    @FindBy(how = How.ID, using = "mapica")
    private WebElement map;

    public RidePage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);

        // Refresh the page
        driver.navigate().refresh();

        PageFactory.initElements(driver, this);
    }

    public void enterForm(String name, String pets , String baby , String carType , String minutes , List<String> usernames) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Wait for the URL to be updated to the expected value
        wait.until(ExpectedConditions.urlToBe("http://localhost:4200/citizen-home"));

        nameField.clear();

        nameField.sendKeys(name);

        // Create a Select object
        Select petsDropdown = new Select(petsSelectField);

        // Select an option by visible text
        petsDropdown.selectByVisibleText(pets);

        // Create a Select object
        Select babyDropdown = new Select(babySelectField);

        // Select an option by visible text
        babyDropdown.selectByVisibleText(baby);

        // Create a Select object
        Select carTypeDropdown = new Select(carTypeSelectField);

        // Select an option by visible text
        carTypeDropdown.selectByVisibleText(carType);

        // Create a Select object
        Select minutesDropdown = new Select(minutesSelectField);

        // Select an option by visible text
        minutesDropdown.selectByVisibleText(minutes);

        for(String username:usernames){

            usernameField.clear();

            usernameField.sendKeys(username);

            addUserButton.click();

        }

    }

    public void addRoutes(){

        Actions actions = new Actions(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(map));
//        actions.moveToElement(map).click();

        // Scroll to the map element
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", map);

        // Perform the click at the specified pixel location
        actions.moveByOffset(920, 380).click().perform();
        // Perform the click at the specified pixel location
        actions.moveByOffset(20, 10).click().perform();
        // Perform the click at the specified pixel location
        actions.moveByOffset(10, 10).click().perform();

    }

    public void clickCalculateButton() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", calculateButton);
        jsExecutor.executeScript("arguments[0].click();", calculateButton);
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                jsExecutor.executeScript("arguments[0].click();", calculateButton);
                WebElement totalPriceElement = driver.findElement(By.id("total-price"));
                return totalPriceElement.isDisplayed();
            }
        });

    }

    public void clickRideButton() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", rideButton);
//        WebElement rideButton = driver.findElement(By.id("ride-button"));
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.visibilityOf(map));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(rideButton)).click();

//        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", rideButton);

//        jsExecutor.executeScript("arguments[0].click();", rideButton);

    }

    public void logout(){
        logoutButton.click();
    }

    public void notificationLink(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(notificationLink)).click();

//        notificationLink.click();
    }

    public boolean isWrongdata() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ride-wrong")));

        return element.isDisplayed();
    }

    public boolean isResetPassworUrl(){
        return driver.getCurrentUrl().contains("reset-password");
    }

}
